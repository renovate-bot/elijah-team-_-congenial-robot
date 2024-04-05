package tripleo.elijah_durable_congenial.stages.deduce.nextgen;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Name;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_durable_congenial.stages.deduce.DT_Function;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DG_ClassStatement;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.ProcIA;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.ProcIA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DR_Ident implements DR_Item {
	public final            List<Understanding>                         u                    = new ArrayList<>();
	private final           List<DT_ResolveObserver>            resolveObserverList  = new LinkedList<>();
	private final @Nullable IdentExpression                     ident;
	private final @Nullable VariableTableEntry                  vteBl1;
	private final @NotNull  List<DoneCallback<DR_PossibleType>> typePossibles        = new ArrayList<>();
	private final @Nullable IdentTableEntry                     _identTableEntry;
	private final           BaseEvaFunction                     baseEvaFunction;
	private final           int                                 mode;
	private final           DeferredObject<DR_PossibleType, Void, Void> typePossibleDeferred = new DeferredObject<>();
	private final           List<DR_PossibleType>                       typeProposals        = new ArrayList<>();
	boolean _b;

	public DR_Ident(final @NotNull IdentTableEntry aIdentTableEntry, final BaseEvaFunction aBaseEvaFunction) {
		ident            = aIdentTableEntry.getIdent();
		vteBl1           = null;
		_identTableEntry = aIdentTableEntry;
		baseEvaFunction  = aBaseEvaFunction;
		mode             = 1;
	}

	public DR_Ident(final IdentExpression aIdent, final VariableTableEntry aVteBl1, final DT_Function aDTFunction) {
		ident                 = aIdent;
		vteBl1                = aVteBl1;
		this._identTableEntry = null;
		baseEvaFunction       = aDTFunction.getGeneratedFunction();
		mode                  = 1;
	}

	public DR_Ident(final IdentExpression aIdent, final VariableTableEntry aVteBl1, final BaseEvaFunction aBaseEvaFunction) {
		ident                 = aIdent;
		vteBl1                = aVteBl1;
		this._identTableEntry = null;
		baseEvaFunction       = aBaseEvaFunction;
		mode                  = 1;
	}

	public DR_Ident(final VariableTableEntry aVteBl1, final BaseEvaFunction aBaseEvaFunction) {
		vteBl1           = aVteBl1;
		baseEvaFunction  = aBaseEvaFunction;
		mode             = 2;
		_identTableEntry = null;
		ident            = null;
	}

	public static @NotNull DR_Ident create(final IdentExpression aIdent, final VariableTableEntry aVteBl1, final BaseEvaFunction aBaseEvaFunction) {
		return new DR_Ident(aIdent, aVteBl1, aBaseEvaFunction);
	}

	public static @NotNull DR_Ident create(@NotNull IdentTableEntry aIdentTableEntry, BaseEvaFunction aGeneratedFunction) {
		return new DR_Ident(aIdentTableEntry, aGeneratedFunction);
	}

	public static @NotNull DR_Ident create(final VariableTableEntry aVariableTableEntry, final BaseEvaFunction aGeneratedFunction) {
		return new DR_Ident(aVariableTableEntry, aGeneratedFunction);
	}

	public void addPossibleType(final DR_PossibleType aPt) {
		for (DoneCallback<DR_PossibleType> typePossible : typePossibles) {
			typePossible.onDone(aPt);
		}
	}

	public void foo() {
	}

	public boolean isResolved() {
		for (Understanding understanding : u) {
			if (understanding instanceof PTEUnderstanding ptu) {
				var ci = ptu.pte.getClassInvocation();
				var fi = ptu.pte.getFunctionInvocation();

				if (false) {
					ci.resolvePromise();
					fi.generatePromise();
				}

				return true;
			}
		}

		if (ident == null && _identTableEntry == null) {
			assert mode == 2;
			return vteBl1.getStatus() == BaseTableEntry.Status.KNOWN;
		}

		if (_identTableEntry == null) {
			return vteBl1.getStatus() == BaseTableEntry.Status.KNOWN;
		}

		return _identTableEntry.isResolved();
	}

	public void onPossibleType(final DoneCallback<DR_PossibleType> cb) {
		//this.typePossibleDeferred.then(cb);
		typePossibles.add(cb);
	}

	public void proposeType(final DR_PossibleType aPt) {
		//if (_b) throw new IllegalStateException("Error"); // FIXME testing only call once

		typeProposals.add(aPt);

		_b = true;
	}

	public void resolve() {
		if (_identTableEntry == null) {
			assert vteBl1 != null;

			assert vteBl1.getStatus() == BaseTableEntry.Status.KNOWN;

			vteBl1.elementPromise((OS_Element x) -> {
				addUnderstanding(new ElementUnderstanding(x));
//				System.err.println("-- [DR_Ident:104] addElementUnderstanding for vte " + x);
			}, null);
			return;
		}

		assert _identTableEntry != null;
		_identTableEntry.elementPromise(this::addElementUnderstanding, null);
		_identTableEntry.onBacklinkSet(ia -> {
			if (ia instanceof ProcIA procIA) {
				var mainLogic = procIA.getEntry();

				if (mainLogic.expression_num instanceof IdentIA mlIdentIA) {
					@NotNull final IdentTableEntry mlident = mlIdentIA.getEntry();

					final DR_Ident ident1 = baseEvaFunction.getIdent(mlident);

					final String name = ident1.name();
					assert name.equals(mlident.getIdent().getText());

					addUnderstanding(new ProcedureCallUnderstanding(mainLogic));
				} else
					addUnderstanding(new BacklinkUnderstanding(ia));
			} else
				addUnderstanding(new BacklinkUnderstanding(ia));
		});
	}

	public void addUnderstanding(final @NotNull Understanding aUnderstanding) {
		final Compilation compilation = this.baseEvaFunction.module().getCompilation();
		if (compilation.reports().outputOn(Finally.Outs.Out_153)) {
			System.err.println("*** 162 Understanding DR_Ident >> " + this.simplified() + " " + aUnderstanding.asString());
		}
		//u.add(aUnderstanding);

		final EN_Name i1 = ident != null ? ident.getName() : null;

		final var osElement = vteBl1 != null ? vteBl1.getResolvedElement() : null;

		//IdentExpression i2 = osElement != null ? switch (DecideElObjectType.getElObjectType(osElement)) {
		//	case FORMAL_ARG_LIST_ITEM -> {
		//		yield ((FormalArgListItem) osElement).getNameToken();
		//	}
		//	default -> {
		//		//throw new IllegalStateException("Unexpected value: " + DecideElObjectType.getElObjectType(osElement));
		//		yield null;
		//	}
		//} : null;
		final EN_Name i2 = osElement != null ? switch (DecideElObjectType.getElObjectType(osElement)) {
			case FORMAL_ARG_LIST_ITEM -> {
				yield ((FormalArgListItem) osElement).getEnName();
			}
			case UNKNOWN -> null;
			default -> {
				throw new IllegalStateException("Unexpected value: " + DecideElObjectType.getElObjectType(osElement));
				//yield null;
			}
		} : null;

		if (i1 != null) {
			i1.addUnderstanding(aUnderstanding.getENU());
		}
		if (i2 != null) {
			i2.addUnderstanding(aUnderstanding.getENU());
		}
	}

	private void addElementUnderstanding(OS_Element x) {
		addUnderstanding(new ElementUnderstanding(x));
		//LOG.info("104 addElementUnderstanding %s %s".formatted(name(), x));
	}

	public String name() {
		if (ident != null)
			return ident.getText();
		if (mode == 2) {
			return vteBl1.getName();
		}
		assert false;
		return "890890809890809";
	}

	public void resolve(final DG_ClassStatement aDcs) {
		addUnderstanding(new ClassUnderstanding(aDcs));
	}

	public void resolve(final @NotNull IElementHolder aEh, final ProcTableEntry aPte) {
		addUnderstanding(new ElementUnderstanding(aEh.getElement()));
		addUnderstanding(new PTEUnderstanding(aPte));
	}

	private @Nullable String simplified() {
		if (ident != null)
			return "ident: %s %s".formatted(ident.getText(), baseEvaFunction);
		if (vteBl1 != null)
			return "vte: %s %s".formatted(vteBl1.getName(), baseEvaFunction);
		return null;
	}

	@Override
	public @NotNull String toString() {
		return "DR_Ident{" +
				"ident=" + ident +
				", vteBl1=" + vteBl1 +
				", baseEvaFunction=" + baseEvaFunction +
				", mode=" + mode +
				", typeProposals=" + typeProposals +
				", typePossibleDeferred=" + typePossibleDeferred +
				", _b=" + _b +
				", typePossibles=" + typePossibles +
				'}';
	}

	public void try_resolve_normal(final @NotNull Context aContext) {
		LookupResultList     lrl1 = aContext.lookup(this._identTableEntry.getIdent().getText());
		@Nullable OS_Element best = lrl1.chooseBest(null);

		for (DT_ResolveObserver resolveObserver : resolveObserverList) {
			resolveObserver.onElement(best);
		}
	}

	public void addResolveObserver(final DT_ResolveObserver aDTResolveObserver) {
		resolveObserverList.add(aDTResolveObserver);
	}

	public @Nullable IdentTableEntry identTableEntry() {
		return _identTableEntry;
	}

	public void addUnderstanding(final EN_Understanding u) {
		assert ident != null;
		ident.getName().addUnderstanding(u);
	}

	public BaseEvaFunction getNode() {
		return baseEvaFunction;
	}

	public interface Understanding {
		String asString();

		EN_Understanding getENU();
	}

	public static class ElementUnderstanding implements Understanding {
		private final OS_Element x;

		public ElementUnderstanding(final OS_Element aX) {
			x = aX;
		}

		@Override
		public @NotNull String asString() {
			String xx = x.toString();

			if (x instanceof VariableStatement vs) {
				xx = vs.getName();
			}

			return "ElementUnderstanding " + xx;
		}

		@Override
		public EN_Understanding getENU() {
			return new EN_Understanding() {};
		}

		public OS_Element getElement() {
			return x;
		}
	}

	class BacklinkUnderstanding implements Understanding {
		private final InstructionArgument ia;

		public BacklinkUnderstanding(final InstructionArgument aIa) {
			ia = aIa;
		}

		@Override
		public String asString() {
			return String.format("BacklinkUnderstanding %s", ia);
		}

		@Override
		public EN_Understanding getENU() {
			return new EN_Understanding() {};
		}

	}

	class ClassUnderstanding implements Understanding {
		private final DG_ClassStatement dcs;

		public ClassUnderstanding(final DG_ClassStatement aDcs) {
			dcs = aDcs;
		}

		@Override
		public @NotNull String asString() {
			return "ClassUnderstanding " + dcs.classInvocation();
		}

		@Override
		public EN_Understanding getENU() {
			return new EN_Understanding() {};
		}
	}

	class PTEUnderstanding implements Understanding {

		private final ProcTableEntry pte;

		public PTEUnderstanding(final ProcTableEntry aPte) {
			pte = aPte;
		}

		@Override
		public String asString() {
			return String.format("PTEUnderstanding " + pte.__debug_expression);
		}

		@Override
		public EN_Understanding getENU() {
			return new EN_Understanding() {};
		}
	}
}
