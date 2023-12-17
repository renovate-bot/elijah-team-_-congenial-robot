/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce.post_bytecode;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Diagnostic.Severity;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.post_bytecode.DED.DED_VTE;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.stateful.StateRegistrationToken;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;

public class DeduceElement3_VariableTableEntry extends DefaultStateful implements IDeduceElement3 {

	private final VariableTableEntry principal;
	private final State              st;
	private       DeduceTypes2       deduceTypes2;
	private       BaseEvaFunction    generatedFunction;
	private       GenType            genType;

	public DeduceElement3_VariableTableEntry(final VariableTableEntry aVariableTableEntry, final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		this(aVariableTableEntry);
		setDeduceTypes2(aDeduceTypes2, aGeneratedFunction);
	}

	public void setDeduceTypes2(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		deduceTypes2      = aDeduceTypes2;
		generatedFunction = aGeneratedFunction;
	}

	public void __action_VAR_pot_1_tableEntry_null(final @NotNull VariableStatement aVariableStatement) {
		final @NotNull IExpression iv = aVariableStatement.initialValue();
		if (iv == IExpression.UNASSIGNED) {
			NotImplementedException.raise();
		}

		final @Nullable GenType gt = __getGenTypeFromInitialValue(iv);
		assert gt != null;
	}

	public @Nullable GenType __getGenTypeFromInitialValue(final @NotNull IExpression iv) {
/*
		final @NotNull IExpression iv = aVariableStatement.initialValue();
		if (iv == IExpression.UNASSIGNED) {
			NotImplementedException.raise();
		}
*/

		switch (iv.getKind()) {
		case PROCEDURE_CALL -> {
			final ProcedureCallExpression procedureCallExpression = (ProcedureCallExpression) iv;
			final IExpression             name_exp                = procedureCallExpression.getLeft();
			assert name_exp instanceof IdentExpression;

			final IdentExpression      name2 = (IdentExpression) name_exp;
			final LookupResultList     lrl2  = name2.getContext().lookup(name2.getText());
			final @Nullable OS_Element el2   = lrl2.chooseBest(null);

			if (el2 == null) {
				return null;
			}

			switch (DecideElObjectType.getElObjectType(el2)) {
			case CLASS -> {
				final ClassStatement classStatement = (ClassStatement) el2;

				var dt2 = principal._deduceTypes2();
				assert dt2 != null;

				final GenType genType1 = dt2._inj().new_GenTypeImpl(classStatement);

				//deferredMember.typeResolved().resolve(genType1);
				genType1.genCIForGenType2(deduceTypes2);

				// TODO and what? 05/24

				this.genType = genType1;

				return genType1;
			}
			default -> {
				NotImplementedException.raise();
			}
			}
		}
		case IDENT -> {
			final IdentExpression identExpression = (IdentExpression) iv;
			final String          ident           = identExpression.getText();
			int                   y               = 2;
		}
		default -> {
			NotImplementedException.raise();
		}
		}

		return null;
	}

	public void __action_vp1o(final @NotNull VariableTableEntry vte,
							  final @NotNull TypeTableEntry aPot,
							  final @NotNull ProcTableEntry pte1,
							  final @NotNull OS_Element e) {
		assert vte == principal;

		if (e instanceof FunctionDef) {
			__action_vp1o__FunctionDef(vte, pte1, (FunctionDef) e);
		} else {
			OS_Element         ee = e;
			final OS_Element[] es = {null};
			final boolean[]    b  = {false};

			if (e instanceof AliasStatementImpl) {
				final DG_AliasStatement                    dg = deduceTypes2().DG_AliasStatement((AliasStatementImpl) e, deduceTypes2);
				final Promise<DG_Item, ResolveError, Void> p  = dg.resolvePromise();

				if (p.isResolved()) {
					p.then(xx -> {
						if (xx instanceof DG_ClassStatement) {
							es[0] = ((DG_ClassStatement) xx).evaClass().getKlass();
						} else if (xx instanceof DG_FunctionDef) {
							__action_vp1o(vte, aPot, pte1, ((DG_FunctionDef) xx).getFunctionDef());
							b[0] = true;
						}
					});
				}

				if (es[0] != null)
					ee = es[0];
			}

			if (!b[0]) {
				assert ee != null;
				if (ee instanceof AliasStatement ali) {
					ee = DeduceLookupUtils._resolveAlias(ali, deduceTypes2);
				}
				if (ee instanceof ClassStatement) {
					final DG_ClassStatement dg = deduceTypes2().DG_ClassStatement((ClassStatement) ee);

					vte.setStatus(BaseTableEntry.Status.KNOWN, dg.GenericElementHolder());
					pte1.setStatus(BaseTableEntry.Status.KNOWN, dg.ConstructableElementHolder(e, vte));
//			vte.setCallablePTE(pte1);

					GenType gt = aPot.genType;
					setup_GenType(e, gt);
//			if (gt.node == null)
//				gt.node = vte.genType.node;

					vte.getGenType().copy(gt);
				}
			}
		}
	}

