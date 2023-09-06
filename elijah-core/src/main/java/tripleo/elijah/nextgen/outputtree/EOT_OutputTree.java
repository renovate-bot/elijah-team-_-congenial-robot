package tripleo.elijah.nextgen.outputtree;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author tripleo
 */
public class EOT_OutputTree {
	private final List<EOT_OutputFile> list = new ArrayList<>();

	public void add(final @NotNull EOT_OutputFile aOff) {
		//05/18 System.err.printf("[add] %s %s%n", aOff.getFilename(), aOff.getStatementSequence().getText());

		list.add(aOff);
	}

	public void addAll(final @NotNull List<EOT_OutputFile> aLeof) {
		list.addAll(aLeof);
	}

	public void recompute() {
		// TODO big wtf
		final Multimap<String, EOT_OutputFile> mmfn = ArrayListMultimap.create();
		for (EOT_OutputFile outputFile : list) {
			mmfn.put(outputFile.getFilename(), outputFile);
		}

		for (Map.Entry<String, Collection<EOT_OutputFile>> sto : mmfn.asMap().entrySet()) {
			var tt = sto.getValue();
			if (tt.size() > 1) {
				list.removeAll(tt);

				var model = tt.iterator().next();

				var type     = model.getType();
				var inputs   = model.getInputs(); // FIXME can possibly contain others
				var filename = sto.getKey();

				final List<EG_Statement> list2 = _EOT_OutputTree__Utils._extractStatementSequenceFromAllOutputFiles(tt);

				var seq = new EG_SequenceStatement(new EG_Naming("redone"), list2);
				var ofn = new EOT_OutputFile(inputs, filename, type, seq);

				list.add(ofn);
			}
		}
	}

	public @NotNull List<EOT_OutputFile> getList() {
		return list;
	}

	public void set(final @NotNull List<EOT_OutputFile> aLeof) {
		list.addAll(aLeof);
	}
}
