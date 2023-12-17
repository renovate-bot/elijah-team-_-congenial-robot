/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.Subject;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah.lang.nextgen.names.impl.*;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_UnitType;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.lang2.SpecialFunctions;
import tripleo.elijah.lang2.SpecialVariables;
import tripleo.elijah.nextgen.ClassDefinition;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Rosetta;
import tripleo.elijah.stages.deduce.Resolve_Ident_IA.DeduceElementIdent;
import tripleo.elijah.stages.deduce.declarations.DeferredMember;
import tripleo.elijah.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.deduce.tastic.*;
import tripleo.elijah.stages.gdm.GDM_IdentExpression;
import tripleo.elijah.stages.gdm.GDM_VariableTableEntry;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.Holder;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Created 9/15/20 12:51 PM
 */
public class DeduceTypes2 {

	CHAIN __CHAIN__post_dof_idte_register_resolved = new _CHAIN___post_dof_idte_register_resolved();

	private final DeduceTypes2Injector __inj = new DeduceTypes2Injector();
	private final DeduceTypes2Rosetta  rosetta;

	public DeduceElement3_IdentTableEntry _zero_getIdent(final IdentTableEntry aIdentTableEntryBte, final BaseEvaFunction aGf, final DeduceTypes2 aDt2) {
		return _p_zero.getIdent(aIdentTableEntryBte, aGf, aDt2);
	}

	public CompilationEnclosure _ce() {
		return _phase()._compilation().getCompilationEnclosure();
	}

	public static final   String PHASE = "DeduceTypes2";
	public final @NotNull ElLog  LOG;
	public final @NotNull  OS_Module                module;
	public final @NotNull  DeducePhase              phase;
	public final @NotNull  WorkManager              wm                      = _inj().new_WorkManager();
	private final          Zero                     _p_zero                 = _inj().new_Zero(this);
	private final          ITasticMap               _p_tasticMap            = _inj().new_TasticMap();
	private final          DeduceCentral            _p_central              = _inj().new_DeduceCentral(this);
	private final          Map<OS_Element, DG_Item> _map_dgs                = _inj().new_HashMap_DGS();
	private final @NotNull List<Runnable>           onRunnables             = _inj().new_ArrayList__Runnable();
	private final          List<FunctionInvocation> functionInvocations     = _inj().new_ArrayList__FunctionInvocation(); // TODO never used!
	private final          List<IDeduceResolvable>  _pendingResolves        = _inj().new_ArrayList__IDeduceResolvable();
	private final          ErrSink                  errSink;
	private final          PromiseExpectations      expectations            = _inj().new_PromiseExpectations(this);
	private final          List<DE3_Active>         _actives                = _inj().new_ArrayList__DE3_Active();
	private final          DeduceCreationContext    _defaultCreationContext = _inj().new_DefaultDeduceCreationContext(this);

	public DeduceTypes2(DeduceTypes2Request aRequest) {
		this.rosetta = new DeduceTypes2Rosetta(aRequest);

		this.module  = rosetta.getModule();
		this.phase   = rosetta.getDeducePhase();
		this.errSink = rosetta.getErrSink();

		this.LOG     = rosetta.createAndAddLog_DeduceTypes2();

		phase.waitOn(this);
	}

	// TODO 09/09 provide "direct"/wrapped reportDiagnostic
	public ErrSink _errSink() {
		return errSink;
	}

	private final @NotNull List<DT_External> externals = _inj().new_LinkedList__DT_External();

	private void checkEvaClassVarTable(final @NotNull BaseEvaFunction generatedFunction) {
		//for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
		//	variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
		//}
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			identTableEntry.getDeduceElement3(this, generatedFunction)
					.mvState(null, DeduceElement3_IdentTableEntry.ST.CHECK_EVA_CLASS_VAR_TABLE);
			//identTableEntry.setDeduceTypes2(this, aContext, generatedFunction);

		}
	}

	public void addResolvePending(final IDeduceResolvable aResolvable, final IDeduceElement_old aDeduceElement, final Holder<OS_Element> aHolder) {
		assert !hasResolvePending(aResolvable);

		_pendingResolves.add(aResolvable);
	}

	public boolean hasResolvePending(final IDeduceResolvable aResolvable) {
		final boolean b = _pendingResolves.contains(aResolvable);
		return b;
	}

	public @NotNull ElLog _LOG() {
		return LOG;
	}

	public void deduce_generated_constructor(final @NotNull EvaConstructor generatedFunction) {
		var ce = _phase().ca().getCompilation().getCompilationEnclosure();
		var mt = ce.addModuleThing(generatedFunction.module());

		final @NotNull ConstructorDef fd = (ConstructorDef) generatedFunction.getFD();
		deduce_generated_function_base(generatedFunction, fd, mt);
	}

	public @NotNull GenerateFunctions getGenerateFunctions(@NotNull OS_Module aModule) {
		return phase.generatePhase.getGenerateFunctions(aModule);
	}

	public @NotNull DeducePhase _phase() {
		return phase;
	}

	private void resolve_cte_expression_builtin(@NotNull ConstantTableEntry cte, Context aContext, BuiltInTypes aBuiltInType) {
		final OS_Type a = cte.getTypeTableEntry().getAttached();
		if (a == null || a.getType() != OS_Type.Type.USER_CLASS) {
			try {
				cte.getTypeTableEntry().setAttached(resolve_type(_inj().new_OS_BuiltinType(aBuiltInType), aContext));
			} catch (ResolveError resolveError) {
				SimplePrintLoggerToRemoveSoon.println_out_2("117 Can't be here");
				resolveError.printStackTrace(); // TODO print diagnostic
			}
		}
	}

	@NotNull
	public GenType resolve_type(final @NotNull OS_Type type, final Context ctx) throws ResolveError {
		//return ResolveType.resolve_type2(module, type, ctx, LOG, this);
		return ResolveType.resolve_type(module, type, ctx, LOG, this);
	}

	private @NotNull DeferredMember deferred_member(final @NotNull DeduceElementWrapper aParent,
													final /*@NotNull*/ IInvocation aInvocation,
													final @NotNull VariableStatementImpl aVariableStatement,
													final @NotNull IdentTableEntry ite) {
		@NotNull DeferredMember dm = Objects.requireNonNull(deferred_member(aParent, aInvocation, aVariableStatement));
		dm.externalRef().then(result -> ite.externalRef = result);
		return dm;
	}

	private @NotNull DeferredMember deferred_member(final @NotNull DeduceElementWrapper aParent, @Nullable IInvocation aInvocation, VariableStatementImpl aVariableStatement) {
		final IInvocation    invocation = _deferred_member_invocation(aParent, aInvocation);
		final DeferredMember dm         = _inj().new_DeferredMember(aParent, invocation, aVariableStatement);
		phase.addDeferredMember(dm);
		DebugPrint.addDeferredMember(dm);
		return dm;
	}

	private IInvocation _deferred_member_invocation(final @NotNull DeduceElementWrapper aParent, final @Nullable IInvocation aInvocation) {
		final IInvocation invocation;
		if (aInvocation == null) {
			if (aParent.isNamespaceStatement()) {
				invocation = phase.registerNamespaceInvocation((NamespaceStatement) aParent.element());
			} else if (aParent.element() instanceof ClassStatement cs) {
				invocation = _inj().new_ClassInvocation(cs, null, () -> this);
			} else
				throw new IllegalStateException("bad invocation");
		} else {
			invocation = aInvocation;
		}
		return invocation;
	}

	public void assign_type_to_idte(@NotNull IdentTableEntry ite,
									@NotNull BaseEvaFunction generatedFunction,
									@NotNull Context aFunctionContext,
									@NotNull Context aContext) {

		final DeduceElement3_IdentTableEntry x = ite.getDeduceElement3(this, generatedFunction);
		x.assign_type_to_idte(aFunctionContext, aContext);
	}

	public void deduceOneClass(final @NotNull EvaClass aEvaClass) {
		for (EvaContainer.VarTableEntry entry : aEvaClass.varTable) {
			final OS_Type vt      = entry.varType;
			GenType       genType = GenType.makeFromOSType(vt, aEvaClass.ci.genericPart(), this, phase, LOG, errSink);
			if (genType != null) {
				if (genType.getNode() != null) {
					entry.resolve(genType.getNode());
				} else {
					int y = 2; // 05/22
				}
			}

			NotImplementedException.raise();

		}
	}

	public void deduceFunctions(final @NotNull Iterable<EvaNode> lgf) {
		for (final EvaNode evaNode : lgf) {
			if (evaNode instanceof @NotNull final EvaFunction generatedFunction) {
				deduceOneFunction(generatedFunction, phase);
			}
		}
		@NotNull List<EvaNode> generatedClasses = (phase.generatedClasses.copy());
		// TODO consider using reactive here
		int size;
		do {
			size             = df_helper(generatedClasses, new dfhi_functions());
			generatedClasses = phase.generatedClasses.copy();
		} while (size > 0);
		do {
			size             = df_helper(generatedClasses, new dfhi_constructors());
			generatedClasses = phase.generatedClasses.copy();
		} while (size > 0);
	}

	public IInvocation getInvocation(@NotNull EvaFunction generatedFunction) {
		final ClassInvocation     classInvocation = generatedFunction.fi.getClassInvocation();
		final NamespaceInvocation ni;
		if (classInvocation == null) {
			ni = generatedFunction.fi.getNamespaceInvocation();
			return ni;
		} else
			return classInvocation;
	}

	public boolean deduceOneFunction(@NotNull EvaFunction aGeneratedFunction, @NotNull DeducePhase aDeducePhase) {
		var ce = aDeducePhase.ca().getCompilation().getCompilationEnclosure();
		var mt = ce.getModuleThing(aGeneratedFunction.module());


		if (aGeneratedFunction.deducedAlready) return false;
		deduce_generated_function(aGeneratedFunction, mt);

		mt.addFunction(aGeneratedFunction);


		aGeneratedFunction.deducedAlready = true;
		__post_dof_idte_register_resolved(aGeneratedFunction, aDeducePhase);
		__post_dof_result_type(aGeneratedFunction);
		aDeducePhase.addFunction(aGeneratedFunction, aGeneratedFunction.getFD());

		for (IdentTableEntry identTableEntry : aGeneratedFunction.idte_list) {
			final DR_Ident drIdent = aGeneratedFunction.getIdent(identTableEntry);
			aGeneratedFunction.onInformGF(gf-> {
				final GDM_IdentExpression mie = gf.monitor(identTableEntry.getIdent());
				mie.resolveIdentTableEntry(identTableEntry);
				mie.resolveDrIdent(drIdent);

			});
			aGeneratedFunction.drs.add(drIdent);
		}
		for (VariableTableEntry variableTableEntry : aGeneratedFunction.vte_list) {
			final DR_Ident drIdent = aGeneratedFunction.getIdent(variableTableEntry);
			aGeneratedFunction.onInformGF(gf-> {
				final GDM_VariableTableEntry mie = gf.monitor(variableTableEntry);
				mie.resolveDrIdent(drIdent);
			});
			aGeneratedFunction.drs.add(drIdent);
		}


		return true;
	}

	private void __post_dof_result_type(final @NotNull EvaFunction aGeneratedFunction) {
		final @NotNull EvaFunction gf = aGeneratedFunction;

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
						vte.getGenType().setTypeName(a);
						try {
							@NotNull GenType rt = resolve_type(a, a.getTypeName().getContext());
							if (rt.getResolved() != null && rt.getResolved().getType() == OS_Type.Type.USER_CLASS) {
								if (rt.getResolved().getClassOf().getGenericPart().size() > 0)
									vte.getGenType().setNonGenericTypeName(a.getTypeName()); // TODO might be wrong
								dof_uc(vte, rt.getResolved());
							}
						} catch (ResolveError aResolveError) {
							errSink.reportDiagnostic(aResolveError);
						}
						break;
					default:
						int y3 = 2;


						vte.typePromise().then(gt -> {
							int y4 = 2;

							if (vte.getStatus() == BaseTableEntry.Status.UNCHECKED) {
								// NOTE curious...
								int y5 = 25;

								final OS_Element resolvedElement = vte.getResolvedElement();

								if (resolvedElement == null) {
//									throw new AssertionError();
								} else {
									vte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolder(resolvedElement));
								}
							} else throw new IllegalStateException("Error");

						});


						if (vte.getVtt() == VariableTableType.RESULT) {
							final OS_Element resolvedElement = vte.getResolvedElement();

							if (resolvedElement == null) {
								//throw new AssertionError();
							} else {
								vte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolder(resolvedElement));
							}
						}

						break;
					}
				} else {
					// 08/31 Back to this...
					throw new NotImplementedException();
				}
			}
		}
	}
	ST2_ ST2 = new ST2_();

	public DT_Function deduce_generated_function_base(final @NotNull BaseEvaFunction generatedFunction,
													  final @NotNull FunctionDef fd,
													  final @NotNull ModuleThing aMt) {
		DT_Function f = new DT_Function(generatedFunction, this);

		f.fix_tables();
		f.log();
		f.loop();

		final Context fd_ctx = fd.getContext();

		f.__post_vte_list_001();
		f.__post_vte_list_002(fd_ctx);
		f.__post_deferred_calls(fd_ctx);

		return f;
	}

	public Implement_construct newImplement_construct(final BaseEvaFunction aGeneratedFunction, final Instruction aInstruction) {
		return _inj().new_Implement_construct(this, aGeneratedFunction, aInstruction);
	}

	private /*static*/ void __post_dof_idte_register_resolved(final @NotNull EvaFunction aGeneratedFunction, final @NotNull DeducePhase aDeducePhase) {
		final DT_Function dtFunction = new DT_Function(aGeneratedFunction, this);

		dtFunction.set__state_dp(aDeducePhase);
		dtFunction.eachIdent()
				.applyState(ST2.getState___post_dof_idte_register_resolved(), new Times.Once(), __CHAIN__post_dof_idte_register_resolved);
	}

	void implement_is_a(final @NotNull BaseEvaFunction gf, final @NotNull Instruction instruction) {
		final IntegerIA testing_var_  = (IntegerIA) instruction.getArg(0);
		final IntegerIA testing_type_ = (IntegerIA) instruction.getArg(1);
		final Label     target_label  = ((LabelIA) instruction.getArg(2)).label;

		final VariableTableEntry testing_var    = gf.getVarTableEntry(testing_var_.getIndex());
		final TypeTableEntry     testing_type__ = gf.getTypeTableEntry(testing_type_.getIndex());

		GenType genType = testing_type__.genType;
		if (genType.getResolved() == null) {
			try {
				genType.setResolved(resolve_type(genType.getTypeName(), gf.getFD().getContext()).getResolved());
			} catch (ResolveError aResolveError) {
//				aResolveError.printStackTrace();
				errSink.reportDiagnostic(aResolveError);
				return;
			}
		}
		if (genType.getCi() == null) {
			genType.genCI(genType.getNonGenericTypeName(), this, errSink, phase);
		}
		if (genType.getNode() == null) {
			if (genType.getCi() instanceof ClassInvocation) {
				WlGenerateClass gen = _inj().new_WlGenerateClass(getGenerateFunctions(module), (ClassInvocation) genType.getCi(), phase.generatedClasses, phase.codeRegistrar);
				gen.run(null);
				gen.resultPromise(genType::setNode);
			} else if (genType.getCi() instanceof NamespaceInvocation) {
				WlGenerateNamespace gen = _inj().new_WlGenerateNamespace(getGenerateFunctions(module), (NamespaceInvocation) genType.getCi(), phase.generatedClasses, phase.codeRegistrar);
				gen.run(null);
				genType.setNode(gen.getResult());
			}
		}
		//EvaNode testing_type = testing_type__.resolved();
		//assert testing_type != null;
		assert testing_type__.isResolved();
	}

	void do_agnk(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Instruction instruction) {
		final @NotNull IntegerIA          arg  = (IntegerIA) instruction.getArg(0);
		final @NotNull VariableTableEntry vte  = generatedFunction.getVarTableEntry(arg.getIndex());
		final InstructionArgument         i2   = instruction.getArg(1);
		final @NotNull ConstTableIA       ctia = (ConstTableIA) i2;

		{
			if (vte.getType().getAttached() != null) {
				// TODO check types
			}
			final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(ctia.getIndex());
			if (cte.type.getAttached() == null) {
				LOG.info("Null type in CTE " + cte);
			}
//		vte.type = cte.type;
			vte.addPotentialType(instruction.getIndex(), cte.type);
			DebugPrint.addPotentialType(vte, cte);
		}
	}

	void do_calls(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Context fd_ctx, final @NotNull Instruction instruction) {
		final int                     i1  = to_int(instruction.getArg(0));
		final InstructionArgument     i2  = (instruction.getArg(1));
		final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
		{
			final int pc = instruction.getIndex();
			if (generatedFunction.deferred_calls.contains(pc)) {
				LOG.err("Call is deferred "/*+gf.getInstruction(pc)*/ + " " + fn1);
			} else {
				Implement_Calls_ ic = _inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1, pc, this);
				ic.action();
			}
		}
