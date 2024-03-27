package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.WorkList;

public class DefaultClassGenerator implements IClassGenerator {

	private final          ICodeRegistrar cr;
	private final          DeducePhase    deducePhase;
	private final @NotNull WorkList       wl;

	public DefaultClassGenerator(DeducePhase aDeducePhase) {
		// given
		deducePhase = aDeducePhase;

		// transitive
		cr          = deducePhase.codeRegistrar;

		// creating
		wl = new WorkList();
	}

	@Override
	public ICodeRegistrar getCodeRegistrar() {
		return cr;
	}

	@Override
	public @NotNull GeneratedClasses getGeneratedClasses() {
		return deducePhase.generatedClasses;
	}

	@Override
	public FunctionInvocation newFunctionInvocation(final FunctionDef fd,
													final ProcTableEntry pte,
													final @NotNull ClassInvocation ci) {
		final @NotNull FunctionInvocation fi = deducePhase.newFunctionInvocation(fd, pte, ci);
		return fi;
	}

	@Override
	public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement cs, final String className) {
		final ClassInvocation ci = deducePhase.registerClassInvocation(cs, className, new NULL_DeduceTypes2());
		return ci;
	}

	@Override
	public void submitGenerateClass(final @NotNull ClassInvocation ci, final GenerateFunctions gf) {
		wl.addJob(new WlGenerateClass(gf, ci, deducePhase.generatedClasses, cr));
	}

	@Override
	public void submitGenerateFunction(final @NotNull FunctionInvocation fi, final GenerateFunctions gf) {
		wl.addJob(new WlGenerateFunction(gf, fi, cr));
	}

	@Override
	public @NotNull WorkList wl() {
		return wl;
	}
}
