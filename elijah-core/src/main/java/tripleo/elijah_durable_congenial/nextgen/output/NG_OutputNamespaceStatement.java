package tripleo.elijah_durable_congenial.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult.TY;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.util.buffer.Buffer;

public class NG_OutputNamespaceStatement implements NG_OutputStatement {
	private final          Buffer            buf;
	private final          GenerateResult.TY ty;
	private final @NotNull NG_OutDep         moduleDependency;

	public NG_OutputNamespaceStatement(final Buffer aBuf, final GenerateResult.TY aTY, final @NotNull OS_Module aM) {
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
	public GenerateResult.TY getTy() {
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
