package tripleo.elijah_durable_congenial.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.impl.VariableStatementImpl;
import tripleo.elijah_durable_congenial.nextgen.rosetta.FakeRosetta;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DED;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.IntegerIA;
import tripleo.elijah_durable_congenial.stages.instructions.VariableTableType;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;

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
									if (!rt.getResolved().getClassOf().getGenericPart().isEmpty()) {
										b.setNonGenericTypeName(a.getTypeName()); // TODO might be wrong
									}
									final OS_Type c = rt.getResolved();
									dof_uc(vte, c);
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
		final ClassStatement classStatement = aOSType.getClassOf();
		assert classStatement.getGenericPart().isEmpty();

		FakeRosetta
				.registerClassInvocation(classStatement, deduceTypes2)
				.on(ci2 -> {
					//TODO 24j2(!) what is getGenType?

					aVte.getGenType().setResolved(aOSType); // README assuming OS_Type cannot represent namespaces
					aVte.getGenType().setCi(ci2);

					ci2.resolvePromise().then(aVte::resolveTypeToClass);
				})
				.triggerOn(null);
	}
}
