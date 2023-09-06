package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.CD_CompilationRunnerStart;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.nextgen.query.Mode;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {

	@Override
	public void start(final @NotNull CompilerInstructions aCompilerInstructions,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		final @NotNull CompilationRunner             cr  = crState.runner();
		final @NotNull IPipelineAccess               pa  = crState.ca.getCompilation().getCompilationEnclosure().getPipelineAccess();
		final @NotNull Compilation.CompilationConfig cfg = crState.ca.getCompilation().cfg();

		final CompilerBeginning beginning = new CompilerBeginning(cr._accessCompilation(), aCompilerInstructions, pa.getCompilerInput(), cr.progressSink, cfg);

		___start(crState, beginning, out);
	}

	protected void ___start(final @NotNull CR_State crState,
							final @NotNull CompilerBeginning beginning,
							final @NotNull CB_Output out) {
		if (crState.started) {
			boolean should_never_happen = true;
			assert should_never_happen;
		} else {
			crState.started = true;
		}

		final CR_FindCIs              f1 = crState.runner().cr_find_cis;
		final CR_ProcessInitialAction f2 = new CR_ProcessInitialAction(beginning);
		final CR_AlmostComplete       f3 = new CR_AlmostComplete();
		final CR_RunBetterAction      f4 = new CR_RunBetterAction();

		final @NotNull List<CR_Action>     crActionList       = List_of(f1, f2, f3, f4);
		final @NotNull List<Operation<Ok>> crActionResultList = new ArrayList<>(crActionList.size());

		for (final CR_Action each : crActionList) {
			each.attach(crState.runner());
			var res = each.execute(crState, out);
			crActionResultList.add(res);
		}

		for (int i = 0; i < crActionResultList.size(); i++) {
			var                      action           = crActionList.get(i);
			final Operation<Ok> booleanOperation = crActionResultList.get(i);

			final String s = ("5959 %s %b").formatted(action.name(), (booleanOperation.mode() == Mode.SUCCESS));
			out.logProgress(5959, s);
		}
	}
}
