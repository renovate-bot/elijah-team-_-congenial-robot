package tripleo.elijah.stages.gen_c.c_ast1;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Helpers;

import java.util.List;

public class C_ProcedureCall {
	private List<String> args;
	private String       targetName;

	public @NotNull String getString() {

		final String str = targetName +
				"(" +
				Helpers.String_join(", ", args) + // FIXME
				")";
		return str;
	}

	public void setArgs(final List<String> aArgs) {
		args = aArgs;
	}

	public void setTargetName(final String aS) {
		targetName = aS;
	}
}
