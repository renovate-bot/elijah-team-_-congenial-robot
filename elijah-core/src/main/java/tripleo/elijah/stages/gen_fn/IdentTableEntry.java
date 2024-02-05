/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.DefaultReactive;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.Resolve_Ident_IA.DeduceElementIdent;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gdm.GDM_IdentExpression;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_congenial.progress.X;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created 9/12/20 10:27 PM
 */
@SuppressWarnings("TypeMayBeWeakened")
public class IdentTableEntry extends BaseTableEntry1 implements Constructable, TableEntryIV, DeduceTypes2.ExpectationBase, IDeduceResolvable {
	protected final        DeferredObject<InstructionArgument, Void, Void> _p_backlinkSet             = new DeferredObject<InstructionArgument, Void, Void>();
	protected final        DeferredObject<ProcTableEntry, Void, Void>      _p_constructableDeferred   = new DeferredObject<>();
	private final          DeferredObject<GenType, Void, Void>             _p_fefiDone                = new DeferredObject<GenType, Void, Void>();
	private final          DeferredObject<IdentTableEntry, Void, Void>     _p_resolveListenersPromise = new DeferredObject<>();
	private final          Eventual<EvaNode>                               _p_resolvedType            = new Eventual<>();
	private final          Eventual<OS_Element>                            _p_resolvedElementPromise  = new Eventual<>();
	private final          Eventual<_Reactive_IDTE>                        _reactiveEventual          = new Eventual<>();
	private final          DeduceElementIdent                              dei                        = new DeduceElementIdent(this);
	private final @NotNull EvaExpression<IdentExpression>                  ident;
	private final          int                                             index;
	private final          Context                                         pc;
	private final          BaseEvaFunction                                 _definedFunction;
	private final          List<ITE_Resolver>                              resolvers                  = new ArrayList<>();
	public                 EvaNode                                         externalRef;
	public                 ProcTableEntry                                  constructable_pte;
	public @NotNull        Map<Integer, TypeTableEntry>                    potentialTypes             = new HashMap<Integer, TypeTableEntry>();
	public                 boolean                                         fefi                       = false;
	public                 boolean                                         preUpdateStatusListenerAdded;
	public                 DeduceTypes2.PromiseExpectation<String>         resolveExpectation;
	public                 TypeTableEntry                                  type;
	public                 VariableStatement                               _cheat_variableStatement; // DTR_VariableStatement 07/30
	InstructionArgument backlink;
	boolean             insideGetResolvedElement = false;
	private _BoilerTypeFactory typeFactory = new _BoilerTypeFactory();
	private EvaNode            resolvedType;
	private DR_Ident                       _ident;
	private DeduceElement3_IdentTableEntry _de3;
	private _Reactive_IDTE                 _reactive;
	private ITE_Resolver_Result            _resolver_result;

	public IdentTableEntry(final int index, final IdentExpression ident, Context pc, final BaseEvaFunction aBaseEvaFunction) {
		this.index = index;
		this.ident = new EvaExpression<>(ident, this);
		this.pc    = pc;

		this._definedFunction = aBaseEvaFunction;

		addStatusListener((eh, newStatus) -> {
			if (newStatus == Status.KNOWN) {
				if (eh != null) {
					setResolvedElement(eh.getElement(), new GG_ResolveEvent() {
						String id = "IdentTableEntry::ctor";
					});
				}
			}
		});
		setupResolve();

		_p_resolvedElementPromise.then(this::resolveLanguageLevelConstruct);
		getTypeResolve().typeResolution().then(gt -> {
			if (type != null && type.genType != null) // !! 07/30
				type.genType.copy(gt);
		});

		aBaseEvaFunction.onInformGF(gf -> {
			_reactiveEventual.then((_Reactive_IDTE rct) -> {
				rct.join(gf);
			});
			reactive().addResolveListener((IdentTableEntry x) -> {
				int y = 2;
			});
			var im = gf.monitor(ident);
			im.resolveIdentTableEntry(this);

			//__gf = gf; //!!
		});

		reactive().add(new Reactivable() {
			@Override
			public void respondTo(final ReactiveDimension aDimension) {
				if (aDimension instanceof GenerateFunctions gf) {
					final GDM_IdentExpression mie = gf.monitor(ident);
					mie.resolveIdentTableEntry(IdentTableEntry.this);
				}
			}
		});
	}

	private void resolveLanguageLevelConstruct(OS_Element element) {
		//assert __gf != null;
		//assert this._deduceTypes2() != null;
		//
		if (element instanceof FunctionDef fd) {
			NotImplementedException.raise_stop();
			if (get_p_elementPromise().isResolved()) {
				get_p_elementPromise().then(e -> {
					assert e == fd;
				});
			} else {
				get_p_elementPromise().resolve(fd);
			}
		}

		get_p_elementPromise().then(x -> {
			NotImplementedException.raise_stop();
			assert x == element;
		});
	}