	private void __action_vp1o__FunctionDef(final @NotNull VariableTableEntry vte,
											final @NotNull ProcTableEntry pte1,
											final @NotNull FunctionDef e) {
		NotImplementedException.raise();

		if (pte1.expression_num instanceof IdentIA) {
			@NotNull IdentTableEntry ite1 = ((IdentIA) pte1.expression_num).getEntry();
			__action_vp1o__FunctionDef__ITE(pte1, e, ite1);
		} else if (pte1.expression_num instanceof IntegerIA) {
			@NotNull final VariableTableEntry vte1 = ((IntegerIA) pte1.expression_num).getEntry();
			__action_vp1o__FunctionDef__VTE(vte, pte1, e, vte1);
		}
	}

	private void __action_vp1o__FunctionDef__VTE(final @NotNull VariableTableEntry vte, final @NotNull ProcTableEntry pte1, final @NotNull FunctionDef e, final @NotNull VariableTableEntry vte1) {
		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		//DeducePath               dp   = vte1.buildDeducePath(generatedFunction);

		final DeduceElement3_VariableTableEntry de3_vte = vte1.getDeduceElement3();
		final GenType                           t       = de3_vte.genType;

		//assert t != null;

		vte.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
		pte1.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));

		pte1.typePromise().then(genType1 -> {
			if (t != null)
				t.copy(genType1);
		});
	}

	private void __action_vp1o__FunctionDef__ITE(final @NotNull ProcTableEntry pte1, final @NotNull FunctionDef e, final @NotNull IdentTableEntry ite1) {
		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		DeducePath        dp = ite1.buildDeducePath(generatedFunction);
		@Nullable GenType t  = dp.getType(dp.size() - 1);
		ite1.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
		pte1.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(e));
		pte1.typePromise().then(new DoneCallback<GenType>() {
			@Override
			public void onDone(@NotNull GenType result) {
				if (t == null) {
					ite1.makeType(generatedFunction, TypeTableEntry.Type.TRANSIENT, result.getResolved());
					ite1.setGenType(result);
				} else {
					//						assert false; // we don't expect this, but note there is no problem if it happens
					t.copy(result);
				}
			}
		});
	}

	DeduceElement3_VariableTableEntry(final OS_Type vte_type_attached) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Contract(pure = true)
	public DeduceElement3_VariableTableEntry(final VariableTableEntry aVariableTableEntry) {
		principal = aVariableTableEntry;
		st        = ST.INITIAL;
	}

	private void __itemFali__isFunctionDef(final @NotNull DeduceTypes2 aDeduceTypes2, final @NotNull FunctionDef fd) {
		@Nullable ProcTableEntry pte        = null;
		final IInvocation        invocation = aDeduceTypes2.getInvocation((EvaFunction) generatedFunction);
		aDeduceTypes2.forFunction(aDeduceTypes2.newFunctionInvocation(fd, pte, invocation, aDeduceTypes2.phase), new ForFunction() {
			@Override
			public void typeDecided(final @NotNull GenType aType) {
				assert fd == generatedFunction.getFD();
				//
				@NotNull TypeTableEntry tte1 = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, aDeduceTypes2.gt(aType), principal); // TODO expression?
				principal.setType(tte1);
			}
		});
	}

	public void _action_002_no_resolved_element(final @NotNull ErrSink errSink, final @NotNull ProcTableEntry pte, final @NotNull IdentTableEntry ite, final DeduceTypes2.@NotNull DeduceClient3 dc, final @NotNull DeducePhase phase) {
		final DeferredObject<Context, Void, Void> d = new DeferredObject<Context, Void, Void>();
		d.then(context -> {
			try {
//				final ContextImpl context = resolvedElement.getContext();
				final LookupResultList     lrl2 = dc.lookupExpression(ite.getIdent(), context);
				@Nullable final OS_Element best = lrl2.chooseBest(null);
				assert best != null;
				ite.setStatus(BaseTableEntry.Status.KNOWN, dc._deduceTypes2()._inj().new_GenericElementHolder(best));
				action_002_1(pte, ite, false, dc, phase);
			} catch (final ResolveError aResolveError) {
				errSink.reportDiagnostic(aResolveError);
				assert false;
			}
		});

		final VariableTableEntry backlink = principal;

		final OS_Element resolvedElement = backlink.getResolvedElement();
		assert resolvedElement != null;

		if (resolvedElement instanceof IdentExpression) {
			backlink.typePromise().then(result -> {
				final Context context = result.getResolved().getClassOf().getContext();
				d.resolve(context);
			});
		} else {
			final Context context = resolvedElement.getContext();
			d.resolve(context);
		}

	}

	private void action_002_1(@NotNull final ProcTableEntry pte, @NotNull final IdentTableEntry ite, final boolean setClassInvocation, final DeduceTypes2.@NotNull DeduceClient3 dc, final @NotNull DeducePhase phase) {
		final OS_Element resolvedElement = ite.getResolvedElement();

		assert resolvedElement != null;

		action_002_1_001(pte, setClassInvocation, dc, phase, resolvedElement);
	}

	private void action_002_1_001(final @NotNull ProcTableEntry pte,
								  final boolean setClassInvocation,
								  final DeduceTypes2.@NotNull DeduceClient3 dc,
								  final @NotNull DeducePhase phase,
								  final @NotNull OS_Element resolvedElement) {
		if (pte.getFunctionInvocation() != null) return;

		final Pair<ClassInvocation, FunctionInvocation> p = action_002_1_002_1(pte, dc, phase, resolvedElement);
		if (p == null)
			throw new IllegalStateException();
		final ClassInvocation    ci = p.getLeft();
		final FunctionInvocation fi = p.getRight();

		if (setClassInvocation) {
			if (ci != null) {
				pte.setClassInvocation(ci);
			} else
				SimplePrintLoggerToRemoveSoon.println_err2("542 Null ClassInvocation");
		}

		pte.setFunctionInvocation(fi);
	}

	private @Nullable Pair<ClassInvocation, FunctionInvocation> action_002_1_002_1(final @NotNull ProcTableEntry pte, final DeduceTypes2.@NotNull DeduceClient3 dc, final @NotNull DeducePhase phase, final @NotNull OS_Element resolvedElement) {
		final Pair<ClassInvocation, FunctionInvocation> p;
		final FunctionInvocation                        fi;
		ClassInvocation                                 ci;

		var dt2 = principal._deduceTypes2();
		assert dt2 != null;

		if (resolvedElement instanceof ClassStatement) {
			// assuming no constructor name or generic parameters based on function syntax
			ci = dt2._inj().new_ClassInvocation((ClassStatement) resolvedElement, null, new ReadySupplier_1<>(dt2));
			ci = phase.registerClassInvocation(ci);
			fi = phase.newFunctionInvocation(null, pte, ci);
			p  = new ImmutablePair<ClassInvocation, FunctionInvocation>(ci, fi);
		} else if (resolvedElement instanceof final @NotNull FunctionDef functionDef) {
			final IInvocation invocation = dc.getInvocation((EvaFunction) generatedFunction);
			fi = phase.newFunctionInvocation(functionDef, pte, invocation);
			if (functionDef.getParent() instanceof ClassStatement) {
				final ClassStatement classStatement = (ClassStatement) fi.getFunction().getParent();
				ci = dt2._inj().new_ClassInvocation(classStatement, null, new ReadySupplier_1<>(dt2)); // TODO generics
				ci = phase.registerClassInvocation(ci);
			} else {
				ci = null;
			}
			p = new ImmutablePair<ClassInvocation, FunctionInvocation>(ci, fi);
		} else {
			p = null;
		}

		return p;
	}

	@Override
	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public @NotNull DED elementDiscriminator() {
		return new DED_VTE(principal);
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		return generatedFunction;
	}

	@Override
	public GenType genType() {
		return genType;
	}

	@Override
	public @NotNull DeduceElement3_Kind kind() {
		return DeduceElement3_Kind.GEN_FN__VTE;
	}

	@Override
	public OS_Element getPrincipal() {
		return principal.getResolvedElement();
	}

	public @NotNull Operation2<OS_Type> decl_test_001(final BaseEvaFunction gf) {
		//var dt2 = principal.getDeduceElement3().deduceTypes2();
		//assert dt2 != null;

		final VariableTableEntry vte = principal;

		final OS_Type x = vte.getType().getAttached();
		if (x == null && vte.potentialTypes().isEmpty()) {
			final Diagnostic diag;
			if (vte.getVtt() == VariableTableType.TEMP) {
				diag = /*dt2._inj().new_*/new Diagnostic_8884(vte, gf);
			} else {
				diag = /*dt2._inj().new_*/new Diagnostic_8885(vte);
			}
			return Operation2.failure(diag);
		}

		if (x == null) {
			return Operation2.failure(GCFM_Diagnostic.forThis("113/133 x is null", "133", Severity.INFO));
		}

		return Operation2.success(x);
	}

	public void doLogic(@NotNull final List<TypeTableEntry> potentialTypes, final @NotNull Promise<GenType, Void, Void> p, final @NotNull ElLog LOG, final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink, final @NotNull Context ctx, final @NotNull String e_text, final @NotNull VariableTableEntry vte) {
		switch (potentialTypes.size()) {
		case 1 -> __doLogic_pot_size_1(potentialTypes, p, LOG, vte1, errSink);
		case 0 -> __doLogic_pot_size_0(p, LOG, vte1, errSink, ctx, e_text, vte);
		default -> __doLogic_pot_size_default(potentialTypes, p, LOG, vte1, errSink, ctx, e_text, vte);
		}
	}

	private void __doLogic_pot_size_default(final @NotNull List<TypeTableEntry> potentialTypes, final @NotNull Promise<GenType, Void, Void> p, final @NotNull ElLog LOG, final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink, final @NotNull Context ctx, final @NotNull String e_text, final @NotNull VariableTableEntry vte) {
		// TODO hopefully this works
		var potentialTypes1 = potentialTypes.stream()
				.filter(input -> input.getAttached() != null)
				.collect(Collectors.toList());

		// prevent infinite recursion
		if (potentialTypes1.size() < potentialTypes.size())
			doLogic(potentialTypes1, p, LOG, vte1, errSink, ctx, e_text, vte);
		else
			LOG.info("913 Don't know");
	}

	private void __doLogic_pot_size_0(final @NotNull Promise<GenType, Void, Void> p, final @NotNull ElLog LOG, final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink, final @NotNull Context ctx, final @NotNull String e_text, final @NotNull VariableTableEntry vte) {
		// README moved up here to elimiate work
		if (p.isResolved()) {
			System.out.printf("890-1 Already resolved type: vte1.type = %s, gf = %s %n", vte1.getType(), generatedFunction);
			return;
		}
		final LookupResultList     lrl  = ctx.lookup(e_text);
		@Nullable final OS_Element best = lrl.chooseBest(null);
		if (best instanceof @NotNull final FormalArgListItem fali) {
			__doLogic_pot_size_0__fali(vte1, errSink, vte, fali);
		} else if (best instanceof final @NotNull VariableStatementImpl vs) {
			__doLogic_pot_size_0__varstmt(p, vte1, e_text, vte, vs, best);
		} else {
			final int y = 2;
			LOG.err("543 " + best.getClass().getName());
			throw new NotImplementedException();
		}
	}

	private void __doLogic_pot_size_0__varstmt(final @NotNull Promise<GenType, Void, Void> p, final @NotNull VariableTableEntry vte1, final @NotNull String e_text, final @NotNull VariableTableEntry vte, final @NotNull VariableStatementImpl vs, final @NotNull OS_Element best) {
		//
		assert vs.getName().equals(e_text);
		//
		@Nullable final InstructionArgument vte2_ia = generatedFunction.vte_lookup(vs.getName());
		@NotNull final VariableTableEntry   vte2    = generatedFunction.getVarTableEntry(to_int(vte2_ia));
		if (p.isResolved())
			System.out.printf("915 Already resolved type: vte2.type = %s, gf = %s %n", vte1.getType(), generatedFunction);
		else {
			final GenType gt       = vte1.getGenType();
			final OS_Type attached = vte2.getType().getAttached();
			gt.setResolved(attached);
			vte1.resolveType(gt);
		}
//								vte.type = vte2.type;
//								tte.attached = vte.type.attached;
		vte.setStatus(BaseTableEntry.Status.KNOWN, deduceTypes2._inj().new_GenericElementHolder(best));
		vte2.setStatus(BaseTableEntry.Status.KNOWN, deduceTypes2._inj().new_GenericElementHolder(best)); // TODO ??
	}

	private void __doLogic_pot_size_0__fali(final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink, final @NotNull VariableTableEntry vte, final @NotNull FormalArgListItem fali) {
		final @NotNull OS_Type osType = deduceTypes2._inj().new_OS_UserType(fali.typeName());
		if (!osType.equals(vte.getType().getAttached())) {
			final TypeTableEntry tte1 = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, osType, fali.getNameToken(), vte1);
			/*if (p.isResolved())
				System.out.printf("890 Already resolved type: vte1.type = %s, gf = %s, tte1 = %s %n", vte1.type, generatedFunction, tte1);
			else*/
			{
				final OS_Type attached = tte1.getAttached();
				switch (attached.getType()) {
				case USER -> vte1.getType().setAttached(attached); // !!
				case USER_CLASS -> {
					final GenType gt = vte1.getGenType();
					gt.setResolved(attached);
					vte1.resolveType(gt);
				}
				default -> errSink.reportWarning("2853 Unexpected value: " + attached.getType());
				}
			}
		}
	}

	private void __doLogic_pot_size_1(final @NotNull List<TypeTableEntry> potentialTypes, final @NotNull Promise<GenType, Void, Void> p, final @NotNull ElLog LOG, final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink) {
		//							tte.attached = ll.get(0).attached;
//							vte.addPotentialType(instructionIndex, ll.get(0));
		if (p.isResolved()) {
			LOG.info(String.format("1047 (vte already resolved) %s vte1.type = %s, gf = %s, tte1 = %s %n", vte1.getName(), vte1.getType(), generatedFunction, potentialTypes.get(0)));
		} else {
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
//										throw new IllegalStateException("Unexpected value: " + attached.getType());
			}
		}
	}

	@Override
	public void resolve(final Context aContext, final DeduceTypes2 aDeduceTypes2) {
		throw new UnsupportedOperationException("Should not be reached");
	}

	@Override
	public void resolve(final IdentIA aIdentIA, final Context aContext, final FoundElement aFoundElement) {
		throw new UnsupportedOperationException("Should not be reached");
	}

	public void getItemFali(final Context aCtx, final @NotNull DeduceTypes2 aDeduceTypes2, final @NotNull GenType genType) {

		assert generatedFunction != null;

		final ErrSink errSink = aDeduceTypes2._errSink();


		final @Nullable OS_Type ty2 = genType.getTypeName()/*.getAttached()*/;
		assert ty2 != null;

		@NotNull GenType rtype = null;
		try {
			rtype = aDeduceTypes2.resolve_type(ty2, aCtx);
		} catch (ResolveError resolveError) {
			errSink.reportError("Cant resolve " + ty2); // TODO print better diagnostic
			return;
		}
		if (rtype.getResolved() != null && rtype.getResolved().getType() == OS_Type.Type.USER_CLASS) {
			LookupResultList     lrl2  = rtype.getResolved().getClassOf().getContext().lookup("__getitem__");
			@Nullable OS_Element best2 = lrl2.chooseBest(null);
			if (best2 != null) {
				if (best2 instanceof @Nullable final FunctionDef fd) {
					__itemFali__isFunctionDef(aDeduceTypes2, fd);
				} else {
					throw new NotImplementedException();
				}
			} else {
				throw new NotImplementedException();
			}
		}

	}

	public void potentialTypesRunnableDo(final @Nullable InstructionArgument vte_ia, final @NotNull ElLog aLOG, final @NotNull VariableTableEntry aVte1, final @NotNull ErrSink errSink, final @NotNull Context ctx, final @NotNull String aE_text, final @NotNull VariableTableEntry aVte) {
		final @NotNull List<TypeTableEntry> ll = getPotentialTypesVte((EvaFunction) generatedFunction, vte_ia);
		doLogic(ll, aVte1.typePromise(), aLOG, aVte1, errSink, ctx, aE_text, aVte);
	}

	@NotNull
	private static ArrayList<TypeTableEntry> getPotentialTypesVte(@NotNull final EvaFunction generatedFunction, @NotNull final InstructionArgument vte_index) {
		return getPotentialTypesVte(generatedFunction.getVarTableEntry(to_int(vte_index)));
	}

	@NotNull
	static ArrayList<TypeTableEntry> getPotentialTypesVte(@NotNull final VariableTableEntry vte) {
		return new ArrayList<TypeTableEntry>(vte.potentialTypes());
	}

	private void setup_GenType(@NotNull OS_Element element, @NotNull GenType aGt) {
		final List<setup_GenType_Action> list  = new ArrayList<>();
		final setup_GenType_Action_Arena arena = new setup_GenType_Action_Arena();

		final DeducePhase phase = deduceTypes2._phase();

		switch (DecideElObjectType.getElObjectType(element)) {
		case NAMESPACE -> {
			final @NotNull NamespaceStatement namespaceStatement = (NamespaceStatement) element;

			list.add(deduceTypes2._inj().new_SGTA_SetResolvedNamespace(namespaceStatement));
			list.add(deduceTypes2._inj().new_SGTA_RegisterNamespaceInvocation(namespaceStatement, phase));

//			pte.setNamespaceInvocation(nsi);

			list.add(deduceTypes2._inj().new_SGTA_SetNamespaceInvocation());

//			fi = newFunctionInvocation(fd, pte, nsi, phase);

			for (setup_GenType_Action action : list) {
				action.run(aGt, arena);
			}
		}
		case CLASS -> {
			final @NotNull ClassStatement classStatement = (ClassStatement) element;

			list.add(deduceTypes2._inj().new_SGTA_SetResolvedClass(classStatement));
			list.add(deduceTypes2._inj().new_SGTA_RegisterClassInvocation(classStatement, phase));
			list.add(deduceTypes2._inj().new_SGTA_SetClassInvocation());

			for (setup_GenType_Action action : list) {
				action.run(aGt, arena);
			}
		}
		case FUNCTION -> {
			// TODO this seems to be an elaborate copy of the above with no differentiation for functionDef
			final @NotNull FunctionDef functionDef = (FunctionDef) element;
			OS_Element                 parent      = functionDef.getParent();
			@Nullable IInvocation      inv;
			switch (DecideElObjectType.getElObjectType(parent)) {
			case CLASS:
				aGt.setResolved(((ClassStatement) parent).getOS_Type());
				inv = phase.registerClassInvocation((ClassStatement) parent, null, new ReadySupplier_1<>(deduceTypes2));
				((ClassInvocation) inv).resolveDeferred().then((final @NotNull EvaClass result) -> {
					result.functionMapDeferred(functionDef, aGt::setNode);
				});
				break;
			case NAMESPACE:
				aGt.setResolvedn((NamespaceStatement) parent);
				inv = phase.registerNamespaceInvocation((NamespaceStatement) parent);
				((NamespaceInvocation) inv).resolveDeferred().then((final @NotNull EvaNamespace result) -> {
					result.functionMapDeferred(functionDef, aGt::setNode);
				});
				break;
			default:
				throw new NotImplementedException();
			}
			aGt.setCi(inv);
		}
		case ALIAS -> {
			@Nullable OS_Element el = element;
			while (el instanceof AliasStatementImpl) {
				el = DeduceLookupUtils._resolveAlias((AliasStatementImpl) el, deduceTypes2);
			}
			setup_GenType(el, aGt);
		}
		default -> throw new IllegalStateException("Unknown parent");
		}
	}

	public void __dof_uc(final OS_Type aType) {

		var phase = deduceTypes2._phase();

		assert aType.getType() == OS_Type.Type.USER_CLASS;

		final ClassStatement classStatement = aType.getClassOf();
		final List<TypeName> genericPart    = classStatement.getGenericPart();
		if (genericPart.isEmpty()) {
			@Nullable ClassInvocation ci = new ClassInvocation(classStatement, null, ()->deduceTypes2);
			ci = phase.registerClassInvocation(ci);

			principal.getGenType().setResolved(aType); // README assuming OS_Type cannot represent namespaces
			principal.getGenType().setCi(ci);

			ci.resolvePromise().then(principal::resolveTypeToClass);
		} else {
			// TODO 11/06
			@Nullable ClassInvocation ci = new ClassInvocation(classStatement, null, ()->deduceTypes2);
			ci = phase.registerClassInvocation(ci);

			principal.getGenType().setResolved(aType); // README assuming OS_Type cannot represent namespaces
			principal.getGenType().setCi(ci);

			ci.resolvePromise().then(principal::resolveTypeToClass);
		}
	}

	public void __post_vte_list_001() {
		principal.typeResolvePromise().then((GenType gt) -> {
			principal.resolvedTypePromise().then((EvaNode resolvedNode) -> {
				if (resolvedNode instanceof EvaClass evaClass) {
					if (gt.getCi() == null) {
						gt.setCi(evaClass.ci);
					}

					gt.setResolved(evaClass.getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					principal.getType().setAttached(gt);
				} else if (resolvedNode instanceof EvaConstructor evaConstructor) {
					if (gt.getCi() == null) {
//						gt.ci = evaConstructor.ci;
					}

					gt.setResolved(evaConstructor.fi.getClassInvocation().getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					principal.getType().setAttached(gt);
				}
			});
		});
	}

	public enum ST {
		;

		public static State EXIT_CONVERT_USER_TYPES;
		public static State EXIT_RESOLVE;
		static        State INITIAL;

		public static void register(final @NotNull DeducePhase aDeducePhase) {
			EXIT_RESOLVE            = aDeducePhase.registerState(new ExitResolveState());
			INITIAL                 = aDeducePhase.registerState(new InitialState());
			EXIT_CONVERT_USER_TYPES = aDeducePhase.registerState(new ExitConvertUserTypes());
		}

		static class ExitConvertUserTypes implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final @NotNull DefaultStateful element) {
				final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry) element).principal;

				final DeduceTypes2         dt2     = ((DeduceElement3_VariableTableEntry) element).deduceTypes2();
				final ErrSink              errSink = dt2._errSink();
				final @NotNull DeducePhase phase   = dt2._phase();
				final @NotNull ElLog       LOG     = dt2._LOG();

				if (vte.getType() == null) return; // TODO only for tests

				final @Nullable OS_Type attached = vte.getType().getAttached();

				if (attached == null) return;
				if (Objects.requireNonNull(attached.getType()) == OS_Type.Type.USER) {
					final TypeName x = attached.getTypeName();
					if (x instanceof NormalTypeName) {
						final String tn = ((NormalTypeName) x).getName();
						apply_normal(vte, dt2, errSink, phase, LOG, attached, x, tn);
					}
				}
			}

			private static void apply_normal(final @NotNull VariableTableEntry vte,
											 final @NotNull DeduceTypes2 dt2,
											 final @NotNull ErrSink errSink,
											 final DeducePhase phase,
											 final @NotNull ElLog LOG,
											 final @NotNull OS_Type attached,
											 final @NotNull TypeName x,
											 final @NotNull String tn) {
				final LookupResultList lrl  = x.getContext().lookup(tn);
				@Nullable OS_Element   best = lrl.chooseBest(null);

				while (best instanceof AliasStatementImpl) {
					best = DeduceLookupUtils._resolveAlias((AliasStatementImpl) best, dt2);
				}

				if (best != null) {
					if (!(OS_Type.isConcreteType(best))) {
						errSink.reportError(String.format("Not a concrete type %s for (%s)", best, tn));
					} else {
						LOG.info("705 " + best);
						// NOTE that when we set USER_CLASS from USER generic information is
						// still contained in constructable_pte
						@NotNull final GenType genType = dt2._inj().new_GenTypeImpl(attached, ((ClassStatement) best).getOS_Type(), true, x, dt2, errSink, phase);
						vte.setLikelyType(genType);
					}
					//vte.el = best;
					// NOTE we called resolve_var_table_entry above
					LOG.info("200 " + best);
					assert vte.getResolvedElement() == null || vte.getStatus() == BaseTableEntry.Status.KNOWN;
					//									vte.setStatus(BaseTableEntry.Status.KNOWN, best/*vte.el*/);
				} else {
					errSink.reportDiagnostic(dt2._inj().new_ResolveError(x, lrl));
				}
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

		static class ExitResolveState implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final @NotNull DefaultStateful element) {
				final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry) element).principal;
				vte.resolve_var_table_entry_for_exit_function();
			}

			@Override
			public boolean checkState(final @NotNull DefaultStateful aElement3) {
				return ((DeduceElement3_VariableTableEntry) aElement3).st == ST.INITIAL;
			}

			@Override
			public void setIdentity(final StateRegistrationToken aId) {
				identity = aId;
			}
		}

		static class InitialState implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final DefaultStateful element) {

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
	}

	public static class Diagnostic_8884 implements GCFM_Diagnostic {
		private final int                _code = 8884;
		private final BaseEvaFunction    gf;
		private final VariableTableEntry vte;

		public Diagnostic_8884(final VariableTableEntry aVte, final BaseEvaFunction aGf) {
			vte = aVte;
			gf  = aGf;
		}

		@Override
		public @NotNull String code() {
			return "" + _code;
		}

		@Override
		public @NotNull Locatable primary() {
			return null;
		}

		@Override
		public void report(final @NotNull PrintStream stream) {
			stream.printf(_message());
		}

		@Override
		public String _message() {
			return String.format("%d temp variable has no type %s %s", _code, vte, gf);
		}

		@Override
		public @NotNull List<Locatable> secondary() {
			return null;
		}

		@Override
		public @NotNull Severity severity() {
			return Severity.ERROR;
		}
	}

	public static class Diagnostic_8885 implements GCFM_Diagnostic {
		private final int                _code = 8885;
		private final VariableTableEntry vte;

		public Diagnostic_8885(final VariableTableEntry aVte) {
			vte = aVte;
		}

		@Override
		public @NotNull String code() {
			return "" + _code;
		}

		@Override
		public @NotNull Locatable primary() {
			return null;
		}

		@Override
		public void report(final @NotNull PrintStream stream) {
			stream.printf(_message());
		}

		@Override
		public String _message() {
			return String.format("%d x is null (No typename specified) for %s%n", _code, vte.getName());
		}

		@Override
		public @NotNull List<Locatable> secondary() {
			return null;
		}

		@Override
		public @NotNull Severity severity() {
			return Severity.ERROR;
		}
	}

}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
