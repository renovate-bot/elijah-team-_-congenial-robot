package tripleo.elijah_durable_congenial.comp.i;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CB_Action {
	void execute();

	String name();

	@Nullable List<CB_OutputString> outputStrings();

}
