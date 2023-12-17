package tripleo.elijah.stages.write_stage.pipeline_impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.nextgen.output.NG_OutputStatement;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategyC;

import java.util.*;

public enum WPIS_GenerateOutputsFinalizer {
	;

	static void _finalizeItems(final @NotNull List<NG_OutputItem> aItms,
							   final List<NG_OutputRequest> aOrs1,
							   final OutputStrategyC aOutputStrategyC,
							   final ICompilationAccess2 ca) {
		var cot  = ca.getOutputTree();
		var aCot = cot;
		for (NG_OutputItem o : aItms) {
			var oxs = o.getOutputs();
			for (NG_OutputStatement ox : oxs) {
				GenerateResult.TY oxt = ox.getTy();
				String            oxb = ox.getText();

				EOT_OutputFile.FileNameProvider s = o.outName(aOutputStrategyC, oxt);

				var or = new NG_OutputRequest(s, ox, ox, o);
				aOrs1.add(or);
			}
		}

		final Multimap<NG_OutputRequest, EG_Statement> mfss2 = ArrayListMultimap.create();
		final Multimap<String, NG_OutputRequest>       mfsm  = ArrayListMultimap.create();

		// TODO fix this... (maybe, can't see it yet)
		for (NG_OutputRequest or : aOrs1) {
			mfsm.put(or.fileName().getFilename(), or);
		}

		for (Map.Entry<String, Collection<NG_OutputRequest>> entry : mfsm.asMap().entrySet()) {
			final Collection<NG_OutputRequest> value = entry.getValue();
			final NG_OutputRequest             or2   = value.iterator().next();

			EG_Statement new_st = recombine(value);

			mfss2.put(or2, new_st);
		}

		final List<WPIS_GenerateOutputs.Writeable> writables = new ArrayList<>();

		for (Map.Entry<NG_OutputRequest, Collection<EG_Statement>> entry : mfss2.asMap().entrySet()) {
			writables.add(new WPIS_GenerateOutputs.MyWriteable(Pair.of(entry.getKey(), entry.getValue())));
		}

		for (WPIS_GenerateOutputs.Writeable writable : writables) {
			final EOT_OutputFile.FileNameProvider filename   = writable.getFilenameProvider();
			final EG_Statement                    statement0 = writable.statement();
			final List<EG_Statement>              list2      = relist3(statement0);
			final EG_Statement                    statement;

			if (filename.getFilename().endsWith(".h")) {
				final String uuid = "elinc_%s".formatted(UUID.randomUUID().toString().replace('-', '_'));

				final EG_Statement b = EG_Statement.of("#ifndef %s\n#define %s 1\n\n".formatted(uuid, uuid), EX_Explanation.withMessage("Header file prefix"));
				final EG_Statement e = EG_Statement.of("\n#endif\n", EX_Explanation.withMessage("Header file postfix"));

				if (false) {
					//final List<EG_Statement> list3 = new ArrayList<>(list2.size() + 2);
					//list3.add(b);
					//list3.addAll(list2);
					//list3.add(e);
					//
					//statement = new EG_SequenceStatement(new EG_Naming("relist3"), list3);
				} else {
					statement = new EG_CompoundStatement(b, e, list2, false, EX_Explanation.withMessage("djksaldnsajlkda"));
				}
			} else {
				statement = statement0;
			}

			final EOT_OutputFile off = ca.createOutputFile(writable.inputs(), filename, EOT_OutputType.SOURCES, statement);
			ca.addCodeOutput(filename, off, true);
		}
	}

	private static @NotNull EG_Statement recombine(final @NotNull Collection<NG_OutputRequest> aValue) {
		final List<EG_Statement> list = aValue.stream()
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
}
