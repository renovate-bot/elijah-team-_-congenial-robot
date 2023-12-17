package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_VariableTableEntry;
import tripleo.elijah.stages.deduce.tastic.FCA_Stop;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;

public class DT_Function {
	private final BaseEvaFunction generatedFunction;
	private final DeduceTypes2 d;
	private boolean            deducedAlready;
	private DeducePhase __state_dp;

	public DT_Function(final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aD) {
		generatedFunction = aGeneratedFunction;
		d                 = aD;
	}

	public void fix_tables() {
		var evaFunction = generatedFunction;

		for (VariableTableEntry variableTableEntry : evaFunction.vte_list) {
			variableTableEntry._fix_table(d, evaFunction);
		}
		for (IdentTableEntry identTableEntry : evaFunction.idte_list) {
			identTableEntry._fix_table(d, evaFunction);
		}
		for (TypeTableEntry typeTableEntry : evaFunction.tte_list) {
			typeTableEntry._fix_table(d, evaFunction);
		}
		for (ProcTableEntry procTableEntry : evaFunction.prte_list) {
			procTableEntry._fix_table(d, evaFunction);
		}
	}

	public void log() {
		final var             fd         = generatedFunction.getFD();
		final ProcTableEntry  pte        = generatedFunction.fi.pte;
		final @NotNull String pte_string = d.getPTEString(pte);
		d.LOG.info("** deduce_generated_function " + fd.name() + " " + pte_string);//+" "+((OS_Container)((FunctionDef)fd).getParent()).name());
	}

