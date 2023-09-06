package tripleo.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.gen_fn.GenType;

public class SGTA_SetResolvedClass implements setup_GenType_Action {

	private final ClassStatement classStatement;

	@Contract(pure = true)
	public SGTA_SetResolvedClass(final ClassStatement aClassStatement) {
		classStatement = aClassStatement;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setResolved(classStatement.getOS_Type());
	}
}