	public _Reactive_IDTE reactive() {
		if (_reactive == null) {
			_reactive = new _Reactive_IDTE();
		}
		if (_de3 != null) {
			var ce = _de3.deduceTypes2()._ce();
			ce.reactiveJoin(_reactive);
		}

		if (!_reactiveEventual.isResolved()) {
			// TODO !!
			_reactiveEventual.resolve(_reactive);
		}

		return _reactive;
	}

	public Eventual<_Reactive_IDTE> getReactiveEventual() {
		return _reactiveEventual;
	}

	public void addPotentialType(final int instructionIndex, final TypeTableEntry tte) {
		potentialTypes.put(instructionIndex, tte);
	}

	public void calculateResolvedElement() {
		var resolved_element = dei.getResolvedElement();
		if (resolved_element != null) {
			if (!_p_resolvedElementPromise.isResolved()) {
				_p_resolvedElementPromise.resolve(resolved_element);
			}
		} else {
			//08/13 System.err.println("283283 resolution failure for " + dei.getIdentTableEntry().getIdent().getText());
		}
	}

	public @NotNull DeducePath buildDeducePath(BaseEvaFunction generatedFunction) {
		@NotNull List<InstructionArgument> x = BaseEvaFunction._getIdentIAPathList(new IdentIA(index, generatedFunction));
		return new DeducePath(this, x);
	}

	@Override
	public Promise<ProcTableEntry, Void, Void> constructablePromise() {
		return _p_constructableDeferred.promise();
	}

	@Override
	public void resolveTypeToClass(EvaNode gn) {
		// only do once
		if (_p_resolvedType.isResolved()) {
			X.xl_324("ITE::resolveTypeToClass already resolved.");
			return;
		}

		// do it now
		this._p_resolvedType.resolve(gn);

		// do complicated stuff
		getTypeFactory().get().resolve(gn);

		// do more stuff that might not still be necc.
		// FIXME 06/16 (24/01/28 ??); prob dbl trigger
		if (!_p_resolveListenersPromise.isResolved()) {
			_p_resolveListenersPromise.resolve(this);
		}
	}

	@Override
	public void setConstructable(ProcTableEntry aPte) {
		constructable_pte = aPte;
		if (!_p_constructableDeferred.isResolved()) {
			_p_constructableDeferred.resolve(constructable_pte);
		} else {
			_p_constructableDeferred.then(el -> SimplePrintLoggerToRemoveSoon.println_err_2(String.format("Setting constructable_pte twice 1) %s and 2) %s", el, aPte)));
		}
	}

	@Override
	public void setGenType(GenType aGenType) {
		if (type != null)
			getTypeFactory().set(type);
		getTypeFactory().get().genType.copy(aGenType);
	}

	@Override
	public @NotNull String expectationString() {
		return "IdentTableEntry{" +
				"index=" + index +
				", ident=" + ident.get() +
				", backlink=" + backlink +
				"}";
	}

	public void fefiDone(final GenType aGenType) {
		if (_p_fefiDone.isPending())
			_p_fefiDone.resolve(aGenType);
	}

	/**
	 * Either an {@link IntegerIA} which is a vte
	 * or a {@link IdentIA} which is an idte
	 */
	public InstructionArgument getBacklink() {
		return backlink;
	}

	public void setBacklink(InstructionArgument aBacklink) {
		backlink = aBacklink;
		_p_backlinkSet.resolve(backlink);
	}

	public DeduceElement3_IdentTableEntry getDeduceElement3() {
		return getDeduceElement3(this._deduceTypes2(), get__gf());
	}

	public @NotNull DeduceElement3_IdentTableEntry getDeduceElement3(DeduceTypes2 aDeduceTypes2, BaseEvaFunction aGeneratedFunction) {
		if (_de3 == null) {
			_de3                   = new DeduceElement3_IdentTableEntry(this);
			_de3.deduceTypes2      = aDeduceTypes2;
			_de3.generatedFunction = aGeneratedFunction;
		}
		return _de3;
	}

	public @NotNull DeduceElementIdent getDeduceElement() {
		return dei;
	}

	public void onResolvedElement(final DoneCallback<OS_Element> cb) {
		_p_resolvedElementPromise.then((cb));
	}

	public int getIndex() {
		return index;
	}

	public Context getPC() {
		return pc;
	}

