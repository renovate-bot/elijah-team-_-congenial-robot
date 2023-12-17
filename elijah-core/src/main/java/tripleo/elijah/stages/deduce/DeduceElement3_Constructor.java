package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.deduce.post_bytecode.DED;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;

public class DeduceElement3_Constructor implements IDeduceElement3 {
	private final EvaConstructor evaConstructor;
	private final DeduceTypes2   deduceTypes2;

	public DeduceElement3_Constructor(final EvaConstructor aEvaConstructor, final DeduceTypes2 aDeduceTypes2) {
		evaConstructor = aEvaConstructor;
		deduceTypes2   = aDeduceTypes2;
	}

	@Override
	public DED elementDiscriminator() {
		return null;
	}

	@Override
	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public OS_Element getPrincipal() {
		return evaConstructor.getFD();
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		return evaConstructor;
	}

	@Override
	public GenType genType() {
		return null;
	}

	@Override
	public void resolve(final Context aContext, final DeduceTypes2 dt2) {

	}

	@Override
	public DeduceElement3_Kind kind() {
		return null;
	}

	@Override
	public void resolve(final IdentIA aIdentIA, final Context aContext, final FoundElement aFoundElement) {

	}

	public void __post_deduce_generated_function_base(final @NotNull DeducePhase aDeducePhase) {
		for (@NotNull IdentTableEntry identTableEntry : evaConstructor.idte_list) {
			if (identTableEntry.getResolvedElement() instanceof final @NotNull VariableStatementImpl vs) {
				final @NotNull DeduceElement3_IdentTableEntry de3_ite = identTableEntry.getDeduceElement3(deduceTypes2(), evaConstructor);
				de3_ite.stipulate_ResolvedVariable(aDeducePhase, identTableEntry, vs, evaConstructor);
			}
		}
		{
			final @NotNull EvaConstructor gf = evaConstructor;

			@Nullable InstructionArgument result_index = gf.vte_lookup("Result");
			if (result_index == null) {
				// if there is no Result, there should be Value
				result_index = gf.vte_lookup("Value");
				// but Value might be passed in. If it is, discard value
				if (result_index != null) {
					@NotNull VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
					if (vte.getVtt() != VariableTableType.RESULT) {
						result_index = null;
					}
				}
			}
			if (result_index != null) {
				@NotNull VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
				if (vte.resolvedType() == null) {
					GenType b = vte.getGenType();
					OS_Type a = vte.getType().getAttached();
					if (a != null) {
						// see resolve_function_return_type
						switch (a.getType()) {
						case USER_CLASS:
							dof_uc(vte, a);
							break;
						case USER:
							b.setTypeName(a);
							try {
								@NotNull GenType rt = deduceTypes2.resolve_type(a, a.getTypeName().getContext());
								if (rt.getResolved() != null && rt.getResolved().getType() == OS_Type.Type.USER_CLASS) {
									if (rt.getResolved().getClassOf().getGenericPart().size() > 0)
										b.setNonGenericTypeName(a.getTypeName()); // TODO might be wrong
									dof_uc(vte, rt.getResolved());
								}
							} catch (ResolveError aResolveError) {
								deduceTypes2._errSink().reportDiagnostic(aResolveError);
							}
							break;
						default:
							// TODO do nothing for now
							int y3 = 2;
							break;
						}
					} /*else
							throw new NotImplementedException();*/
				}
			}
		}
//		aDeducePhase.addFunction(aGeneratedConstructor, (FunctionDef) aGeneratedConstructor.getFD()); // TODO do we need this?
	}

	private void dof_uc(@NotNull VariableTableEntry aVte, @NotNull OS_Type aOSType) {
		// we really want a ci from somewhere
		assert aOSType.getClassOf().getGenericPart().size() == 0;

		@Nullable ClassInvocation ci = new ClassInvocation(aOSType.getClassOf(), null, new ReadySupplier_1<>(deduceTypes2));
		ci = deduceTypes2.phase.registerClassInvocation(ci);

		aVte.getGenType().setResolved(aOSType); // README assuming OS_Type cannot represent namespaces
		aVte.getGenType().setCi(ci);

		ci.resolvePromise().done(aVte::resolveTypeToClass);
	}
}
