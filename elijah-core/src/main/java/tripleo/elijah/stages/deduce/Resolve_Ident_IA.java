/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.impl.MatchConditionalImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang.nextgen.names.impl.ENU_ClassName;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.Holder;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.work.WorkList;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

/**
 * Created 7/8/21 2:31 AM
 */
public class Resolve_Ident_IA {
	private final @NotNull Context                    context;
	private final          DeduceElementIdent         dei;
	private final @NotNull ErrSink                    errSink;
	private final @NotNull DeduceTypes2.DeduceClient3 dc;
	private final @NotNull FoundElement               foundElement;
	private final          BaseEvaFunction            generatedFunction;
	private final @NotNull IdentIA                    identIA;
	private final @NotNull ElLog                      LOG;
	private final @NotNull DeducePhase                phase;
	private final          DeduceTypes2               dt2;
	Context ectx;
	@Nullable OS_Element el;

	public Resolve_Ident_IA(final DeduceTypes2.@NotNull DeduceClient3 aDeduceClient3,
							final @NotNull Context aContext,
							final DeduceElementIdent aDei,
							final BaseEvaFunction aGeneratedFunction,
							final @NotNull FoundElement aFoundElement,
							final @NotNull ErrSink aErrSink) {
		dc       = aDeduceClient3;
		this.dt2 = dc.deduceTypes2;


		phase             = dc.getPhase();
		context           = aContext;
		dei               = aDei;
		identIA           = dt2._inj().new_IdentIA(dei.getIdentTableEntry().getIndex(), aGeneratedFunction);
		generatedFunction = aGeneratedFunction;
		foundElement      = aFoundElement;
		errSink           = aErrSink;
		//
		LOG = dc.getLOG();
	}

	private void _procIA_constructor_helper(@NotNull ProcTableEntry pte) {
		if (pte.getClassInvocation() != null)
			throw new IllegalStateException();

		if (pte.getFunctionInvocation() == null) {
			_procIA_constructor_helper_create_invocations(pte);
		} else {
			FunctionInvocation fi = pte.getFunctionInvocation();
			ClassInvocation    ci = fi.getClassInvocation();
			if (fi.getFunction() instanceof ConstructorDef) {
				@NotNull GenType genType = dt2._inj().new_GenTypeImpl(ci.getKlass());
				genType.setCi(ci);
				ci.resolvePromise().then(new DoneCallback<EvaClass>() {
					@Override
					public void onDone(EvaClass result) {
						genType.setNode(result);
					}
				});
				final @NotNull WorkList          wl                = dt2._inj().new_WorkList();
				final @NotNull OS_Module         module            = ci.getKlass().getContext().module();
				final @NotNull GenerateFunctions generateFunctions = dc.getGenerateFunctions(module);
				if (pte.getFunctionInvocation().getFunction() == LangGlobals.defaultVirtualCtor)
					wl.addJob(dt2._inj().new_WlGenerateDefaultCtor(generateFunctions, fi, dc.deduceTypes2.creationContext(), phase.codeRegistrar));
				else
					wl.addJob(dt2._inj().new_WlGenerateCtor(generateFunctions, fi, null, dc.deduceTypes2.phase.codeRegistrar));
				dc.addJobs(wl);
//				generatedFunction.addDependentType(genType);
//				generatedFunction.addDependentFunction(fi);
			}
		}
	}

