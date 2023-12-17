package tripleo.elijah.stages.deduce;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_Type;
import tripleo.elijah.stages.deduce.tastic.FCA_Stop;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.work.WorkList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Implement_construct {
	private final InstructionArgument expression;
	private final BaseEvaFunction     generatedFunction;
	private final DeduceTypes2        deduceTypes2;
	private final Instruction         instruction;

	private void _implement_construct_type(final @Nullable Constructable co,
										   final @Nullable String constructorName,
										   final @NotNull NormalTypeName aTyn1,
										   final @Nullable GenType aGenType) {
		final String          s      = aTyn1.getName();
		final ICH             ich    = _inj().new_ICH(aGenType, this);
		final ClassStatement  best   = ich.lookupTypeName(aTyn1, s);
		final ClassInvocation clsinv = ich.getClassInvocation(constructorName, aTyn1, aGenType, best);
		if (co != null) {
			genTypeCI_and_ResolveTypeToClass(co, clsinv);
		}
		pte.setClassInvocation(clsinv);
		pte.setResolvedElement(best);
		// set FunctionInvocation with pte args
		{
			@Nullable ConstructorDef cc = null;
			if (constructorName != null) {
				Collection<ConstructorDef> cs = best.getConstructors();
				for (@NotNull ConstructorDef c : cs) {
					if (c.name().sameName(constructorName)) {
						cc = c;
						break;
					}
				}
			}
			// TODO also check arguments
			{
				// TODO is cc ever null (default_constructor)
				if (cc == null) {
					//assert pte.getArgs().size() == 0;
					for (ClassItem item : best.getItems()) {
						if (item instanceof final @NotNull ConstructorDef constructorDef) {
							if (constructorDef.getArgs().size() == pte.getArgs().size()) {
								// TODO we now have to find a way to check arg matching of two different types
								//  of arglists. This is complicated by the fact that constructorDef doesn't have
								//  to specify the argument types and currently, pte args is underspecified

								// TODO this is explicitly wrong, but it works for now
								cc = constructorDef;
								break;
							}
						}
					}
				}
				// TODO do we still want to do this if cc is null?
				@NotNull FunctionInvocation fi = deduceTypes2.newFunctionInvocation(cc, pte, clsinv, deduceTypes2.phase);
				pte.setFunctionInvocation(fi);
			}
		}
	}

	private final @NotNull ProcTableEntry pte;

	public Implement_construct(final DeduceTypes2 aDeduceTypes2, BaseEvaFunction aGeneratedFunction, Instruction aInstruction) {
		deduceTypes2      = aDeduceTypes2;
		generatedFunction = aGeneratedFunction;
		instruction       = aInstruction;

		// README all these asserts are redundant, I know
		assert instruction.getName() == InstructionName.CONSTRUCT;
		assert instruction.getArg(0) instanceof ProcIA;

		final int pte_num = ((ProcIA) instruction.getArg(0)).index();
		pte = generatedFunction.getProcTableEntry(pte_num);

		expression = pte.expression_num;

		assert expression instanceof IntegerIA || expression instanceof IdentIA;
	}

	private void genTypeCI_and_ResolveTypeToClass(@NotNull final Constructable co, final @NotNull ClassInvocation aClsinv) {
		if (co instanceof final @Nullable IdentTableEntry idte3) {
			idte3.type.genTypeCI(aClsinv);
			aClsinv.resolvePromise().then(
					idte3::resolveTypeToClass);
		} else if (co instanceof final @NotNull VariableTableEntry vte) {
			vte.getType().genTypeCI(aClsinv);
			aClsinv.resolvePromise().then(
					vte::resolveTypeToClass
										 );
		}
	}

	public void action(final Context aContext) throws FCA_Stop {
		if (expression instanceof IntegerIA) {
			action_IntegerIA();
		} else if (expression instanceof IdentIA) {
			action_IdentIA(aContext);
		} else {
			throw new IllegalStateException("this.expression is of the wrong type");
		}

		deduceTypes2.activePTE(pte, pte.getClassInvocation());
	}

	public void action_IdentIA(final Context aContext) throws FCA_Stop {
		@NotNull IdentTableEntry idte       = ((IdentIA) expression).getEntry();
		DeducePath               deducePath = idte.buildDeducePath(generatedFunction);

		if (pte.dpc == null) {
			pte.dpc = _inj().new_DeduceProcCall(pte);
			pte.dpc.setDeduceTypes2(deduceTypes2, aContext, generatedFunction, deduceTypes2._errSink()); // TODO setting here seems right. Don't check member
		}

		final DeduceProcCall dpc = pte.dpc;

		dpc.targetP2().then((final @NotNull DeduceElement target) -> {
			DeclAnchor xxv = target.declAnchor();
			System.out.println("144 " + xxv);

			{
				// for class_instantiation2: class Bar {constructor x{}} class Main {main(){var bar:Bar[SysInt];construct bar.x ...}}
				// deducePath.getElement(0) == [bar]
				// deducePath.getElement(1) == [x]
				//deducePath.

				if (target != null) {
					deducePath.setTarget(target);
				}
			}

			action_IdentIA___0001(idte, deducePath, dpc, target);
		});
	}

	private void action_IdentIA___0001(final @NotNull IdentTableEntry idte, final @NotNull DeducePath deducePath, final @NotNull DeduceProcCall dpc, final @NotNull DeduceElement target) {
		@Nullable OS_Element el3;
		@Nullable Context    ectx = generatedFunction.getFD().getContext();
		for (int i = 0; i < deducePath.size(); i++) {
			InstructionArgument ia2 = deducePath.getIA(i);

			el3 = deducePath.getElement(i);

			boolean p = false;

			if (ia2 instanceof IntegerIA) {
				@NotNull VariableTableEntry vte = ((IntegerIA) ia2).getEntry();
				// TODO will fail if we try to construct a tmp var, but we never try to do that
				assert vte.getVtt() != VariableTableType.TEMP;
				assert el3 != null;
				assert i == 0;
				ectx = deducePath.getContext(i);
			} else if (ia2 instanceof final @NotNull IdentIA identIA) {
				@NotNull IdentTableEntry idte2 = identIA.getEntry();
				final String             s     = idte2.getIdent().toString();
				LookupResultList         lrl   = ectx.lookup(s);


				if (el3 == null) {
					int yy = 2;
				}


				if (lrl == null && ectx instanceof DeducePath.MemberContext) {
					p = (action_IdentIA___0001_5(deducePath, (DeducePath.MemberContext) ectx, i, idte2, s));
				} else {
					@Nullable OS_Element el2 = lrl.chooseBest(null);
					if (el2 == null) {
						action_IdentIA___0001_3(deducePath, el3, i, idte2, s);
						p = true;
					} else {
						if (i + 1 == deducePath.size() && deducePath.size() > 1) {
							if (el2 instanceof ConstructorDef) {
								action_IdentIA___0001_2(deducePath, i, idte2, s);
							} else if (el2 instanceof ClassStatement) {
								action_IdentIA___0001_1(idte, deducePath, dpc, target, i, idte2, s);
							} else
								throw new NotImplementedException();
						} else {
							ectx = deducePath.getContext(i);
						}
					}
				}
			}

			if (p) break;
		}
	}

	private boolean action_IdentIA___0001_5(final @NotNull DeducePath deducePath, final @NotNull DeducePath.MemberContext ectx, final int i, final @NotNull IdentTableEntry idte2, final String s) {
		final DeduceElement3_IdentTableEntry de3_idte      = deduceTypes2._zero_getIdent(idte2, generatedFunction, deduceTypes2);
		final DeduceElement3_Type            de3_idte_type = de3_idte.type();

		final OS_Type ty = de3_idte_type.genType().getTypeName();

		Preconditions.checkState(ty.getType() == OS_Type.Type.USER);

		final Operation2<GenType> resolved = de3_idte_type.resolved(ectx);

		if (resolved.mode() == Mode.FAILURE) {
			deduceTypes2._errSink().reportDiagnostic(resolved.failure());
		} else {
			action_IdentIA___0001_4(deducePath, i, idte2, s, ty, resolved);
			return true;
		}
		return false;
	}

	private void action_IdentIA___0001_4(final @NotNull DeducePath deducePath, final int i, final @NotNull IdentTableEntry idte2, final String s, final @NotNull OS_Type ty, final @NotNull Operation2<GenType> resolved) {
		GenType success = resolved.success();

		idte2.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolder(success.getResolved().getElement()));

		deduceTypes2.LOG.err("892 resolved: " + success);

		implement_construct_type(idte2, ty, s, null);

/*
						if (success == null) {
							try {
								success = resolve_type(ty, ectx);
							} catch (ResolveError aResolveError) {
								errSink.reportDiagnostic(aResolveError);
//									aResolveError.printStackTrace();
								assert false;
							}
						}
*/
		final VariableTableEntry x = (VariableTableEntry) (deducePath.getEntry(i - 1));
		x.resolveType(success);
		//success.genCIForGenType2(DeduceTypes2.this);
		return;
	}

	private void action_IdentIA___0001_3(final @NotNull DeducePath deducePath, final @Nullable OS_Element el3, final int i, final @NotNull IdentTableEntry idte2, final String s) {
		assert el3 instanceof VariableStatementImpl;
		@Nullable VariableStatementImpl vs = (VariableStatementImpl) el3;
		@NotNull TypeName               tn = vs.typeName();
		@NotNull OS_Type                ty = _inj().new_OS_UserType(tn);

		GenType resolved = null;
		if (idte2.type == null) {
			// README Don't remember enough about the constructors to select a different one
			@NotNull TypeTableEntry tte = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, ty);
			try {
				resolved = deduceTypes2.resolve_type(ty, tn.getContext());
				deduceTypes2.LOG.err("892 resolved: " + resolved);
				tte.setAttached(resolved);
			} catch (ResolveError aResolveError) {
				deduceTypes2._errSink().reportDiagnostic(aResolveError);
			}

			idte2.type = tte;
		}
		// s is constructor name
		implement_construct_type(idte2, ty, s, null);

		if (resolved == null) {
			try {
				resolved = deduceTypes2.resolve_type(ty, tn.getContext());
			} catch (ResolveError aResolveError) {
				deduceTypes2._errSink().reportDiagnostic(aResolveError);
//									aResolveError.printStackTrace();
				assert false;
			}
		}
		final VariableTableEntry x = (VariableTableEntry) (deducePath.getEntry(i - 1));
		x.resolveType(resolved);
		resolved.genCIForGenType2(deduceTypes2);
		return;
	}

	private void action_IdentIA___0001_2(final @NotNull DeducePath deducePath, final int i, final @NotNull IdentTableEntry idte2, final String s) {
		@Nullable GenType type = deducePath.getType(i);
		if (type.getNonGenericTypeName() == null) {
			type.setNonGenericTypeName(Objects.requireNonNull(deducePath.getType(i - 1)).getNonGenericTypeName()); // HACK. not guararnteed to work!
		}
		@NotNull OS_Type ty = _inj().new_OS_UserType(type.getNonGenericTypeName());
		implement_construct_type(idte2, ty, s, type);

		final VariableTableEntry x = (VariableTableEntry) (deducePath.getEntry(i - 1));
		if (type.getCi() == null && type.getNode() == null)
			type.genCIForGenType2(deduceTypes2);
		assert x != null;
		x.resolveTypeToClass(type.getNode());
	}

	private void action_IdentIA___0001_1(final @NotNull IdentTableEntry idte, final @NotNull DeducePath deducePath, final @NotNull DeduceProcCall dpc, final @NotNull DeduceElement target, final int i, final @NotNull IdentTableEntry idte2, final String s) {
		@Nullable GenType type = deducePath.getType(i);

		// FIXME or idte2??
		if (idte.type == null) {
			final OS_UserType osType = _inj().new_OS_UserType(((VariableStatementImpl) target.element()).typeName());
			idte.type = dpc._generatedFunction().newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, osType);

			type = idte.type.genType;

			deducePath.injectType(i, type);
		}

		if (type.getNonGenericTypeName() != null) {
			if (((NormalTypeName) type.getNonGenericTypeName()).getGenericPart().size() > 0) {
				// goal: create an equavalent (Regular)TypeName without the genericPart
				// method: copy the context and the name
				final RegularTypeName rtn = _inj().new_RegularTypeNameImpl(type.getNonGenericTypeName().getContext());
				rtn.setName(((NormalTypeName) type.getNonGenericTypeName()).getRealName());
				type.setNonGenericTypeName(rtn);
			}
			//type.nonGenericTypeName = Objects.requireNonNull(deducePath.getType(i - 1)).nonGenericTypeName; // HACK. not guararnteed to work!
		}

		implement_construct_type(idte2, type.getTypeName(), s, type);

		final VariableTableEntry x = (VariableTableEntry) (deducePath.getEntry(i - 1));
		if (type.getCi() == null && type.getNode() == null)
			type.genCIForGenType2(deduceTypes2);
		assert x != null;
		x.resolveTypeToClass(type.getNode());
	}

	public void action_IntegerIA() {
		@NotNull VariableTableEntry vte      = ((IntegerIA) expression).getEntry();
		final @Nullable OS_Type     attached = vte.getType().getAttached();
//			assert attached != null; // TODO will fail when empty variable expression
		if (attached != null && attached.getType() == OS_Type.Type.USER) {
			implement_construct_type(vte, attached, null, vte.getType().genType);
		} else {
			final OS_Type ty2 = vte.getType().genType.getTypeName();
			assert ty2 != null;
			implement_construct_type(vte, ty2, null, vte.getType().genType);
		}
	}

	private void implement_construct_type(final @Nullable Constructable co,
										  final @NotNull OS_Type aTy,
										  final @Nullable String constructorName,
										  final @Nullable GenType aGenType) {
		if (aTy.getType() != OS_Type.Type.USER)
			throw new IllegalStateException("must be USER type");

		TypeName tyn = aTy.getTypeName();
		if (tyn instanceof final @NotNull NormalTypeName tyn1) {
			_implement_construct_type(co, constructorName, tyn1, aGenType);
		}

		final ClassInvocation classInvocation = pte.getClassInvocation();
		if (co != null) {
			co.setConstructable(pte);
			assert classInvocation != null;
			classInvocation.resolvePromise().done(co::resolveTypeToClass);
		}

		if (classInvocation != null) {
			if (classInvocation.getConstructorName() != null) {
				final ClassStatement     classStatement    = classInvocation.getKlass();
				final GenerateFunctions  generateFunctions = deduceTypes2.getGenerateFunctions(classStatement.getContext().module());
				@Nullable ConstructorDef cc                = null;
				{
					Collection<ConstructorDef> cs = classStatement.getConstructors();
					for (@NotNull ConstructorDef c : cs) {
						if (c.name().sameName(constructorName)) {
							cc = c;
							break;
						}
					}
				}
				WlGenerateCtor gen = _inj().new_WlGenerateCtor(generateFunctions, pte.getFunctionInvocation(), cc.getNameNode(), deduceTypes2._phase().codeRegistrar);
				gen.run(null);
				final EvaConstructor gc = gen.getResult();
				classInvocation.resolveDeferred().then(result -> {
					result.addConstructor(gc.cd, gc);

					final WorkList              wl   = _inj().new_WorkList();
					final List<BaseEvaFunction> coll = new ArrayList<>();

					wl.addJob(deduceTypes2._inj().new_WlDeduceFunction(gen, coll, deduceTypes2));
					deduceTypes2.wm.addJobs(wl);
				});
			}
		}
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return deduceTypes2._inj();
	}

	public class ICH {
		private final GenType genType;

		public ICH(final GenType aGenType) {
			genType = aGenType;
		}

		@NotNull
		ClassInvocation getClassInvocation(final @Nullable String constructorName,
										   final @NotNull NormalTypeName aTyn1,
										   final @Nullable GenType aGenType,
										   final @NotNull ClassStatement aBest) {
			final ClassInvocation clsinv;
			if (aGenType != null && aGenType.getCi() != null) {
				assert aGenType.getCi() instanceof ClassInvocation;
				clsinv = (ClassInvocation) aGenType.getCi();
			} else {
				final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(aBest, constructorName, aTyn1, deduceTypes2);
				assert oi.mode() == Mode.SUCCESS;

				ClassInvocation clsinv2 = oi.success();
				clsinv = deduceTypes2.phase.registerClassInvocation(clsinv2);
			}
			return clsinv;
		}

		@NotNull ClassStatement lookupTypeName(final @NotNull NormalTypeName normalTypeName, final @NotNull String typeName) {
			final OS_Element best;
			if (genType != null && genType.getResolved() != null) {
				best = genType.getResolved().getClassOf();
			} else {
				LookupResultList lrl = normalTypeName.getContext().lookup(typeName);
				best = lrl.chooseBest(null);
			}
			assert best instanceof ClassStatement;
			return (ClassStatement) best;
		}
	}
}
