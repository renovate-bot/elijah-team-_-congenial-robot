package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.contexts.ClassContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.DR_Type;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation2;

import java.util.function.Supplier;

public interface GenType {
	static @Nullable GenType makeFromOSType(@NotNull OS_Type aVt, ClassInvocation.@NotNull CI_GenericPart aGenericPart, @NotNull DeduceTypes2 dt2, DeducePhase phase, @NotNull ElLog aLOG, @NotNull ErrSink errSink) {
		return makeGenTypeFromOSType(aVt, aGenericPart, aLOG, errSink, dt2, phase);
	}

	static @Nullable GenType makeGenTypeFromOSType(@NotNull OS_Type aType,
												   ClassInvocation.@NotNull CI_GenericPart aGenericPart,
												   @NotNull ElLog aLOG,
												   @NotNull ErrSink errSink, @NotNull DeduceTypes2 dt2, DeducePhase phase) {
		GenType gt = new GenTypeImpl();
		gt.setTypeName(aType);
		if (aType.getType() == OS_Type.Type.USER) {
			final TypeName tn1 = aType.getTypeName();
			if (tn1.isNull()) return null; // TODO Unknown, needs to resolve somewhere

			assert tn1 instanceof NormalTypeName;
			final NormalTypeName       tn  = (NormalTypeName) tn1;
			final LookupResultList     lrl = tn.getContext().lookup(tn.getName());
			final @Nullable OS_Element el  = lrl.chooseBest(null);

			DeduceTypes2.ProcessElement.processElement(el, new DeduceTypes2.IElementProcessor() {
				private void __hasElement__typeNameElement(final ClassContext.@NotNull OS_TypeNameElement typeNameElement) {
					assert aGenericPart != null;

					final OS_Type x = aGenericPart.get(typeNameElement.getTypeName());

					switch (x.getType()) {
					case USER_CLASS:
						final @Nullable ClassStatement classStatement1 = x.getClassOf(); // always a ClassStatement

						assert classStatement1 != null;

						// TODO test next 4 (3) lines are copies of above
						gt.setResolved(classStatement1.getOS_Type());
						break;
					case USER:
						final NormalTypeName tn2 = (NormalTypeName) x.getTypeName();
						final LookupResultList lrl2 = tn.getContext().lookup(tn2.getName());
						final @Nullable OS_Element el2 = lrl2.chooseBest(null);

						// TODO test next 4 lines are copies of above
						if (el2 instanceof final @NotNull ClassStatement classStatement2) {
							gt.setResolved(classStatement2.getOS_Type());
						} else
							throw new NotImplementedException();
						break;
					}
				}

				@Override
				public void elementIsNull() {
					NotImplementedException.raise();
				}

				private void gotResolved(final @NotNull GenType gt) {
					if (gt.getResolved().getClassOf().getGenericPart().size() != 0) {
						//throw new AssertionError();
						aLOG.info("149 non-generic type " + tn1);
					}
					gt.genCI(null, dt2, errSink, phase); // TODO aGenericPart
					assert gt.getCi() != null;
					genNodeForGenType2(gt);
				}

				@Override
				public void hasElement(final OS_Element el) {
					final Operation2<OS_Element> best1 = preprocess(el);
					if (best1.mode() == Mode.FAILURE) {
						aLOG.err("152 Can't resolve Alias statement " + el);
						errSink.reportDiagnostic(best1.failure());
						return;
					}

					final OS_Element best = best1.success();

					switch (DecideElObjectType.getElObjectType(best)) {
					case CLASS:
						final ClassStatement classStatement = (ClassStatement) best;
						gt.setResolved(classStatement.getOS_Type());
						break;
					case TYPE_NAME_ELEMENT:
						final ClassContext.OS_TypeNameElement typeNameElement = (ClassContext.OS_TypeNameElement) best;
						__hasElement__typeNameElement(typeNameElement);
						break;
					default:
						aLOG.err("143 " + el);
						throw new NotImplementedException();
					}

					if (gt.getResolved() != null)
						gotResolved(gt);
					else {
						int y = 2; //05/22
					}
				}

				private @NotNull Operation2<@NotNull OS_Element> preprocess(final OS_Element el) {
					@Nullable OS_Element best = el;
					try {
						while (best instanceof AliasStatement) {
							best = DeduceLookupUtils._resolveAlias2((AliasStatement) best, dt2);
						}
						assert best != null;
						return Operation2.success(best);
					} catch (ResolveError aResolveError) {
						return Operation2.failure(aResolveError);
					}
				}
			});
		} else
			throw new AssertionError("Not a USER Type");
		return gt;
	}

	OS_Type getResolved();

	void setResolved(OS_Type aOSType);

	void setTypeName(OS_Type aType);

	/**
	 * Sets the node for a GenType, invocation must already be set
	 *
	 * @param aGenType the GenType to modify.
	 */
	static void genNodeForGenType2(@NotNull GenType aGenType) {
//		assert aGenType.nonGenericTypeName != null;

		final IInvocation invocation = aGenType.getCi();

		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(new DoneCallback<EvaNamespace>() {
				@Override
				public void onDone(final EvaNamespace result) {
					aGenType.setNode(result);
				}
			});
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(final EvaClass result) {
					aGenType.setNode(result);
				}
			});
		} else
			throw new IllegalStateException("invalid invocation");
	}

	void setNode(EvaNode aResult);

	IInvocation getCi();

	//@ensures Result.ci != null
	//@ensures ResultgetResolved() != null
	static @NotNull GenType of(NamespaceStatement aNamespaceStatement, @NotNull Supplier<NamespaceInvocation> aNamespaceInvocationSupplier) {
		final GenType genType = new GenTypeImpl(aNamespaceStatement);

		final NamespaceInvocation nsi = aNamespaceInvocationSupplier.get();

		genType.setCi(nsi);

		return genType;
	}

	void setCi(IInvocation aInvocation);

	ClassInvocation genCI(TypeName aGenericTypeName,
						  DeduceTypes2 deduceTypes2,
						  ErrSink errSink,
						  DeducePhase phase);

	String asString();

	void copy(GenType aGenType);

	@Contract(value = "null -> false", pure = true)
	@Override
	boolean equals(Object aO);

	@Override
	int hashCode();

	void genCIForGenType2(DeduceTypes2 deduceTypes2);

	void genCIForGenType2__(DeduceTypes2 aDeduceTypes2);

	boolean isNull();

	void set(@NotNull OS_Type aType);

	EvaNode getNode();

	NamespaceStatement getResolvedn();

	OS_Type getTypeName();

	TypeName getNonGenericTypeName();

	void setFunctionInvocation(FunctionInvocation aFi);

	FunctionInvocation getFunctionInvocation();

	void setNonGenericTypeName(@NotNull TypeName typeName);

	void setResolvedn(NamespaceStatement parent);

	void setDrType(DR_Type aDrType);
}
