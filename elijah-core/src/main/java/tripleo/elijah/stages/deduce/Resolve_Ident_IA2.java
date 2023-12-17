/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang.nextgen.names.impl.ENU_LookupResult;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.*;

import java.util.List;

/**
 * Created 7/21/21 7:33 PM
 */
public class Resolve_Ident_IA2 {
	private final          DeduceTypes2 deduceTypes2;
	private final          ErrSink      errSink;
	private final @NotNull FoundElement foundElement;
	private final @NotNull ElLog        LOG;
	private final          DeducePhase  phase;
	Context ectx;
	private final @NotNull BaseEvaFunction generatedFunction;
	@Nullable              OS_Element      el = null;

	/* @requires idte2.type.getAttached() != null; */
	private boolean findResolved(@NotNull IdentTableEntry idte2, Context ctx) {
		try {
			if (idte2.type.getAttached() instanceof OS_UnknownType) // TODO ??
				return false;

			final OS_Type attached = idte2.type.getAttached();
			if (attached.getType() == OS_Type.Type.USER_CLASS) {
				if (idte2.type.genType.getResolved() == null) {
					@NotNull GenType rtype = deduceTypes2.resolve_type(attached, ctx);
					if (rtype.getResolved() != null) {
						switch (rtype.getResolved().getType()) {
						case USER_CLASS:
							ctx = rtype.getResolved().getClassOf().getContext();
							break;
						case FUNCTION:
							ctx = rtype.getResolved().getElement().getContext();
							break;
						}
						idte2.type.setAttached(rtype); // TODO may be losing alias information here
					}
				}
			}
		} catch (ResolveError resolveError) {
			if (resolveError.resultsList().size() > 1)
				errSink.reportDiagnostic(resolveError);
			else
				LOG.info("1089 Can't attach type to " + idte2.type.getAttached());
//				resolveError.printStackTrace(); // TODO print diagnostic
			return true;
		}
		return false;
	}

	public Resolve_Ident_IA2(final DeduceTypes2 aDeduceTypes2,
							 ErrSink aErrSink,
							 DeducePhase aPhase,
							 @NotNull BaseEvaFunction aGeneratedFunction,
							 @NotNull FoundElement aFoundElement) {
		deduceTypes2      = aDeduceTypes2;
		errSink           = aErrSink;
		phase             = aPhase;
		generatedFunction = aGeneratedFunction;
		foundElement      = aFoundElement;
		//
		LOG = deduceTypes2.LOG;
	}

	//record

	private @NotNull RIA_STATE ia2_IdentIA(@NotNull IdentIA ia2, @NotNull Context ectx) {
		final @NotNull IdentTableEntry idte2 = ia2.getEntry();
		final String                   text  = idte2.getIdent().getText();

		if (idte2.getStatus() == BaseTableEntry.Status.KNOWN) {
			el = idte2.getResolvedElement();
			//assert el != null;
			if (el == null) {
				if (idte2.get_ident() != null) {
					var ident = idte2.get_ident();

					if (ident.getNode() != null) {
						BaseEvaFunction evaFunction = ident.getNode();
						el = evaFunction.getFD();
					}
				}
			}
		} else {
			final LookupResultList lrl = ectx.lookup(text);
			el = lrl.chooseBest(null);

			DeduceElement3_IdentTableEntry de3_idte2 = idte2.getDeduceElement3(deduceTypes2, generatedFunction);
			DR_Ident                       drIdent      = de3_idte2.getDR();

			drIdent.resolve();



			if (el == null) {
				errSink.reportDiagnostic(deduceTypes2._inj().new_ResolveError(idte2.getIdent(), lrl));
//				errSink.reportError("1007 Can't resolve " + text);
				foundElement.doNoFoundElement();
				return RIA_STATE.RETURN;
			} else {
				drIdent.addUnderstanding(new DR_Ident.ElementUnderstanding(el));
				drIdent.addUnderstanding(new ENU_LookupResult(lrl));
			}
		}

		if (!idte2.hasResolvedElement()) {
			idte2.setStatus(BaseTableEntry.Status.KNOWN, deduceTypes2._inj().new_GenericElementHolder(el));
		}
		if (idte2.type == null) {
			if (el instanceof @NotNull final VariableStatementImpl vs) {
				var op = ia2_IdentIA_VariableStatement(idte2, vs, ectx);
				if (op.mode() == Mode.FAILURE) {
					return RIA_STATE.RETURN;
				}
			} else if (el instanceof FunctionDef) {
				ia2_IdentIA_FunctionDef(idte2);
			}
		}
		if (idte2.type != null) {
			//assert idte2.type.getAttached() != null;
			if (idte2.type.getAttached() == null) {
				return RIA_STATE.CONTINUE; // FIXME/TODO 06/19
			}
			if (findResolved(idte2, ectx)) return RIA_STATE.CONTINUE;
		} else {
//			throw new IllegalStateException("who knows");
			errSink.reportWarning("2010 idte2.type == null for " + text);
		}

		return RIA_STATE.NEXT;
	}

