package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_generic.GenerateResult.TY;
import tripleo.util.buffer.Buffer;

public class NG_OutputNamespaceStatement implements NG_OutputStatement {
	private final          Buffer    buf;
	private final          TY        ty;
	private final @NotNull NG_OutDep moduleDependency;

	public NG_OutputNamespaceStatement(final Buffer aBuf, final TY aTY, final @NotNull OS_Module aM) {
		buf              = aBuf;
		ty               = aTY;
		moduleDependency = new NG_OutDep(aM);
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputNamespaceStatement");
	}

	@Override
	public String getText() {
		return buf.getText();
	}

	@Override
	public TY getTy() {
		return ty;
	}

	@Override
	@NotNull
	public EIT_ModuleInput getModuleInput() {
		var m = moduleDependency().getModule();

		final EIT_ModuleInput moduleInput = new EIT_ModuleInput(m, m.getCompilation());
		return moduleInput;
	}

	public @NotNull NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
