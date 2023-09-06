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
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.impl.FuncTypeNameImpl;
import tripleo.elijah.lang.types.OS_FuncExprType;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_VariableTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah.stages.deduce.tastic.DT_External_2;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 9/5/21 2:54 AM
 */
class Resolve_Variable_Table_Entry {
	private final Context      ctx;
	private final DeduceTypes2 deduceTypes2;

	private final          ErrSink         errSink;
	private final          BaseEvaFunction generatedFunction;
	private final @NotNull ElLog           LOG;
	private final @NotNull DeducePhase     phase;
	private final @NotNull WorkManager     wm;

	public Resolve_Variable_Table_Entry(BaseEvaFunction aGeneratedFunction, Context aCtx, DeduceTypes2 aDeduceTypes2) {
		generatedFunction = aGeneratedFunction;
		ctx               = aCtx;
		deduceTypes2      = aDeduceTypes2;
		//
		LOG     = deduceTypes2._LOG();
		wm      = deduceTypes2.wm;
		errSink = deduceTypes2._errSink();
		phase   = deduceTypes2._phase();
	}

	public void action(final @NotNull VariableTableEntry vte, final @NotNull DeduceTypes2.IVariableConnector aConnector) {
		switch (vte.getVtt()) {
		case ARG:
			action_ARG(vte);
			break;
		case VAR:
			action_VAR(vte);
			break;
		}
		aConnector.connect(vte, vte.getName());
	}

	private void action_ARG(@NotNull VariableTableEntry vte) {
		TypeTableEntry tte      = vte.getType();
		final OS_Type  attached = tte.getAttached();
		if (attached != null) {
			final GenType genType = tte.genType;
			switch (attached.getType()) {
			case USER:
				if (genType.getTypeName() == null)
					genType.setTypeName(attached);
				try {
					if (attached.getTypeName() instanceof RegularTypeName rtn) {
						if (rtn.getGenericPart() != null) {
							// README Can't resolve generic typenames, need a classInvocation ...
							//08/13 System.err.println("===== future 8181");
							return;
						}
					}
					if (attached.getTypeName() instanceof FuncTypeName ftn) {
						if (ftn.argListIsGeneric() || ((FuncTypeNameImpl) ftn)._arglist.p() != null) {
							// README Can't resolve generic typenames, need a classInvocation ...
							//08/13 System.err.println("===== future 8181");
							return;
						}
					}

					genType.copy(deduceTypes2.resolve_type(attached, ctx));
					tte.setAttached(genType.getResolved()); // TODO probably not necessary, but let's leave it for now
				} catch (ResolveError aResolveError) {
					errSink.reportDiagnostic(aResolveError);
					LOG.err("Can't resolve argument type " + attached);
					return;
				}
				if (generatedFunction.fi.getClassInvocation() != null)
					genNodeForGenType(genType, generatedFunction.fi.getClassInvocation());
				else
					genCIForGenType(genType);
				vte.resolveType(genType);
				break;
			case USER_CLASS:
				if (genType.getResolved() == null)
					genType.setResolved(attached);
				// TODO genCI and all that -- Incremental?? (.increment())
				vte.resolveType(genType);
				genCIForGenType2(genType);
				break;
			}
		} else {
			int y = 2;
		}
	}

	private void action_VAR(@NotNull VariableTableEntry vte) {
		if (vte.getType().getAttached() == null && vte.getPotentialTypes().size() == 1) {
			TypeTableEntry pot = deduceTypes2._inj().new_ArrayList__TypeTableEntry(vte.potentialTypes()).get(0);
			if (pot.getAttached() instanceof OS_FuncExprType) {
				action_VAR_potsize_1_and_FuncExprType(vte, (OS_FuncExprType) pot.getAttached(), pot.genType, pot.__debug_expression);
			} else if (pot.getAttached() != null && pot.getAttached().getType() == OS_Type.Type.USER_CLASS) {
				int y = 1;
				vte.setType(pot);
				vte.resolveType(pot.genType);
			} else {
				action_VAR_potsize_1_other(vte, pot);
			}
		}
	}