	@Override
	public @Nullable OS_Element getResolvedElement() {
		OS_Element resolved_element;

		if (get_p_elementPromise().isResolved()) {
			final OS_Element[] r = new OS_Element[1];
			get_p_elementPromise().then(x -> r[0] = x);
			return r[0];
		}

		if (insideGetResolvedElement)
			return null;
		insideGetResolvedElement = true;
		resolved_element         = dei.getResolvedElement();
		if (resolved_element != null) {
			if (get_p_elementPromise().isResolved()) {
				NotImplementedException.raise();
			} else {
				get_p_elementPromise().resolve(resolved_element);
			}
		}
		insideGetResolvedElement = false;
		return resolved_element;
	}

	public boolean hasResolvedElement() {
		return !get_p_elementPromise().isPending();
	}

	public boolean isResolved() {
		return _p_resolvedType.isResolved();
	}

	public void makeType(final @NotNull BaseEvaFunction aGeneratedFunction, final TypeTableEntry.@NotNull Type aType, final IExpression aExpression) {
		type = aGeneratedFunction.newTypeTableEntry(aType, null, aExpression, this);
	}

	public void makeType(final @NotNull BaseEvaFunction aGeneratedFunction, final TypeTableEntry.@NotNull Type aType, final OS_Type aOS_Type) {
		type = aGeneratedFunction.newTypeTableEntry(aType, aOS_Type, getIdent(), this);
	}

	public IdentExpression getIdent() {
		return ident.get();
	}

	public void onFefiDone(DoneCallback<GenType> aCallback) {
		_p_fefiDone.then(aCallback);
	}

	public void onType(@NotNull DeducePhase phase, OnType callback) {
		phase.onType(this, callback);
	}

	public EvaNode resolvedType() {
		return resolvedType;
	}

	public void failingResolvedType(final FailCallback<Diagnostic> cb) {
		_p_resolvedType.onFail(cb);
	}

	public void onResolvedType(DoneCallback<EvaNode> cb) {
		_p_resolvedType.then(cb);
	}

	public @NotNull Collection<TypeTableEntry> potentialTypes() {
		return potentialTypes.values();
	}

	public void setDeduceTypes2(final @NotNull DeduceTypes2 aDeduceTypes2, final Context aContext, final @NotNull BaseEvaFunction aGeneratedFunction) {
		dei.setDeduceTypes2(aDeduceTypes2, aContext, aGeneratedFunction);
	}

	public DR_Ident get_ident() {
		return _ident;
	}

	public void set_ident(DR_Ident a_ident) {
		_ident = a_ident;
	}

	public DR_Ident getDefinedIdent() {
		return _definedFunction.getIdent(this);
	}

	public void resolvers_round() {
		if (_resolver_result != null) return;

		for (ITE_Resolver resolver : resolvers) {
			if (!resolver.isDone()) {
				resolver.check();
			}

			if (resolver.isDone()) {
				_resolver_result = resolver.getResult(); // TODO resolve here?? 07/20
				break;
			}

			// ... ??
		}
	}

	public @NotNull BaseEvaFunction _generatedFunction() {
		return get__gf();
	}

	public void onBacklinkSet(final DoneCallback<? super InstructionArgument> cb) {
		backlinkSet().then(cb);
	}

	public Promise<InstructionArgument, Void, Void> backlinkSet() {
		return _p_backlinkSet.promise();
	}

	@Override
	public @NotNull String toString() {
		return "IdentTableEntry{" +
				"index=" + index +
				", ident=" + ident.get() +
				", backlink=" + backlink +
				", status=" + getStatus() +
				", resolved=" + resolvedType +
				", potentialTypes=" + potentialTypes +
				", type=" + type +
				'}';
	}

	public void addResolver(ITE_Resolver aResolver) {
		resolvers.add(aResolver);
	}

	public EvaExpression<IdentExpression> evaExpression() {
		return ident;
	}

	public _BoilerTypeFactory getTypeFactory() {
		return typeFactory;
	}

	public void setTypeFactory(_BoilerTypeFactory aTypeFactory) {
		typeFactory = aTypeFactory;
	}

	public record ITE_Resolver_Result(OS_Element element) {
	}

	public class _BoilerTypeFactory {
		private TypeTableEntry result;

		public TypeTableEntry get() {
			if (this.result == null) {
				//assert false;
				X.xl_324("ITE::_BoilerTypeFactory get == null");
				if (type != null) {
				} else {
					assert false;
				}
			}

			return this.result;
		}

		public void set(TypeTableEntry aTypeTableEntry) {
			this.result = aTypeTableEntry;
		}
	}

	public class _Reactive_IDTE extends DefaultReactive {
		@Override
		public <IdentTableEntry> void addListener(final Consumer<IdentTableEntry> t) {
			throw new IllegalStateException("Error");
		}

		public <IdentTableEntry> void addResolveListener(final @NotNull Consumer<IdentTableEntry> t) {
			_p_resolveListenersPromise.then((DoneCallback<? super tripleo.elijah.stages.gen_fn.IdentTableEntry>) result -> t.accept((IdentTableEntry) result));
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