/*
				if (i2 instanceof IntegerIA) {
					int i2i = to_int(i2);
					VariableTableEntry vte = generatedFunction.getVarTableEntry(i2i);
					int y =2;
				} else
					throw new NotImplementedException();
*/
	}

	/**
	 * Deduce functions or constructors contained in classes list
	 *
	 * @param aGeneratedClasses assumed to be a list of {@link EvaContainerNC}
	 * @param dfhi              specifies what to select for:<br>
	 *                          {@link dfhi_functions} will select all functions from {@code functionMap}, and <br>
	 *                          {@link dfhi_constructors} will select all constructors from {@code constructors}.
	 * @param <T>               generic parameter taken from {@code dfhi}
	 * @return the number of deduced functions or constructors, or 0
	 */
	<T> int df_helper(@NotNull List<EvaNode> aGeneratedClasses, @NotNull df_helper_i<T> dfhi) {
		int size = 0;
		for (EvaNode evaNode : aGeneratedClasses) {
			@NotNull EvaContainerNC      generatedContainerNC = (EvaContainerNC) evaNode;
			final @Nullable df_helper<T> dfh                  = dfhi.get(generatedContainerNC);
			if (dfh == null) continue;
			@NotNull Collection<T> lgf2 = dfh.collection();
			for (final T generatedConstructor : lgf2) {
				if (dfh.deduce(generatedConstructor))
					size++;
			}
		}
		return size;
	}

	public void deduceClasses(final @NotNull List<EvaNode> lgc) {
		for (EvaNode evaNode : lgc) {
			if (!(evaNode instanceof final @NotNull EvaClass evaClass)) continue;

			deduceOneClass(evaClass);
		}
	}

	private static void __post_vte_list_001(final @NotNull BaseEvaFunction generatedFunction) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.typeResolvePromise().then(gt -> {
				var xx = vte.resolvedType();

				if (xx instanceof EvaClass evaClass) {
					if (gt.getCi() == null) {
						gt.setCi(evaClass.ci);
					}

					gt.setResolved(evaClass.getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getType().setAttached(gt);
				} else if (xx instanceof EvaConstructor evaConstructor) {
					if (gt.getCi() == null) {
//						gt.ci = evaConstructor.ci;
					}

					gt.setResolved(evaConstructor.fi.getClassInvocation().getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getType().setAttached(gt);
				}
			});
		}
	}

	void do_call(final @NotNull BaseEvaFunction generatedFunction, final @NotNull FunctionDef fd, final @NotNull Instruction instruction, final @NotNull Context context) {
		final int                     pte_num = ((ProcIA) instruction.getArg(0)).index();
		final @NotNull ProcTableEntry pte     = generatedFunction.getProcTableEntry(pte_num);
//				final InstructionArgument i2 = (instruction.getArg(1));
		{
			final @NotNull IdentIA identIA = (IdentIA) pte.expression_num;

			final IdentTableEntry identTableEntry = identIA.getEntry();
			var                   idnt            = generatedFunction.getIdent(identTableEntry);

			{
				ENU_ResolveToFunction rtf = null;
				var                   x   = identTableEntry.getIdent().getName();

				List<EN_Understanding> understandings = x.getUnderstandings();
				// README 11/10 optimized, better than streams
				for (int i = 0, understandingsSize = understandings.size(); i < understandingsSize; i++) {
					final EN_Understanding understanding = understandings.get(i);
					if (understanding instanceof ENU_ResolveToFunction rtf1) {
						rtf = rtf1;
					}
				}
				for (EN_Usage usage : x.getUsages()) {
					if (usage instanceof EN_DeduceUsage edu) {
						final ProcTableEntry callable_pte = ((IdentTableEntry) edu.getBte())._callable_pte();
						var                  e            = callable_pte.__debug_expression;
						if (e instanceof DotExpression de) {
							var r = de.getRight();
							if (r instanceof IdentExpression ie) {
								ie.getName().addUnderstanding(_inj().new_ENU_FunctionInvocation(callable_pte));
							}
						}
					}
				}
			}

			var          xx = generatedFunction._getIdentIAResolvable(identIA);
			final String x  = xx.getNormalPath(generatedFunction, identIA);

			assert x.equals(xx.getNormalPath(generatedFunction, identIA));

			LOG.info("298 Calling " + x);
			resolveIdentIA_(context, identIA, generatedFunction, new FoundElement(phase) {

				@SuppressWarnings("unused")
				final String xx = x;

				@Override
				public void foundElement(OS_Element e) {
					found_element_for_ite(generatedFunction, identIA.getEntry(), e, context, central());
//							identIA.getEntry().setCallablePTE(pte); // TODO ??

					pte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_ConstructableElementHolder(e, identIA));
					if (fd instanceof DefFunctionDef) {
						final IInvocation invocation = getInvocation((EvaFunction) generatedFunction);
						forFunction(newFunctionInvocation((FunctionDef) e, pte, invocation, phase), new ForFunction() {
							@Override
							public void typeDecided(@NotNull GenType aType) {
								@Nullable InstructionArgument x = generatedFunction.vte_lookup("Result");
								assert x != null;
								((IntegerIA) x).getEntry().getType().setAttached(gt(aType));
							}
						});
					}
				}

				@Override
				public void noFoundElement() {
					errSink.reportError("370 Can't find callsite " + x);
					// TODO don't know if this is right
					@NotNull IdentTableEntry entry = identIA.getEntry();
					if (entry.getStatus() != BaseTableEntry.Status.UNKNOWN)
						entry.setStatus(BaseTableEntry.Status.UNKNOWN, null);
				}
			});
		}
	}

	public DeduceTypes2Injector _inj() {
		return this.__inj;
	}

	public void deduce_generated_function(final @NotNull EvaFunction generatedFunction, final @NotNull ModuleThing aMt) {
		final @NotNull FunctionDef fd = generatedFunction.getFD();
		deduce_generated_function_base(generatedFunction, fd, aMt);
	}

	private void dof_uc(@NotNull VariableTableEntry aVte, @NotNull OS_Type aA) {
		// we really want a ci from somewhere
		assert aA.getClassOf().getGenericPart().size() == 0;
		@Nullable ClassInvocation ci = _inj().new_ClassInvocation(aA.getClassOf(), null, new ReadySupplier_1<>(this));
		ci = phase.registerClassInvocation(ci);

		aVte.getGenType().setResolved(aA); // README assuming OS_Type cannot represent namespaces
		aVte.getGenType().setCi(ci);

		ci.resolvePromise().done(new DoneCallback<EvaClass>() {
			@Override
			public void onDone(@NotNull EvaClass result) {
				aVte.resolveTypeToClass(result);
			}
		});
	}

	interface DT_State {
		void applyState(Times.T aTimes, CHAIN aCHAIN, Object xx);
	}

	public void do_assign_normal(final @NotNull BaseEvaFunction generatedFunction,
								 final @NotNull Context aFd_ctx,
								 final @NotNull Instruction instruction,
								 final @NotNull Context aContext) {
		// TODO doesn't account for __assign__
		final InstructionArgument agn_lhs = instruction.getArg(0);
		if (agn_lhs instanceof IntegerIA lhs_integerIA) {
			final @NotNull VariableTableEntry vte = generatedFunction.getVarTableEntry(lhs_integerIA.getIndex());

			OS_Element name = null;
			{
				final OS_Element[] el1 = new OS_Element[1];

				switch (vte.getVtt()) {
				case ARG -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				case VAR -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				}
			}

			if (name != null) {
				int y = 2;
			}

			final InstructionArgument i2 = instruction.getArg(1);
			if (i2 instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(to_int(i2));
				vte.addPotentialType(instruction.getIndex(), vte2.getType());
			} else if (i2 instanceof final @NotNull FnCallArgs fca) {
				final @Nullable ITastic fcat = tasticFor(fca);

				fcat.do_assign_call(generatedFunction, aContext, vte, instruction, name);
			} else if (i2 instanceof ConstTableIA) {
				if (vte.getType().getAttached() != null) {
					// TODO check types
				}
				final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(((ConstTableIA) i2).getIndex());
				if (cte.type.getAttached() == null) {
					LOG.info("Null type in CTE " + cte);
				}
//		vte.type = cte.type;
				vte.addPotentialType(instruction.getIndex(), cte.type);
				DebugPrint.addPotentialType(vte, cte);
			} else if (i2 instanceof IdentIA identIA) {
				@NotNull IdentTableEntry idte = identIA.getEntry();

				var de3_ite = idte.getDeduceElement3();

				de3_ite.dan(generatedFunction, instruction, aContext, vte, identIA, idte, this);
			} else if (i2 instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		} else if (agn_lhs instanceof IdentIA arg) {
			final @NotNull IdentTableEntry agn_lhs_ite = arg.getEntry();
			final InstructionArgument      agn_rhs_ia  = instruction.getArg(1);
			if (agn_rhs_ia instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(to_int(agn_rhs_ia));
				agn_lhs_ite.addPotentialType(instruction.getIndex(), vte2.getType());
			} else if (agn_rhs_ia instanceof final @NotNull FnCallArgs fca) {
				tasticFor(agn_rhs_ia).do_assign_call(generatedFunction, aFd_ctx, agn_lhs_ite, instruction.getIndex());
			} else if (agn_rhs_ia instanceof IdentIA identIA) {
				if (agn_lhs_ite.getResolvedElement() instanceof VariableStatementImpl) {
					do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_lhs_ite);
				}
				@NotNull IdentTableEntry agn_rhs_ite = identIA.getEntry();
				do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_rhs_ite);
				agn_lhs_ite.addPotentialType(instruction.getIndex(), agn_rhs_ite.type);
			} else if (agn_rhs_ia instanceof ConstTableIA) {
				do_assign_constant(generatedFunction, instruction, agn_lhs_ite, (ConstTableIA) agn_rhs_ia);
			} else if (agn_rhs_ia instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		}
	}

	public static int to_int(@NotNull final InstructionArgument arg) {
		if (arg instanceof IntegerIA)
			return ((IntegerIA) arg).getIndex();
		if (arg instanceof ProcIA)
			return ((ProcIA) arg).index();
		if (arg instanceof IdentIA)
			return ((IdentIA) arg).getIndex();
		throw new NotImplementedException();
	}

	public @Nullable ITastic tasticFor(Object o) {
		if (_p_tasticMap.containsKey(o)) {
			return _p_tasticMap.get(o);
		}

		ITastic r = null;

		if (o instanceof FnCallArgs) {
			r = _inj().new_FT_FnCallArgs(this, (FnCallArgs) o);
			_p_tasticMap.put(o, r);
		}

		return r;
	}

	private void __post_vte_list_002(final @NotNull BaseEvaFunction generatedFunction, final Context fd_ctx) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			if (vte.getType().getAttached() == null) {
				int potential_size = vte.potentialTypes().size();
				if (potential_size == 1)
					vte.getType().setAttached(getPotentialTypesVte(vte).get(0).getAttached());
				else if (potential_size > 1) {
					// TODO Check type compatibility
					LOG.err("703 " + vte.getName() + " " + vte.potentialTypes());
					errSink.reportDiagnostic(_inj().new_CantDecideType(vte, vte.potentialTypes()));
				} else {
					// potential_size == 0
					// Result is handled by phase.typeDecideds, self is always valid
					if (/*vte.getName() != null &&*/ !(vte.getVtt() == VariableTableType.RESULT || vte.getVtt() == VariableTableType.SELF))
						errSink.reportDiagnostic(_inj().new_CantDecideType(vte, vte.potentialTypes()));
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
						} else if (attached instanceof OS_UnitType) {
							state = 1;
						}


						if (state == 0)
							vte.getType().setAttached(resolve_type(attached, fd_ctx));
					} catch (ResolveError aResolveError) {
						aResolveError.printStackTrace();
						assert false;
					}
				}
			}
		}
	}

	public void fix_tables(final @NotNull BaseEvaFunction evaFunction) {
		for (VariableTableEntry variableTableEntry : evaFunction.vte_list) {
			variableTableEntry._fix_table(this, evaFunction);
		}
		for (IdentTableEntry identTableEntry : evaFunction.idte_list) {
			identTableEntry._fix_table(this, evaFunction);
		}
		for (TypeTableEntry typeTableEntry : evaFunction.tte_list) {
			typeTableEntry._fix_table(this, evaFunction);
		}
		for (ProcTableEntry procTableEntry : evaFunction.prte_list) {
			procTableEntry._fix_table(this, evaFunction);
		}
	}

	public void do_assign_normal_ident_deferred(final @NotNull BaseEvaFunction generatedFunction,
												final @NotNull Context aContext,
												final @NotNull IdentTableEntry aIdentTableEntry) {
		if (aIdentTableEntry.type == null) {
			// TODO 08/28 making a type with a null type
			aIdentTableEntry.makeType(generatedFunction, TypeTableEntry.Type.TRANSIENT, (OS_Type) null);
		}

		final DR_Ident ident = aIdentTableEntry.__gf.getIdent(aIdentTableEntry);
		ident.try_resolve_normal(aContext);
		ident.addResolveObserver(new do_assign_normal_ident_deferred__DT_ResolveObserver(aIdentTableEntry, generatedFunction));
	}

	private void resolve_cte_expression(@NotNull ConstantTableEntry cte, Context aContext) {
		final IExpression initialValue = cte.initialValue;
		switch (initialValue.getKind()) {
		case NUMERIC:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.SystemInteger);
			break;
		case STRING_LITERAL:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.String_);
			break;
		case CHAR_LITERAL:
			resolve_cte_expression_builtin(cte, aContext, BuiltInTypes.SystemCharacter);
			break;
		case IDENT: {
			final OS_Type a = cte.getTypeTableEntry().getAttached();
			if (a != null) {
				assert a.getType() != null;
				if (a.getType() == OS_Type.Type.BUILT_IN && a.getBType() == BuiltInTypes.Boolean) {
					assert BuiltInTypes.isBooleanText(cte.getName());
				} else
					throw new NotImplementedException();
			} else {
				assert false;
			}
			break;
		}
		default: {
			LOG.err("8192 " + initialValue.getKind());
			throw new NotImplementedException();
		}
		}
	}

	private void add_proc_table_listeners(@NotNull BaseEvaFunction generatedFunction) {
		__Add_Proc_Table_Listeners aptl = _inj().new___Add_Proc_Table_Listeners();

		for (final @NotNull ProcTableEntry pte : generatedFunction.prte_list) {
			aptl.add_proc_table_listeners(generatedFunction, pte, this);
		}
	}

	public <B> @NotNull PromiseExpectation<B> promiseExpectation(ExpectationBase base, String desc) {
		final @NotNull PromiseExpectation<B> promiseExpectation = _inj().new_PromiseExpectation(base, desc, this);
		expectations.add(promiseExpectation);
		return promiseExpectation;
	}

	static class ST2_ {
		State___post_dof_idte_register_resolved state___postDofIdteRegisterResolved;

		public DT_State getState___post_dof_idte_register_resolved() {
			if (state___postDofIdteRegisterResolved == null) {
				state___postDofIdteRegisterResolved = new State___post_dof_idte_register_resolved();
			}
			return state___postDofIdteRegisterResolved;
		}

		class State___post_dof_idte_register_resolved implements DT_State {
			@Override
			public void applyState(final Times.T aTimes, final CHAIN aCHAIN, final Object xx) {
				//X x = (X)xx;
				DT_Function x = (DT_Function) xx;

				final BaseEvaFunction      generatedFunction = x.state_generatedFunction();
				final @NotNull DeducePhase deducePhase       = x.state_deducePhase();
				for (@NotNull IdentTableEntry identTableEntry : generatedFunction.idte_list) {
					if (identTableEntry.getResolvedElement() instanceof final @NotNull VariableStatementImpl vs) {
						OS_Element el  = vs.getParent().getParent();
						OS_Element el2 = generatedFunction.getFD().getParent();
						if (el != el2) {
							if (el instanceof ClassStatement || el instanceof NamespaceStatement)
								// NOTE there is no concept of gf here
								deducePhase.registerResolvedVariable(identTableEntry, el, vs.getName());
						}
					}
				}
			}

			public record X(EvaFunction generatedFunction, DeducePhase deducePhase) {
			}
		}
	}

	class _CHAIN___post_dof_idte_register_resolved implements CHAIN {
		@Override
		public CHAIN getParent() {
			throw new UnintendedUseException();
		}

		@Override
		public String getIdentitity() {
			throw new UnintendedUseException();
		}
	}

	public void onEnterFunction(final @NotNull BaseEvaFunction generatedFunction, final Context aContext) {
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
		}
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			identTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
			//identTableEntry._fix_table(this, generatedFunction);
		}
		for (ProcTableEntry procTableEntry : generatedFunction.prte_list) {
			procTableEntry.setDeduceTypes2(this, aContext, generatedFunction, errSink);
		}
		//
		// resolve all cte expressions
		//
		for (final @NotNull ConstantTableEntry cte : generatedFunction.cte_list) {
			resolve_cte_expression(cte, aContext);
		}
		//
		// add proc table listeners
		//
		add_proc_table_listeners(generatedFunction);
		//
		// resolve ident table
		//
		{
			for (@NotNull IdentTableEntry ite : generatedFunction.idte_list) {
				ite.resolveExpectation = promiseExpectation(ite, "Element Resolved");
				ite.addResolver(_inj().new_Unnamed_ITE_Resolver1(this, ite, generatedFunction, aContext));
			}
			for (@NotNull IdentTableEntry ite : generatedFunction.idte_list) {
				ite.resolvers_round();
			}
		}
		//
		// resolve arguments table
		//
		@NotNull Resolve_Variable_Table_Entry    rvte = _inj().new_Resolve_Variable_Table_Entry(generatedFunction, aContext, this);
		@NotNull DeduceTypes2.IVariableConnector connector;
		if (generatedFunction instanceof EvaConstructor) {
			connector = _inj().new_CtorConnector((EvaConstructor) generatedFunction);
		} else {
			connector = _inj().new_NullConnector();
		}
		for (@NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			rvte.action(vte, connector);
		}
	}

	@Deprecated
	public void resolveIdentIA_(@NotNull Context context, @NotNull IdentIA identIA, @NotNull BaseEvaFunction generatedFunction, @NotNull FoundElement foundElement) {
		@NotNull Resolve_Ident_IA ria = _inj().new_Resolve_Ident_IA(_inj().new_DeduceClient3(this), context, identIA, generatedFunction, foundElement, errSink);
		ria.action();
	}

	public void onExitFunction(final @NotNull BaseEvaFunction generatedFunction, final Context aFd_ctx, final Context aContext) {
		//
		// resolve var table. moved from `E'
		//
		for (@NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.resolve_var_table_entry_for_exit_function();
		}
		for (@NotNull Runnable runnable : onRunnables) {
			runnable.run();
		}
//					LOG.info("167 "+generatedFunction);
		//
		// ATTACH A TYPE TO VTE'S
		// CONVERT USER TYPES TO USER_CLASS TYPES
		//
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
//                                              LOG.info("704 "+vte.type.attached+" "+vte.potentialTypes());
			final DeduceElement3_VariableTableEntry vte_de = vte.getDeduceElement3();
			vte_de.setDeduceTypes2(this, generatedFunction);
			vte_de.mvState(null, DeduceElement3_VariableTableEntry.ST.EXIT_CONVERT_USER_TYPES);
		}
		__checkVteList(generatedFunction);
		//
		// ATTACH A TYPE TO IDTE'S
		//
		for (@NotNull final IdentTableEntry ite : generatedFunction.idte_list) {
			final DeduceElement3_IdentTableEntry ite_de = ite.getDeduceElement3(this, generatedFunction);
			ite_de._ctxts(aFd_ctx, aContext);
			ite_de.mvState(null, DeduceElement3_IdentTableEntry.ST.EXIT_GET_TYPE);
		}
		{
			// TODO why are we doing this?
			final Resolve_each_typename ret = _inj().new_Resolve_each_typename(phase, this, errSink);
			for (final TypeTableEntry typeTableEntry : generatedFunction.tte_list) {
				ret.action(typeTableEntry);
			}
		}
		{
			final @NotNull WorkManager  workManager = wm;//_inj().new_WorkManager();
			@NotNull final Dependencies deps        = _inj().new_Dependencies(/*this, *//*phase, this, errSink*/workManager, this);
			deps.subscribeTypes(generatedFunction.dependentTypesSubject());
			deps.subscribeFunctions(generatedFunction.dependentFunctionSubject());
//                                              for (@NotNull GenType genType : generatedFunction.dependentTypes()) {
//                                                      deps.action_type(genType, workManager);
//                                              }
//                                              for (@NotNull FunctionInvocation dependentFunction : generatedFunction.dependentFunctions()) {
//                                                      deps.action_function(dependentFunction, workManager);
//                                              }
			final int x = workManager.totalSize();

			// FIXME 06/14
			workManager.drain();


			phase.addDrs(generatedFunction, generatedFunction.drs);

			phase.doneWait(this, generatedFunction);
		}

		//
		// RESOLVE FUNCTION RETURN TYPES
		//
		resolve_function_return_type(generatedFunction);

		__on_exit__post_vte_something(generatedFunction, aFd_ctx);

		//
		// LOOKUP FUNCTIONS
		//
		{
			@NotNull WorkList wl = _inj().new_WorkList();

			for (@NotNull ProcTableEntry pte : generatedFunction.prte_list) {
				final DeduceElement3_ProcTableEntry de3_pte = (DeduceElement3_ProcTableEntry) pte.getDeduceElement3(DeduceTypes2.this, generatedFunction);
				de3_pte.lfoe_action(DeduceTypes2.this, wl, (j) -> wm.addJobs(j));
			}

			wm.addJobs(wl);
			//wm.drain();
		}

		checkEvaClassVarTable(generatedFunction);

		expectations.check();

		phase.addActives(_actives);

		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			generatedFunction.drs.add(_inj().new_DR_Ident(identTableEntry, generatedFunction, this));
		}
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			generatedFunction.drs.add(DR_Ident.create(variableTableEntry, generatedFunction));
		}

		phase.addDrs(generatedFunction, generatedFunction.drs);

		for (DT_External external : externals) {
			//external.onTargetModule(tm -> {phase.modulePromise(tm, external::actualise);}); //[T1160118]
			phase.modulePromise(external.targetModule(), external::actualise);
		}
	}

	private void __checkVteList(final @NotNull BaseEvaFunction generatedFunction) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			__checkVteList_each(vte);
		}
	}

	void resolve_function_return_type(@NotNull BaseEvaFunction generatedFunction) {
		final DeduceElement3_Function f = _p_zero.get(DeduceTypes2.this, generatedFunction);

		final GenType gt = f.resolve_function_return_type_int(errSink);
		if (gt != null)
			//phase.typeDecided((EvaFunction) generatedFunction, gt);
			generatedFunction.resolveTypeDeferred(gt);
	}

	private void __on_exit__post_vte_something(final @NotNull BaseEvaFunction generatedFunction, final Context aFd_ctx) {
		int y = 2;
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			final @NotNull Collection<TypeTableEntry> pot = variableTableEntry.potentialTypes();
			if (pot.size() == 1 && variableTableEntry.getGenType().isNull()) {
				final OS_Type x = pot.iterator().next().getAttached();
				if (x != null)
					if (x.getType() == OS_Type.Type.USER_CLASS) {
						try {
							final @NotNull GenType yy = resolve_type(x, aFd_ctx);
							// HACK TIME
							if (yy.getResolved() == null && yy.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
								yy.setResolved(yy.getTypeName());
								yy.setTypeName(null);
							}

							yy.genCIForGenType2(this);
							variableTableEntry.resolveType(yy);
							variableTableEntry.resolveTypeToClass(yy.getNode());
//								variableTableEntry.dlv.type.resolve(yy);
						} catch (ResolveError aResolveError) {
							aResolveError.printStackTrace();
						}
					}
			}
		}
	}

	public @NotNull DeduceCentral central() {
		return _p_central;
	}

	@NotNull
	public String getPTEString(final @Nullable ProcTableEntry aProcTableEntry) {
		String pte_string;
		if (aProcTableEntry == null)
			pte_string = "[]";
		else {
			pte_string = aProcTableEntry.getLoggingString(this);
		}
		return pte_string;
	}

	public void found_element_for_ite(BaseEvaFunction generatedFunction, @NotNull IdentTableEntry ite, @Nullable OS_Element y, Context ctx, final DeduceCentral central) {
		if (y != ite.getResolvedElement()) {
			SimplePrintLoggerToRemoveSoon.println_err_2(String.format("2571 Setting FoundElement for ite %s to %s when it is already %s", ite, y, ite.getResolvedElement()));
		}

		var env = _inj().new_DT_Env(LOG, errSink, central);

		@NotNull Found_Element_For_ITE fefi = _inj().new_Found_Element_For_ITE(generatedFunction, ctx, env, _inj().new_DeduceClient1(this));
		fefi.action(ite);
	}

	private void __checkVteList_each(final @NotNull VariableTableEntry vte) {
		if (vte.getVtt() == VariableTableType.ARG) {
			final TypeTableEntry vteType = vte.getType();

			if (vteType.genType instanceof ForwardingGenType fgt)
				fgt.unsparkled();

			if (vteType.genType != null) {
				var vte_genType = vte.getGenType();
				if (vte_genType.getNode() != null)
					return;
			}

			final OS_Type attached = vteType.getAttached();
			if (attached != null) {
				if (attached.getType() == OS_Type.Type.USER) {
					//throw new AssertionError();
					errSink.reportError("369 ARG USER type (not deduced) " + vte);
				}
			} else {
				//08/13 errSink.reportError("457 ARG type not deduced/attached " + vte);
			}
		}
	}

	public void forFunction(@NotNull FunctionInvocation gf, @NotNull ForFunction forFunction) {
		phase.forFunction(this, gf, forFunction);
	}

	@NotNull
	public FunctionInvocation newFunctionInvocation(FunctionDef aFunctionDef, ProcTableEntry aPte, @NotNull IInvocation aInvocation, @NotNull DeducePhase aDeducePhase) {
		@NotNull FunctionInvocation fi = aDeducePhase.newFunctionInvocation(aFunctionDef, aPte, aInvocation);
		// TODO register here
		return fi;
	}

	public OS_Type gt(@NotNull GenType aType) {
		return aType.getResolved() != null ? aType.getResolved() : aType.getTypeName();
	}

	void implement_construct(BaseEvaFunction generatedFunction, Instruction instruction, final Context aContext) {
		final @NotNull Implement_construct ic = _inj().new_Implement_construct(this, generatedFunction, instruction);
		try {
			ic.action(aContext);
		} catch (FCA_Stop e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@NotNull List<TypeTableEntry> getPotentialTypesVte(@NotNull VariableTableEntry vte) {
		return _inj().new_ArrayList__TypeTableEntry(vte.potentialTypes());
	}

	private void __post_deferred_calls(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Context fd_ctx) {
		//
		// NOW CALCULATE DEFERRED CALLS
		//
		for (final Integer deferred_call : generatedFunction.deferred_calls) {
			final Instruction instruction = generatedFunction.getInstruction(deferred_call);

			final int                     i1  = to_int(instruction.getArg(0));
			final InstructionArgument     i2  = (instruction.getArg(1));
			final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
			{
//				generatedFunction.deferred_calls.remove(deferred_call);
				Implement_Calls_ ic = _inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1, instruction.getIndex(), this);
				ic.action();
			}
		}
	}

//	private GeneratedNode makeNode(GenType aGenType) {
//		if (aGenType.ci instanceof ClassInvocation) {
//			final ClassInvocation ci = (ClassInvocation) aGenType.ci;
//			@NotNull GenerateFunctions gen = phase.generatePhase.getGenerateFunctions(ci.getKlass().getContext().module());
//			WlGenerateClass wlgc = _inj().new_WlGenerateClass(gen, ci, phase.generatedClasses);
//			wlgc.run(null);
//			return wlgc.getResult();
//		}
//		return null;
//	}

	private void do_assign_constant(final @NotNull BaseEvaFunction generatedFunction, final @NotNull Instruction instruction, final @NotNull IdentTableEntry idte, final @NotNull ConstTableIA i2) {
		if (idte.type != null && idte.type.getAttached() != null) {
			// TODO check types
		}
		final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(i2.getIndex());
		if (cte.type.getAttached() == null) {
			LOG.err("*** ERROR: Null type in CTE " + cte);
		}
		// idte.type may be null, but we still addPotentialType here
		idte.addPotentialType(instruction.getIndex(), cte.type);
	}

	public boolean deduceOneConstructor(@NotNull EvaConstructor aEvaConstructor, @NotNull DeducePhase aDeducePhase) {
		var ce = aDeducePhase.ca().getCompilation().getCompilationEnclosure();
		var mt = ce.addModuleThing(aEvaConstructor.module());

		if (aEvaConstructor.deducedAlready) return false;
		deduce_generated_function_base(aEvaConstructor, aEvaConstructor.getFD(), mt);
		aEvaConstructor.deducedAlready = true;

		final Eventual<DeduceElement3_Constructor> deduceElement3ConstructorEventual = aEvaConstructor.de3_Promise();

		var ccc = new DeduceElement3_Constructor(aEvaConstructor, this);
		deduceElement3ConstructorEventual.resolve(ccc);

		deduceElement3ConstructorEventual.then((DeduceElement3_Constructor c) -> c.__post_deduce_generated_function_base(aDeducePhase));
		return true;
	}

	@NotNull DeferredMemberFunction deferred_member_function(OS_Element aParent, @Nullable IInvocation aInvocation, @NotNull FunctionDef aFunctionDef, final @NotNull FunctionInvocation aFunctionInvocation) {
		if (aInvocation == null) {
			if (aParent instanceof NamespaceStatement)
				aInvocation = phase.registerNamespaceInvocation((NamespaceStatement) aParent);
			else if (aParent instanceof OS_SpecialVariable) {
				aInvocation = ((OS_SpecialVariable) aParent).getInvocation(this);
			}
		}
		DeferredMemberFunction dm = _inj().new_DeferredMemberFunction(aParent, aInvocation, aFunctionDef, this, aFunctionInvocation);
		phase.addDeferredMember(dm);
		return dm;
	}

	public DG_AliasStatement DG_AliasStatement(final AliasStatementImpl aE, final DeduceTypes2 aDt2) {
		if (_map_dgs.containsKey(aE)) {
			return (DG_AliasStatement) _map_dgs.get(aE);
		}

		final DG_AliasStatement R = _inj().new_DG_AliasStatement(aE, aDt2);
		_map_dgs.put(aE, R);
		return R;
	}

	public DG_ClassStatement DG_ClassStatement(final ClassStatement aClassStatement) {
		if (_map_dgs.containsKey(aClassStatement)) {
			return (DG_ClassStatement) _map_dgs.get(aClassStatement);
		}

		final DG_ClassStatement R = _inj().new_DG_ClassStatement(aClassStatement);
		_map_dgs.put(aClassStatement, R);
		return R;
	}

	public DG_FunctionDef DG_FunctionDef(final FunctionDef aFunctionDef) {
		if (_map_dgs.containsKey(aFunctionDef)) {
			return (DG_FunctionDef) _map_dgs.get(aFunctionDef);
		}

		final DG_FunctionDef R = _inj().new_DG_FunctionDef(aFunctionDef);
		_map_dgs.put(aFunctionDef, R);
		return R;
	}

	@NotNull
	public List<TypeTableEntry> getPotentialTypesVte(@NotNull EvaFunction generatedFunction, @NotNull InstructionArgument vte_index) {
		return getPotentialTypesVte(generatedFunction.getVarTableEntry(to_int(vte_index)));
	}

	void onFinish(Runnable r) {
		onRunnables.add(r);
	}

	/*static*/
	@NotNull GenType resolve_type(final @NotNull OS_Module module, final @NotNull OS_Type type, final Context ctx) throws ResolveError {
		return ResolveType.resolve_type(module, type, ctx, LOG, this);
	}

	public void resolveIdentIA_(@NotNull Context context, @NotNull DeduceElementIdent dei, BaseEvaFunction generatedFunction, @NotNull FoundElement foundElement) {
		@NotNull Resolve_Ident_IA ria = _inj().new_Resolve_Ident_IA(_inj().new_DeduceClient3(this), context, dei, generatedFunction, foundElement, errSink);
		ria.action();
	}

	public DeduceElement3_ProcTableEntry zeroGet(final ProcTableEntry aPte, final BaseEvaFunction aEvaFunction) {
		return _p_zero.get(aPte, aEvaFunction, this);
	}

	public String getFileName() {
		return module.getFileName();
	}

	public DeduceElement3_VariableTableEntry zeroGet(final VariableTableEntry aVte, final BaseEvaFunction aGeneratedFunction) {
		return _p_zero.get(aVte, aGeneratedFunction);
	}

	private @Nullable IInvocation getInvocationFromBacklink(@Nullable InstructionArgument aBacklink) {
		if (aBacklink == null) return null;
		// TODO implement me
		return null;
	}

	boolean lookup_name_calls(final @NotNull Context ctx, final @NotNull String pn, final @NotNull ProcTableEntry pte) {
		final LookupResultList     lrl  = ctx.lookup(pn);
		final @Nullable OS_Element best = lrl.chooseBest(null); // TODO check arity and arg matching
		if (best != null) {
			pte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_ConstructableElementHolder(best, null)); // TODO why include if only to be null?
			return true;
		}
		return false;
	}

	public void register_and_resolve(@NotNull VariableTableEntry aVte, @NotNull ClassStatement aKlass) {
		@Nullable ClassInvocation ci = _inj().new_ClassInvocation(aKlass, null, new ReadySupplier_1<>(this));
		ci = phase.registerClassInvocation(ci);
		ci.resolvePromise().done(aVte::resolveTypeToClass);
	}

	public void removeResolvePending(final IdentTableEntry aResolvable) {
		assert hasResolvePending(aResolvable);

		_pendingResolves.remove(aResolvable);
	}

	public void activePTE(@NotNull ProcTableEntry pte, ClassInvocation classInvocation) {
		// TODO Auto-generated method stub
		_actives.add(_inj().new_DE3_ActivePTE(this, pte, classInvocation));
	}

	public void addExternal(final DT_External aExt) {
		externals.add(aExt);
	}

	public @NotNull Zero _zero() {
		return _p_zero;
	}

	public enum ClassInvocationMake {
		;

		public static @NotNull Operation<ClassInvocation> withGenericPart(@NotNull ClassStatement best,
																		  String constructorName,
																		  @Nullable NormalTypeName aTyn1,
																		  @NotNull DeduceTypes2 dt2) {
			if (aTyn1 == null) {
				//throw new IllegalStateException("blank typename");
			}

			@NotNull GenericPart genericPart = dt2._inj().new_GenericPart(best, aTyn1);

			@Nullable ClassInvocation clsinv = dt2._inj().new_ClassInvocation(best, constructorName, new ReadySupplier_1<>(dt2));

			if (genericPart.hasGenericPart()) {
				final @NotNull List<TypeName> gp  = best.getGenericPart();
				final @Nullable TypeNameList  gp2 = genericPart.getGenericPartFromTypeName();

				if (gp2 != null) {
					for (int i = 0; i < gp.size(); i++) {
						final TypeName   typeName = gp2.get(i);
						@NotNull GenType typeName2;
						try {
							typeName2 = dt2.resolve_type(dt2._inj().new_OS_UserType(typeName), typeName.getContext());
							// TODO transition to GenType
							clsinv.set(i, gp.get(i), typeName2.getResolved());
						} catch (ResolveError aResolveError) {
							return Operation.failure(aResolveError);
						}
					}
				}
			}
			return Operation.success(clsinv);
		}
	}

	interface df_helper<T> {
		@NotNull Collection<T> collection();

		boolean deduce(T generatedConstructor);
	}

	interface df_helper_i<T> {
		@Nullable df_helper<T> get(EvaContainerNC generatedClass);
	}

	public interface ExpectationBase {
		String expectationString();
	}

	public interface IElementProcessor {
		void elementIsNull();

		void hasElement(OS_Element el);
	}

	interface IVariableConnector {
		void connect(VariableTableEntry aVte, String aName);
	}

	static class CtorConnector implements IVariableConnector {
		private final EvaConstructor evaConstructor;

		public CtorConnector(final EvaConstructor aEvaConstructor) {
			evaConstructor = aEvaConstructor;
		}

		@Override
		public void connect(final VariableTableEntry aVte, final String aName) {
			final List<EvaContainer.VarTableEntry> vt = ((EvaClass) evaConstructor.getGenClass()).varTable;
			for (EvaContainer.VarTableEntry gc_vte : vt) {
				if (gc_vte.nameToken.getText().equals(aName)) {
					gc_vte.connect(aVte, evaConstructor);
					break;
				}
			}
		}
	}

	public enum ProcessElement {
		;

		public static void processElement(@Nullable OS_Element el, @NotNull IElementProcessor ep) {
			if (el == null)
				ep.elementIsNull();
			else
				ep.hasElement(el);
		}
	}

	public static class DeduceClient2 {
		private final DeduceTypes2 deduceTypes2;

		public DeduceClient2(DeduceTypes2 deduceTypes2) {
			this.deduceTypes2 = deduceTypes2;
		}

		public DeduceTypes2 deduceTypes2() {
			return deduceTypes2;
		}

		public @NotNull ClassInvocation genCI(@NotNull GenType genType, TypeName typeName) {
			return genType.genCI(typeName, deduceTypes2, deduceTypes2.errSink, deduceTypes2.phase);
		}

		public @NotNull ElLog getLOG() {
			return deduceTypes2.LOG;
		}

		public @NotNull FunctionInvocation newFunctionInvocation(FunctionDef constructorDef, ProcTableEntry pte, @NotNull IInvocation ci) {
			return deduceTypes2.newFunctionInvocation(constructorDef, pte, ci, deduceTypes2.phase);
		}

		// TODO 11/10 begging for promiseification
		//public @Nullable ClassInvocation registerClassInvocation(@NotNull ClassInvocation ci) {
		//	return deduceTypes2.phase.registerClassInvocation(new RegisterClassInvocation_env(ci, deduceTypes2, deduceTypes2.phase));
		//}

		public NamespaceInvocation registerNamespaceInvocation(NamespaceStatement namespaceStatement) {
			return deduceTypes2.phase.registerNamespaceInvocation(namespaceStatement);
		}
	}

	public static class DeduceClient1 {
		private final DeduceTypes2 dt2;

		@Contract(pure = true)
		public DeduceClient1(DeduceTypes2 aDeduceTypes2) {
			dt2 = aDeduceTypes2;
		}

		public @Nullable OS_Element _resolveAlias(@NotNull AliasStatementImpl aAliasStatement) {
			return DeduceLookupUtils._resolveAlias(aAliasStatement, dt2);
		}

		public @NotNull DeferredMember deferred_member(DeduceElementWrapper aParent, IInvocation aInvocation, VariableStatementImpl aVariableStatement, @NotNull IdentTableEntry aIdentTableEntry) {
			return dt2.deferred_member(aParent, aInvocation, aVariableStatement, aIdentTableEntry);
		}

		public void found_element_for_ite(BaseEvaFunction aGeneratedFunction, @NotNull IdentTableEntry aIte, OS_Element aX, Context aCtx) {
			dt2.found_element_for_ite(aGeneratedFunction, aIte, aX, aCtx, dt2.central());
		}

		public void genCI(final @NotNull GenType aResult, final TypeName aNonGenericTypeName) {
			aResult.genCI(aNonGenericTypeName, dt2, dt2.errSink, dt2.phase);
		}

		public void genCIForGenType2(final @NotNull GenType genType) {
			genType.genCIForGenType2(dt2);
		}

		public @Nullable IInvocation getInvocationFromBacklink(InstructionArgument aInstructionArgument) {
			return dt2.getInvocationFromBacklink(aInstructionArgument);
		}

		public @NotNull List<TypeTableEntry> getPotentialTypesVte(@NotNull VariableTableEntry aVte) {
			return dt2.getPotentialTypesVte(aVte);
		}

		public void LOG_err(String aS) {
			dt2.LOG.err(aS);
		}

		public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement aClassStatement, final String aS) {
			return dt2.phase.registerClassInvocation(aClassStatement, aS, new ReadySupplier_1<>(dt2));
		}

		public @NotNull GenType resolve_type(@NotNull OS_Type aType, Context aCtx) throws ResolveError {
			return dt2.resolve_type(aType, aCtx);
		}

		public DeduceTypes2Injector _inj() {
			return dt2._inj();
		}

		public DeduceTypes2 _deduceTypes2() {
			return dt2;
		}
	}

	public static class DeduceClient3 {
		final DeduceTypes2 deduceTypes2;

		public DeduceClient3(final DeduceTypes2 aDeduceTypes2) {
			deduceTypes2 = aDeduceTypes2;
		}

		public void addJobs(final WorkList aWl) {
			deduceTypes2.wm.addJobs(aWl);
		}

		public void found_element_for_ite(final BaseEvaFunction generatedFunction,
										  final @NotNull IdentTableEntry ite,
										  final @Nullable OS_Element y,
										  final Context ctx) {
			deduceTypes2.found_element_for_ite(generatedFunction, ite, y, ctx, deduceTypes2.central());
		}

		public void genCIForGenType2(final @NotNull GenType genType) {
			genType.genCIForGenType2(deduceTypes2);
		}

		public @NotNull GenerateFunctions getGenerateFunctions(final @NotNull OS_Module aModule) {
			return deduceTypes2.getGenerateFunctions(aModule);
		}

		public IInvocation getInvocation(final @NotNull EvaFunction aGeneratedFunction) {
			return deduceTypes2.getInvocation(aGeneratedFunction);
		}

		public @NotNull ElLog getLOG() {
			return deduceTypes2.LOG;
		}

		public @NotNull DeducePhase getPhase() {
			return deduceTypes2.phase;
		}

		public @NotNull List<TypeTableEntry> getPotentialTypesVte(final @NotNull VariableTableEntry aVte) {
			return deduceTypes2.getPotentialTypesVte(aVte);
		}

		public LookupResultList lookupExpression(final @NotNull IExpression aExp, final @NotNull Context aContext) throws ResolveError {
			return DeduceLookupUtils.lookupExpression(aExp, aContext, deduceTypes2);
		}

		public @NotNull FunctionInvocation newFunctionInvocation(final BaseFunctionDef aFunctionDef, final ProcTableEntry aPte, final @NotNull IInvocation aInvocation) {
			return deduceTypes2.newFunctionInvocation(aFunctionDef, aPte, aInvocation, deduceTypes2.phase);
		}

		public @NotNull IElementHolder newGenericElementHolderWithType(final @NotNull OS_Element aElement, final @NotNull TypeName aTypeName) {
			final OS_Type typeName;
			if (aTypeName.isNull())
				typeName = null;
			else
				typeName = this.deduceTypes2._inj().new_OS_UserType(aTypeName);
			return this.deduceTypes2._inj().new_GenericElementHolderWithType(aElement, typeName, deduceTypes2);
		}

		public @NotNull GenType resolve_type(final @NotNull OS_Type aType, final Context aContext) throws ResolveError {
			return deduceTypes2.resolve_type(aType, aContext);
		}

		public void resolveIdentIA2_(final @NotNull Context aEctx,
									 final IdentIA aIdentIA,
									 final @Nullable List<InstructionArgument> ss,
									 final @NotNull BaseEvaFunction aGeneratedFunction,
									 final @NotNull FoundElement aFoundElement) {
			// README 11/10 overcomplication, but nicer
			MonitorRequest_IdentTableEntry mr = new MonitorRequest_IdentTableEntry(aIdentIA);
			mr.trigger(aEctx, ss, aFoundElement, deduceTypes2);
			aGeneratedFunction.monitorRequest_IdentTableEntry(mr);
/*
			//  overcomplication b/c we don't need gf
			//  nicer b/c we never not have gf
			aGeneratedFunction.onInformGF(gf -> {
				@NotNull final IdentTableEntry entry = aIdentIA.getEntry();
				final GDM_IdentExpression      mie   = gf.monitor(entry.getIdent());
				mie.trigger_resolve(aEctx, ss, aFoundElement, deduceTypes2, aGeneratedFunction);
			});
*/
//			deduceTypes2.resolveIdentIA2_(aEctx, aIdentIA, ss, aGeneratedFunction, aFoundElement);
		}

		public DeduceTypes2 _deduceTypes2() {
			return deduceTypes2;
		}
	}

	static class NullConnector implements IVariableConnector {
		@Override
		public void connect(final VariableTableEntry aVte, final String aName) {
		}
	}

	static class GenericPart {
		private final ClassStatement classStatement;
		private final TypeName       genericTypeName;

		@Contract(pure = true)
		public GenericPart(final ClassStatement aClassStatement, final TypeName aGenericTypeName) {
			classStatement  = aClassStatement;
			genericTypeName = aGenericTypeName;
		}

		@Contract(pure = true)
		public @Nullable TypeNameList getGenericPartFromTypeName() {
			final NormalTypeName ntn = getGenericTypeName();
			if (ntn == null) return null;
			return ntn.getGenericPart();
		}

		@Contract(pure = true)
		private @Nullable NormalTypeName getGenericTypeName() {
			//assert genericTypeName != null;
			if (genericTypeName == null) {

				System.err.println("1860 who cares // assert genericTypeName != null");

			}
/*
			for (boolean aB : _inj().new_boolean[]{genericTypeName != null, genericTypeName instanceof NormalTypeName}) {
				assert aB;
			}
*/

			return (NormalTypeName) genericTypeName;
		}

		@Contract(pure = true)
		public boolean hasGenericPart() {
			return classStatement.getGenericPart().size() > 0;
		}
	}

	public static class OS_SpecialVariable implements OS_Element {
		private final BaseEvaFunction                      generatedFunction;
		private final VariableTableType                    type;
		private final VariableTableEntry                   variableTableEntry;
		public        DeduceLocalVariable.MemberInvocation memberInvocation;

		public OS_SpecialVariable(final VariableTableEntry aVariableTableEntry, final VariableTableType aType, final BaseEvaFunction aGeneratedFunction) {
			variableTableEntry = aVariableTableEntry;
			type               = aType;
			generatedFunction  = aGeneratedFunction;
		}

		@Override
		public Context getContext() {
			return generatedFunction.getFD().getContext();
		}

		@Nullable
		public IInvocation getInvocation(final @NotNull DeduceTypes2 aDeduceTypes2) {
			final @Nullable IInvocation aInvocation;
			final OS_SpecialVariable    specialVariable = this;
			assert specialVariable.type == VariableTableType.SELF;
			// first parent is always a function
			switch (DecideElObjectType.getElObjectType(specialVariable.getParent().getParent())) {
			case CLASS:
				final ClassStatement classStatement = (ClassStatement) specialVariable.getParent().getParent();
				aInvocation = aDeduceTypes2.phase.registerClassInvocation(classStatement, null, new ReadySupplier_1<>(aDeduceTypes2)); // TODO generics
//				ClassInvocationMake.withGenericPart(classStatement, null, null, this);
				break;
			case NAMESPACE:
				throw new NotImplementedException(); // README ha! implemented in
			default:
				throw new IllegalArgumentException("Illegal object type for parent");
			}
			return aInvocation;
		}

		@Override
		public void visitGen(final ElElementVisitor visit) {
			throw new IllegalArgumentException("not implemented");
		}

		@Override
		public void serializeTo(final SmallWriter sw) {

		}

		@Override
		public @NotNull OS_Element getParent() {
			return generatedFunction.getFD();
		}
	}

	class df_helper_Constructors implements df_helper<EvaConstructor> {
		private final EvaClass evaClass;

		public df_helper_Constructors(EvaClass aEvaClass) {
			evaClass = aEvaClass;
		}

		@Override
		public @NotNull Collection<EvaConstructor> collection() {
			return evaClass.constructors.values();
		}

		@Override
		public boolean deduce(@NotNull EvaConstructor aEvaConstructor) {
			return deduceOneConstructor(aEvaConstructor, phase);
		}
	}

	class df_helper_Functions implements df_helper<EvaFunction> {
		private final EvaContainerNC generatedContainerNC;

		public df_helper_Functions(EvaContainerNC aGeneratedContainerNC) {
			generatedContainerNC = aGeneratedContainerNC;
		}

		@Override
		public @NotNull Collection<EvaFunction> collection() {
			return generatedContainerNC.functionMap.values();
		}

		@Override
		public boolean deduce(@NotNull EvaFunction aGeneratedFunction) {
			return deduceOneFunction(aGeneratedFunction, phase);
		}
	}

	class dfhi_constructors implements df_helper_i<EvaConstructor> {
		@Override
		public @Nullable df_helper_Constructors get(EvaContainerNC aGeneratedContainerNC) {
			if (aGeneratedContainerNC instanceof EvaClass) // TODO namespace constructors
				return new df_helper_Constructors((EvaClass) aGeneratedContainerNC);
			else
				return null;
		}
	}

	class dfhi_functions implements df_helper_i<EvaFunction> {
		@Override
		public @NotNull df_helper_Functions get(EvaContainerNC aGeneratedContainerNC) {
			return new df_helper_Functions(aGeneratedContainerNC);
		}
	}

	public class DeduceClient4 {
		private final DeduceTypes2 deduceTypes2;

		public DeduceClient4(final DeduceTypes2 aDeduceTypes2) {
			deduceTypes2 = aDeduceTypes2;
		}

		public @Nullable OS_Element _resolveAlias(final @NotNull AliasStatement aAliasStatement) {
			return DeduceLookupUtils._resolveAlias(aAliasStatement, deduceTypes2);
		}

		public @Nullable OS_Element _resolveAlias2(final @NotNull AliasStatementImpl aAliasStatement) throws ResolveError {
			return DeduceLookupUtils._resolveAlias2(aAliasStatement, deduceTypes2);
		}

		public @NotNull DeferredMemberFunction deferred_member_function(final OS_Element aParent, final IInvocation aInvocation, final @NotNull FunctionDef aFunctionDef, final @NotNull FunctionInvocation aFunctionInvocation) {
			return deduceTypes2.deferred_member_function(aParent, aInvocation, aFunctionDef, aFunctionInvocation);
		}

		public void forFunction(final @NotNull FunctionInvocation aFunctionInvocation, final @NotNull ForFunction aForFunction) {
			deduceTypes2.forFunction(aFunctionInvocation, aForFunction);
		}

		public void found_element_for_ite(final BaseEvaFunction aGeneratedFunction, final @NotNull IdentTableEntry aEntry, final OS_Element aE, final Context aCtx) {
			deduceTypes2.found_element_for_ite(aGeneratedFunction, aEntry, aE, aCtx, central());
		}

		public ClassInvocation genCI(final @NotNull GenType aType, final TypeName aGenericTypeName) {
			return aType.genCI(aGenericTypeName, deduceTypes2, deduceTypes2.errSink, deduceTypes2.phase);
		}

		public DeduceTypes2 get() {
			return deduceTypes2;
		}

		public ErrSink getErrSink() {
			return deduceTypes2.errSink;
		}

		public IInvocation getInvocation(final @NotNull EvaFunction aGeneratedFunction) {
			return deduceTypes2.getInvocation(aGeneratedFunction);
		}

		public @NotNull ElLog getLOG() {
			return LOG;
		}

		public @NotNull OS_Module getModule() {
			return module;
		}

		public @NotNull DeducePhase getPhase() {
			return deduceTypes2.phase;
		}

		public @NotNull List<TypeTableEntry> getPotentialTypesVte(final @NotNull EvaFunction aGeneratedFunction, final @NotNull InstructionArgument aVte_ia) {
			return deduceTypes2.getPotentialTypesVte(aGeneratedFunction, aVte_ia);
		}

		public OS_Type gt(final @NotNull GenType aType) {
			return deduceTypes2.gt(aType);
		}

		public void implement_calls(final @NotNull BaseEvaFunction aGeneratedFunction, final @NotNull Context aParent, final @NotNull InstructionArgument aArg, final @NotNull ProcTableEntry aPte, final int aInstructionIndex) {
			if (aGeneratedFunction.deferred_calls.contains(aInstructionIndex)) {
				deduceTypes2.LOG.err("Call is deferred "/*+gf.getInstruction(pc)*/ + " " + aPte);
				return;
			}
			Implement_Calls_ ic = _inj().new_Implement_Calls_(aGeneratedFunction, aParent, aArg, aPte, aInstructionIndex, deduceTypes2);
			ic.action();
		}

		public @Nullable OS_Element lookup(final @NotNull IdentExpression aElement, final @NotNull Context aContext) throws ResolveError {
			return DeduceLookupUtils.lookup(aElement, aContext, deduceTypes2);
		}

		public LookupResultList lookupExpression(final @NotNull IExpression aExpression, final @NotNull Context aContext) throws ResolveError {
			return DeduceLookupUtils.lookupExpression(aExpression, aContext, deduceTypes2);
		}

		public @NotNull FunctionInvocation newFunctionInvocation(final FunctionDef aElement, final ProcTableEntry aPte, final @NotNull IInvocation aInvocation) {
			return deduceTypes2.newFunctionInvocation(aElement, aPte, aInvocation, deduceTypes2.phase);
		}

		public void onFinish(final Runnable aRunnable) {
			deduceTypes2.onFinish(aRunnable);
		}

		public <T> @NotNull PromiseExpectation<T> promiseExpectation(final BaseEvaFunction aGeneratedFunction, final String aName) {
			return deduceTypes2.promiseExpectation(aGeneratedFunction, aName);
		}

		public void register_and_resolve(final @NotNull VariableTableEntry aVte, final @NotNull ClassStatement aClassStatement) {
			deduceTypes2.register_and_resolve(aVte, aClassStatement);
		}

		public @NotNull ClassInvocation registerClassInvocation(final @NotNull ClassInvocation aCi) {
			return deduceTypes2.phase.registerClassInvocation(aCi);
		}

		public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement aClassStatement, final String constructorName) {
			return deduceTypes2.phase.registerClassInvocation(aClassStatement, constructorName, new ReadySupplier_1<>(deduceTypes2));
		}

		public NamespaceInvocation registerNamespaceInvocation(final NamespaceStatement aNamespaceStatement) {
			return deduceTypes2.phase.registerNamespaceInvocation(aNamespaceStatement);
		}

		public void reportDiagnostic(final ResolveError aResolveError) {
			deduceTypes2.errSink.reportDiagnostic(aResolveError);
		}

		public @NotNull GenType resolve_type(final @NotNull OS_Type aTy, final Context aCtx) throws ResolveError {
			return deduceTypes2.resolve_type(aTy, aCtx);
		}

		public void resolveIdentIA_(final @NotNull Context aCtx, final @NotNull IdentIA aIdentIA, final @NotNull BaseEvaFunction aGeneratedFunction, final @NotNull FoundElement aFoundElement) {
			deduceTypes2.resolveIdentIA_(aCtx, aIdentIA, aGeneratedFunction, aFoundElement);
		}
	}

	public class PromiseExpectation<B> {

		private final ExpectationBase base;
		private final String          desc;
		private       boolean         _printed;
		private       long            counter;
		private       B               result;
		private       boolean         satisfied;

		public PromiseExpectation(ExpectationBase aBase, String aDesc) {
			base = aBase;
			desc = aDesc;
		}

		public void fail() {
			if (!_printed) {
				LOG.err(String.format("Expectation (%s, %d) not met: %s", DeduceTypes2.this, counter, desc));
				_printed = true;
			}
		}

		public boolean isSatisfied() {
			return satisfied;
		}

		public void satisfy(B aResult) {
			final String satisfied_already = satisfied ? " already" : "";
			//assert !satisfied;
			if (!satisfied) {
				result    = aResult;
				satisfied = true;
				LOG.info(String.format("Expectation (%s, %d)%s met: %s %s", DeduceTypes2.this, counter, satisfied_already, desc, base.expectationString()));
			}
		}

		public void setCounter(long aCounter) {
			counter = aCounter;

///////			LOG.info(String.format("Expectation (%s, %d) set: %s %s", DeduceTypes2.this, counter, desc, base.expectationString()));
		}
	}

	class PromiseExpectations {
		long counter = 0;

		@NotNull List<PromiseExpectation> exp = new ArrayList<>();

		public void add(@NotNull PromiseExpectation aExpectation) {
			counter++;
			aExpectation.setCounter(counter);
			exp.add(aExpectation);
		}

		public void check() {
			for (@NotNull PromiseExpectation promiseExpectation : exp) {
				if (!promiseExpectation.isSatisfied())
					promiseExpectation.fail();
			}
		}
	}

	class Resolve_each_typename {

		private final DeduceTypes2 dt2;
		private final ErrSink      errSink;
		private final DeducePhase  phase;

		public Resolve_each_typename(final DeducePhase aPhase, final DeduceTypes2 aDeduceTypes2, final ErrSink aErrSink) {
			phase   = aPhase;
			dt2     = aDeduceTypes2;
			errSink = aErrSink;
		}

		public void action(@NotNull final TypeTableEntry typeTableEntry) {
			@Nullable final OS_Type attached = typeTableEntry.getAttached();
			if (attached == null) return;
			if (attached.getType() == OS_Type.Type.USER) {
				action_USER(typeTableEntry, attached);
			} else if (attached.getType() == OS_Type.Type.USER_CLASS) {
				action_USER_CLASS(typeTableEntry, attached);
			}
		}

		public void action_USER(@NotNull final TypeTableEntry typeTableEntry, @Nullable final OS_Type aAttached) {
			final TypeName tn = aAttached.getTypeName();
			if (tn == null) return; // hack specifically for Result
			switch (tn.kindOfType()) {
			case FUNCTION:
			case GENERIC:
			case TYPE_OF:
				return;
			}
			try {
				typeTableEntry.setAttached(dt2.resolve_type(aAttached, aAttached.getTypeName().getContext()));
				switch (typeTableEntry.getAttached().getType()) {
				case USER_CLASS:
					action_USER_CLASS(typeTableEntry, typeTableEntry.getAttached());
					break;
				case GENERIC_TYPENAME:
					LOG.err(String.format("801 Generic Typearg %s for %s", tn, "genericFunction.getFD().getParent()"));
					break;
				default:
					LOG.err("245 typeTableEntry attached wrong type " + typeTableEntry);
					break;
				}
			} catch (final ResolveError aResolveError) {
				LOG.err("288 Failed to resolve type " + aAttached);
				errSink.reportDiagnostic(aResolveError);
			}
		}

		public void action_USER_CLASS(@NotNull final TypeTableEntry typeTableEntry, @NotNull final OS_Type aAttached) {
			final ClassStatement c = aAttached.getClassOf();
			assert c != null;
			phase.onClass(c, cc -> typeTableEntry.resolve(cc));
		}
	}

	class WlDeduceFunction implements WorkJob {
		private final List<BaseEvaFunction> coll;
		private final WorkJob               workJob;
		private       boolean               _isDone;

		public WlDeduceFunction(final WorkJob aWorkJob, List<BaseEvaFunction> aColl) {
			workJob = aWorkJob;
			coll    = aColl;
		}

		@Override
		public boolean isDone() {
			return _isDone;
		}

		@Override
		public void run(final WorkManager aWorkManager) {
			// TODO assumes result is in the same file as this (DeduceTypes2)

			if (workJob instanceof WlGenerateFunction) {
				final EvaFunction generatedFunction1 = ((WlGenerateFunction) workJob).getResult();
				if (!coll.contains(generatedFunction1)) {
					coll.add(generatedFunction1);
					if (!generatedFunction1.deducedAlready) {
						var ce = _phase().ca().getCompilation().getCompilationEnclosure();
						var mt = ce.addModuleThing(generatedFunction1.module());


						deduce_generated_function(generatedFunction1, mt);
					}
					generatedFunction1.deducedAlready = true;
				}
			} else if (workJob instanceof WlGenerateDefaultCtor) {
				final EvaConstructor evaConstructor = (EvaConstructor) ((WlGenerateDefaultCtor) workJob).getResult();
				if (!coll.contains(evaConstructor)) {
					coll.add(evaConstructor);
					if (!evaConstructor.deducedAlready) {
						deduce_generated_constructor(evaConstructor);
					}
					evaConstructor.deducedAlready = true;
				}
			} else if (workJob instanceof WlGenerateCtor) {
				final EvaConstructor evaConstructor = ((WlGenerateCtor) workJob).getResult();
				if (!coll.contains(evaConstructor)) {
					coll.add(evaConstructor);
					if (!evaConstructor.deducedAlready) {
						deduce_generated_constructor(evaConstructor);
					}
					evaConstructor.deducedAlready = true;
				}
			} else
				throw new NotImplementedException();

			assert coll.size() == 1;

			_isDone = true;
		}
	}

	class Implement_Calls_ {
		private final @NotNull Context             context;
		private final @NotNull BaseEvaFunction     gf;
		private final @NotNull InstructionArgument i2;
		private final          int                 pc;
		private final @NotNull ProcTableEntry      pte;

		public Implement_Calls_(final @NotNull BaseEvaFunction aGf,
								final @NotNull Context aContext,
								final @NotNull InstructionArgument aI2,
								final @NotNull ProcTableEntry aPte,
								final int aPc) {
			gf      = aGf;
			context = aContext;
			i2      = aI2;
			pte     = aPte;
			pc      = aPc;
		}

		void action() {
			final IExpression pn1 = pte.__debug_expression;
			if (!(pn1 instanceof IdentExpression)) {
				throw new IllegalStateException("pn1 is not IdentExpression");
			}

			final String pn    = ((IdentExpression) pn1).getText();
			boolean      found = lookup_name_calls(context, pn, pte);
			if (found) return;

			final @Nullable String pn2 = SpecialFunctions.reverse_name(pn);
			if (pn2 != null) {
//				LOG.info("7002 "+pn2);
				found = lookup_name_calls(context, pn2, pte);
				if (found) return;
			}

			if (i2 instanceof IntegerIA) {
				found = action_i2_IntegerIA(pn, pn2);
			} else {
				found = action_dunder(pn);
			}

			if (!found) {
				pte.setStatus(BaseTableEntry.Status.UNKNOWN, null);
			}
		}

		private boolean action_i2_IntegerIA(@NotNull String pn, @Nullable String pn2) {
			boolean                           found;
			final @NotNull VariableTableEntry vte     = gf.getVarTableEntry(to_int(i2));
			final Context                     ctx     = gf.getContextFromPC(pc); // might be inside a loop or something
			final String                      vteName = vte.getName();
			if (vteName != null) {
				found = action_i2_IntegerIA_vteName_is_null(pn, pn2, ctx, vteName);
			} else {
				found = action_i2_IntegerIA_vteName_is_not_null(pn, pn2, vte);
			}
			return found;
		}

		private boolean action_dunder(String pn) {
			assert Pattern.matches("__[a-z]+__", pn);
//			LOG.info(String.format("i2 is not IntegerIA (%s)",i2.getClass().getName()));
			//
			// try to get dunder method from class
			//
			IExpression exp = pte.getArgs().get(0).__debug_expression;
			if (exp instanceof IdentExpression) {
				return action_dunder_doIt(pn, (IdentExpression) exp);
			}
			return false;
		}

		private boolean action_i2_IntegerIA_vteName_is_null(@NotNull String pn, @Nullable String pn2, @NotNull Context ctx, @NotNull String vteName) {
			boolean found = false;
			if (SpecialVariables.contains(vteName)) {
				LOG.err("Skipping special variable " + vteName + " " + pn);
			} else {
				final LookupResultList lrl2 = ctx.lookup(vteName);
//				LOG.info("7003 "+vteName+" "+ctx);
				final @Nullable OS_Element best2 = lrl2.chooseBest(null);
				if (best2 != null) {
					found = lookup_name_calls(best2.getContext(), pn, pte);
					if (found) return true;

					if (pn2 != null) {
						found = lookup_name_calls(best2.getContext(), pn2, pte);
						if (found) return true;
					}

					errSink.reportError("Special Function not found " + pn);
				} else {
					throw new NotImplementedException(); // Cant find vte, should never happen
				}
			}
			return found;
		}

		private boolean action_i2_IntegerIA_vteName_is_not_null(@NotNull String pn, @Nullable String pn2, @NotNull VariableTableEntry vte) {
			final @NotNull List<TypeTableEntry> tt = getPotentialTypesVte(vte);
			if (tt.size() != 1) {
				return false;
			}
			final OS_Type x = tt.get(0).getAttached();
			assert x != null;
			switch (x.getType()) {
			case USER_CLASS -> {
				pot_types_size_is_1_USER_CLASS(pn, pn2, x);
				return true;
			}
			case BUILT_IN -> {
				final Context ctx2 = context;//x.getTypeName().getContext();
				try {
					@NotNull GenType ty2 = resolve_type(x, ctx2);
					pot_types_size_is_1_USER_CLASS(pn, pn2, ty2.getResolved());
					return true;
				} catch (ResolveError resolveError) {
					resolveError.printStackTrace();
					errSink.reportDiagnostic(resolveError);
					return false;
				}
			}
			default -> {
				assert false;
				return false;
			}
			}
		}

		private boolean action_dunder_doIt(String pn, IdentExpression exp) {
			final @NotNull IdentExpression identExpression = exp;
			@Nullable InstructionArgument  vte_ia          = gf.vte_lookup(identExpression.getText());
			if (vte_ia != null) {
				VTE_TypePromises.dunder(pn, (IntegerIA) vte_ia, pte, DeduceTypes2.this);
				return true;
			}
			return false;
		}

		private void pot_types_size_is_1_USER_CLASS(@NotNull String pn, @Nullable String pn2, @NotNull OS_Type x) {
			boolean       found;
			final Context ctx1 = x.getClassOf().getContext();

			found = lookup_name_calls(ctx1, pn, pte);
			if (found) return;

			if (pn2 != null) {
				found = lookup_name_calls(ctx1, pn2, pte);
			}

			if (!found) {
				//throw new NotImplementedException(); // TODO
				errSink.reportError("Special Function not found " + pn);
			}
		}
	}

	public DeduceCreationContext creationContext() {
		return _defaultCreationContext;
	}

	class Dependencies {
		final WorkList    wl = _inj().new_WorkList();
		final WorkManager wm;

		Dependencies(final WorkManager aWm) {
			wm = aWm;
		}

		public void subscribeFunctions(final @NotNull Subject<FunctionInvocation> aDependentFunctionSubject) {
			aDependentFunctionSubject.subscribe(new Observer<FunctionInvocation>() {
				@Override
				public void onSubscribe(@NonNull final Disposable d) {

				}

				@Override
				public void onNext(@NonNull final @NotNull FunctionInvocation aFunctionInvocation) {
					action_function(aFunctionInvocation);
				}

				@Override
				public void onError(@NonNull final Throwable e) {

				}

				@Override
				public void onComplete() {

				}
			});
		}

		public void action_function(@NotNull FunctionInvocation aDependentFunction) {
			final FunctionDef        function = aDependentFunction.getFunction();
			WorkJob                  gen;
			final @NotNull OS_Module mod;
			if (function == LangGlobals.defaultVirtualCtor) {
				ClassInvocation ci = aDependentFunction.getClassInvocation();
				if (ci == null) {
					NamespaceInvocation ni = aDependentFunction.getNamespaceInvocation();
					assert ni != null;
					mod = ni.getNamespace().getContext().module();

					ni.resolvePromise().then(result -> result.dependentFunctions().add(aDependentFunction));
				} else {
					mod = ci.getKlass().getContext().module();
					ci.resolvePromise().then(result -> result.dependentFunctions().add(aDependentFunction));
				}
				final @NotNull GenerateFunctions gf = getGenerateFunctions(mod);
				gen = _inj().new_WlGenerateDefaultCtor(gf, aDependentFunction, creationContext(), phase.codeRegistrar);
			} else {
				mod = function.getContext().module();
				final @NotNull GenerateFunctions gf = getGenerateFunctions(mod);
				gen = _inj().new_WlGenerateFunction(gf, aDependentFunction, phase.codeRegistrar);
			}
			wl.addJob(gen);
			List<BaseEvaFunction> coll = new ArrayList<>();
			wl.addJob(_inj().new_WlDeduceFunction(gen, coll, DeduceTypes2.this));
			wm.addJobs(wl);
		}

		public void subscribeTypes(final @NotNull Subject<GenType> aDependentTypesSubject) {
			aDependentTypesSubject.subscribe(new Observer<GenType>() {
				@Override
				public void onSubscribe(@NonNull final Disposable d) {
				}

				@Override
				public void onError(final Throwable aThrowable) {
				}

				@Override
				public void onComplete() {
				}

				@Override
				public void onNext(final @NotNull GenType aGenType) {
					action_type(aGenType);
				}
			});
		}

		public void action_type(@NotNull GenType genType) {
			// TODO work this out further, maybe like a Deepin flavor
			if (genType.getResolvedn() != null) {
				@NotNull OS_Module               mod = genType.getResolvedn().getContext().module();
				final @NotNull GenerateFunctions gf  = phase.generatePhase.getGenerateFunctions(mod);
				NamespaceInvocation              ni  = phase.registerNamespaceInvocation(genType.getResolvedn());
				@NotNull WlGenerateNamespace     gen = _inj().new_WlGenerateNamespace(gf, ni, phase.generatedClasses, phase.codeRegistrar);

				assert genType.getCi() == null || genType.getCi() == ni;
				genType.setCi(ni);

				wl.addJob(gen);

				ni.resolvePromise().then(result -> {
					genType.setNode(result);
					result.dependentTypes().add(genType);
				});
			} else if (genType.getResolved() != null) {
				if (genType.getFunctionInvocation() != null) {
					action_function(genType.getFunctionInvocation());
					return;
				}

				final ClassStatement             c   = genType.getResolved().getClassOf();
				final @NotNull OS_Module         mod = c.getContext().module();
				final @NotNull GenerateFunctions gf  = phase.generatePhase.getGenerateFunctions(mod);
				@Nullable ClassInvocation        ci;
				if (genType.getCi() == null) {
					ci = _inj().new_ClassInvocation(c, null, new ReadySupplier_1<>(DeduceTypes2.this));
					ci = phase.registerClassInvocation(ci);

					genType.setCi(ci);
				} else {
					assert genType.getCi() instanceof ClassInvocation;
					ci = (ClassInvocation) genType.getCi();
				}

				final Eventual<ClassDefinition> pcd = phase.generateClass(gf, ci);

				pcd.then(result -> {
					final EvaClass genclass = result.getNode();

					genType.setNode(genclass);
					genclass.dependentTypes().add(genType);
				});
			}
			//
			wm.addJobs(wl);
		}
	}

	public class Zero {
		private final Map<Object, IDeduceElement3> l = new HashMap<>();

		public DeduceElement3_Function get(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
			if (l.containsKey(aGeneratedFunction)) {
				return (DeduceElement3_Function) l.get(aGeneratedFunction);
			}

			final DeduceElement3_Function de3 = _inj().new_DeduceElement3_Function(aDeduceTypes2, aGeneratedFunction);
			l.put(aGeneratedFunction, de3);
			return de3;
		}

		public DeduceElement3_ProcTableEntry get(final ProcTableEntry pte, final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
			if (l.containsKey(pte)) {
				return (DeduceElement3_ProcTableEntry) l.get(pte);
			}

			final DeduceElement3_ProcTableEntry de3 = _inj().new_DeduceElement3_ProcTableEntry(pte, aDeduceTypes2, aGeneratedFunction);
			l.put(pte, de3);
			return de3;
		}

		public DeduceElement3_VariableTableEntry get(final VariableTableEntry vte, final BaseEvaFunction aGeneratedFunction) {
			if (l.containsKey(vte)) {
				return (DeduceElement3_VariableTableEntry) l.get(vte);
			}

			final DeduceElement3_VariableTableEntry de3 = _inj().new_DeduceElement3_VariableTableEntry(vte, DeduceTypes2.this, aGeneratedFunction);
			l.put(vte, de3);
			return de3;
		}

		public DeduceElement3_IdentTableEntry getIdent(final IdentTableEntry ite, final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
			if (l.containsKey(ite)) {
				return (DeduceElement3_IdentTableEntry) l.get(ite);
			}

			final DeduceElement3_IdentTableEntry de3 = _inj().new_DeduceElement3_IdentTableEntry(ite);
			de3.setDeduceTypes(aDeduceTypes2, aGeneratedFunction);
			l.put(ite, de3);
			return de3;
		}

		// TODO search living classes?
		public @NotNull List<EvaClass> findClassesFor(ClassStatement classStatement) {
			List<EvaClass> c = _inj().new_LinkedList__EvaClass();

			for (Map.Entry<Object, IDeduceElement3> entry : l.entrySet()) {
				if (entry.getKey() instanceof EvaFunction evaFunction) {
					if (evaFunction.getFD().getParent() == classStatement) {
						var cls = (EvaClass) entry.getValue().generatedFunction().getGenClass();

						assert cls.getKlass() == classStatement;

						c.add(cls);
					}
				}
			}

			return c;
		}
	}

	public static class DeduceTypes2Injector {
		public List<DE3_Active> new_ArrayList__DE3_Active() {
			return new ArrayList<>();
		}

		public DeduceCreationContext new_DefaultDeduceCreationContext(final DeduceTypes2 aDeduceTypes2) {
			return new DefaultDeduceCreationContext(aDeduceTypes2);
		}

		public WorkManager new_WorkManager() {
			return new WorkManager();
		}

		public Zero new_Zero(final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new Zero();
		}

		public List<Reactivable> new_ArrayList__Ables() {
			return new ArrayList<>();
		}

		public GenerateResultSink new_DefaultGenerateResultSink(final IPipelineAccess aPa) {
			return new DefaultGenerateResultSink(aPa);
		}

		public List<EvaClass> new_LinkedList__EvaClass() {
			return new ArrayList<>();
		}

		public ITasticMap new_TasticMap() {
			return new TasticMap_1();
		}

		public DeduceCentral new_DeduceCentral(final DeduceTypes2 aDeduceTypes2) {
			return new DeduceCentral(aDeduceTypes2);
		}

		public Map<OS_Element, DG_Item> new_HashMap_DGS() {
			return new HashMap<>();
		}

		public List<Runnable> new_ArrayList__Runnable() {
			return new ArrayList<>();
		}

		public List<FunctionInvocation> new_ArrayList__FunctionInvocation() {
			return new ArrayList<>();
		}

		public PromiseExpectations new_PromiseExpectations(final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new PromiseExpectations();
		}

		public List<IDeduceResolvable> new_ArrayList__IDeduceResolvable() {
			return new ArrayList<>();
		}

		public ElLog new_ElLog(final String aFileName, final ElLog.Verbosity aVerbosity, final String aPhase) {
			return new ElLog(aFileName, aVerbosity, aPhase);
		}

		public List<DT_External> new_LinkedList__DT_External() {
			return new ArrayList<>();
		}

		public OS_Type new_OS_BuiltinType(final BuiltInTypes aBuiltInType) {
			return new OS_BuiltinType(aBuiltInType);
		}

		public @NotNull DeferredMember new_DeferredMember(final DeduceElementWrapper aParent, final IInvocation aInvocation, final VariableStatementImpl aVariableStatement) {
			return new DeferredMember(aParent, aInvocation, aVariableStatement);
		}

		public WlGenerateClass new_WlGenerateClass(final GenerateFunctions aGenerateFunctions, final ClassInvocation aCi, final DeducePhase.GeneratedClasses aGeneratedClasses, final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateClass(aGenerateFunctions, aCi, aGeneratedClasses, aCodeRegistrar);
		}

		public WlGenerateNamespace new_WlGenerateNamespace(final GenerateFunctions aGenerateFunctions, final NamespaceInvocation aCi, final DeducePhase.GeneratedClasses aGeneratedClasses, final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateNamespace(aGenerateFunctions, aCi, aGeneratedClasses, aCodeRegistrar);
		}

		public ClassInvocation new_ClassInvocation(final ClassStatement aClassOf, final String aO, final @NotNull Supplier<DeduceTypes2> aDeduceTypes2) {
			return new ClassInvocation(aClassOf, aO, aDeduceTypes2);
		}

		public ITastic new_FT_FnCallArgs(final DeduceTypes2 aDeduceTypes2, final FnCallArgs aO) {
			return new FT_FnCallArgs(aDeduceTypes2, aO);
		}

		public CantDecideType new_CantDecideType(final VariableTableEntry aVte, final Collection<TypeTableEntry> aTypeTableEntries) {
			return new CantDecideType(aVte, aTypeTableEntries);
		}

		public IElementHolder new_GenericElementHolder(final OS_Element aBest) {
			return new GenericElementHolder(aBest);
		}

		public GenType new_GenTypeImpl() {
			return new GenTypeImpl();
		}

		public __Add_Proc_Table_Listeners new___Add_Proc_Table_Listeners() {
			return new __Add_Proc_Table_Listeners();
		}

		public <B> PromiseExpectation<B> new_PromiseExpectation(final ExpectationBase aBase, final String aDesc, final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new PromiseExpectation<B>(aBase, aDesc);
		}

		public Implement_Calls_ new_Implement_Calls_(final BaseEvaFunction aGeneratedFunction, final Context aFdCtx, final InstructionArgument aI2, final ProcTableEntry aFn1, final int aPc, final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new Implement_Calls_(aGeneratedFunction, aFdCtx, aI2, aFn1, aPc);
		}

		public EN_Understanding new_ENU_FunctionInvocation(final ProcTableEntry aCallablePte) {
			return new ENU_FunctionInvocation(aCallablePte);
		}

		public IElementHolder new_ConstructableElementHolder(final OS_Element aE, final IdentIA aIdentIA) {
			return new ConstructableElementHolder(aE, aIdentIA);
		}

		public ITE_Resolver new_Unnamed_ITE_Resolver1(final DeduceTypes2 aDeduceTypes2, final IdentTableEntry aIte, final BaseEvaFunction aGeneratedFunction, final Context aContext) {
			return new Unnamed_ITE_Resolver1(aDeduceTypes2, aIte, aGeneratedFunction, aContext);
		}

		public Resolve_Variable_Table_Entry new_Resolve_Variable_Table_Entry(final BaseEvaFunction aGeneratedFunction, final Context aContext, final DeduceTypes2 aDeduceTypes2) {
			return new Resolve_Variable_Table_Entry(aGeneratedFunction, aContext, aDeduceTypes2);
		}

		public IVariableConnector new_CtorConnector(final EvaConstructor aGeneratedFunction) {
			return new CtorConnector(aGeneratedFunction);
		}

		public IVariableConnector new_NullConnector() {
			return new NullConnector();
		}

		public DeduceClient3 new_DeduceClient3(final DeduceTypes2 aDeduceTypes2) {
			return new DeduceClient3(aDeduceTypes2);
		}

		public DeduceClient1 new_DeduceClient1(final DeduceTypes2 aDeduceTypes2) {
			return new DeduceClient1(aDeduceTypes2);
		}

		public DeduceClient4 new_DeduceClient4(final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new DeduceClient4(aDeduceTypes2);
		}

		public Resolve_Ident_IA new_Resolve_Ident_IA(final DeduceClient3 aDeduceClient3, final Context aContext, final IdentIA aIdentIA, final BaseEvaFunction aGeneratedFunction, final FoundElement aFoundElement, final ErrSink aErrSink) {
			return new Resolve_Ident_IA(aDeduceClient3, aContext, aIdentIA, aGeneratedFunction, aFoundElement, aErrSink);
		}

		public Resolve_each_typename new_Resolve_each_typename(final DeducePhase aPhase, final DeduceTypes2 aDeduceTypes2, final ErrSink aErrSink) {
			return aDeduceTypes2.new Resolve_each_typename(aPhase, aDeduceTypes2, aErrSink);
		}

		public Dependencies new_Dependencies(final WorkManager aWorkManager, final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new Dependencies(aWorkManager);
		}

		public WorkList new_WorkList() {
			return new WorkList();
		}

		public DR_Item new_DR_Ident(final IdentTableEntry aIdentTableEntry, final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
			return new DR_Ident(aIdentTableEntry, aGeneratedFunction/*, aDeduceTypes2*/);
		}

		public DT_Env new_DT_Env(final ElLog aLOG, final ErrSink aErrSink, final DeduceCentral aCentral) {
			return new DT_Env(aLOG, aErrSink, aCentral);
		}

		public Found_Element_For_ITE new_Found_Element_For_ITE(final BaseEvaFunction aGeneratedFunction, final Context aCtx, final DT_Env aEnv, final DeduceClient1 aDeduceClient1) {
			return new Found_Element_For_ITE(aGeneratedFunction, aCtx, aEnv, aDeduceClient1);
		}

		public List<TypeTableEntry> new_ArrayList__TypeTableEntry(final Collection<TypeTableEntry> aTypeTableEntries) {
			return new ArrayList<>(aTypeTableEntries);
		}

		public Implement_construct new_Implement_construct(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction, final Instruction aInstruction) {
			return new Implement_construct(aDeduceTypes2, aGeneratedFunction, aInstruction);
		}

		public DeferredMemberFunction new_DeferredMemberFunction(final OS_Element aParent, final IInvocation aInvocation, final FunctionDef aFunctionDef, final DeduceTypes2 aDeduceTypes2, final FunctionInvocation aFunctionInvocation) {
			return new DeferredMemberFunction(aParent, aInvocation, aFunctionDef, aDeduceTypes2, aFunctionInvocation);
		}

		public DG_AliasStatement new_DG_AliasStatement(final AliasStatementImpl aE, final DeduceTypes2 aDt2) {
			return new DG_AliasStatement(aE, aDt2);
		}

		public DG_ClassStatement new_DG_ClassStatement(final ClassStatement aClassStatement) {
			return new DG_ClassStatement(aClassStatement);
		}

		public DG_FunctionDef new_DG_FunctionDef(final FunctionDef aFunctionDef) {
			return new DG_FunctionDef(aFunctionDef);
		}

		public Resolve_Ident_IA new_Resolve_Ident_IA(final DeduceClient3 aDeduceClient3, final Context aContext, final DeduceElementIdent aDei, final BaseEvaFunction aGeneratedFunction, final FoundElement aFoundElement, final ErrSink aErrSink) {
			return new Resolve_Ident_IA(aDeduceClient3, aContext, aDei, aGeneratedFunction, aFoundElement, aErrSink);
		}

		public Resolve_Ident_IA2 new_Resolve_Ident_IA2(final DeduceTypes2 aDeduceTypes2, final ErrSink aErrSink, final DeducePhase aPhase, final BaseEvaFunction aGeneratedFunction, final FoundElement aFoundElement) {
			return new Resolve_Ident_IA2(aDeduceTypes2, aErrSink, aPhase, aGeneratedFunction, aFoundElement);
		}

		public DE3_Active new_DE3_ActivePTE(final DeduceTypes2 aDeduceTypes2, final ProcTableEntry aPte, final ClassInvocation aClassInvocation) {
			return new DE3_ActivePTE(aDeduceTypes2, aPte, aClassInvocation);
		}

		public GenericPart new_GenericPart(final ClassStatement aBest, final NormalTypeName aTyn1) {
			return new GenericPart(aBest, aTyn1);
		}

		public OS_UserType new_OS_UserType(final TypeName aTypeName) {
			return new OS_UserType(aTypeName);
		}

		public DC_ClassNote.DC_ClassNote_DT2 new_DC_ClassNote_DT2(final IdentTableEntry aIte, final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
			return new DC_ClassNote.DC_ClassNote_DT2(aIte, aGeneratedFunction, aDeduceTypes2);
		}

		public DC_ClassNote new_DC_ClassNote(final ClassStatement aE, final Context aCtx, final DeduceCentral aDeduceCentral) {
			return new DC_ClassNote(aE, aCtx, aDeduceCentral);
		}

		public IElementHolder new_GenericElementHolderWithType(final OS_Element aElement, final OS_Type aTypeName, final DeduceTypes2 aDeduceTypes2) {
			return new GenericElementHolderWithType(aElement, aTypeName, aDeduceTypes2);
		}

		public @NotNull WlGenerateDefaultCtor new_WlGenerateDefaultCtor(final GenerateFunctions aGf, final FunctionInvocation aDependentFunction, final DeduceCreationContext aDeduceCreationContext, final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateDefaultCtor(aGf, aDependentFunction, aDeduceCreationContext, aCodeRegistrar);
		}

		public @Nullable WlGenerateFunction new_WlGenerateFunction(final GenerateFunctions aGf, final FunctionInvocation aDependentFunction, final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateFunction(aGf, aDependentFunction, aCodeRegistrar);
		}

		public WorkJob new_WlDeduceFunction(final WorkJob aGen, final List<BaseEvaFunction> aColl, final DeduceTypes2 aDeduceTypes2) {
			return aDeduceTypes2.new WlDeduceFunction(aGen, aColl);
		}

		public DeduceElement3_Function new_DeduceElement3_Function(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
			return new DeduceElement3_Function(aDeduceTypes2, aGeneratedFunction);
		}

		public DeduceElement3_ProcTableEntry new_DeduceElement3_ProcTableEntry(final ProcTableEntry aPte, final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
			return new DeduceElement3_ProcTableEntry(aPte, aDeduceTypes2, aGeneratedFunction);
		}

		public DeduceElement3_VariableTableEntry new_DeduceElement3_VariableTableEntry(final VariableTableEntry aVte, final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
			return new DeduceElement3_VariableTableEntry(aVte, aDeduceTypes2, aGeneratedFunction);
		}

		public DeduceElement3_IdentTableEntry new_DeduceElement3_IdentTableEntry(final IdentTableEntry aIte) {
			return new DeduceElement3_IdentTableEntry(aIte);
		}

		public @NotNull IdentIA new_IdentIA(int index, @NotNull BaseEvaFunction generatedFunction) {
			return new IdentIA(index, generatedFunction);
		}

		public BaseTableEntry.StatusListener new__StatusListener__BTE_203(final DeduceTypeResolve aDeduceTypeResolve) {
			return aDeduceTypeResolve.new _StatusListener__BTE_203();
		}

		public GenType new_GenTypeImpl(final ClassStatement aKlass) {
			return new GenTypeImpl(aKlass);
		}

		public SGTA_SetResolvedClass new_SGTA_SetResolvedClass(final ClassStatement aKlass) {
			return new SGTA_SetResolvedClass(aKlass);
		}

		public EN_Usage new_EN_DeduceUsage(final InstructionArgument aBacklink, final BaseEvaFunction aGf, final IdentTableEntry aIte) {
			return new EN_DeduceUsage(aBacklink, aGf, aIte);
		}

		public IdentTableEntry.ITE_Resolver_Result new_ITE_Resolver_Result(final OS_Element aE) {
			return new IdentTableEntry.ITE_Resolver_Result(aE);
		}

		public EN_Usage new_EN_NameUsage(final EN_Name aName, final DeduceElement3_IdentTableEntry aDe3Ite) {
			return new EN_NameUsage(aName, aDe3Ite);
		}

		public DTR_IdentExpression new_DTR_IdentExpression(final DeduceTypeResolve aDeduceTypeResolve, final IdentExpression aIdentExpression, final BaseTableEntry aBte) {
			return new DTR_IdentExpression(aDeduceTypeResolve, aIdentExpression, aBte);
		}

		public DTR_VariableStatement new_DTR_VariableStatement(final DeduceTypeResolve aDeduceTypeResolve, final VariableStatementImpl aVariableStatement) {
			return new DTR_VariableStatement(aDeduceTypeResolve, aVariableStatement);
		}

		public TypeTableEntry new_TypeTableEntry(final int aI, final TypeTableEntry.Type aType, final OS_Type aTypeName, final IdentExpression aIdent, final TableEntryIV aO) {
			return new TypeTableEntry(aI, aType, aTypeName, aIdent, aO);
		}

		public BaseTableEntry.StatusListener new__StatusListener__BTE_86(final DeduceTypeResolve aDeduceTypeResolve) {
			return aDeduceTypeResolve.new _StatusListener__BTE_86();
		}

		public BaseTableEntry.StatusListener new_StatusListener__RIA__176(final IdentTableEntry aY, final FoundElement aFoundElement, final Resolve_Ident_IA aResolve_Ident_IA) {
			return aResolve_Ident_IA.new StatusListener__RIA__176(aY, aFoundElement);
		}

		public WlGenerateCtor new_WlGenerateCtor(final GenerateFunctions aGenerateFunctions, final FunctionInvocation aFi, final IdentExpression aO, final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateCtor(aGenerateFunctions, aFi, aO, aCodeRegistrar);
		}

		public GenType new_GenTypeImpl(final NamespaceStatement aParent) {
			return new GenTypeImpl(aParent);
		}

		public EN_Understanding new_ENU_AliasedFrom(final AliasStatement aOrigE) {
			return new ENU_AliasedFrom(aOrigE);
		}

		public FT_FCA_FunctionDef new_FT_FCA_FunctionDef(final FunctionDef aEl, final DeduceTypes2 aDeduceTypes2) {
			return new FT_FCA_FunctionDef(aEl, aDeduceTypes2);
		}

		public FT_FCA_ClassStatement new_FT_FCA_ClassStatement(final ClassStatement aEl) {
			return new FT_FCA_ClassStatement(aEl);
		}

		public FT_FCA_IdentIA.FakeDC4 new_FakeDC4(final DeduceClient4 aDc4, final FT_FCA_IdentIA aFT_FCA_IdentIA) {
			return aFT_FCA_IdentIA.new FakeDC4(aDc4);
		}

		public FT_FCA_IdentIA.FT_FCA_Ctx new_FT_FCA_Ctx(final BaseEvaFunction aGeneratedFunction, final TypeTableEntry aTte, final Context aCtx, final ErrSink aErrSink, final DeduceClient4 aDc) {
			return new FT_FCA_IdentIA.FT_FCA_Ctx(aGeneratedFunction, aTte, aCtx, aErrSink, aDc);
		}

		public FT_FCA_FormalArgListItem new_FT_FCA_FormalArgListItem(final FormalArgListItem aFali, final BaseEvaFunction aGeneratedFunction) {
			return new FT_FCA_FormalArgListItem(aFali, aGeneratedFunction);
		}

		public FT_FCA_VariableStatement new_FT_FCA_VariableStatement(final VariableStatementImpl aVs, final BaseEvaFunction aGeneratedFunction) {
			return new FT_FCA_VariableStatement(aVs, aGeneratedFunction);
		}

		public FT_FCA_IdentIA.FT_FCA_ProcedureCall new_FT_FCA_ProcedureCall(final ProcedureCallExpression aPce, final Context aCtx, final FT_FCA_IdentIA aFT_FCA_IdentIA) {
			return aFT_FCA_IdentIA.new FT_FCA_ProcedureCall(aPce, aCtx);
		}

		public StatementWrapperImpl new_StatementWrapperImpl(final IExpression aLeft, final Context aO, final OS_Element aO1) {
			return new StatementWrapperImpl(aLeft, aO, aO1);
		}

		public OS_Type new_OS_UnknownType(final StatementWrapperImpl aStatementWrapper) {
			return new OS_UnknownType(aStatementWrapper);
		}

		public FoundElement new_FT_FnCallArgs_DoAssignCall_NullFoundElement(final DeduceClient4 aDc) {
			return new FT_FnCallArgs.DoAssignCall.NullFoundElement(aDc);
		}

		public DT_External_2 new_DT_External_2(final IdentTableEntry aEntry, final OS_Module aModule, final ProcTableEntry aPte, final FT_FCA_IdentIA.FakeDC4 aDc, final ElLog aLOG, final Context aCtx, final BaseEvaFunction aGeneratedFunction, final int aInstructionIndex, final IdentIA aIdentIA, final VariableTableEntry aVte) {
			return new DT_External_2(aEntry, aModule, aPte, aDc, aLOG, aCtx, aGeneratedFunction, aInstructionIndex, aIdentIA, aVte);
		}

		public DR_Ident.ElementUnderstanding new_DR_Ident_ElementUnderstanding(final OS_Element aEl) {
			return new DR_Ident.ElementUnderstanding(aEl);
		}

		public EN_Understanding new_ENU_LangConstVar() {
			return new ENU_LangConstVar();
		}

		public EN_Understanding new_ENU_FunctionName() {
			return new ENU_FunctionName();
		}

		public EN_Understanding new_ENU_ResolveToFunction(final FunctionDef aFd) {
			return new ENU_ResolveToFunction(aFd);
		}

		public IElementHolder new_DE3_EH_GroundedVariableStatement(final VariableStatement aE, final DeduceElement3_IdentTableEntry aDeduceElement3IdentTableEntry) {
			return aDeduceElement3IdentTableEntry.new DE3_EH_GroundedVariableStatement(aE, aDeduceElement3IdentTableEntry);
		}

		public EN_Understanding new_ENU_LookupResult(final LookupResultList aLrl) {
			return new ENU_LookupResult(aLrl);
		}

		public DeduceElement3_IdentTableEntry.DE3_ITE_Holder new_DE3_ITE_Holder(final OS_Element aEl, final DeduceElement3_IdentTableEntry aDeduceElement3IdentTableEntry) {
			return aDeduceElement3IdentTableEntry.new DE3_ITE_Holder(aEl);
		}

		public DR_PossibleTypeCI new_DR_PossibleTypeCI(final ClassInvocation aCi, final FunctionInvocation aFi) {
			return new DR_PossibleTypeCI(aCi, aFi);
		}

		public EN_Understanding new_ENU_VariableName() {
			return new ENU_VariableName();
		}

		public setup_GenType_Action new_SGTA_SetResolvedNamespace(final NamespaceStatement aNamespaceStatement) {
			return new SGTA_SetResolvedNamespace(aNamespaceStatement);
		}

		public setup_GenType_Action new_SGTA_RegisterNamespaceInvocation(final NamespaceStatement aNamespaceStatement, final DeducePhase aPhase) {
			return new SGTA_RegisterNamespaceInvocation(aNamespaceStatement, aPhase);
		}

		public setup_GenType_Action new_SGTA_SetNamespaceInvocation() {
			return new SGTA_SetNamespaceInvocation();
		}

		public setup_GenType_Action new_SGTA_RegisterClassInvocation(final ClassStatement aClassStatement, final DeducePhase aPhase) {
			return new SGTA_RegisterClassInvocation(aClassStatement, aPhase);
		}

		public setup_GenType_Action new_SGTA_SetClassInvocation() {
			return new SGTA_SetClassInvocation();
		}

		public GenType new_GenTypeImpl(final OS_Type aAttached, final OS_Type aOSType, final boolean aB, final TypeName aX, final DeduceTypes2 aDt2, final ErrSink aErrSink, final DeducePhase aPhase) {
			return new GenTypeImpl(aAttached, aOSType, aB, aX, aDt2, aErrSink, aPhase);
		}

		public Diagnostic new_ResolveError(final TypeName aX, final LookupResultList aLrl) {
			return new ResolveError(aX, aLrl);
		}

		public Diagnostic new_Diagnostic_8884(final VariableTableEntry aVte, final BaseEvaFunction aGf) {
			return new DeduceElement3_VariableTableEntry.Diagnostic_8884(aVte, aGf);
		}

		public IdentTableEntry new_IdentTableEntry(final int aI, final IdentExpression aIdentExpression, final Context aContext, final BaseEvaFunction aGeneratedFunction) {
			return new IdentTableEntry(aI, aIdentExpression, aContext, aGeneratedFunction);
		}

		public Implement_construct.ICH new_ICH(final GenType aGenType, final Implement_construct aImplement_construct) {
			return aImplement_construct.new ICH(aGenType);
		}

		public OS_Type new_OS_UnknownType(final OS_Element aElement) {
			return new OS_UnknownType(aElement);
		}

		public DeduceProcCall new_DeduceProcCall(final ProcTableEntry aPte) {
			return new DeduceProcCall(aPte);
		}

		public ClassInvocation.CI_GenericPart new_CI_GenericPart(final List<TypeName> aGenericPart, final ClassInvocation aClassInvocation) {
			return aClassInvocation.new CI_GenericPart(aGenericPart);
		}

		public RegularTypeName new_RegularTypeNameImpl(final Context aContext) {
			return new RegularTypeNameImpl(aContext);
		}

		public IElementHolder new_GenericElementHolderWithDC(final OS_Element aEl, final DeduceClient3 aDc, final Resolve_Ident_IA aResolve_Ident_IA) {
			return aResolve_Ident_IA.new GenericElementHolderWithDC(aEl, aDc);
		}

		public FunctionDef new_FunctionDefImpl(final NamespaceStatement aModNs, final Context aContext) {
			return new FunctionDefImpl(aModNs, aContext);
		}

		public NamespaceInvocation new_NamespaceInvocation(final NamespaceStatement aModNs) {
			return new NamespaceInvocation(aModNs);
		}

		public NamespaceStatement new_NamespaceStatementImpl(final OS_Module aModule, final Context aContext) {
			return new NamespaceStatementImpl(aModule, aContext);
		}

		public DeduceLocalVariable.MemberInvocation new_MemberInvocation(final ClassStatement aB, final DeduceLocalVariable.MemberInvocation.Role aRole) {
			return new DeduceLocalVariable.MemberInvocation(aB, aRole);
		}

		public OS_Element new_DeduceTypes2_OS_SpecialVariable(final VariableTableEntry aEntry, final VariableTableType aVariableTableType, final BaseEvaFunction aGeneratedFunction) {
			return new OS_SpecialVariable(aEntry, aVariableTableType, aGeneratedFunction);
		}

		public Diagnostic new_Diagnostic_8885(final VariableTableEntry aVte) {
			return new DeduceElement3_VariableTableEntry.Diagnostic_8885(aVte);
		}

		public DeduceElement new_DeclTarget(final FunctionDef aBest, final OS_Element aDeclAnchor, final DeclAnchor.AnchorType aAnchorType, final DeduceProcCall aDeduceProcCall) throws FCA_Stop {
			return aDeduceProcCall.new DeclTarget(aBest, aDeclAnchor, aAnchorType);
		}

		public DeclAnchor new_DeclAnchor(final OS_Element aDeclAnchor, final DeclAnchor.AnchorType aAnchorType) {
			return new DeclAnchor(aDeclAnchor, aAnchorType);
		}

		public IInvocation new_DerivedClassInvocation(final ClassStatement aDeclAnchor, final ClassInvocation aDeclaredInvocation, final Supplier<DeduceTypes2> aDeduceTypes2) {
			return new DerivedClassInvocation(aDeclAnchor, aDeclaredInvocation, aDeduceTypes2);
		}

		public Diagnostic new_ResolveError(final IdentExpression aIdent, final LookupResultList aLrl) {
			return new ResolveError(aIdent, aLrl);
		}

		public Resolve_Ident_IA2.RIA_Clear_98 new_RIA_Clear_98(final IdentTableEntry aIdte, final VariableStatementImpl aVs, final Context aCtx, final Resolve_Ident_IA2 aResolve_Ident_IA2) {
			return aResolve_Ident_IA2.new RIA_Clear_98(aIdte, aVs, aCtx);
		}

		public EN_Understanding new_ENU_FunctionCallTarget(final ProcTableEntry aPte) {
			return new ENU_FunctionCallTarget(aPte);
		}

		public EN_Understanding new_ENU_TypeTransitiveOver(final ProcTableEntry aPte) {
			return new ENU_TypeTransitiveOver(aPte);
		}

		public FT_FnCallArgs.DoAssignCall new_DoAssignCall(final DeduceClient4 aClient4, final BaseEvaFunction aGeneratedFunction, final FT_FnCallArgs aFT_FnCallArgs) {
			return aFT_FnCallArgs.new DoAssignCall(aClient4, aGeneratedFunction);
		}

		public FT_FCA_IdentIA new_FT_FCA_IdentIA(final FT_FnCallArgs aFTFnCallArgs, final IdentIA aIdentIA, final VariableTableEntry aVte) {
			return new FT_FCA_IdentIA(aFTFnCallArgs, aIdentIA, aVte);
		}

		public FT_FCA_IdentIA.Resolve_VTE new_FT_FCA_IdentIA_Resolve_VTE(final VariableTableEntry aVte, final Context aCtx, final ProcTableEntry aPte, final Instruction aInstruction, final FnCallArgs aFca) {
			return new FT_FCA_IdentIA.Resolve_VTE(aVte, aCtx, aPte, aInstruction, aFca);
		}

		public EN_Understanding new_ENU_ClassName() {
			return new ENU_ClassName();
		}

		public EN_Understanding new_ENU_ConstructorCallTarget() {
			return new ENU_ConstructorCallTarget();
		}

		public DeduceClient2 new_DeduceClient2(final DeduceTypes2 aDeduceTypes2) {
			return new DeduceClient2(aDeduceTypes2);
		}

		public BaseTableEntry.StatusListener new_ProcTableListener(final ProcTableEntry aPte, final BaseEvaFunction aGeneratedFunction, final DeduceClient2 aO) {
			return new ProcTableListener(aPte, aGeneratedFunction, aO);
		}

		public DeferredObject<GenType, Diagnostic, Void> new_DeferredObject__GenType() {
			return new DeferredObject<>();
		}

		public DeferredObject<BaseEvaFunction, Void, Void> new_DeferredObject__BaseEvaFunction() {
			return new DeferredObject<>();
		}

		public WlGenerateDefaultCtor new_WlGenerateDefaultCtor(final OS_Module aModule, final FunctionInvocation aFunctionInvocation, final Deduce_CreationClosure aCl) {
			return new WlGenerateDefaultCtor(aModule, aFunctionInvocation, aCl);
		}

		public WlGenerateCtor new_WlGenerateCtor(final OS_Module aModule, final IdentExpression aNameNode, final FunctionInvocation aFunctionInvocation, final Deduce_CreationClosure aCl) {
			return new WlGenerateCtor(aModule, aFunctionInvocation.getFunction().getNameNode(), aFunctionInvocation, aCl);
		}

		public WlGenerateFunction new_WlGenerateFunction(final OS_Module aModule, final FunctionInvocation aDependentFunction, final Deduce_CreationClosure aCl) {
			return new WlGenerateFunction(aModule, aDependentFunction, aCl);
		}
	}

	private class do_assign_normal_ident_deferred__DT_ResolveObserver implements DT_ResolveObserver {
		private final @NotNull IdentTableEntry identTableEntry;
		private final @NotNull BaseEvaFunction generatedFunction;

		public do_assign_normal_ident_deferred__DT_ResolveObserver(final @NotNull IdentTableEntry aIdentTableEntry, final @NotNull BaseEvaFunction aGeneratedFunction) {
			identTableEntry   = aIdentTableEntry;
			generatedFunction = aGeneratedFunction;
		}

		@Override
		public void onElement(@Nullable OS_Element best) {
			if (best != null) {
				identTableEntry.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolder(best));
				// TODO check for elements which may contain type information
				if (best instanceof final @NotNull VariableStatementImpl vs) {
					do_assign_normal_ident_deferred_VariableStatement(generatedFunction, identTableEntry, vs);
				} else if (best instanceof final @NotNull FormalArgListItem fali) {
					do_assign_normal_ident_deferred_FALI(generatedFunction, identTableEntry, fali);
				} else
					throw new NotImplementedException();
			} else {
				identTableEntry.setStatus(BaseTableEntry.Status.UNKNOWN, null);
				LOG.err("242 Bad lookup" + identTableEntry.getIdent().getText());
			}
		}

		private void do_assign_normal_ident_deferred_FALI(final @NotNull BaseEvaFunction generatedFunction, final @NotNull IdentTableEntry aIdentTableEntry, final @NotNull FormalArgListItem fali) {
			final GenType            genType            = _inj().new_GenTypeImpl();
			final FunctionInvocation functionInvocation = generatedFunction.fi;
			final String             fali_name          = fali.name().asString();

			IInvocation invocation = null;
			if (functionInvocation.getClassInvocation() != null) {
				invocation = functionInvocation.getClassInvocation();
				genType.setResolved(((ClassInvocation) invocation).getKlass().getOS_Type());
			} else if (functionInvocation.getNamespaceInvocation() != null) {
				invocation = functionInvocation.getNamespaceInvocation();
				genType.setResolvedn(((NamespaceInvocation) invocation).getNamespace());
			}
			assert invocation != null;
			genType.setCi(Objects.requireNonNull(invocation));

			final @Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(fali_name);
			assert vte_ia != null;
			((IntegerIA) vte_ia).getEntry().typeResolvePromise().then(new DoneCallback<GenType>() {
				@Override
				public void onDone(final @NotNull GenType result) {
					assert result.getResolved() != null;
					aIdentTableEntry.type.setAttached(result);
				}
			});
			generatedFunction.addDependentType(genType);
			DebugPrint.addDependentType(generatedFunction, genType);
		}

		class X_S implements Supplier<IInvocation> {
			private final BaseEvaFunction generatedFunction1;

			public X_S(final BaseEvaFunction aGeneratedFunction) {
				generatedFunction1 = aGeneratedFunction;
			}

			@Override
			@NotNull
			public IInvocation get() {
				final IInvocation invocation;

				if (generatedFunction1.fi.getClassInvocation() != null) {
					invocation = generatedFunction1.fi.getClassInvocation();
				} else {
					invocation = generatedFunction1.fi.getNamespaceInvocation();
				}

				return invocation;
			}
		}

		public void do_assign_normal_ident_deferred_VariableStatement(final @NotNull BaseEvaFunction generatedFunction, final @NotNull IdentTableEntry aIdentTableEntry, final @NotNull VariableStatementImpl vs) {
			final DeduceElementWrapper parent = new DeduceElementWrapper(vs.getParent().getParent());
			final DeferredMember       dm     = deferred_member(parent, new X_S(generatedFunction).get(), vs, aIdentTableEntry);

			dm.typePromise().done(result -> {
				assert result.getResolved() != null;
				aIdentTableEntry.type.setAttached(result.getResolved());
			});

			final GenType genType = _inj().new_GenTypeImpl();
			genType.setCi(dm.getInvocation());

			if (genType.getCi() instanceof NamespaceInvocation) {
				genType.setResolvedn(((NamespaceInvocation) genType.getCi()).getNamespace());
			} else if (genType.getCi() instanceof ClassInvocation) {
				genType.setResolved(((ClassInvocation) genType.getCi()).getKlass().getOS_Type());
			} else {
				throw new IllegalStateException();
			}
			generatedFunction.addDependentType(genType);
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
