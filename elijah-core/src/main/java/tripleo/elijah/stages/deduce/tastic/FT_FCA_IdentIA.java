/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce.tastic;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;

/*static*/ public class FT_FCA_IdentIA {

	private final FT_FnCallArgs      FTFnCallArgs;
	private final IdentIA            identIA;
	private final VariableTableEntry vte;

	public FT_FCA_IdentIA(final FT_FnCallArgs aFTFnCallArgs, final IdentIA aIdentIA, final VariableTableEntry aVte) {
		FTFnCallArgs = aFTFnCallArgs;
		identIA      = aIdentIA;
		vte          = aVte;
	}

	public void loop1(final FT_FnCallArgs.@NotNull DoAssignCall dac, final @NotNull Resolve_VTE rvte) {
		var generatedFunction = dac.generatedFunction;
		var dc                = dac.dc;
		var errSink           = dac.errSink;
		var ctx               = rvte.ctx();
		var pte               = rvte.pte();
		var instructionIndex  = rvte.instruction().getIndex();

		var dt2 = dac.dc.get();

		List<TypeTableEntry> args = pte.getArgs();

		for (int i = 0; i < args.size(); i++) {
			final TypeTableEntry tte = args.get(i); // TODO this looks wrong
//			LOG.info("770 "+tte);

			final FT_FCA_Ctx fdctx = dt2._inj().new_FT_FCA_Ctx(generatedFunction, tte, ctx, errSink, dc);

			IExpression e = tte.__debug_expression;
			if (e == null) continue;
			if (e instanceof SubExpression) e = ((SubExpression) e).getExpression();
			switch (e.getKind()) {
			case NUMERIC:
				tte.setAttached(dt2._inj().new_OS_BuiltinType(BuiltInTypes.SystemInteger));
				//vte.type = tte;
				break;
			case CHAR_LITERAL:
				tte.setAttached(dt2._inj().new_OS_BuiltinType(BuiltInTypes.SystemCharacter));
				break;
			case IDENT:
				do_assign_call_args_ident(vte, instructionIndex, pte, i, (IdentExpression) e, fdctx);
				break;
			case PROCEDURE_CALL:
				__loop1__PROCEDURE_CALL(pte, (ProcedureCallExpression) e, fdctx);
				break;
			case DOT_EXP:
				final @NotNull DotExpression de = (DotExpression) e;
				__loop1__DOT_EXP(de, fdctx);
				break;
			case ADDITION:
			case MODULO:
			case SUBTRACTION:
				int y = 2;
				SimplePrintLoggerToRemoveSoon.println_err_2("2363");
				break;
			case GET_ITEM:
				final @NotNull GetItemExpression gie = (GetItemExpression) e;
				do_assign_call_GET_ITEM(gie, fdctx);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + e.getKind());
			}
		}
	}

	void do_assign_call_args_ident(@NotNull VariableTableEntry vte,
								   int aInstructionIndex,
								   @NotNull ProcTableEntry aPte,
								   int aI,
								   @NotNull IdentExpression aExpression,
								   final @NotNull FT_FCA_Ctx fdctx) {
		final DeduceTypes2.DeduceClient4 dc                = fdctx.dc();
		final ErrSink                    errSink           = fdctx.errSink();
		final TypeTableEntry             aTte              = fdctx.tte();
		final BaseEvaFunction            generatedFunction = fdctx.generatedFunction();
		final Context                    ctx               = fdctx.ctx();

		var dt2 = dc.get();

		final String                        e_text = aExpression.getText();
		final @Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(e_text);
//		LOG.info("10000 "+vte_ia);
		if (vte_ia != null) {
			final @NotNull VariableTableEntry  vte1 = generatedFunction.getVarTableEntry(to_int(vte_ia));
			final Promise<GenType, Void, Void> p    = VTE_TypePromises.do_assign_call_args_ident_vte_promise(aTte, vte1);
			@NotNull Runnable runnable = new Runnable() {
				boolean isDone;

				private void __doLogic0__FormalArgListItem(final @NotNull FormalArgListItem fali) {
					dt2._inj().new_FT_FCA_FormalArgListItem(fali, generatedFunction).doLogic0(vte, vte1, errSink);
				}

				private void __doLogic0__VariableStatement(final @NotNull VariableStatementImpl vs) {
					dt2._inj().new_FT_FCA_VariableStatement(vs, generatedFunction).doLogic0(e_text, p, vte1, vte);
				}

				public void doLogic(@NotNull List<TypeTableEntry> potentialTypes) {
					switch (potentialTypes.size()) {
					case 1:
						doLogic1(potentialTypes);
						break;
					case 0:
						doLogic0();
						break;
					default:
						doLogic_default(potentialTypes);
						break;
					}
				}

				private void doLogic_default(final @NotNull List<TypeTableEntry> potentialTypes) {
					// TODO hopefully this works
					final @NotNull List<TypeTableEntry> potentialTypes1 = potentialTypes.stream()
							.filter(input -> input.getAttached() != null)
							.collect(Collectors.toList());

					// prevent infinite recursion
					if (potentialTypes1.size() < potentialTypes.size())
						doLogic(potentialTypes1);
					else
						FTFnCallArgs.LOG.info("913 Don't know");
				}

				private void doLogic0() {
					// README moved up here to elimiate work
					if (p.isResolved()) {
						System.out.printf("890-1 Already resolved type: vte1.type = %s, gf = %s %n", vte1.getType(), generatedFunction);
						return;
					}
					LookupResultList     lrl  = ctx.lookup(e_text);
					@Nullable OS_Element best = lrl.chooseBest(null);
					if (best instanceof @NotNull final FormalArgListItem fali) {
						__doLogic0__FormalArgListItem(fali);
					} else if (best instanceof final @NotNull VariableStatementImpl vs) {
						__doLogic0__VariableStatement(vs);
					} else {
						int y = 2;
						FTFnCallArgs.LOG.err("543 " + best.getClass().getName());
						throw new NotImplementedException();
					}
				}

				private void doLogic1(final @NotNull List<TypeTableEntry> potentialTypes) {
//						tte.attached = ll.get(0).attached;
//						vte.addPotentialType(instructionIndex, ll.get(0));
					if (p.isResolved()) {
						FTFnCallArgs.LOG.info(String.format("1047 (vte already resolved) %s vte1.type = %s, gf = %s, tte1 = %s %n", vte1.getName(), vte1.getType(), generatedFunction, potentialTypes.get(0)));
						return;
					}

					final OS_Type attached = potentialTypes.get(0).getAttached();
					if (attached == null) return;
					switch (attached.getType()) {
					case USER:
						vte1.getType().setAttached(attached); // !!
						break;
					case USER_CLASS:
						final GenType gt = vte1.getGenType();
						gt.setResolved(attached);
						vte1.resolveType(gt);
						break;
					default:
						errSink.reportWarning("Unexpected value: " + attached.getType());
//							throw new IllegalStateException("Unexpected value: " + attached.getType());
					}
				}

				@Override
				public void run() {
					if (isDone) return;
					final @NotNull List<TypeTableEntry> ll = dc.getPotentialTypesVte((EvaFunction) generatedFunction, vte_ia);
					doLogic(ll);
					isDone = true;
				}
			};
			dc.onFinish(runnable);
		} else {
			int                      ia   = generatedFunction.addIdentTableEntry(aExpression, ctx);
			@NotNull IdentTableEntry idte = generatedFunction.getIdentTableEntry(ia);
			idte.addPotentialType(aInstructionIndex, aTte); // TODO DotExpression??
			final int ii = aI;
			idte.onType(dc.getPhase(), new OnType() {
				@Override
				public void noTypeFound() {
					FTFnCallArgs.LOG.err("719 no type found " + generatedFunction.getIdentIAPathNormal(dt2._inj().new_IdentIA(ia, generatedFunction)));
				}

				@Override
				public void typeDeduced(@NotNull OS_Type aType) {
					aPte.setArgType(ii, aType); // TODO does this belong here or in FunctionInvocation?
					aTte.setAttached(aType); // since we know that tte.attached is always null here
				}
			});
		}
	}

	private void __loop1__PROCEDURE_CALL(final ProcTableEntry pte, final @NotNull ProcedureCallExpression pce, final @NotNull FT_FCA_Ctx fdctx) {
		final DeduceTypes2.DeduceClient4 dc                = fdctx.dc();
		final ErrSink                    errSink           = fdctx.errSink();
		final TypeTableEntry             tte               = fdctx.tte();
		final BaseEvaFunction            generatedFunction = fdctx.generatedFunction();
		final Context                    ctx               = fdctx.ctx();

		var dt2 = dc.get();

		FT_FCA_ProcedureCall fcapce = dt2._inj().new_FT_FCA_ProcedureCall(pce, ctx, this);

		try {
			final LookupResultList lrl  = dc.lookupExpression(pce.getLeft(), ctx);
			@Nullable OS_Element   best = lrl.chooseBest(null);
			if (best != null) {
				while (best instanceof AliasStatementImpl) {
					best = dc._resolveAlias2((AliasStatementImpl) best);
				}
				if (best instanceof FunctionDef) {
					final OS_Element      parent = best.getParent();
					@Nullable IInvocation invocation;
					if (parent instanceof final @NotNull NamespaceStatement nsp) {
						invocation = dc.registerNamespaceInvocation(nsp);
					} else if (parent instanceof final @NotNull ClassStatement csp) {
						invocation = dc.registerClassInvocation(csp, null);
					} else
						throw new NotImplementedException(); // TODO implement me

					// tte transitiveOver: {best/codePoint >> FunctionInvocation} resolvedReturnType
					dc.forFunction(dc.newFunctionInvocation((FunctionDef) best, pte, invocation), new ForFunction() {
						@Override
						public void typeDecided(@NotNull GenType aType) {
							tte.setAttached(aType);
//									vte.addPotentialType(instructionIndex, tte);
						}
					});
//							tte.setAttached(_inj().new_OS_FuncType((FunctionDef) best));

				} else {
					final int y = 2;
					throw new NotImplementedException();
				}
			} else {
				final int y = 2;
				throw new NotImplementedException();
			}
		} catch (ResolveError aResolveError) {
//					aResolveError.printStackTrace();
//					int y=2;
//					throw new NotImplementedException();
			dc.reportDiagnostic(aResolveError);
			tte.setAttached(dt2._inj().new_OS_UnknownType(dt2._inj().new_StatementWrapperImpl(pce.getLeft(), null, null)));
		}
	}

	private void __loop1__DOT_EXP(final @NotNull DotExpression de, final @NotNull FT_FCA_Ctx fdctx) {
		final DeduceTypes2.DeduceClient4 dc                = fdctx.dc();
		final ErrSink                    errSink           = fdctx.errSink();
		final TypeTableEntry             tte               = fdctx.tte();
		final BaseEvaFunction            generatedFunction = fdctx.generatedFunction();
		final Context                    ctx               = fdctx.ctx();

		try {
			final LookupResultList lrl  = dc.lookupExpression(de.getLeft(), ctx);
			@Nullable OS_Element   best = lrl.chooseBest(null);
			if (best != null) {
				while (best instanceof AliasStatementImpl) {
					best = dc._resolveAlias2((AliasStatementImpl) best);
				}
				if (best instanceof FunctionDef) {
					tte.setAttached(((FunctionDef) best).getOS_Type());
					//vte.addPotentialType(instructionIndex, tte);
				} else if (best instanceof ClassStatement) {
					tte.setAttached(((ClassStatement) best).getOS_Type());
				} else if (best instanceof final @NotNull VariableStatementImpl vs) {
					@Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(vs.getName());
					TypeTableEntry                tte1   = ((IntegerIA) Objects.requireNonNull(vte_ia)).getEntry().getType();
					tte.setAttached(tte1.getAttached());
				} else {
					final int y = 2;
					FTFnCallArgs.LOG.err(best.getClass().getName());
					throw new NotImplementedException();
				}
			} else {
				final int y = 2;
				throw new NotImplementedException();
			}
		} catch (ResolveError aResolveError) {
			aResolveError.printStackTrace();
			int y = 2;
			throw new NotImplementedException();
		}
	}

	void do_assign_call_GET_ITEM(@NotNull GetItemExpression gie, final @NotNull FT_FCA_Ctx fdctx) {
		final DeduceTypes2.DeduceClient4 dc                = fdctx.dc();
		final ErrSink                    errSink           = fdctx.errSink();
		final TypeTableEntry             tte               = fdctx.tte();
		final BaseEvaFunction            generatedFunction = fdctx.generatedFunction();
		final Context                    ctx               = fdctx.ctx();

		var dt2 = dc.get();

		try {
			final LookupResultList     lrl  = dc.lookupExpression(gie.getLeft(), ctx);
			final @Nullable OS_Element best = lrl.chooseBest(null);
			if (best != null) {
				if (best instanceof @NotNull final VariableStatementImpl vs) { // TODO what about alias?
					String                        s      = vs.getName();
					@Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(s);
					if (vte_ia != null) {
						@NotNull VariableTableEntry vte1 = generatedFunction.getVarTableEntry(to_int(vte_ia));
						throw new NotImplementedException();
					} else {
						final IdentTableEntry idte = generatedFunction.getIdentTableEntryFor(vs.getNameToken());
						assert idte != null;
						if (idte.type == null) {
							final IdentIA identIA = dt2._inj().new_IdentIA(idte.getIndex(), generatedFunction);
							dc.resolveIdentIA_(ctx, identIA, generatedFunction, dt2._inj().new_FT_FnCallArgs_DoAssignCall_NullFoundElement(dc));
						}
						@Nullable OS_Type ty;
						if (idte.type == null) ty = null;
						else ty = idte.type.getAttached();
						idte.onType(dc.getPhase(), new OnType() {
							@Override
							public void noTypeFound() {
								throw new NotImplementedException();
							}

							@Override
							public void typeDeduced(final @NotNull OS_Type ty) {
								assert ty != null;
								@NotNull GenType rtype = null;
								try {
									rtype = dc.resolve_type(ty, ctx);
								} catch (ResolveError resolveError) {
									//								resolveError.printStackTrace();
									errSink.reportError("Cant resolve " + ty); // TODO print better diagnostic
									return;
								}
								if (rtype.getResolved() != null && rtype.getResolved().getType() == OS_Type.Type.USER_CLASS) {
									LookupResultList     lrl2  = rtype.getResolved().getClassOf().getContext().lookup("__getitem__");
									@Nullable OS_Element best2 = lrl2.chooseBest(null);
									if (best2 != null) {
										if (best2 instanceof @NotNull final FunctionDef fd) {
											@Nullable ProcTableEntry pte        = null;
											final IInvocation        invocation = dc.getInvocation((EvaFunction) generatedFunction);
											dc.forFunction(dc.newFunctionInvocation(fd, pte, invocation), new ForFunction() {
												@Override
												public void typeDecided(final @NotNull GenType aType) {
													assert fd == generatedFunction.getFD();
													//
													if (idte.type == null) {
														idte.makeType(generatedFunction, TypeTableEntry.Type.TRANSIENT, dc.gt(aType));  // TODO expression?
													} else
														idte.type.setAttached(dc.gt(aType));
												}
											});
										} else {
											throw new NotImplementedException();
										}
									} else {
										throw new NotImplementedException();
									}
								}
							}
						});
						if (ty == null) {
							@NotNull TypeTableEntry tte3 = generatedFunction.newTypeTableEntry(
									TypeTableEntry.Type.SPECIFIED, dt2._inj().new_OS_UserType(vs.typeName()), vs.getNameToken());
							idte.type = tte3;
//							ty = idte.type.getAttached();
						}
					}

					//				tte.attached = _inj().new_OS_FuncType((FunctionDef) best); // TODO: what is this??
					//vte.addPotentialType(instructionIndex, tte);
				} else if (best instanceof final @Nullable FormalArgListItem fali) {
					String                        s      = fali.name().asString();
					@Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(s);
					if (vte_ia != null) {
						@NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(to_int(vte_ia));

//						final @Nullable OS_Type ty2 = vte2.type.attached;
						VTE_TypePromises.getItemFali(generatedFunction, ctx, vte2, dc.get());
//					vte2.onType(phase, _inj().new_OnType() {
//						@Override public void typeDeduced(final OS_Type ty2) {
//						}
//
//						@Override
//						public void noTypeFound() {
//							throw new NotImplementedException();
//						}
//					});
/*
					if (ty2 == null) {
						@NotNull TypeTableEntry tte3 = generatedFunction.newTypeTableEntry(
								TypeTableEntry.Type.SPECIFIED, _inj().new_OS_UserType(fali.typeName()), fali.getNameToken());
						vte2.type = tte3;
//						ty2 = vte2.type.attached; // TODO this is final, but why assign anyway?
					}
*/
					}
				} else {
					final int y = 2;
					throw new NotImplementedException();
				}
			} else {
				final int y = 2;
				throw new NotImplementedException();
			}
		} catch (ResolveError aResolveError) {
			aResolveError.printStackTrace();
			int y = 2;
			throw new NotImplementedException();
		}
	}

	void loop2(final FT_FnCallArgs.@NotNull DoAssignCall dac, final @NotNull Resolve_VTE rvte) {
		final Context                     ctx              = rvte.ctx();
		final ProcTableEntry              pte              = rvte.pte();
		final @NotNull Instruction        instruction      = rvte.instruction();
		final int                         instructionIndex = instruction.getIndex();
		final @NotNull VariableTableEntry vte              = rvte.vte();
		final @NotNull FnCallArgs         fca              = rvte.fca();

		if (pte.expression_num == null) {
			if (fca.expression_to_call.getName() != InstructionName.CALLS) {
				final String           text = ((IdentExpression) pte.__debug_expression).getText();
				final LookupResultList lrl  = ctx.lookup(text);

				final @Nullable OS_Element best = lrl.chooseBest(null);
				if (best != null)
					pte.setResolvedElement(best); // TODO do we need to add a dependency for class?
				else {
					dac.errSink.reportError("Cant resolve " + text);
				}
			} else {
				dac.dc.implement_calls(dac.generatedFunction, ctx.getParent(), instruction.getArg(1), pte, instructionIndex);
			}
		} else {
			var idte = identIA.getEntry();
			assert idte != null;
			dac.dc.resolveIdentIA_(ctx, identIA, dac.generatedFunction, new FoundElement(dac.dc.getPhase()) {

				final String x = dac.generatedFunction.getIdentIAPathNormal(identIA);

				@Override
				public void foundElement(OS_Element el) {
					if (pte.getResolvedElement() == null)
						pte.setResolvedElement(el);
					final DeduceTypes2 deduceTypes2 = dac.dc.get();
					if (el instanceof FunctionDef) {
						final FT_FCA_FunctionDef fcafd = deduceTypes2._inj().new_FT_FCA_FunctionDef((FunctionDef) el, deduceTypes2);
						fcafd.loop2_i(dac, pte, vte, instructionIndex);
					} else if (el instanceof @NotNull final ClassStatement kl) {
						final FT_FCA_ClassStatement fcafd = deduceTypes2._inj().new_FT_FCA_ClassStatement((ClassStatement) el);
						loop2_i(kl);
					} else {
						dac.LOG.err("7890 " + el.getClass().getName());
					}
				}

				private void loop2_i(final @NotNull ClassStatement kl) {
					@NotNull OS_Type        type = kl.getOS_Type();
					@NotNull TypeTableEntry tte  = dac.generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, type, pte.__debug_expression, pte);
					vte.addPotentialType(instructionIndex, tte);
					vte.setConstructable(pte);

					dac.dc.register_and_resolve(vte, kl);
				}

				@Override
				public void noFoundElement() {
					dac.LOG.err("IdentIA path cannot be resolved " + x);
				}
			});
		}
	}

	public void make2(final FT_FnCallArgs.@NotNull DoAssignCall dac, final @NotNull Resolve_VTE rvte) throws FCA_Stop {
		var generatedFunction = dac.generatedFunction;
		var dc4               = dac.dc;
		var errSink           = dac.errSink;
		var LOG               = dac.LOG;
		var module            = dac.getModule();
		var ctx               = rvte.ctx();
		var instructionIndex  = rvte.instruction().getIndex();
		var pte               = rvte.pte();

		var dt2 = dc4.get();

		if (identIA != null) {
//				LOG.info("594 "+identIA.getEntry().getStatus());

			var dc = dt2._inj().new_FakeDC4(dc4, this);

			final OS_Element resolved_element = identIA.getEntry().getResolvedElement();

			if (resolved_element == null) return;

			var ext = dt2._inj().new_DT_External_2(identIA.getEntry(),
												   module,
												   pte,
												   dc,
												   LOG,
												   ctx,
												   generatedFunction,
												   instructionIndex,
												   identIA,
												   vte);

			FTFnCallArgs.deduceTypes2.addExternal(ext);
		}
	}

	public void resolve_vte(final FT_FnCallArgs.@NotNull DoAssignCall dac, final @NotNull Resolve_VTE rvte) {
		var aGeneratedFunction = dac.generatedFunction;
		var dc                 = dac.dc;
		var ctx                = rvte.ctx();
		var pte                = rvte.pte();

		var dt2 = dc.get();

		final OS_Element resolvedElement = vte.getResolvedElement();
		if (resolvedElement != null) {
			DeferredObject<IdentExpression, Void, Void> p  = new DeferredObject<>();
			DeferredObject<Void, ResolveError, Void>    p2 = new DeferredObject<>();
			p.then(identExpression -> {
				OS_Element el;
				try {
					el = dc.lookup(identExpression, ctx);
				} catch (ResolveError aE) {
					p2.reject(aE);
					return;
				}

				final DR_Ident ident = aGeneratedFunction.getIdent(identExpression, vte);
				ident.addUnderstanding(dt2._inj().new_DR_Ident_ElementUnderstanding(el));

				if (el instanceof VariableStatement vtn) {
					if (vtn.getTypeModifiers() == (TypeModifiers.CONST)) { // FIXME not a list
						var idex = vtn.getNameToken();
						idex.getName().addUnderstanding(dt2._inj().new_ENU_LangConstVar());
					}
				}

				vte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(el));
			});
			p2.fail(dc::reportDiagnostic);

			if (resolvedElement instanceof IdentExpression) {
				p.resolve((IdentExpression) resolvedElement);
			} else {
				final IdentExpression nameToken = ((VariableStatementImpl) resolvedElement).getNameToken();
				p.resolve(nameToken);
			}
		}

		if (vte.getStatus() == BaseTableEntry.Status.UNCHECKED) {
			pte.typePromise().then(vte::resolveType);
		}
	}

	public class FakeDC4 {
		private final DeduceTypes2.DeduceClient4 dc4;

		public FakeDC4(final DeduceTypes2.DeduceClient4 aDc4) {
			dc4 = aDc4;
		}

		public @NotNull ClassInvocation registerClassInvocation(final ClassInvocation invocation2) {
			return dc4.getPhase().registerClassInvocation((ClassInvocation) invocation2);
		}

		public FunctionInvocation newFunctionInvocation(final FunctionDef aResolvedElement, final ProcTableEntry aPte, final @NotNull IInvocation aInvocation2) {
			return dc4.newFunctionInvocation(aResolvedElement, aPte, aInvocation2);
		}

		public DeferredMemberFunction deferred_member_function(final OS_Element aParent, final IInvocation aInvocation2, final FunctionDef aResolvedElement, final FunctionInvocation aFi) {
			return dc4.deferred_member_function(aParent, aInvocation2, aResolvedElement, aFi);
		}

		public void resolveIdentIA_(final Context aCtx, final IdentIA aIdentIA, final BaseEvaFunction aGeneratedFunction, final FoundElement aFunctionResultType) {
			dc4.resolveIdentIA_(aCtx, aIdentIA, aGeneratedFunction, aFunctionResultType);
		}

		public DeducePhase getPhase() {
			return dc4.getPhase();
		}

		public void found_element_for_ite(final BaseEvaFunction aGeneratedFunction, final IdentTableEntry aIte, final OS_Element aE, final Context aCtx) {
			dc4.found_element_for_ite(aGeneratedFunction, aIte, aE, aCtx);
		}

		public OS_Element _resolveAlias(final AliasStatement aAliasStatement) {
			return dc4._resolveAlias(aAliasStatement);
		}

		public DeduceTypes2.PromiseExpectation<GenType> promiseExpectation(final BaseEvaFunction aBgf, final String aFunctionResultType) {
			return dc4.promiseExpectation(aBgf, aFunctionResultType);
		}

		public DeduceTypes2 _deduceTypes2() {
			return dc4.get();
		}

		public DeduceTypes2.DeduceTypes2Injector _inj() {
			return dc4.get()._inj();
		}
	}

	public record Resolve_VTE(VariableTableEntry vte, Context ctx, ProcTableEntry pte, Instruction instruction,
							  FnCallArgs fca) {
	}

	public record FT_FCA_Ctx(
			BaseEvaFunction generatedFunction,
			TypeTableEntry tte,
			Context ctx,
			ErrSink errSink,
			DeduceTypes2.DeduceClient4 dc
	) {
	}

	public class FT_FCA_ProcedureCall {
		private final Context                 ctx;
		private final ProcedureCallExpression pce;

		public FT_FCA_ProcedureCall(final ProcedureCallExpression aPce, final Context aCtx) {
			pce = aPce;
			ctx = aCtx;
		}
	}

}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