	public void loop() {
		if (deducedAlready) return;
		else deducedAlready = true;

		final var     fd     = generatedFunction.getFD();
		final Context fd_ctx = fd.getContext();

		for (final @NotNull Instruction instruction : generatedFunction.instructions()) {
			final Context context = generatedFunction.getContextFromPC(instruction.getIndex());
//			LOG.info("8006 " + instruction);
			switch (instruction.getName()) {
			case E:
				d.onEnterFunction(generatedFunction, context);
				break;
			case X:
				d.onExitFunction(generatedFunction, fd_ctx, context);
				break;
			case ES, NOP, XS, AGNT, JNE, JL, JMP, RET, YIELD, TRY, PC:
				break;
			case AGN:
				d.do_assign_normal(generatedFunction, fd_ctx, instruction, context);
				break;
			case AGNK:
				d.do_agnk(generatedFunction, instruction);
				break;
			case AGNF:
				d.LOG.info("292 Encountered AGNF");
				break;
			case JE:
				d.LOG.info("296 Encountered JE");
				break;
			case CALL:
				d.do_call(generatedFunction, fd, instruction, context);
				break;
			case CALLS:
				d.do_calls(generatedFunction, fd_ctx, instruction);
				break;
			case CAST_TO:
				// README potentialType info is already added by MatchConditional
				break;
			case DECL:
				// README for GenerateC, etc: marks the spot where a declaration should go. Wouldn't be necessary if we had proper Range's
				break;
			case IS_A:
				d.implement_is_a(generatedFunction, instruction);
				break;
			case CONSTRUCT:
				implement_construct(instruction, context);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + instruction.getName());
			}
		}
	}

	private void implement_construct(final @NotNull Instruction instruction, final Context context) {
		final @NotNull Implement_construct ic = d.newImplement_construct(generatedFunction, instruction);
		try {
			ic.action(context);
		} catch (FCA_Stop e) {
			// TODO 11/06 remove debug
			e.printStackTrace();
		}
	}

	public void __post_vte_list_001() {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			var de3_vte = vte.getDeduceElement3();
			de3_vte.__post_vte_list_001();
		}
	}

	public void __post_vte_list_002(final Context aFdCtx) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			if (vte.getType().getAttached() == null) {
				int potential_size = vte.potentialTypes().size();
				if (potential_size == 1)
					vte.getType().setAttached(d.getPotentialTypesVte(vte).get(0).getAttached());
				else if (potential_size > 1) {
					// TODO Check type compatibility
					d.LOG.err("703 " + vte.getName() + " " + vte.potentialTypes());
					d._errSink().reportDiagnostic(new CantDecideType(vte, vte.potentialTypes()));
				} else {
					// potential_size == 0
					// Result is handled by phase.typeDecideds, self is always valid
					if (/*vte.getName() != null &&*/ !(vte.getVtt() == VariableTableType.RESULT || vte.getVtt() == VariableTableType.SELF))
						d._errSink().reportDiagnostic(new CantDecideType(vte, vte.potentialTypes()));
				}
			} else if (vte.getVtt() == VariableTableType.RESULT) {

				int           state    = 0;
				final OS_Type attached = vte.getType().getAttached();
				if (attached.getType() == OS_Type.Type.USER) {
					try {
						// FIXME 07/03 HACK

						if (attached.getTypeName() instanceof RegularTypeName rtn) {
							if (rtn.getName().equals("Unit")) {
								state = 1;
							}
						}


						if (state == 0)
							vte.getType().setAttached(d.resolve_type(attached, aFdCtx));
					} catch (ResolveError aResolveError) {
						aResolveError.printStackTrace();
						assert false;
					}
				}
			}
		}
	}

	public void __post_deferred_calls(final Context aFdCtx) {
		//
		// NOW CALCULATE DEFERRED CALLS
		//

		for (final Integer deferred_call : generatedFunction.deferred_calls) {
			final Instruction instruction = generatedFunction.getInstruction(deferred_call);

			final int                     i1  = DeduceTypes2.to_int(instruction.getArg(0));
			final InstructionArgument     i2  = (instruction.getArg(1));
			final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);

			//generatedFunction.deferred_calls.remove(deferred_call);

			DeduceTypes2.Implement_Calls_ ic = d.new Implement_Calls_(generatedFunction, aFdCtx, i2, fn1, instruction.getIndex());
			ic.action();
		}
	}

	public boolean deducedAlready() {
		return deducedAlready;
	}

	public void __post_constructor(final @NotNull DeducePhase aDeducePhase) {
		final @NotNull EvaConstructor aEvaConstructor  = (EvaConstructor) generatedFunction;

		for (@NotNull IdentTableEntry identTableEntry : aEvaConstructor.idte_list) {
			if (identTableEntry.getResolvedElement() instanceof final @NotNull VariableStatementImpl vs) {
				OS_Element el  = vs.getParent().getParent();
				OS_Element el2 = aEvaConstructor.getFD().getParent();
				if (el != el2) {
					if (el instanceof ClassStatement || el instanceof NamespaceStatement)
						// NOTE there is no concept of gf here
						aDeducePhase.registerResolvedVariable(identTableEntry, el, vs.getName());
				}
			}
		}

		@Nullable InstructionArgument result_index = aEvaConstructor.vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = aEvaConstructor.vte_lookup("Value");
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
			if (vte.isResolvedYet()) {
				GenType b = vte.getGenType();
				OS_Type     a = vte.getType().getAttached();

				DeduceElement3_VariableTableEntry de3_vte = vte.getDeduceElement3();

				if (a != null) {
					// see resolve_function_return_type
					switch (a.getType()) {
					case USER_CLASS:
						de3_vte.__dof_uc(a);
						break;
					case USER:
						b.setTypeName(a);
						try {
							@NotNull GenType rt = d.resolve_type(a, a.getTypeName().getContext());
							if (rt.getResolved() != null && rt.getResolved().getType() == OS_Type.Type.USER_CLASS) {
								if (rt.getResolved().getClassOf().getGenericPart().size() > 0)
									b.setNonGenericTypeName(a.getTypeName()); // TODO might be wrong
								de3_vte.__dof_uc(rt.getResolved());
							}
						} catch (ResolveError aResolveError) {
							d._errSink().reportDiagnostic(aResolveError);
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
		//		aDeducePhase.addFunction(aGeneratedConstructor, (FunctionDef) aGeneratedConstructor.getFD()); // TODO do we need this?
	}

	public BaseEvaFunction state_generatedFunction() {
		return generatedFunction;
	}

	public DeducePhase state_deducePhase() {
		return __state_dp;
	}

	public void set__state_dp(final DeducePhase a__state_dp) {
		__state_dp = a__state_dp;
	}

	class E<I> {
		private final DT_Function dtFunction;

		public E(final DT_Function aDTFunction) {
			this.dtFunction = aDTFunction;
		}

		public void applyState(final DeduceTypes2.DT_State aState, final Times.T aTimes, final CHAIN aCHAIN) {
			aState.applyState(aTimes, aCHAIN, this.dtFunction);
		}
	}

	public E<IdentTableEntry> eachIdent() {
		return new E<IdentTableEntry>(this);
	}
}