	private Operation2<Ok> ia2_IdentIA_VariableStatement(@NotNull IdentTableEntry idte, @NotNull VariableStatementImpl vs, @NotNull Context ctx) {
		var clr = deduceTypes2._inj().new_RIA_Clear_98(idte, vs, ctx, this);
		return clr.run();
	}

	public class RIA_Clear_98 {
		private final IdentTableEntry       idte;
		private final VariableStatementImpl vs;
		private final Context               ctx;

		public RIA_Clear_98(final IdentTableEntry aIdte, final VariableStatementImpl aVs, final Context aCtx) {
			idte = aIdte;
			vs   = aVs;
			ctx  = aCtx;
		}

		public Operation2<Ok> run() {
			try {
				final boolean has_initial_value = vs.initialValue() != IExpression.UNASSIGNED;
				if (!vs.typeName().isNull()) {
					typeName_is_not_null(has_initial_value);
				} else if (has_initial_value) {
					typeName_is_null_with_initial_value();
				} else {
					LOG.err("1936 Empty Variable Expression");
					final Diagnostic d = Diagnostic.withMessage("1936", "Empty Variable Expression", Diagnostic.Severity.ERROR);
					return Operation2.failure(d);
				}
			} catch (ResolveError aResolveError) {
				LOG.err("1937 resolve error " + vs.getName());
				//aResolveError.printStackTrace();
				errSink.reportDiagnostic(aResolveError);
				return Operation2.failure(aResolveError);
			}
			return Operation2.success(Ok.instance());
		}

		private void typeName_is_not_null(final boolean has_initial_value) throws ResolveError {
			// 1.lookup initial value if we have it or create GenType from typeName
			@Nullable GenType attached;
			if (has_initial_value) {
				attached = DeduceLookupUtils.deduceExpression(deduceTypes2, vs.initialValue(), ctx);
			} else { // if (vs.typeName() != null) {
				attached = _inj().new_GenTypeImpl();
				attached.set(_inj().new_OS_UserType(vs.typeName()));
			}

			@Nullable IExpression initialValue;

			if (has_initial_value) {
				initialValue = vs.initialValue();
			} else {
				initialValue = null; // README presumably there is none, ie when generated
			}

			// 2. get OS_Type
			final OS_Type resolvedOrTypename = attached.getResolved() != null ? attached.getResolved() : attached.getTypeName();

			// 3.create tte
			TypeTableEntry tte = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, resolvedOrTypename, initialValue);

			// 4. commit tte
			idte.type = tte;
		}

		private void typeName_is_null_with_initial_value() throws ResolveError {
			// 1. lookup initial value
			final @Nullable GenType attached = DeduceLookupUtils.deduceExpression(deduceTypes2, vs.initialValue(), ctx);

			// 2. get OS_Type
			final OS_Type resolvedOrTypename = attached.getResolved() != null ? attached.getResolved() : attached.getTypeName();

			// 3. create tte
			final @NotNull TypeTableEntry tte = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, resolvedOrTypename, vs.initialValue());

