package tripleo.elijah.stages.write_stage.pipeline_impl;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategy;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.util.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class WPIS_GenerateOutputs implements WP_Indiviual_Step {
	private final List<NG_OutputRequest>   ors = new ArrayList<>();
	private       WritePipelineSharedState st;
	private       List<Amazing>            amazings;

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

	interface Writeable {
		String filename();

		EG_Statement statement();

		List<EIT_Input> inputs();

		EOT_OutputFile.FileNameProvider getFilenameProvider();
	}

	@FunctionalInterface
	public interface WPIS_GenerateOutputs_Behavior_PrintDBLString {
		void print(String sps);
	}

	// TODO 09/04 Duplication madness
	static class MyWriteable implements Writeable {
		private final          Collection<EG_Statement>        value;
		private final          EOT_OutputFile.FileNameProvider filename;
		private final @NotNull List<EG_Statement>              list;
		private final @NotNull EG_SequenceStatement            statement;
		@Getter
		private final          NG_OutputRequest                outputRequest;

		public MyWriteable(final @NotNull Pair<NG_OutputRequest, Collection<EG_Statement>> aEntry) {
			this.outputRequest = aEntry.getKey();
			filename           = outputRequest.fileName();
			value              = aEntry.getValue();

			list = value.stream().toList();

			statement = new EG_SequenceStatement(new EG_Naming("writable-combined-file"), list);
		}

		@Override
		public String filename() {
			return filename.getFilename();
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

		@Override
		public EOT_OutputFile.FileNameProvider getFilenameProvider() {
			return this.filename;
		}
	}

	static class Default_WPIS_GenerateOutputs_Behavior_PrintDBLString implements WPIS_GenerateOutputs_Behavior_PrintDBLString {
		@Override
		public void print(final String sps) {
			System.err.println(sps);
		}
	}

	class OutputItems {
		@Getter
		private final OutputStrategy         osg;
		@Getter
		private final List<NG_OutputRequest> outputRequestList;
		@Getter
		private final List<NG_OutputItem>    itms;
		private final OutputStrategyC        outputStrategyC;
		private       int                    _readyCount;
		private       int                    _addTally;

		public OutputItems() {
			osg               = st.sys.outputStrategyCreator.get();
			outputStrategyC   = new OutputStrategyC(osg);
			outputRequestList = new ArrayList<>();
			itms              = new ArrayList<>();
		}

		public void addItem(final NG_OutputItem aOutputItem) {
			itms.add(aOutputItem);

			++_addTally;

			if (_addTally == _readyCount) {
				WPIS_GenerateOutputsFinalizer._finalizeItems(itms, outputRequestList, outputStrategyC, st.c.getCompilationAccess2());
			}
		}

		public void readyCount(final int aI) {
			this._readyCount = aI;
		}
	}
}
