package tripleo.elijah.pre_world;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn.IClassGenerator;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.small.ES_Symbol;

import java.util.Objects;

import static tripleo.elijah.util.Helpers.List_of;

public class Mirror_MainClassEntryPoint implements Mirror_EntryPoint {
	private final MainClassEntryPoint mcep;
	private final ModuleThing         mt;
	private final GenerateFunctions   gf;

	public Mirror_MainClassEntryPoint(final MainClassEntryPoint aMcep, final @NotNull ModuleThing aMt, final GenerateFunctions aGenerateFunctions) {
		mcep = aMcep;
		mt   = aMt;
		gf   = aGenerateFunctions;
	}

	@Override
	public void generate(final IClassGenerator dcg) {
		@NotNull final ClassStatement   cs = mcep.getKlass();
		final FunctionDef               f  = mcep.getMainFunction();
		@Nullable final ClassInvocation ci = dcg.registerClassInvocation(cs, null);
		dcg.submitGenerateClass(Objects.requireNonNull(ci), gf);

		final @NotNull FunctionInvocation fi = dcg.newFunctionInvocation(f, null, ci);
//				fi.setPhase(phase);
		dcg.submitGenerateFunction(fi, gf);

		mt.describe(new ModuleThing.GeneralDescription(new ES_Symbol("MainClassEntryPoint"), List_of(ci, fi)));
	}
}
