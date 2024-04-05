package tripleo.elijah_congenial_durable.deduce.rules;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.comp.diagnostic.ExceptionDiagnostic;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;
import tripleo.elijah_durable_congenial.lang.i.TypeName;
import tripleo.elijah_durable_congenial.lang.i.VariableStatement;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.DiagnosticException;
import tripleo.elijah_durable_congenial.stages.deduce.ResolveError;
import tripleo.elijah_durable_congenial.stages.deduce.Unnamed_ITE_Resolver1;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public class UIR1_Rule implements DT_Rule {
	private final Unnamed_ITE_Resolver1 unnamedIteResolver;
	private final OS_Type               aTy;
	private DeduceTypes2 _g_dt2;

	public UIR1_Rule(final @NotNull Unnamed_ITE_Resolver1 	aUnnamedIteResolver1,
					 final @NotNull OS_Type 				aOSType,
					 final @NotNull VariableStatement 		aVariableStatement,
					 final @NotNull DeduceTypes2 			aDeduceTypes2) {

		unnamedIteResolver = aUnnamedIteResolver1;
		_g_dt2             = aDeduceTypes2;

		@NotNull TypeName aTypeName = aVariableStatement.typeName();
		if ((!aTypeName.isNull())) {
			aTy = aOSType;
		} else {
			throw new AssertionError();
		}

	}

	public @NotNull Operation<GenType> product() {
		//noinspection unchecked
		final @NotNull Operation<GenType>[] os = new Operation[1];

		product(aGenType    -> os[0] = Operation.success(aGenType),
				aDiagnostic -> os[0] = Operation.failure(new DiagnosticException(aDiagnostic)));

		assert os[0] != null;
		return os[0];
	}

	public @NotNull void product(DoneCallback<GenType> cb) {
		product(cb, new FailCallback<Diagnostic>() {
			@Override
			public void onFail(final Diagnostic aDiagnostic) {
				// noop
			}
		});
	}

	public void product(@NotNull DoneCallback<GenType> cb, @Nullable FailCallback<Diagnostic> fcb) {
		GenType ty2;
		assert aTy.getTypeName() != null;
		try {
			ty2 = _g_dt2.resolve_type(aTy, aTy.getTypeName().getContext());
			cb.onDone(ty2);
			return;
		} catch (ResolveError aE) {
			if (fcb != null) fcb.onFail(new ExceptionDiagnostic(aE));
			return;
		}
	}

	@Override
	public @NotNull String ruleName() {
		return "Unnamed_ITE_Resolver1::getTY2";
	}
}