	private void _procIA_constructor_helper_create_invocations(@NotNull ProcTableEntry pte) {
		var dt2 = dc.deduceTypes2;

		@Nullable ClassInvocation ci = dt2._inj().new_ClassInvocation((ClassStatement) el, null, new ReadySupplier_1<>(dt2));

		ci = phase.registerClassInvocation(ci);
//		prte.setClassInvocation(ci);
		Collection<ConstructorDef> cs                   = (((ClassStatement) el).getConstructors());
		@Nullable ConstructorDef   selected_constructor = null;
		if (pte.getArgs().size() == 0 && cs.size() == 0) {
			// TODO use a virtual default ctor
			LOG.info("2262 use a virtual default ctor for " + pte.__debug_expression);
			selected_constructor = LangGlobals.defaultVirtualCtor;
		} else {
			// TODO find a ctor that matches prte.getArgs()
			final List<TypeTableEntry> x  = pte.getArgs();
			int                        yy = 2;
		}
		assert ((ClassStatement) el).getGenericPart().size() == 0;
		@NotNull FunctionInvocation fi = phase.newFunctionInvocation(selected_constructor, pte, ci);
//		fi.setClassInvocation(ci);
		pte.setFunctionInvocation(fi);
		if (fi.getFunction() instanceof ConstructorDef) {
			@NotNull GenType genType = dt2._inj().new_GenTypeImpl(ci.getKlass());
			genType.setCi(ci);
			ci.resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(EvaClass result) {
					genType.setNode(result);
				}
			});
			generatedFunction.addDependentType(genType);
			generatedFunction.addDependentFunction(fi);
		} else
			generatedFunction.addDependentFunction(fi);
	}

	@Contract(pure = true)
	public Resolve_Ident_IA(final @NotNull DeduceTypes2.DeduceClient3 aDeduceClient3,
							final @NotNull Context aContext,
							final @NotNull IdentIA aIdentIA,
							final @NotNull BaseEvaFunction aGeneratedFunction,
							final @NotNull FoundElement aFoundElement,
							final @NotNull ErrSink aErrSink) {
		dc                = aDeduceClient3;
		dt2               = dc.deduceTypes2;
		phase             = dc.getPhase();
		generatedFunction = aGeneratedFunction;
		//
		errSink = aErrSink;
		context = aContext;
		//
		identIA      = aIdentIA;
		foundElement = aFoundElement;
		//
		dei = identIA.getEntry().getDeduceElement();
		dei.setDeduceTypes2(this.dc.deduceTypes2, context, generatedFunction);
		//
		LOG = dc.getLOG();
	}

	private void preUpdateStatus(final @NotNull List<InstructionArgument> s) {
		var dt2 = dc.deduceTypes2;


		final var    normal_path1 = BaseEvaFunction._getIdentIAResolvableList(identIA);
		final String normal_path  = generatedFunction.getIdentIAPathNormal(identIA);
		if (s.size() > 1) {
			InstructionArgument x = s.get(s.size() - 1);
			if (x instanceof IntegerIA) {
				assert false;
				@NotNull VariableTableEntry y = ((IntegerIA) x).getEntry();
				y.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(el));
			} else if (x instanceof IdentIA) {
				@NotNull IdentTableEntry y = ((IdentIA) x).getEntry();
				if (!y.preUpdateStatusListenerAdded) {
					y.addStatusListener(dt2._inj().new_StatusListener__RIA__176(y, foundElement, this));
					y.preUpdateStatusListenerAdded = true;
				}
			}
		} else {
//			LOG.info("1431 Found for " + normal_path);
			foundElement.doFoundElement(el);
		}
	}

	@Contract("null, _ -> fail")
	private boolean process(@NotNull InstructionArgument ia, final @NotNull List<InstructionArgument> aS) {
		if (ia instanceof IntegerIA) {
			@NotNull RIA_STATE state = action_IntegerIA(ia);
			if (state == RIA_STATE.RETURN) {
				return false;
			} else if (state == RIA_STATE.NEXT) {
				final IdentIA                  identIA2 = identIA; //(IdentIA) aS.get(1);
				final @NotNull IdentTableEntry idte     = identIA2.getEntry();

				var dt2 = dc.deduceTypes2;

				dc.resolveIdentIA2_(context, identIA2, aS, generatedFunction, new FoundElement(phase) {
					final String z = generatedFunction.getIdentIAPathNormal(identIA2);
					final @NotNull DT_Resolvabley zz = generatedFunction._getIdentIAResolvable(identIA2);

					@Override
					public void foundElement(@NotNull OS_Element e) {
						idte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
						foundElement.doFoundElement(e);
					}

					@Override
					public void noFoundElement() {
						foundElement.noFoundElement();
						LOG.info("2002 Cant resolve " + zz.getNormalPath(generatedFunction, identIA));
						idte.setStatus(BaseTableEntry.Status.UNKNOWN, null);
					}
				});
			}
		} else if (ia instanceof IdentIA) {
			@NotNull RIA_STATE state = action_IdentIA((IdentIA) ia);
			return state != RIA_STATE.RETURN;
		} else if (ia instanceof ProcIA) {
			action_ProcIA(ia);
		} else
			throw new IllegalStateException("Really cant be here");
		return true;
	}

	public void action() {
		dei.resolvedElementPromise().then((el2) -> {

			//final OS_Element                         el2 = dei.getResolvedElement();

			SimplePrintLoggerToRemoveSoon.println_out_2("  70 " + el2);

			final @NotNull List<InstructionArgument> s = BaseEvaFunction._getIdentIAPathList(identIA);

			ectx = context;
			el   = null;

			if (!process(s.get(0), s)) return;

			preUpdateStatus(s);
			updateStatus(s);
		});
	}

	private @NotNull RIA_STATE action_IntegerIA(@NotNull InstructionArgument ia) {
		@NotNull VariableTableEntry vte  = ((IntegerIA) ia).getEntry();
		final String                text = vte.getName();
		final LookupResultList      lrl  = ectx.lookup(text);
		el = lrl.chooseBest(null);
		if (el != null) {
			//
			// TYPE INFORMATION IS CONTAINED IN VARIABLE DECLARATION
			//
			if (el instanceof @NotNull final VariableStatementImpl vs) {
				if (!vs.typeName().isNull()) {
					ectx = vs.typeName().getContext();
					return RIA_STATE.CONTINUE;
				}
			}
			//
			// OTHERWISE TYPE INFORMATION MAY BE IN POTENTIAL_TYPES
			//
			@NotNull List<TypeTableEntry> pot = dc.getPotentialTypesVte(vte);
			if (pot.size() == 1) {
				OS_Type attached = pot.get(0).getAttached();
				if (attached != null) {
					action_001(attached);
				} else {
					action_002(pot.get(0));
				}
			}
		} else {
			errSink.reportError("1001 Can't resolve " + text);
			foundElement.doNoFoundElement();
			return RIA_STATE.RETURN;
		}
		return RIA_STATE.NEXT;
	}

	private void action_001(@NotNull OS_Type aAttached) {
		var dt2 = dc.deduceTypes2;

		switch (aAttached.getType()) {
		case USER_CLASS: {
			ClassStatement x = aAttached.getClassOf();
			ectx = x.getContext();
			break;
		}
		case FUNCTION: {
			int yy = 2;
			LOG.err("1005");
			@NotNull FunctionDef x = (FunctionDef) aAttached.getElement();
			ectx = x.getContext();
			break;
		}
		case USER:
			if (el instanceof MatchConditionalImpl.MatchArm_TypeMatch) {
				// for example from match conditional
				final TypeName tn = ((MatchConditionalImpl.MatchArm_TypeMatch) el).getTypeName();
				try {
					final @NotNull GenType ty = dc.resolve_type(dt2._inj().new_OS_UserType(tn), tn.getContext());
					ectx = ty.getResolved().getElement().getContext();
				} catch (ResolveError resolveError) {
					resolveError.printStackTrace();
					LOG.err("1182 Can't resolve " + tn);
					throw new IllegalStateException("ResolveError.");
				}
//						ectx = el.getContext();
			} else
				ectx = aAttached.getTypeName().getContext(); // TODO is this right?
			break;
		case FUNC_EXPR: {
			@NotNull FuncExpr x = (FuncExpr) aAttached.getElement();
			ectx = x.getContext();
			break;
		}
		default:
			LOG.err("1010 " + aAttached.getType());
			throw new IllegalStateException("Don't know what you're doing here.");
		}
	}

	private @NotNull RIA_STATE action_IdentIA(@NotNull IdentIA ia) {
		var dt2 = dc.deduceTypes2;

		final @NotNull IdentTableEntry idte = ia.getEntry();
		if (idte.getStatus() == BaseTableEntry.Status.UNKNOWN) {
			LOG.info("1257 Not found for " + generatedFunction.getIdentIAPathNormal(ia));
			// No need checking more than once
			idte.resolveExpectation.fail();
			foundElement.doNoFoundElement();
			return RIA_STATE.RETURN;
		}
		//assert idte.backlink == null;

		if (idte.getStatus() == BaseTableEntry.Status.UNCHECKED) {
			var drIdent    = generatedFunction.getIdent(idte);
			var ident_name = idte.getIdent().getName();

			if (ident_name.hasUnderstanding(ENU_ClassName.class)) {
				int y = 2;
			}

			if (idte.getBacklink() == null) {
				final String text = idte.getIdent().getText();
				if (idte.getResolvedElement() == null) {
					final LookupResultList lrl = ectx.lookup(text);
					el = lrl.chooseBest(null);
				} else {
					assert false;
					el = idte.getResolvedElement();
				}
				{
					if (el instanceof final @NotNull FunctionDef functionDef) {
						final OS_Element      parent     = functionDef.getParent();
						@Nullable GenType     genType    = null;
						@Nullable IInvocation invocation = null;
						switch (DecideElObjectType.getElObjectType(parent)) {
						case UNKNOWN:
							break;
						case CLASS:
							genType = dt2._inj().new_GenTypeImpl((ClassStatement) parent);
							@Nullable ClassInvocation ci = dt2._inj().new_ClassInvocation((ClassStatement) parent, null, new ReadySupplier_1<>(dt2));
							invocation = phase.registerClassInvocation(ci);
							break;
						case NAMESPACE:
							genType = dt2._inj().new_GenTypeImpl((NamespaceStatement) parent);
							invocation = phase.registerNamespaceInvocation((NamespaceStatement) parent);
							break;
						default:
							// do nothing
							break;
						}
						if (genType != null) {
							generatedFunction.addDependentType(genType);

							// TODO might not be needed
							if (invocation != null) {
								@NotNull FunctionInvocation fi = phase.newFunctionInvocation((BaseFunctionDef) el, null, invocation);
//								generatedFunction.addDependentFunction(fi); // README program fails if this is included
							}
						}
						final ProcTableEntry callablePTE = idte.getCallablePTE();
						assert callablePTE != null;
						final @NotNull FunctionInvocation fi = dc.newFunctionInvocation((BaseFunctionDef) el, callablePTE, invocation);
						if (invocation instanceof ClassInvocation) {
							callablePTE.setClassInvocation((ClassInvocation) invocation);
						}
						callablePTE.setFunctionInvocation(fi);
						generatedFunction.addDependentFunction(fi);
					} else if (el instanceof ClassStatement) {
						@NotNull GenType genType = dt2._inj().new_GenTypeImpl((ClassStatement) el);
						generatedFunction.addDependentType(genType);
					}
				}
				if (el != null) {
					idte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(el));

					if (el.getContext() == null)
						throw new IllegalStateException("2468 null context");

					ectx = el.getContext();
				} else {
					errSink.reportError("1179 Can't resolve " + text);
					idte.setStatus(BaseTableEntry.Status.UNKNOWN, null);
					foundElement.doNoFoundElement();
					return RIA_STATE.RETURN;
				}
			} else /*if (false)*/ {
				dc.resolveIdentIA2_(ectx/*context*/, ia, null, generatedFunction, new FoundElement(phase) {
					final String z = generatedFunction.getIdentIAPathNormal(ia);

					@Override
					public void foundElement(@NotNull OS_Element e) {
						idte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
						foundElement.doFoundElement(e);
						dc.found_element_for_ite(generatedFunction, idte, e, ectx);
					}

					@Override
					public void noFoundElement() {
						foundElement.noFoundElement();
						LOG.info("2002 Cant resolve " + z);
						idte.setStatus(BaseTableEntry.Status.UNKNOWN, null);
					}
				});
			}
//				assert idte.getStatus() != BaseTableEntry.Status.UNCHECKED;
			final String normal_path = generatedFunction.getIdentIAPathNormal(identIA);
			if (idte.resolveExpectation == null) {
				SimplePrintLoggerToRemoveSoon.println_err_2("385 idte.resolveExpectation is null for " + idte);
			} else
				idte.resolveExpectation.satisfy(normal_path);
		} else if (idte.getStatus() == BaseTableEntry.Status.KNOWN) {
			final String normal_path = generatedFunction.getIdentIAPathNormal(identIA);
			//assert idte.resolveExpectation.isSatisfied();
			if (!idte.resolveExpectation.isSatisfied())
				idte.resolveExpectation.satisfy(normal_path);

			el   = idte.getResolvedElement();
			ectx = el.getContext();
		}
		return RIA_STATE.NEXT;
	}

	private void action_002_no_resolved_element(final @NotNull ProcTableEntry pte, final @NotNull IdentTableEntry ite) {
		var dt2 = dc.deduceTypes2;

		if (ite.getBacklink() instanceof ProcIA) {
			final @NotNull ProcIA   backlink_       = (ProcIA) ite.getBacklink();
			@NotNull ProcTableEntry backlink        = generatedFunction.getProcTableEntry(backlink_.index());
			final OS_Element        resolvedElement = backlink.getResolvedElement();
			assert resolvedElement != null;
			try {
				LookupResultList     lrl2 = dc.lookupExpression(ite.getIdent(), resolvedElement.getContext());
				@Nullable OS_Element best = lrl2.chooseBest(null);
				assert best != null;
				ite.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(best));
			} catch (ResolveError aResolveError) {
				errSink.reportDiagnostic(aResolveError);
				assert false;
			}
			action_002_1(pte, ite);
		} else if (ite.getBacklink() instanceof IntegerIA) {
			final @NotNull IntegerIA    backlink_       = (IntegerIA) ite.getBacklink();
			@NotNull VariableTableEntry backlink        = backlink_.getEntry();
			final OS_Element            resolvedElement = backlink.getResolvedElement();
			assert resolvedElement != null;

			if (resolvedElement instanceof IdentExpression) {
				backlink.typePromise().then(new DoneCallback<GenType>() {
					@Override
					public void onDone(@NotNull GenType result) {
						try {
							final Context context1 = result.getResolved().getClassOf().getContext();
							action_002_2(pte, ite, context1);
						} catch (ResolveError aResolveError) {
							errSink.reportDiagnostic(aResolveError);
						}
					}
				});
			} else {
				try {
					final Context context1 = resolvedElement.getContext();
					action_002_2(pte, ite, context1);
				} catch (ResolveError aResolveError) {
					errSink.reportDiagnostic(aResolveError);
					assert false;
				}
			}
		} else
			assert false;
	}

	private void action_002(final @NotNull TypeTableEntry tte) {
		//>ENTRY
		//assert vte.potentailTypes().size() == 1;
		assert tte.getAttached() == null;
		//<ENTRY

		if (tte.tableEntry instanceof @NotNull final ProcTableEntry pte) {
			@NotNull IdentIA         x = (IdentIA) pte.expression_num;
			@NotNull IdentTableEntry y = x.getEntry();
			if (!y.hasResolvedElement()) {
				action_002_no_resolved_element(pte, y);
			} else {
				final OS_Element               res = y.getResolvedElement();
				final @NotNull IdentTableEntry ite = identIA.getEntry();
				action_002_1(pte, y, true);
			}
		} else
			throw new IllegalStateException("tableEntry must be ProcTableEntry");
	}

	private void action_002_1(@NotNull ProcTableEntry pte, @NotNull IdentTableEntry ite) {
		action_002_1(pte, ite, false);
	}

	private void action_002_1(@NotNull ProcTableEntry pte, @NotNull IdentTableEntry ite, boolean setClassInvocation) {
		final OS_Element resolvedElement = ite.getResolvedElement();

		assert resolvedElement != null;

		ClassInvocation ci = null;

		if (pte.getFunctionInvocation() == null) {
			@NotNull FunctionInvocation fi;

			if (resolvedElement instanceof ClassStatement) {
				// assuming no constructor name or generic parameters based on function syntax
				ci = dt2._inj().new_ClassInvocation((ClassStatement) resolvedElement, null, new ReadySupplier_1<>(dt2));
				ci = phase.registerClassInvocation(ci);
				fi = phase.newFunctionInvocation(null, pte, ci);
			} else if (resolvedElement instanceof FunctionDef) {
				final IInvocation invocation = dc.getInvocation((EvaFunction) generatedFunction);
				fi = phase.newFunctionInvocation((FunctionDef) resolvedElement, pte, invocation);
				if (fi.getFunction().getParent() instanceof final @NotNull ClassStatement classStatement) {
					ci = dt2._inj().new_ClassInvocation(classStatement, null, new ReadySupplier_1<>(dt2)); // TODO generics
					ci = phase.registerClassInvocation(ci);
				}
			} else {
				throw new IllegalStateException();
			}

			if (setClassInvocation) {
				if (ci != null) {
					pte.setClassInvocation(ci);
				} else
					SimplePrintLoggerToRemoveSoon.println_err_2("542 Null ClassInvocation");
			}

			pte.setFunctionInvocation(fi);
		}

		el   = resolvedElement;
		ectx = el.getContext();
	}

	private void action_002_2(final @NotNull ProcTableEntry pte, final @NotNull IdentTableEntry ite, final @NotNull Context aAContext) throws ResolveError {
		var dt2 = dc.deduceTypes2;

		LookupResultList     lrl2 = dc.lookupExpression(ite.getIdent(), aAContext);
		@Nullable OS_Element best = lrl2.chooseBest(null);
		assert best != null;
		ite.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(best));
		action_002_1(pte, ite);
	}

	private void action_ProcIA(@NotNull InstructionArgument ia) {
		var dt2 = dc.deduceTypes2;

		@NotNull ProcTableEntry prte = ((ProcIA) ia).getEntry();
		if (prte.getResolvedElement() == null) {
			IExpression exp = prte.__debug_expression;
			if (exp instanceof final @NotNull ProcedureCallExpression pce) {
				exp = pce.getLeft(); // TODO might be another pce??!!
				if (exp instanceof ProcedureCallExpression)
					throw new IllegalStateException("double pce!");
			} else
				throw new IllegalStateException("prte resolvedElement not ProcCallExpression");
			try {
				LookupResultList lrl = dc.lookupExpression(exp, ectx);
				el = lrl.chooseBest(null);
				assert el != null;
				ectx = el.getContext();
//					prte.setResolvedElement(el);
				prte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(el));
				// handle constructor calls
				if (el instanceof ClassStatement) {
					_procIA_constructor_helper(prte);
				}
			} catch (ResolveError aResolveError) {
				aResolveError.printStackTrace();
				throw new NotImplementedException();
			}
		} else {
			el   = prte.getResolvedElement();
			ectx = el.getContext();
		}
	}

	private void updateStatus(@NotNull List<InstructionArgument> aS) {
		var dt2 = dc.deduceTypes2;

		InstructionArgument x = aS.get(/*aS.size()-1*/0);
		if (x instanceof IntegerIA) {
			@NotNull VariableTableEntry y = ((IntegerIA) x).getEntry();
			if (el instanceof final @NotNull VariableStatementImpl vs) {
				y.setStatus(BaseTableEntry.Status.KNOWN, dc.newGenericElementHolderWithType(el, vs.typeName()));
			}
			y.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolderWithDC(el, dc, this));
		} else if (x instanceof IdentIA) {
			@NotNull IdentTableEntry y = ((IdentIA) x).getEntry();
			assert y.getStatus() == BaseTableEntry.Status.KNOWN;
//				y.setStatus(BaseTableEntry.Status.KNOWN, el);
		} else if (x instanceof ProcIA) {
			@NotNull ProcTableEntry y = ((ProcIA) x).getEntry();
			assert y.getStatus() == BaseTableEntry.Status.KNOWN;
			y.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(el));
		} else
			throw new NotImplementedException();
	}

	enum RIA_STATE {
		CONTINUE, NEXT, RETURN
	}

	class GenericElementHolderWithDC implements IElementHolder {
		private final DeduceTypes2.DeduceClient3 deduceClient3;
		private final OS_Element                 element;

		public GenericElementHolderWithDC(final OS_Element aElement, final DeduceTypes2.DeduceClient3 aDeduceClient3) {
			element       = aElement;
			deduceClient3 = aDeduceClient3;
		}

		public DeduceTypes2.DeduceClient3 getDC() {
			return deduceClient3;
		}

		@Override
		public OS_Element getElement() {
			return element;
		}
	}

	/**
	 * Created 11/22/21 8:23 PM
	 */
	public static class DeduceElementIdent implements IDeduceElement_old {
		private final Deferred<OS_Element, ResolveError, Void> _resolvedElementPromise = new DeferredObject<>();
		private final IdentTableEntry                          identTableEntry;
		private       Context                                  context;
		private       BaseEvaFunction                          generatedFunction;
		private       DeduceTypes2                             deduceTypes2;

		public DeduceElementIdent(final IdentTableEntry aIdentTableEntry) {
			identTableEntry = aIdentTableEntry;
			//_resolvedElementPromise = identTableEntry.resolvedElementPromise;

			if (false) {
				if (identTableEntry.isResolved()) {
					resolveElement(identTableEntry.getResolvedElement()); // README hmm 06/19
				}
			}

			identTableEntry.addStatusListener((eh, newStatus) -> {
				if (newStatus == BaseTableEntry.Status.KNOWN) {
					resolveElement(eh.getElement());
				}
			});
		}

		public IdentTableEntry getIdentTableEntry() {
			return identTableEntry;
		}

		public OS_Element getResolvedElement() {
			if (deduceTypes2 == null) { // TODO remove this ASAP. Should never happen
				SimplePrintLoggerToRemoveSoon.println_err_2("5454 Should never happen. gf is not deduced.");
				return null;
				//throw new IllegalStateException("5454 Should never happen. gf is not deduced.");

				//var x = identTableEntry.get_ident().identTableEntry().
			}

			final Holder<OS_Element> holder = new Holder<>();

			boolean rp = false;

			if (deduceTypes2.hasResolvePending(identTableEntry)) {
				identTableEntry.elementPromise(holder::set, null);
				final DeducePath                     dp  = identTableEntry.buildDeducePath(generatedFunction);
				final DeduceElement3_IdentTableEntry de3 = identTableEntry.getDeduceElement3(deduceTypes2, generatedFunction);

				de3.sneakResolve();

				rp = true;
			} else {
				deduceTypes2.addResolvePending(identTableEntry, this, holder);

				final DeduceElement3_IdentTableEntry de3 = identTableEntry.getDeduceElement3(deduceTypes2, generatedFunction);
				de3.sneakResolve();
			}

			final boolean[] is_set = {false};

			if (!rp) {
				final DeduceTypes2.PromiseExpectation<OS_Element> pe1 = deduceTypes2.promiseExpectation(identTableEntry, "DeduceElementIdent getResolvedElement");

//				assert _resolvedElementPromise.isResolved();

				_resolvedElementPromise.then(e -> {
					is_set[0] = true;
					holder.set(e);

					pe1.satisfy(e);
					deduceTypes2.LOG.info(MessageFormat.format("DeduceElementIdent: found element for {0} {1}", identTableEntry, e));
					deduceTypes2.removeResolvePending(identTableEntry);
				});

				// TODO when you get bored, remove this 07/20
				/*
				deduceTypes2.resolveIdentIA_(context, this, generatedFunction, _inj().new_FoundElement(deduceTypes2.phase) {
					@Override
					public void foundElement(final OS_Element e) {
						is_set[0] = true;
						holder.set(e);

						pe1.satisfy(e);
						deduceTypes2.LOG.info(MessageFormat.format("DeduceElementIdent: found element for {0} {1}", identTableEntry, e));
						deduceTypes2.removeResolvePending(identTableEntry);
					}

					@Override
					public void noFoundElement() {
						pe1.fail();
						deduceTypes2.LOG.err("DeduceElementIdent: can't resolve element for " + identTableEntry);
						deduceTypes2.removeResolvePending(identTableEntry);
					}
				});
				*/
			}

//			assert is_set[0] == true;
			final OS_Element R = holder.get();
			return R;
		}

		public Promise<OS_Element, ResolveError, Void> resolvedElementPromise() {
			return _resolvedElementPromise.promise();
		}

		public void resolveElement(final OS_Element aElement) {
			if (!_resolvedElementPromise.isResolved()) {
				_resolvedElementPromise.resolve(aElement);
				identTableEntry._p_resolvedElementPromise.resolve(aElement);
			}
		}

		public void setDeduceTypes2(final DeduceTypes2 aDeduceTypes2, final Context aContext, final @NotNull BaseEvaFunction aGeneratedFunction) {
			deduceTypes2      = aDeduceTypes2;
			context           = aContext;
			generatedFunction = aGeneratedFunction;
		}
	}

	class StatusListener__RIA__176 implements BaseTableEntry.StatusListener {
		private final @NotNull IdentTableEntry     y;
		private final          String              normal_path;
		private final @NotNull List<DT_Resolvable> normal_path1;
		boolean _called;
		private final FoundElement foundElement;

		public StatusListener__RIA__176(final @NotNull IdentTableEntry aY,
										final FoundElement aFoundElement) {
			y            = aY;
			foundElement = aFoundElement;

			normal_path1 = BaseEvaFunction._getIdentIAResolvableList(identIA);
			normal_path  = generatedFunction.getIdentIAPathNormal(identIA);
		}

		@Override
		public void onChange(@NotNull IElementHolder eh, BaseTableEntry.Status newStatus) {
			if (_called) return;

			if (newStatus == BaseTableEntry.Status.KNOWN) {
				_called = true;

				y.resolveExpectation.satisfy(normal_path);
//				dc.found_element_for_ite(generatedFunction, y, eh.getElement(), null); // No context
//				LOG.info("1424 Found for " + normal_path);
				foundElement.doFoundElement(eh.getElement());
			}
		}
	}
}

//
//
//
