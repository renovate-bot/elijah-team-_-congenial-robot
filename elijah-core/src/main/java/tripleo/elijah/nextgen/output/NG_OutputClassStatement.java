package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_generic.GenerateResult.TY;
import tripleo.elijah.util.BufferTabbedOutputStream;

public class NG_OutputClassStatement implements NG_OutputStatement {
	private final          String                   text;
	private final          TY                       ty;
	private final @NotNull NG_OutDep                moduleDependency;
	private final @NotNull BufferTabbedOutputStream __tos;

	public NG_OutputClassStatement(final @NotNull BufferTabbedOutputStream aText, final @NotNull OS_Module aModuleDependency, final TY aTy) {
		__tos = aText;

		text = aText.getBuffer().getText();
		ty   = aTy;

		moduleDependency = new NG_OutDep(aModuleDependency);
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputClassStatement");
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public @NotNull TY getTy() {
		return ty;
	}

	@Override
	@NotNull
	public EIT_ModuleInput getModuleInput() {
		var m = moduleDependency().module();

		final EIT_ModuleInput moduleInput = new EIT_ModuleInput(m, m.getCompilation());
		return moduleInput;
	}

	public NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
