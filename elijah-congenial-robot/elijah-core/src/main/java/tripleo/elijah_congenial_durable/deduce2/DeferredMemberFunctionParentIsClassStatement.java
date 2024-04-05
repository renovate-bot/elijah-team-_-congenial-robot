package tripleo.elijah_congenial_durable.deduce2;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.stages.deduce.*;
import tripleo.elijah_durable_congenial.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;

import java.util.Map;
import java.util.function.Function;

/*static*/ public class DeferredMemberFunctionParentIsClassStatement {
	private final DeducePhase            deducePhase;
	private final DeferredMemberFunction deferredMemberFunction;
	private final IInvocation            invocation;
	private final OS_Element             parent;

	public DeferredMemberFunctionParentIsClassStatement(final DeducePhase aDeducePhase, final DeferredMemberFunction aDeferredMemberFunction, final IInvocation aInvocation) {
		deducePhase            = aDeducePhase;
		deferredMemberFunction = aDeferredMemberFunction;
		invocation             = aInvocation;
		parent                 = deferredMemberFunction.getParent();//.getParent().getParent();
	}

	public void action() {
		if (invocation instanceof ClassInvocation) {
			final Eventual<EvaClass> evaClassEventual = ((ClassInvocation) invocation).resolvePromise();
			evaClassEventual.then(this::defaultAction);
		} else if (invocation instanceof NamespaceInvocation) {
			final @NotNull Eventual<EvaNamespace> evaNamespaceEventual = ((NamespaceInvocation) invocation).resolvePromise();
			evaNamespaceEventual.then(this::defaultAction);
		}
	}

	<T extends EvaNode> void defaultAction(final T result) {
		final OS_Element p = deferredMemberFunction.getParent();

		if (p instanceof final DeduceTypes2.@NotNull OS_SpecialVariable specialVariable) {
			onSpecialVariable(specialVariable);
			int y = 2;
		} else if (p instanceof ClassStatement) {
			final Function<EvaNode, Map<FunctionDef, EvaFunction>> x = getFunctionMap(result);

			// once again we need EvaFunction, not FunctionDef
			// we seem to have it below, but there can be multiple
			// specializations of each function

			final EvaFunction gf = x.apply(result).get(deferredMemberFunction.getFunctionDef());
			if (gf != null) {
				deferredMemberFunction.externalRefDeferred().resolve(gf);
				gf.typePromise().then(new DoneCallback<GenType>() {
					@Override
					public void onDone(final GenType result) {
						deferredMemberFunction.typeResolved().resolve(result);
					}
				});
			}
		} else
			throw new IllegalStateException("unknown parent");
	}

	public void onSpecialVariable(final DeduceTypes2.@NotNull OS_SpecialVariable aSpecialVariable) {
		final DeduceLocalVariable.MemberInvocation mi = aSpecialVariable.memberInvocation;

		switch (mi.getRole()) {
		case INHERITED:
			final FunctionInvocation functionInvocation = deferredMemberFunction.functionInvocation();
			functionInvocation.generatePromise().
					then(new DoneCallback<BaseEvaFunction>() {
						@Override
						public void onDone(final @NotNull BaseEvaFunction gf) {
							deferredMemberFunction.externalRefDeferred().resolve(gf);
							gf.typePromise().
									then(new DoneCallback<GenType>() {
										@Override
										public void onDone(final GenType result) {
											deferredMemberFunction.typeResolved().resolve(result);
										}
									});
						}
					});
			break;
		case DIRECT:
			if (invocation instanceof NamespaceInvocation)
				assert false;
			else {
				final ClassInvocation classInvocation = (ClassInvocation) invocation;
				classInvocation.resolvePromise().
						then(new DoneCallback<EvaClass>() {
							@Override
							public void onDone(final @NotNull EvaClass element_generated) {
								// once again we need EvaFunction, not FunctionDef
								// we seem to have it below, but there can be multiple
								// specializations of each function
								final EvaFunction gf = element_generated.functionMap.get(deferredMemberFunction.getFunctionDef());
								deferredMemberFunction.externalRefDeferred().resolve(gf);
								gf.typePromise().
										then(new DoneCallback<GenType>() {
											@Override
											public void onDone(final GenType result) {
												deferredMemberFunction.typeResolved().resolve(result);
											}
										});
							}
						});
			}
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + mi.getRole());
		}
	}

	@NotNull
	private <T extends EvaNode> Function<EvaNode, Map<FunctionDef, EvaFunction>> getFunctionMap(final T result) {
		final Function<EvaNode, Map<FunctionDef, EvaFunction>> x;
		if (result instanceof EvaNamespace)
			x = deducePhase._inj().new_GetFunctionMapNamespace();
		else if (result instanceof EvaClass)
			x = deducePhase._inj().new_GetFunctionMapClass();
		else
			throw new NotImplementedException();
		return x;
	}

	public static class GetFunctionMapClass implements Function<EvaNode, Map<FunctionDef, EvaFunction>> {
		@Override
		public Map<FunctionDef, EvaFunction> apply(final @NotNull EvaNode aClass) {
			return ((EvaClass) aClass).functionMap;
		}
	}

	public static class GetFunctionMapNamespace implements Function<EvaNode, Map<FunctionDef, EvaFunction>> {
		@Override
		public Map<FunctionDef, EvaFunction> apply(final @NotNull EvaNode aNamespace) {
			return ((EvaNamespace) aNamespace).functionMap;
		}
	}
}