	private void action_VAR_potsize_1_and_FuncExprType(@NotNull VariableTableEntry vte,
													   @NotNull OS_FuncExprType funcExprType,
													   @NotNull GenType aGenType,
													   IExpression aPotentialExpression) {
		aGenType.setTypeName(funcExprType);

		final @NotNull FuncExpr fe = (FuncExpr) funcExprType.getElement();

		// add namespace
		final @NotNull OS_Module             mod1   = fe.getContext().module();
		final Operation2<NamespaceStatement> nso    = lookup_module_namespace(mod1);
		final NamespaceStatement             mod_ns = nso.success();

		@Nullable ProcTableEntry callable_pte = null;

		if (mod_ns != null) {
			// add func_expr to namespace
			@NotNull FunctionDef fd1 = deduceTypes2._inj().new_FunctionDefImpl(mod_ns, mod_ns.getContext());
			fd1.setFal(fe.fal());
			fd1.setContext((FunctionContext) fe.getContext());
			fd1.scope(fe.getScope());
			fd1.setSpecies(BaseFunctionDef.Species.FUNC_EXPR);
//			tripleo.elijah.util.Stupidity.println_out_2("1630 "+mod_ns.getItems()); // element 0 is ctor$0
			fd1.setName(IdentExpression.forString(String.format("$%d", mod_ns.getItems().size() + 1)));

			@NotNull WorkList              wl   = deduceTypes2._inj().new_WorkList();
			@NotNull GenerateFunctions     gen  = phase.generatePhase.getGenerateFunctions(mod1);
			@NotNull NamespaceInvocation   modi = deduceTypes2._inj().new_NamespaceInvocation(mod_ns);
			final @Nullable ProcTableEntry pte  = findProcTableEntry(generatedFunction, aPotentialExpression);
			assert pte != null;
			callable_pte = pte;
			@NotNull FunctionInvocation fi = phase.newFunctionInvocation(fd1, pte, modi);
			wl.addJob(deduceTypes2._inj().new_WlGenerateNamespace(gen, modi, phase.generatedClasses, phase.codeRegistrar)); // TODO hope this works (for more than one)
			final @Nullable WlGenerateFunction wlgf = deduceTypes2._inj().new_WlGenerateFunction(gen, fi, phase.codeRegistrar);
			wl.addJob(wlgf);
			wm.addJobs(wl);
			wm.drain(); // TODO here?

			aGenType.setCi(modi);
			aGenType.setNode(wlgf.getResult());

			DeduceTypes2.@NotNull PromiseExpectation<GenType> pe = deduceTypes2.promiseExpectation(/*pot.genType.node*/new DeduceTypes2.ExpectationBase() {
				@Override
				public @NotNull String expectationString() {
					return "FuncType..."; // TODO
				}
			}, "FuncType Result");
			((EvaFunction) aGenType.getNode()).typePromise().then(new DoneCallback<GenType>() {
				@Override
				public void onDone(@NotNull GenType result) {
					pe.satisfy(result);
					vte.resolveType(result);
				}
			});

			//vte.typeDeferred().resolve(pot.genType); // this is wrong
		}

		if (callable_pte != null)
			vte.setCallablePTE(callable_pte);
	}

