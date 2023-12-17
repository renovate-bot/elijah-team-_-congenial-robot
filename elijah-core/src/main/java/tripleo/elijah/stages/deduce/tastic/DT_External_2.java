package tripleo.elijah.stages.deduce.tastic;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.lang.nextgen.names.impl.ENU_ResolveToFunction;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.stages.logging.ElLog;

public class DT_External_2 implements DT_External {
	private final IdentTableEntry                       ite;
	private final DeferredObject<OS_Module, Void, Void> mod1Promise = new DeferredObject<>();
	private final OS_Module                             module;
	private       OS_Module                             mod1;
	private final ProcTableEntry                        pte;
	private final FT_FCA_IdentIA.FakeDC4                dc;
	private final ElLog                                 LOG;
	private final OS_Element                            resolved_element;
	private final Context                               ctx;
	private final BaseEvaFunction                       generatedFunction;
	private final int                                   instructionIndex;
	private final IdentIA                               identIA;
	private final VariableTableEntry                    vte;


	private final DeferredObject2<OS_Element, ResolveError, Void> _p_resolvedElementPromise = new DeferredObject2<>();


	public DT_External_2(final IdentTableEntry aIte,
						 final OS_Module aModule,
						 final ProcTableEntry aPte,
						 final FT_FCA_IdentIA.FakeDC4 aDc,
						 final ElLog aLOG,
						 final Context aCtx,
						 final BaseEvaFunction aGeneratedFunction,
						 final int aInstructionIndex,
						 final IdentIA aIdentIA,
						 final VariableTableEntry aVte) {
		ite = aIte;
		vte = aVte;

		// README in essence, why is this set in the constructor? Isn't this what we are supposed to be doing?
		resolved_element = ite.getResolvedElement();

		if (resolved_element != null) {
			_p_resolvedElementPromise.resolve(resolved_element);
			mod1 = resolved_element.getContext().module();
			mod1Promise.resolve(mod1);
		}

		module            = aModule;
		pte               = aPte;
		dc                = aDc;
		LOG               = aLOG;
		identIA           = aIdentIA;
		ctx               = aCtx;
		generatedFunction = aGeneratedFunction;
		instructionIndex  = aInstructionIndex;

		//

		_p_resolvedElementPromise.then(this::onResolvedElement);
	}

	private void onResolvedElement(OS_Element el) {
		if (el instanceof FunctionDef fd) {
			var name = fd.getEnName();

			FunctionDef __test_resolved = null;

			for (EN_Understanding understanding : name.getUnderstandings()) {
				if (understanding instanceof ENU_ResolveToFunction rtf) {
					__test_resolved = rtf.fd();
				}
			}

			final OS_Element parent = el.getParent();

			if (__test_resolved != null) {
				assert __test_resolved == el;
			}

			var target_p2 = pte.dpc.targetP2();

			target_p2.then(target0 -> {

				if (target0 == null) {
					if (false)
						System.err.println("542542  ");
					return;
				}


				IInvocation invocation2 = target0.declAnchor().getInvocation();
				if (invocation2 instanceof ClassInvocation) {
					invocation2 = dc.registerClassInvocation((ClassInvocation) invocation2);
				}

				if (!(invocation2 instanceof FunctionInvocation)) {
					__make2_1__createFunctionInvocation(pte, dc, LOG, (FunctionDef) el, parent, invocation2);
				} else {
					__make2_1__hasFunctionInvocation(pte, (FunctionInvocation) invocation2);
				}
			});
		}
	}

	@Override
	public OS_Module targetModule() { // [T1160118]
		//assert mod1Promise.isResolved(); // !!
		return mod1;
	}