			// 4. commit tte (idte.type)
			idte.type = tte;
		}
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return deduceTypes2._inj();
	}

	private void ia2_IdentIA_FunctionDef(@NotNull IdentTableEntry idte2) {
		@Nullable OS_Type       attached = _inj().new_OS_UnknownType(el);
		@NotNull TypeTableEntry tte      = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, attached, null, idte2);
		idte2.type = tte;

		// Set type to something other than Unknown when found
		@Nullable ProcTableEntry pte = idte2.getCallablePTE();
		if (pte == null) {
			assert false;
		} else {
//			assert pte != null;
			@Nullable FunctionInvocation fi = pte.getFunctionInvocation();
			if (fi == null) {
				InstructionArgument   bl         = idte2.getBacklink();
				@Nullable IInvocation invocation = null;
				if (bl instanceof final @NotNull IntegerIA integerIA) {
					@NotNull VariableTableEntry vte = integerIA.getEntry();
					if (vte.getConstructable_pte() != null) {
						ProcTableEntry cpte = vte.getConstructable_pte();
						invocation = cpte.getFunctionInvocation().getClassInvocation();
					} else if (vte.typeDeferred_isResolved()) {
						final IInvocation[] ainvocation = new IInvocation[1];
						vte.typePromise().then(new DoneCallback<GenType>() {
							@Override
							public void onDone(final @NotNull GenType result) {
								if (result.getCi() == null) {
									result.genCIForGenType2(deduceTypes2);
								}
								ainvocation[0] = result.getCi();
							}
						});
						invocation = ainvocation[0];
					}
				} else if (bl instanceof final @NotNull IdentIA identIA) {
					@NotNull IdentTableEntry ite = identIA.getEntry();
					if (ite.type.genType.getCi() != null)
						invocation = ite.type.genType.getCi();
					else if (ite.type.getAttached() != null) {
						@NotNull OS_Type attached1 = ite.type.getAttached();
						assert attached1.getType() == OS_Type.Type.USER_CLASS;
						invocation = phase.registerClassInvocation(attached1.getClassOf(), null, new ReadySupplier_1<>(deduceTypes2)); // TODO will fail one day
						// TODO dont know if next line is right
						final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(attached1.getClassOf(), null, (NormalTypeName) tte.genType.getNonGenericTypeName(), deduceTypes2);
						assert oi.mode() == Mode.SUCCESS;

						final ClassInvocation invocation2 = oi.success();
						int                   y           = 2;
					}
				} else if (bl instanceof final @NotNull ProcIA procIA) {
					FunctionInvocation bl_fi = procIA.getEntry().getFunctionInvocation();
					if (bl_fi.getClassInvocation() != null) {
						invocation = bl_fi.getClassInvocation();
					} else if (bl_fi.getNamespaceInvocation() != null) {
						invocation = bl_fi.getNamespaceInvocation();
					}
				}

				if (invocation == null) {
					int y = 2;
					// assert false;
				}
				fi = phase.newFunctionInvocation((FunctionDef) el, pte, invocation);
				pte.setFunctionInvocation(fi);
			}

			pte.getFunctionInvocation().generatePromise().then(new DoneCallback<BaseEvaFunction>() {
				@Override
				public void onDone(@NotNull BaseEvaFunction result) {
					result.typePromise().then(new DoneCallback<GenType>() {
						@Override
						public void onDone(GenType result) {
							// NOTE there is no Promise-type notification for when type changes
							idte2.type.setAttached(result);
						}
					});
				}
			});
		}
	}

	public void resolveIdentIA2_(final @NotNull Context ctx,
								 final @Nullable IdentIA identIA,
								 @Nullable List<InstructionArgument> s) {
		boolean found = false;
		try {
			el   = null;
			ectx = ctx;

			if (identIA == null) {
				if (s == null) {
					throw new AssertionError();
				}
			}

			if (s == null) {
				s = BaseEvaFunction._getIdentIAPathList(identIA);
			}

			if (identIA != null) {
				DeducePath          dp    = identIA.getEntry().buildDeducePath(generatedFunction);
				int                 index = dp.size() - 1;
				InstructionArgument ia2   = dp.getIA(index);
				// ia2 is not == equals to identIA, but functionally equivalent
				if (ia2 instanceof IdentIA) {
					final @NotNull IdentTableEntry ite = ((IdentIA) ia2).getEntry();
					if (ite.getBacklink() != null) {
						InstructionArgument backlink = ite.getBacklink();
						if (backlink instanceof final @NotNull IntegerIA integerIA) {
							@NotNull VariableTableEntry                    vte = integerIA.getEntry();
							final DeduceTypes2.PromiseExpectation<GenType> pe  = deduceTypes2.promiseExpectation(vte, "TypePromise for vte " + vte);
							vte.typePromise().then(new DoneCallback<GenType>() {
								@Override
								public void onDone(@NotNull GenType result) {
									pe.satisfy(result);
									switch (result.getResolved().getType()) {
									case FUNCTION:
										ectx = result.getResolved().getElement().getContext();
										break;
									case USER_CLASS:
										ectx = result.getResolved().getClassOf().getContext();
										break;
									default:
										throw new IllegalStateException("Unexpected value: " + result.getResolved().getType());
									}
									ia2_IdentIA((IdentIA) ia2, ectx);
									foundElement.doFoundElement(el);
								}
							});
						} else if (backlink instanceof IdentIA) {
							dp.getElementPromise(index, result -> {
								el   = result;
								ectx = result.getContext();
								ia2_IdentIA((IdentIA) ia2, ectx);
								foundElement.doFoundElement(el);
							}, result -> foundElement.doNoFoundElement());
							dp.getElementPromise(index - 1, result -> {
								ia2_IdentIA((IdentIA) dp.getIA(index - 1), result.getContext()); // might fail
							}, null);

						}
					} else {
						if (!ite.hasResolvedElement()) {
							ia2_IdentIA((IdentIA) ia2, ectx);
							foundElement.doFoundElement(el);
						}
					}
				}
	//			el = dp.getElement(dp.size()-1);
			} else {
				for (InstructionArgument ia2 : s) {
					if (ia2 instanceof IntegerIA) {
						ia2_IntegerIA((IntegerIA) ia2, ectx);
					} else if (ia2 instanceof IdentIA) {
						@NotNull RIA_STATE st = ia2_IdentIA((IdentIA) ia2, ectx);

						switch (st) {
						case CONTINUE:
							continue;
						case NEXT:
							break;
						case RETURN:
							return;
						}
					} else if (ia2 instanceof ProcIA) {
						LOG.err("1373 ProcIA");
	//						@NotNull ProcTableEntry pte = ((ProcIA) ia2).getEntry(); // README ectx seems to be set up already
						return;
					} else
						throw new NotImplementedException();
				}
				foundElement.doFoundElement(el);
			}
		} finally {
			if (found == false) {
				foundElement.noFoundElement();
			}
		}
	}

	private void ia2_IntegerIA(@NotNull IntegerIA ia2, @NotNull Context ctx) {
		@NotNull VariableTableEntry vte  = generatedFunction.getVarTableEntry(DeduceTypes2.to_int(ia2));
		final String                text = vte.getName();

		{
			@NotNull List<TypeTableEntry> pot = deduceTypes2.getPotentialTypesVte(vte);
			if (pot.size() == 1) {
				final OS_Type attached = pot.get(0).getAttached();
				if (attached == null) {
					ia2_IntegerIA_null_attached(ctx, pot);
				} else {
					// TODO what is the state of vte.genType here?
					switch (attached.getType()) {
					case USER_CLASS:
						ectx = attached.getClassOf().getContext(); // TODO can combine later
						break;
					case FUNCTION:
						ectx = attached.getElement().getContext();
						break;
					case USER:
						ectx = attached.getTypeName().getContext();
						break;
					default:
						LOG.err("1098 " + attached.getType());
						throw new IllegalStateException("Can't be here.");
					}
				}
			}
		}

		OS_Type attached = vte.getType().getAttached();
		if (attached != null) {
			switch (attached.getType()) {
			case USER_CLASS:
				ectx = attached.getClassOf().getContext();
				break;
			case FUNCTION:
				ectx = attached.getElement().getContext();
				break;
			case USER:
				try {
					@NotNull GenType ty = deduceTypes2.resolve_type(attached, ctx);
					ectx = ty.getResolved().getClassOf().getContext();
				} catch (ResolveError resolveError) {
					LOG.err("1300 Can't resolve " + attached);
					resolveError.printStackTrace();
				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + attached.getType());
			}
		} else {
			if (vte.potentialTypes().size() == 1) {
				ia2_IntegerIA_potentialTypes_equals_1(vte, text);
			}
		}
	}

	/* @requires pot.get(0).getAttached() == null; */
	private void ia2_IntegerIA_null_attached(@NotNull Context ctx, @NotNull List<TypeTableEntry> pot) {
		try {
			@Nullable FunctionDef    fd  = null;
			@Nullable ProcTableEntry pte = null;
			TableEntryIV             xx  = pot.get(0).tableEntry;
			if (xx != null) {
				if (xx instanceof final @NotNull ProcTableEntry procTableEntry) {
					pte = procTableEntry;
					InstructionArgument xxx = procTableEntry.expression_num;
					if (xxx instanceof final @NotNull IdentIA identIA2) {
						@NotNull IdentTableEntry ite        = identIA2.getEntry();
						DeducePath               deducePath = ite.buildDeducePath(generatedFunction);
						@Nullable OS_Element     el5        = deducePath.getElement(deducePath.size() - 1);
						int                      y          = 2;
						fd = (FunctionDef) el5;
					}
				}
			} else {
				LookupResultList lrl = DeduceLookupUtils.lookupExpression(pot.get(0).__debug_expression.getLeft(), ctx, deduceTypes2);
				@Nullable OS_Element best = lrl.chooseBest(Helpers.List_of(
						new DeduceUtils.MatchFunctionArgs(
								(ProcedureCallExpression) pot.get(0).__debug_expression)));
				if (best instanceof FunctionDef) {
					fd = (FunctionDef) best;
				} else {
					fd = null;
					LOG.err("1195 Can't find match");
				}
			}
			if (fd != null) {
				final IInvocation           invocation = deduceTypes2.getInvocation((EvaFunction) generatedFunction);
				final @Nullable FunctionDef fd2        = fd;
				deduceTypes2.forFunction(deduceTypes2.newFunctionInvocation(fd2, pte, invocation, phase), new ForFunction() {
					@Override
					public void typeDecided(@NotNull GenType aType) {
						assert fd2 == generatedFunction.getFD();
						//
						pot.get(0).setAttached(deduceTypes2.gt(aType));
					}
				});
			} else {
				errSink.reportError("1196 Can't find function");
			}
		} catch (ResolveError aResolveError) {
			aResolveError.printStackTrace();
			int y = 2;
			throw new NotImplementedException();
		}
	}

	private void ia2_IntegerIA_potentialTypes_equals_1(@NotNull VariableTableEntry aVte, String aText) {
		int                                 state     = 0;
		final @NotNull List<TypeTableEntry> pot       = deduceTypes2.getPotentialTypesVte(aVte);
		final OS_Type                       attached1 = pot.get(0).getAttached();
		TableEntryIV                        te        = pot.get(0).tableEntry;
		if (te instanceof final @NotNull ProcTableEntry procTableEntry) {
			// This is how it should be done, with an Incremental
			procTableEntry.getFunctionInvocation().generateDeferred().done(result -> result.typePromise().then(result1 -> {
				int y = 2;
				aVte.resolveType(result1); // save for later
			}));
			// but for now, just set ectx
			InstructionArgument en = procTableEntry.expression_num;
			if (en instanceof final @NotNull IdentIA identIA2) {
				DeducePath           ded = identIA2.getEntry().buildDeducePath(generatedFunction);
				@Nullable OS_Element el2 = ded.getElement(ded.size() - 1);
				if (el2 != null) {
					state = 1;
					ectx  = el2.getContext();
					aVte.getType().setAttached(attached1);
				}
			}
		}
		switch (state) {
		case 0 -> {
			assert attached1 != null;
			aVte.getType().setAttached(attached1);
			// TODO this will break
			switch (attached1.getType()) {
			case USER -> {
				final TypeName attached1TypeName = attached1.getTypeName();
				assert attached1TypeName instanceof RegularTypeName;
				final Qualident realName = ((RegularTypeName) attached1TypeName).getRealName();
				try {
					final List<LookupResult> lrl = DeduceLookupUtils.lookupExpression(realName, ectx, deduceTypes2).results();
					ectx = lrl.get(0).getElement().getContext();
				} catch (ResolveError aResolveError) {
					aResolveError.printStackTrace();
					int y = 2;
					throw new NotImplementedException();
				}
			}
			case USER_CLASS -> ectx = attached1.getClassOf().getContext();
			default -> {
				final TypeName typeName = attached1.getTypeName();
				errSink.reportError("1442 Don't know " + typeName.getClass().getName());
				throw new NotImplementedException();
			}
			}
		}
		case 1 -> {
		}
		default -> LOG.info("1006 Can't find type of " + aText);
		}
	}

	enum RIA_STATE {
		CONTINUE, NEXT, RETURN
	}
}

//
//
//
