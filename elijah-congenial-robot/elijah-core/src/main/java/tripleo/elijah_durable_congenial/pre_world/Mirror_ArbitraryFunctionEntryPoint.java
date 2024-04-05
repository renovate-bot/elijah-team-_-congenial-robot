package tripleo.elijah_durable_congenial.pre_world;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.IClassGenerator;
import tripleo.elijah_durable_congenial.stages.inter.ModuleThing;
import tripleo.elijah_durable_congenial.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.small.ES_Symbol;

import java.util.Objects;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

public class Mirror_ArbitraryFunctionEntryPoint implements Mirror_EntryPoint {
	private final ArbitraryFunctionEntryPoint carrier;
	private final ModuleThing                 mt;
	private final GenerateFunctions           gf;

	public Mirror_ArbitraryFunctionEntryPoint(final ArbitraryFunctionEntryPoint aAfep, final @NotNull ModuleThing aMt, final GenerateFunctions aGenerateFunctions) {
		carrier = aAfep;
		mt      = aMt;
		gf   = aGenerateFunctions;
	}

	@Override
	public void generate(final @NotNull IClassGenerator dcg) {
		final ClassStatement            cs = (ClassStatement) carrier.getParent();
		final FunctionDef               f  = carrier.getFunction();
		@Nullable final ClassInvocation ci = dcg.registerClassInvocation(cs, null);

		dcg.submitGenerateClass(Objects.requireNonNull(ci), gf);

		final @NotNull FunctionInvocation fi = dcg.newFunctionInvocation(f, null, ci);
		//fi.setPhase(phase);
		dcg.submitGenerateFunction(fi, gf);

		mt.describe(new ModuleThing.GeneralDescription(new ES_Symbol("ArbitraryFunctionEntryPoint"), List_of(ci, fi)));
	}
}