	/**
	 * Sets the node for a GenType, given an invocation
	 *
	 * @param aGenType the GenType to modify. must be set to a nonGenericTypeName that is non-null and generic
	 */
	private void genNodeForGenType(final @NotNull GenType aGenType, IInvocation invocation) {
		//assert aGenType.getNonGenericTypeName() != null;

		assert aGenType.getCi() == null || aGenType.getCi() == invocation;

		aGenType.setCi(invocation);
		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(aGenType::setNode);
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(aGenType::setNode);
		} else
			throw new IllegalStateException("invalid invocation");
	}

	/**
	 * Sets the invocation ({@code genType#ci}) and the node for a GenType
	 *
	 * @param aGenType the GenType to modify. must be set to a nonGenericTypeName that is non-null and generic
	 */
	private void genCIForGenType(final @NotNull GenType aGenType) {








		//assert aGenType.getNonGenericTypeName() != null;//&& ((NormalTypeName) aGenType.nonGenericTypeName).getGenericPart().size() > 0;
		if (aGenType.getNonGenericTypeName() == null) return;






		aGenType.genCI(aGenType.getNonGenericTypeName(), deduceTypes2, deduceTypes2._errSink(), deduceTypes2.phase);
		final IInvocation invocation = aGenType.getCi();
		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(result -> aGenType.setNode(result));
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(result -> aGenType.setNode(result));
		} else
			throw new IllegalStateException("invalid invocation");
	}

	/**
	 * Sets the invocation ({@code genType#ci}) and the node for a GenType
	 *
	 * @param aGenType the GenType to modify. doesn;t care about  nonGenericTypeName
	 */
	private void genCIForGenType2(final @NotNull GenType aGenType) {
		final List<setup_GenType_Action> list  = new ArrayList<>();
		final setup_GenType_Action_Arena arena = new setup_GenType_Action_Arena();

		aGenType.genCI(aGenType.getNonGenericTypeName(), deduceTypes2, deduceTypes2._errSink(), deduceTypes2.phase);
		final IInvocation invocation = aGenType.getCi();
		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(result -> aGenType.setNode(result));
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(result -> aGenType.setNode(result));
		} else
			throw new IllegalStateException("invalid invocation");

		for (setup_GenType_Action action : list) {
			action.run(aGenType, arena);
		}
	}

	private void action_VAR_potsize_1_other(@NotNull VariableTableEntry vte, @NotNull TypeTableEntry aPot) {
		try {
			if (aPot.tableEntry instanceof final @NotNull ProcTableEntry pte1) {
				final IExpression debugExpression = pte1.__debug_expression;

				DT_External_2 llext = null;

				if (debugExpression instanceof DotExpression dot) {
					var left = dot.getLeft();
					if (left instanceof ProcedureCallExpression pce) {
						var leftlfeft = pce.getLeft();
						if (leftlfeft instanceof IdentExpression ie) {
							var llname = ie.getName();

							llext = deduceTypes2._inj().new_DT_External_2((IdentTableEntry) pte1.getEvaExpression().getEntry(),
																		  ie.getContext().module(),
																		  pte1,
																		  null,
																		  LOG,
																		  ctx,
																		  generatedFunction,
																		  -1,
																		  null,
																		  vte);

							deduceTypes2.addExternal(llext);
						}
					}
				}


				@Nullable OS_Element e = DeduceLookupUtils.lookup(debugExpression, ctx, deduceTypes2);


				// 05/10
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				// assert e != null;
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//

				if (e != null) {
					final DeduceElement3_VariableTableEntry de3_vte = deduceTypes2.zeroGet(vte, generatedFunction);
					de3_vte.__action_vp1o(vte, aPot, pte1, e);
				} else {
					if (llext != null) {
						llext.onResolve(el -> {
							final DeduceElement3_VariableTableEntry de3_vte = deduceTypes2.zeroGet(vte, generatedFunction);
							de3_vte.__action_vp1o(vte, aPot, pte1, el);
						});
					}

					System.err.println("118118 Making external for " + debugExpression);
				}
			} else if (aPot.tableEntry == null) {
				final OS_Element el = vte.getResolvedElement();
				if (el instanceof final @NotNull VariableStatement variableStatement) {

					final DeduceElement3_VariableTableEntry de3_vte = deduceTypes2.zeroGet(vte, generatedFunction);
					de3_vte.__action_VAR_pot_1_tableEntry_null(variableStatement);
				}
			} else
				throw new NotImplementedException();
		} catch (ResolveError aResolveError) {
			errSink.reportDiagnostic(aResolveError);
		}
	}

	private @Nullable ProcTableEntry findProcTableEntry(@NotNull BaseEvaFunction aGeneratedFunction, IExpression aExpression) {
		for (@NotNull ProcTableEntry procTableEntry : aGeneratedFunction.prte_list) {
			if (procTableEntry.__debug_expression == aExpression)
				return procTableEntry;
		}
		return null;
	}

	@NotNull
	private Operation2<NamespaceStatement> lookup_module_namespace(@NotNull OS_Module aModule) {
		try {
			final @NotNull IdentExpression module_ident = IdentExpression.forString("__MODULE__");
			@Nullable OS_Element           e            = DeduceLookupUtils.lookup(module_ident, aModule.getContext(), deduceTypes2);
			if (e != null) {
				if (e instanceof NamespaceStatement ns) {
					return Operation2.success(ns);
				} else {
					LOG.err("__MODULE__ should be namespace");
					return Operation2.failure(Diagnostic.withMessage("9328", "__MODULE__ should be namespace", Diagnostic.Severity.ERROR));
				}
			} else {
				// not found, so add. this is where AST would come in handy
				// TODO 08/13 this looks wrong
				@NotNull NamespaceStatement ns = deduceTypes2._inj().new_NamespaceStatementImpl(aModule, aModule.getContext());
				ns.setName(module_ident);
				return Operation2.success(ns);
			}
		} catch (ResolveError aResolveError) {
			errSink.reportDiagnostic(aResolveError);
			return Operation2.failure(aResolveError);
		}
	}
}

//
//
//
