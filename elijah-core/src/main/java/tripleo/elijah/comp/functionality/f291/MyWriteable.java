package tripleo.elijah.comp.functionality.f291;

//import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;

import java.util.Collection;
import java.util.List;

// TODO 09/04 Duplication madness
public class MyWriteable implements Writeable {
	private final          Collection<EG_Statement> value;
	private final          EOT_FileNameProvider     filename;
	private final @NotNull List<EG_Statement>       list;
	private final @NotNull EG_SequenceStatement            statement;
	//@Getter
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
	public EOT_FileNameProvider getFilenameProvider() {
		return this.filename;
	}
}
