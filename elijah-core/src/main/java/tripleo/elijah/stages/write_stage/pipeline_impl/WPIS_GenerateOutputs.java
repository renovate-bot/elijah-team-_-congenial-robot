package tripleo.elijah.stages.write_stage.pipeline_impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategy;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.util.Helpers;

import java.util.*;
import java.util.function.Consumer;

public class WPIS_GenerateOutputs implements WP_Indiviual_Step {
	private final List<NG_OutputRequest>   ors = new ArrayList<>();
	private       WritePipelineSharedState st;

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final WP_State_Control sc) {
		Preconditions.checkState(st.getGr() != null);
		Preconditions.checkState(st.sys != null);

		GenerateResult result = st.getGr();

		final SPrintStream sps = new SPrintStream();
		//DebugBuffersLogic.debug_buffers_logic(result, sps);

		final Default_WPIS_GenerateOutputs_Behavior_PrintDBLString printDBLString = new Default_WPIS_GenerateOutputs_Behavior_PrintDBLString();
		printDBLString.print(sps.getString());

		var cs = st.pa.getActiveClasses();
		var ns = st.pa.getActiveNamespaces();
		var fs = st.pa.getActiveFunctions();

		this.st = st;

		act0(st, result, cs, ns, fs);
	}

	private void act0(final @NotNull WritePipelineSharedState st, final GenerateResult result, final @NotNull List<EvaClass> cs, final @NotNull List<EvaNamespace> ns, final @NotNull List<BaseEvaFunction> fs) {
		final CP_Paths paths = st.c.paths();
		paths.signalCalculateFinishParse(); // TODO maybe move this 06/22

		final OutputItems itms = new OutputItems();

		act3(result, cs, ns, fs, itms);

		for (Amazing amazing : amazings) {
			amazing.run();
		}
	}

	private List<Amazing> amazings;

	private void act3(final GenerateResult result,
					  final @NotNull List<EvaClass> cs,
					  final @NotNull List<EvaNamespace> ns,
					  final @NotNull List<BaseEvaFunction> fs,
					  final @NotNull OutputItems itms) {
		final int totalCount = cs.size() + ns.size() + fs.size();
		itms.readyCount(totalCount); // looks like it should work, but also looks like it won't

		amazings = new ArrayList<>(totalCount);

		for (EvaClass c : cs) {
			final AmazingClass amazingClass = new AmazingClass(c, itms, st.pa);
			waitGenC(amazingClass.mod(), amazingClass::waitGenC);
			amazings.add(amazingClass);
		}
		for (BaseEvaFunction f : fs) {
			final AmazingFunction amazingFunction = new AmazingFunction(f, itms, result, st.pa);
			waitGenC(amazingFunction.mod(), amazingFunction::waitGenC);
			amazings.add(amazingFunction);
		}
		for (EvaNamespace n : ns) {
			final AmazingNamespace amazingNamespace = new AmazingNamespace(n, itms, st.pa);
			waitGenC(amazingNamespace.mod(), amazingNamespace::waitGenC);
			amazings.add(amazingNamespace);
		}
	}

	void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
		this.st.pa.waitGenC(mod, cb);
	}

	// TODO 09/04 Duplication madness
	private static class MyWritable implements Writable {
		final          Collection<EG_Statement> value;
		final          String                   filename;
		final @NotNull List<EG_Statement>       list;
		final @NotNull EG_SequenceStatement     statement;
		private final  NG_OutputRequest         outputRequest;

		public MyWritable(final Map.@NotNull Entry<NG_OutputRequest, Collection<EG_Statement>> aEntry) {
			this.outputRequest = aEntry.getKey();
			filename           = outputRequest.fileName();
			value              = aEntry.getValue();

			list = value.stream().toList();

			statement = new EG_SequenceStatement(new EG_Naming("writable-combined-file"), list);
		}

		@Override
		public String filename() {
			return filename;
		}

		@Override
		public EG_Statement statement() {
			return statement;
		}

		@Override
		public @NotNull List<EIT_Input> inputs() {
			if (outputRequest == null) {
				throw new IllegalStateException("shouldn't be here");
			}

			final EIT_ModuleInput moduleInput = outputRequest.outputStatment().getModuleInput();

			return Helpers.List_of(moduleInput);
		}

	}

	@FunctionalInterface
	public interface WPIS_GenerateOutputs_Behavior_PrintDBLString {
		void print(String sps);
	}

	interface Writable {
		String filename();

		EG_Statement statement();

		List<EIT_Input> inputs();
	}

	static class Default_WPIS_GenerateOutputs_Behavior_PrintDBLString implements WPIS_GenerateOutputs_Behavior_PrintDBLString {
		@Override
		public void print(final String sps) {
			System.err.println(sps);
		}
	}

	class OutputItems {
		final   OutputStrategy         osg             = st.sys.outputStrategyCreator.get();
		final   OutputStrategyC        outputStrategyC = new OutputStrategyC(osg);
		final   List<NG_OutputRequest> ors1            = new ArrayList<>();
		final   List<NG_OutputItem>    itms            = new ArrayList<>();
		private int                    _readyCount;
		private int                    _addTally;

		public void addItem(final NG_OutputItem aOutputItem) {
			itms.add(aOutputItem);

			++_addTally;
			if (_addTally == _readyCount) {
				for (NG_OutputItem o : itms) {
					var oxs = o.getOutputs();
					for (NG_OutputStatement ox : oxs) {
						GenerateResult.TY oxt = ox.getTy();
						String            oxb = ox.getText();

						EOT_OutputFile.FileNameProvider s = o.outName(outputStrategyC, oxt);

						var or = new NG_OutputRequest(
								s.getFilename(),
								ox,
								ox,
								o);
						ors1.add(or);
					}
				}

				final Multimap<NG_OutputRequest, EG_Statement> mfss = ArrayListMultimap.create();
				var                                            cot  = st.c.getOutputTree();

				// README combine output requests into file requests
				for (NG_OutputRequest or : ors1) {
					mfss.put(or, or.statement());
				}

				final List<Writable> writables = new ArrayList<>();

				for (Map.Entry<NG_OutputRequest, Collection<EG_Statement>> entry : mfss.asMap().entrySet()) {
					writables.add(new MyWritable(entry));
				}

				for (Writable writable : writables) {
					final String             filename   = writable.filename();
					final EG_Statement       statement0 = writable.statement();
					final List<EG_Statement> list2      = relist3(statement0);
					final EG_Statement       statement;

					if (filename.endsWith(".h")) {
						final String uuid = "elinc_%s".formatted(UUID.randomUUID().toString().replace('-', '_'));

						var b = EG_Statement.of("#ifndef %s\n#define %s 1\n\n".formatted(uuid, uuid), EX_Explanation.withMessage("Header file prefix"));

						final List<EG_Statement> list3 = new ArrayList<>(list2.size() + 2);
						list3.add(b);
						list3.addAll(list2);
						final EG_Statement e = EG_Statement.of("\n#endif\n", EX_Explanation.withMessage("Header file postfix"));
						list3.add(e);
						statement = new EG_SequenceStatement(new EG_Naming("relist3"), list3);
					} else {
						statement = statement0;
					}

					var off = new EOT_OutputFile(writable.inputs(), filename, EOT_OutputType.SOURCES, statement);
					cot.add(off);
				}
			}
		}

		private static @NotNull List<EG_Statement> relist3(final EG_Statement sequence) {
			var llll = new ArrayList<EG_Statement>();

			if (sequence instanceof EG_SequenceStatement seqst) {
				llll.addAll(seqst._list());
			} else {
				llll.add(sequence);
			}

			return llll;
		}

		public void readyCount(final int aI) {
			this._readyCount = aI;
		}
	}
}