	@Override
	public void actualise(final @NotNull DeduceTypes2 aDt2) {
		//if (mod1 != module) { // README this is kinda by construction
		assert aDt2.module != module;

		final IdentTableEntry ite1 = identIA.getEntry();

		//assert ite._p_resolvedElementPromise.isResolved();

		ite1._p_resolvedElementPromise.then((final @NotNull OS_Element orig_e) -> {
			OS_Element e = orig_e;

//			LOG.info(String.format("600 %s %s", xx ,e));
//			LOG.info("601 "+identIA.getEntry().getStatus());

			final OS_Element pre_resolved_element = ite1.getResolvedElement();

			if (pre_resolved_element == null) {
				dc.found_element_for_ite(generatedFunction, ite1, e, ctx);
			}

			final OS_Element resolved_element1 = ite1.getResolvedElement();

			boolean set_alias = false;

			if (orig_e instanceof AliasStatement) {
				set_alias = true;

				while (e instanceof AliasStatement al) {
					e = dc._resolveAlias(al);
				}
			}

			assert e == resolved_element1
					|| /*HACK*/ resolved_element1 instanceof AliasStatementImpl
					|| resolved_element1 == null
					;

			if (e instanceof OS_NamedElement el2) {
				final EN_Name name = el2.getEnName();
				if (set_alias)
					name.addUnderstanding(_inj().new_ENU_AliasedFrom((AliasStatement) orig_e));
			}

			if (pte.getStatus() != BaseTableEntry.Status.KNOWN) {
				pte.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_ConstructableElementHolder(e, identIA));
			}

			pte.onFunctionInvocation((@NotNull FunctionInvocation functionInvocation) -> {
				functionInvocation.generateDeferred().done((@NotNull BaseEvaFunction bgf) -> {
					@NotNull DeduceTypes2.PromiseExpectation<GenType> pe = dc.promiseExpectation(bgf, "Function Result type");
					bgf.typePromise().then((@NotNull GenType result) -> {
						pe.satisfy(result);
						@NotNull TypeTableEntry tte = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, result.getResolved()); // TODO there has to be a better way
						tte.genType.copy(result);
						vte.addPotentialType(instructionIndex, tte);
					});
				});
			});
		});

		ite1._p_resolvedElementPromise.fail(aResolveError -> {
			// TODO create Diagnostic and quit
			LOG.info("1005 Can't find element for " + aResolveError);
		});
	}

	private /*static*/ void __make2_1__hasFunctionInvocation(final @NotNull ProcTableEntry pte, final @NotNull FunctionInvocation fi) {
		fi.generateDeferred().then((BaseEvaFunction ef) -> {
			var result = _inj().new_GenTypeImpl();
			result.setFunctionInvocation(fi);
			result.setNode(ef);

			assert fi.pte != pte;

			if (fi.pte == null) {
				final Compilation c = dc._deduceTypes2().module.getCompilation();
				if (c.reports().outputOn(Finally.Outs.Out_252)) {
					System.err.println("******************************* Unexpected error");
				}
				return;
			}

			if (fi.pte.typeResolvePromise().isResolved()) {
				fi.pte.typeResolvePromise().then(gt -> {
					result.setResolved(gt.getResolved());
					gt.copy(result);
				});
			}

			LOG.info("2717c " + ef.getFD() + " " + result);

			if (pte.typeDeferred().isPending()) {
				pte.typeDeferred().resolve(result);
			}
		});
	}

	@Override
	public @NotNull Promise<OS_Module, Void, Void> onTargetModulePromise() {
		return mod1Promise;
	}

	/* extra large wtf */
	@Override
	public void tryResolve() {
		final InstructionArgument bl = ite.getBacklink();

		if (bl instanceof ProcIA procIA) {
			final ProcTableEntry bl_pte = procIA.getEntry();

			if (bl_pte.expression.getEntry() instanceof IdentTableEntry ite1) {
				ite1._p_resolvedElementPromise.then(el -> {
					final Context ctx2 = el.getContext();

					final LookupResultList lrl = ctx2.lookup(ite.getIdent().getText());
					final OS_Element       e   = lrl.chooseBest(null);

					if (e != null) {
						_p_resolvedElementPromise.resolve(e);
					}
				});
			}
		}
	}

	private /*static*/ void __make2_1__createFunctionInvocation(final ProcTableEntry pte__,
																final FT_FCA_IdentIA.FakeDC4 dc__,
																final ElLog LOG__,
																final FunctionDef resolved_element,
																final OS_Element parent,
																final IInvocation invocation2) {
		final @NotNull FunctionInvocation fi;
		fi = dc.newFunctionInvocation(resolved_element, pte, invocation2);

		final DeferredMemberFunction dmf = dc.deferred_member_function(parent, invocation2, resolved_element, fi);

		dmf.typeResolved().then((final @NotNull GenType result) -> {
			LOG.info("2717 " + dmf.getFunctionDef() + " " + result);
			if (pte.typeDeferred().isPending())
				pte.typeDeferred().resolve(result);
			else {
				int y = 2;
			}
		});
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return dc._inj();
	}

	public void onResolve(DoneCallback<OS_Element> cb) {
		_p_resolvedElementPromise.then(cb); // TODO ?? shaky
	}
}
