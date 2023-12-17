package tripleo.elijah.stages.deduce.post_bytecode;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.imports.NormalImportStatement;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.deduce.post_bytecode.DED.DED_ITE;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.stateful.StateRegistrationToken;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeduceElement3_IdentTableEntry extends DefaultStateful implements IDeduceElement3 {

	public final  IdentTableEntry     principal;
	public        DeduceTypes2        deduceTypes2;
	public        BaseEvaFunction     generatedFunction;
	private       GenType             _resolved;
	private final DeduceElement3_Type _type = new DeduceElement3_Type() {

		@Contract(pure = true)
		@Override
		public GenType genType() {
			return typeTableEntry().genType;
		}

		@Override
		public @NotNull Operation2<GenType> resolved(final Context ectx) {
			try {
				if (_resolved == null) {
					_resolved = deduceTypes2.resolve_type(genType().getTypeName(), ectx);

					typeTableEntry().setAttached(_resolved);
				}

				return Operation2.success(_resolved);
			} catch (ResolveError aResolveError) {
				return Operation2.failure(aResolveError);
			}
		}

		@Contract(pure = true)
		@Override
		public TypeTableEntry typeTableEntry() {
			return principal.type;
		}
	};
	Context context;
	Context fdCtx;
	private       GenType             genType;

	@Contract(pure = true)
	public DeduceElement3_IdentTableEntry(final IdentTableEntry aIdentTableEntry) {
		principal = aIdentTableEntry;
	}

	public void _ctxts(final Context aFdCtx, final Context aContext) {
		fdCtx   = aFdCtx;
		context = aContext;
	}

	public void assign_type_to_idte(final @NotNull Context aFunctionContext, final @NotNull Context aContext) {
		new ExitGetType().
				assign_type_to_idte(principal, generatedFunction, aFunctionContext, aContext, deduceTypes2, deduceTypes2._phase());
	}

	public void backlinkPte(final @NotNull ClassStatement classStatement,
							final ProcTableEntry ignoredPte,
							final @NotNull IElementHolder __eh) {
		// README classStatement [T310-231]

		// README setStause on callablePTE and principal

		final IdentExpression principal_ident      = principal.getIdent();
		var                   principal_ident_name = principal_ident.getName();
		final String          text                 = principal_ident.getText();

		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		final LookupResultList     lrl = classStatement.getContext().lookup(text);
		final @Nullable OS_Element e   = lrl.chooseBest(null);

		if (e != null) {
			if (e instanceof FunctionDef fd) {
				principal_ident_name.addUnderstanding(dt2._inj().new_ENU_FunctionName());
				principal_ident_name.addUnderstanding(dt2._inj().new_ENU_ResolveToFunction(fd));

				// FIXME move this to constructor i guess
				var ename = fd.getNameNode().getName();
				ename.addUnderstanding(dt2._inj().new_ENU_FunctionName());
				ename.addUsage(dt2._inj().new_EN_NameUsage(principal_ident_name, this));
			} else if (e instanceof VariableStatement vs) {
				principal_ident_name.addUnderstanding(dt2._inj().new_ENU_VariableName());
				//principal_ident_name.addUnderstanding(_inj().new_ENU_ResolveToFunction(fd));

				var ename = vs.getNameToken().getName();
				//ename.addUnderstanding(_inj().new_ENU_FunctionName());
				ename.addUsage(dt2._inj().new_EN_NameUsage(principal_ident_name, this));
			} else if (e instanceof PropertyStatement ps) {
				NotImplementedException.raise();
			} else {
				assert false;
			}
		}

		final ProcTableEntry callablePTE = principal.getCallablePTE();
		if (callablePTE != null && e != null) {
			assert e instanceof BaseFunctionDef;  // sholud fail for constructor and destructor
			callablePTE.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
		}

		if (principal.getStatus() == BaseTableEntry.Status.UNCHECKED) {
			final OS_Element e2 = __eh.getElement();
			assert e2 != null;

			//assert e != null;
			if (e instanceof VariableStatement) {
				principal.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_DE3_EH_GroundedVariableStatement((VariableStatement) e, this));
			} else if (e instanceof FunctionDef) {
				principal.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
			}
		}

		if (principal.getStatus() == BaseTableEntry.Status.KNOWN) {
			principal.__gf.getIdent(principal).resolve();
		}
	}

	@Override
	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public @NotNull DED elementDiscriminator() {
		return new DED_ITE(principal);
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		return generatedFunction;
	}

	@Override
	public @NotNull GenType genType() {
		if (genType == null) {
			genType = new GenTypeImpl();//_inj().new_GenTypeImpl();
		}
		return genType;
		//return principal.type.genType;
	}

	@Override
	public OS_Element getPrincipal() {
		return principal.getDeduceElement3(deduceTypes2, generatedFunction).getPrincipal();
	}

	@Override
	public @NotNull DeduceElement3_Kind kind() {
		return DeduceElement3_Kind.GEN_FN__ITE;
	}

	//	@NotNull final GenType xx = // TODO xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	@Override
	public void resolve(final Context aContext, final DeduceTypes2 aDeduceTypes2) {
		//		deduceTypes2.resolveIdentIA_(aContext, aIdentIA, generatedFunction, aFoundElement);
		throw new NotImplementedException();
		// careful with this
		//		throw new UnsupportedOperationException("Should not be reached");
	}

	@Override
	public void resolve(final @NotNull IdentIA aIdentIA, final @NotNull Context aContext, final @NotNull FoundElement aFoundElement) {
		// FoundElement is the "disease"
		deduceTypes2.resolveIdentIA_(aContext, aIdentIA, generatedFunction, aFoundElement);
	}

	public @Nullable EvaNode getResolved() {
		EvaClass R    = null;
		Context  ectx = principal.getIdent().getContext();

		if (_type.typeTableEntry().genType.getTypeName() == null) {
			// README we don't actually care about the typeName, we just
			// wanted to use it to recreate a GenType, where we can then
			// extract .node

			//_type.typeTableEntry().genType.typeName = null;
			//throw new IllegalStateException("Error");
		}

		if (principal.getResolvedElement() instanceof final ClassStatement classStatement) {
			// README but skip this and get the evaClass saved from earlier to
			// Grande [T168-089] when all these objects are being created and
			// manipulated (dern video yttv)
			final DG_ClassStatement dcs = principal._deduceTypes2().DG_ClassStatement(classStatement);

			// README fixup GenType
			//   Still ignoring TypeName and nonGenericTypeName
			//   b/c only client is in gen_c, not deduce
			final GenType gt1 = _type.typeTableEntry().genType;
			//gt1.typeName
			gt1.setCi(dcs.classInvocation());
			gt1.setNode(dcs.evaClass());
			gt1.setFunctionInvocation(dcs.functionInvocation());

			return dcs.evaClass();
		}

		if (false) {
			// README to "prosecute" this we need a ContextImpl. But where to get
			// it from?  And can we #resolveTypeToClass with `dcs' above?
			// Technically, there is one (more) above, but this line does not
			// produce results.
			final Operation2<GenType> or = _type.resolved(ectx);
			if (or.mode() == Mode.SUCCESS) {
				R = (EvaClass) or.success().getResolved();
			}

			assert R != null;
			principal.resolveTypeToClass(R);
		}
		return R;
	}

	public @NotNull Operation2<GenType> resolve1(final @NotNull IdentTableEntry ite, final @NotNull Context aContext) {
		// FoundElement is the "disease"
		try {
			return Operation2.success(deduceTypes2.resolve_type(ite.type.getAttached(), aContext));
		} catch (final ResolveError aE) {
			return Operation2.failure(aE);
		}
	}

	public void setDeduceTypes(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		assert principal.__gf != null;
		assert principal._deduceTypes2() != null;

		deduceTypes2      = aDeduceTypes2;
		generatedFunction = aGeneratedFunction;
	}

	public void sneakResolve() {
		final IdentExpression ident = principal.getIdent();
		final Context         ctx   = ident.getContext();

		final LookupResultList lrl = ctx.lookup(ident.getText());
		OS_Element[]           elx = {null};
		OS_Element             el  = lrl.chooseBest(null);

		if (el != null) {
			var dt2 = principal._deduceTypes2();
			assert dt2 != null;
			ident.getName().addUnderstanding(dt2._inj().new_ENU_LookupResult(lrl)); // [T267665]
		}

		if (el == null) {
			final InstructionArgument bl1 = principal.getBacklink();
			if (bl1 != null) {
				el = sneak_el_null__bl1_not_null(ident, elx, el, bl1);
			} else {
				el = sneak_el_null__bl1_null(ident, el);
			}

			if (el == null) {
				//////////////
				//////////////
				//////////////
				//////////////calculate
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				return; ///throw new IllegalStateException("Error");
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
				//////////////
			}
		}

		if (principal.getCallablePTE() != null) {
			@NotNull final ProcTableEntry callable = principal.getCallablePTE();

			DeduceTypes2    dt2 = callable._deduceTypes2();
			BaseEvaFunction gf  = callable.__gf;

			if (dt2 == null) dt2 = principal._deduceTypes2();
			if (gf == null) gf = principal._generatedFunction();

			var de3_pte = (DeduceElement3_ProcTableEntry) callable.getDeduceElement3(dt2, gf);

			boolean b = de3_pte.sneakResolve_IDTE(el, this);

			if (b) return;
		}

		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		if (el instanceof IdentExpression ie) {
			if (ie.getText().equals(ident.getText())) {
				final DE3_ITE_Holder de3_ite_holder = dt2._inj().new_DE3_ITE_Holder(el, this);
				principal.setStatus(BaseTableEntry.Status.KNOWN, de3_ite_holder);
				de3_ite_holder.commitGenTypeActions();
			}
		} else if (el instanceof VariableStatement vs) {
			if (vs.getName().equals(ident.getText())) {
				final DE3_ITE_Holder de3_ite_holder = dt2._inj().new_DE3_ITE_Holder(el, DeduceElement3_IdentTableEntry.this);
				principal.setStatus(BaseTableEntry.Status.KNOWN, de3_ite_holder);
				de3_ite_holder.commitGenTypeActions();
			}
		} else {
			var c = dt2._phase().pa.getCompilation();
			if (c.reports().outputOn(Finally.Outs.Out_353)) {
				System.err.printf("DeduceElement3_IdentTableEntry >> cant sneakResolve %s based on %s%n", ident.getText(), "" + el/*((IdentExpression)el).getText()*/);
			}
		}
	}

	private OS_Element sneak_el_null__bl1_not_null(final @NotNull IdentExpression ident, final OS_Element[] elx, OS_Element el, final InstructionArgument bl1) {
		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		if (bl1 instanceof final IntegerIA integerIA) {
			@NotNull final VariableTableEntry vte_bl1 = integerIA.getEntry();

			// get DR
			final DR_Ident x = generatedFunction.getIdent(ident, vte_bl1);
			x.foo();

			// get backlink
			final DR_Ident b = generatedFunction.getIdent(vte_bl1);
			b.foo();

			// set up rel
			b.onPossibleType(x::proposeType);

			{
				for (TypeTableEntry potentialType : vte_bl1.potentialTypes()) {
					var y = potentialType.tableEntry;
					if (y instanceof ProcTableEntry pte) {
						//var z  = pte.__debug_expression;
						//var zz = generatedFunction.getProcCall(z, pte);

						if (pte.getStatus() == BaseTableEntry.Status.KNOWN) {
							var ci = pte.getClassInvocation();
							var fi = pte.getFunctionInvocation();

							//System.err.println("322 " + ci);
							//System.err.println("323 " + fi);


							var pt = dt2._inj().new_DR_PossibleTypeCI(ci, fi);
							b.addPossibleType(pt);

							//deduceTypes2.deducePhase.reg

							// README taking a chance here
							var eef = deduceTypes2.creationContext().makeGenerated_fi__Eventual(fi);

							eef.then(new DoneCallback<BaseEvaFunction>() {
								@Override
								public void onDone(final BaseEvaFunction gf) {
									printString(330, "" + gf);

									InstructionArgument ret = (gf.vte_lookup("Result"));
									if (ret instanceof IntegerIA aIntegerIA) {
										var retvte = aIntegerIA.getEntry();
										retvte.typeResolvePromise().then(gt -> {
											printString(336, ""+gt);

											System.exit(336);
										});
										var retvtept = retvte.potentialTypes();
										for (TypeTableEntry typeTableEntry : retvtept) {

										}

										var retvtety = retvte.getType();
										if (retvtety.getAttached() != null) {
											var att  = retvtety.getAttached();
											var resl = att.resolve(principal.getIdent().getContext());

											if (resl != null) {
												var ci11 = deduceTypes2.phase.registerClassInvocation(resl.getClassOf());

												final Compilation c = deduceTypes2.module.getCompilation();
												if (c.reports().outputOn(Finally.Outs.Out_350)) {
													printString(350, ""+ resl);
												}
												var pt2 = dt2._inj().new_DR_PossibleTypeCI(ci11, null);
												b.addPossibleType(pt2);
											} else {
												final Compilation c = deduceTypes2.module.getCompilation();
												if (c.reports().outputOn(Finally.Outs.Out_364)) {
													System.err.println("364 " + principal.getIdent().getText());
												}
											}
										}
									}
								}

								private static void printString(final int code, final String txt) {
									if (code == 330) return;
									System.err.println(""+code+" "+txt);
									if (code == 336)
										printString(336, "********************");
								}
							});
						}
					}

				}
			}

			final DeduceTypeResolve tr = vte_bl1.typeResolve();

			if (vte_bl1.typeDeferred_isResolved()) {
				vte_bl1.typePromise().then(type1 -> {
					if (tr.typeResolution().isPending()) {
						if (!type1.isNull()) {
							tr.typeResolve(type1);
						}
					}

					if (type1.getResolved() != null) {
						final Context          ctx2 = type1.getResolved().getClassOf().getContext();
						final LookupResultList lrl2 = ctx2.lookup(ident.getText());
						elx[0] = lrl2.chooseBest(null);
					}
				});

				el = elx[0];
			} else {
				vte_bl1.getDlv().resolve_var_table_entry_for_exit_function();

				//assert vte_bl1.typeDeferred_isResolved();

				if (vte_bl1.typeDeferred_isResolved()) {
					el = vte_bl1.getResolvedElement();
					assert el != null;
				}
			}
			int y = 2;
		} else if (bl1 instanceof final ProcIA procIA) {
			final ProcTableEntry pte1 = procIA.getEntry();

			int yyy  = 2;
			int yyyy = yyy + 1;

			final IDeduceElement3 de3 = pte1.getDeduceElement3();
		}
		return el;
	}

	@Nullable
	private OS_Element sneak_el_null__bl1_null(final @NotNull IdentExpression ident, OS_Element el) {
		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		List<Context> ctxs = new ArrayList<>();

		Context ctx2 = principal.getIdent().getContext();
		boolean f    = true;
		while (f) {
			if (ctxs.contains(ctx2)) {
				f = false;
				continue;
			}
			if (ctx2 == null) {
				f = false;
				continue;
			}

			if (ctx2 instanceof ModuleContext) {
				ctxs.add(ctx2);

				final @NotNull Collection<ModuleItem> itms = ((ModuleContext) ctx2).getCarrier().getItems();
				for (ModuleItem moduleItem : itms) {
					if (moduleItem instanceof final NormalImportStatement importStatement) {
						ctx2 = importStatement.myContext();

						final LookupResultList lrl2 = ctx2.lookup(ident.getText());
						el = lrl2.chooseBest(null);

						if (el != null) {
							f = false;
							break;
						}
					}
				}

				ctx2 = ctx2.getParent();
			} else {
				ctxs.add(ctx2);
				ctx2 = ctx2.getParent();
			}
		}
		return el;
	}

	public @NotNull DeduceElement3_Type type() {
		return _type;
	}

	public void dan(final @NotNull BaseEvaFunction generatedFunction,
					final @NotNull Instruction instruction,
					final @NotNull Context aContext,
					final @NotNull VariableTableEntry vte,
					final @NotNull IdentIA identIA,
					final @NotNull IdentTableEntry idte,
					final @NotNull DeduceTypes2 aDeduceTypes2) {

		assert idte == principal;

		if (idte.type == null) {
			aDeduceTypes2.resolveIdentIA_(aContext, identIA, generatedFunction, new FoundElement(aDeduceTypes2.phase) {

				@Override
				public void foundElement(final OS_Element e) {
					aDeduceTypes2.found_element_for_ite(generatedFunction, idte, e, aContext, aDeduceTypes2.central());
					assert idte.hasResolvedElement();
					vte.addPotentialType(instruction.getIndex(), idte.type);
				}

				@Override
				public void noFoundElement() {
					// TODO: log error
					int y = 2;
				}
			});
		}
	}

	public DeduceElement3_IdentTableEntry getZero() {
		return deduceTypes2._zero_getIdent(principal, generatedFunction, deduceTypes2);
	}

	public DR_Ident getDR() {
		return principal.get_ident();
	}

	public void stipulate_ResolvedVariable(final @NotNull DeducePhase aDeducePhase,
										   final @NotNull IdentTableEntry identTableEntry,
										   final @NotNull VariableStatement vs,
										   final @NotNull IEvaFunctionBase evaFunction) {
		assert identTableEntry == principal;

		final OS_Element parent = vs.getParent();
		final OS_Element el;
		if (parent == null) {
			assert false;
		} else {
			el = parent.getParent();

			final OS_Element el2 = evaFunction.getFD().getParent();

			if (el != el2) {
				if (!(el instanceof ClassStatement) && !(el instanceof NamespaceStatement)) {
					return;
				}

				// NOTE there is no concept of gf here
				aDeducePhase.registerResolvedVariable(identTableEntry, el, vs.getName());
			}
		}
	}

	public enum ST {
		;

		public static State EXIT_GET_TYPE;
		public static State CHECK_EVA_CLASS_VAR_TABLE;

		public static void register(final @NotNull _RegistrationTarget aRegistrationTarget) {
			EXIT_GET_TYPE             = aRegistrationTarget.registerState(new ExitGetType());
			CHECK_EVA_CLASS_VAR_TABLE = aRegistrationTarget.registerState(new CheckEvaClassVarTable());
		}
	}

	static class CheckEvaClassVarTable implements State {
		private StateRegistrationToken identity;

		@Override
		public void apply(final DefaultStateful element) {
			final DeduceElement3_IdentTableEntry ite_de          = ((DeduceElement3_IdentTableEntry) element);
			final IdentTableEntry                identTableEntry = ite_de.principal;

			identTableEntry.backlinkSet().then(new DoneCallback<InstructionArgument>() {
				@Override
				public void onDone(final InstructionArgument backlink0) {
					BaseTableEntry backlink;

					if (backlink0 instanceof IdentIA) {
						backlink = ((IdentIA) backlink0).getEntry();
						setBacklinkCallback(backlink);
					} else if (backlink0 instanceof IntegerIA) {
						backlink = ((IntegerIA) backlink0).getEntry();
						setBacklinkCallback(backlink);
					} else if (backlink0 instanceof ProcIA) {
						backlink = ((ProcIA) backlink0).getEntry();
						setBacklinkCallback(backlink);
					} else
						backlink = null;
				}

				public void setBacklinkCallback(BaseTableEntry backlink) {
					if (backlink instanceof final ProcTableEntry procTableEntry) {
						procTableEntry.typeResolvePromise().then((final @NotNull GenType result) -> {
							final DeduceElement3_IdentTableEntry de3_ite = identTableEntry.getDeduceElement3();

							if (result.getCi() == null && result.getNode() == null)
								result.genCIForGenType2(de3_ite.deduceTypes2());

							for (EvaContainer.VarTableEntry entry : ((EvaContainerNC) result.getNode()).varTable) {
								if (!entry.isResolved()) {
									System.err.println("629 entry not resolved " + entry.nameToken);
								}
							}
						});
					}
				}
			});
		}

		@Override
		public boolean checkState(final DefaultStateful aElement3) {
			return true;
		}

		@Override
		public void setIdentity(final StateRegistrationToken aId) {
			identity = aId;
		}
	}

	public class DE3_EH_GroundedVariableStatement implements IElementHolder {
		private final @NotNull VariableStatement              element;
		private final          DeduceElement3_IdentTableEntry ground;
		private final @NotNull List<setup_GenType_Action>     actions = new ArrayList<>();

		public DE3_EH_GroundedVariableStatement(final @NotNull VariableStatement aVariableStatement, final DeduceElement3_IdentTableEntry aPrincipal) {
			element = aVariableStatement;
			ground  = aPrincipal;
		}

		public void commitGenTypeActions() {
			final setup_GenType_Action_Arena arena = new setup_GenType_Action_Arena();

			if (principal.type == null) {
				principal.type = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null);
			}
			if (genType == null) {
				genType = principal.type.genType;
			}

			for (setup_GenType_Action action : actions) {
				action.run(principal.type.genType, arena);
			}
		}

		public void genTypeAction(final SGTA_SetResolvedClass aSGTASetResolvedClass) {
			actions.add(aSGTASetResolvedClass);
		}

		@Override
		public @NotNull VariableStatement getElement() {
			return element;
		}

		public DeduceElement3_IdentTableEntry getGround() {
			return ground;
		}
	}

	public class DE3_ITE_Holder implements IElementHolder {
		private final @NotNull List<setup_GenType_Action> actions = new ArrayList<>();
		private final @NotNull OS_Element                 element;

		public DE3_ITE_Holder(final @NotNull OS_Element aElement) {
			element = aElement;
		}

		public void commitGenTypeActions() {
			final setup_GenType_Action_Arena arena = new setup_GenType_Action_Arena();

			if (principal.type == null) {
				principal.type = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null);
			}
			if (genType == null) {
				genType = principal.type.genType;
			}

			for (setup_GenType_Action action : actions) {
				action.run(principal.type.genType, arena);
			}
		}

		public void genTypeAction(final SGTA_SetResolvedClass aSGTASetResolvedClass) {
			actions.add(aSGTASetResolvedClass);
		}

		@Override
		public @NotNull OS_Element getElement() {
			return element;
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
