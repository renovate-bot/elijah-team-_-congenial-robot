package tripleo.elijah_durable_congenial.comp.functionality.f291;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_congenial.pipelines.write.NG_OutputRequest;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess2;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputItem;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputStatement;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.generate.OutputStrategyC;

import java.util.*;

public enum WPIS_GenerateOutputsFinalizer {
	;

	private static @NotNull EG_Statement recombine(final @NotNull Collection<NG_OutputRequest> aValue) {
		final List<EG_Statement> list =
				aValue.stream()
						.map(NG_OutputRequest::statement)
						.toList();

		return new EG_SequenceStatement(new EG_Naming("recombined"), list);
	}

	private static @NotNull List<EG_Statement> relist3(final EG_Statement sequence) {
		final List<EG_Statement> result = new ArrayList<EG_Statement>();

		if (sequence instanceof EG_SequenceStatement seqst) {
			result.addAll(seqst._list());
		} else {
			result.add(sequence);
		}

		return result;
	}

	public static void _finalizeItems(final List<NG_OutputItem> aItms,
									  final WPIS_GenerateOutputsFinaliation aF) {

		final ICompilationAccess2    compilationAccess2 = aF.aCompilationAccess2();
		final OutputStrategyC        outputStrategyC    = aF.aOutputStrategyC();
		final List<NG_OutputRequest> outputRequestList  = aF.aOutputRequestList();

		for (NG_OutputItem o : aItms) {
			var oxs = o.getOutputs();
			for (NG_OutputStatement ox : oxs) {
				final GenerateResult.TY oxt = ox.getTy();
				final String            oxb = ox.getText();
				final EOT_FileNameProvider s   = o.outName(outputStrategyC, oxt);

				var or = new NG_OutputRequest(s, ox, ox, o);
				outputRequestList.add(or);
			}
		}

		final Multimap<NG_OutputRequest, EG_Statement> mfss2 = ArrayListMultimap.create();
		final Multimap<String, NG_OutputRequest>       mfsm  = ArrayListMultimap.create();

		// TODO fix this... (maybe, can't see it yet)
		for (NG_OutputRequest or : outputRequestList) {
			mfsm.put(or.fileName().getFilename(), or);
		}

		for (Map.Entry<String, Collection<NG_OutputRequest>> entry : mfsm.asMap().entrySet()) {
			final Collection<NG_OutputRequest> value = entry.getValue();
			final NG_OutputRequest             or2   = value.iterator().next();

			EG_Statement new_st = recombine(value);

			mfss2.put(or2, new_st);
		}

		final List<Writeable> writables = new ArrayList<>();

		for (Map.Entry<NG_OutputRequest, Collection<EG_Statement>> entry : mfss2.asMap().entrySet()) {
			writables.add(new MyWriteable(Pair.of(entry.getKey(), entry.getValue())));
		}

		for (Writeable writable : writables) {
			final EOT_FileNameProvider filename   = writable.getFilenameProvider();
			final EG_Statement         statement0 = writable.statement();
			final List<EG_Statement>   list2      = relist3(statement0);
			final EG_Statement         statement;

			if (filename.getFilename().endsWith(".h")) {
				final String uuid = "elinc_%s".formatted(UUID.randomUUID().toString().replace('-', '_'));

				final EG_SingleStatement   b   = new EG_SingleStatement("#ifndef %s\n#define %s 1\n\n".formatted(uuid, uuid), EX_Explanation.withMessage("Header file prefix"));
				final EG_SingleStatement   e   = new EG_SingleStatement("\n#endif\n", EX_Explanation.withMessage("Header file postfix"));
				final EG_SequenceStatement m   = new EG_SequenceStatement(new EG_Naming("relist2"), list2);
				final EX_Explanation       msg = EX_Explanation.withMessage("djksaldnsajlkda");
				statement = new EG_CompoundStatement(b, e, m, false, msg);
			} else {
				statement = statement0;
			}

			// eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
			final EOT_OutputFile off = compilationAccess2.createOutputFile(writable.inputs(), filename, EOT_OutputType.SOURCES, statement);
			compilationAccess2.addCodeOutput(filename, off, true);
		}
	}

	class Stage1 {
		void add() {
		}

		void trigger() {
		}
	}
}
