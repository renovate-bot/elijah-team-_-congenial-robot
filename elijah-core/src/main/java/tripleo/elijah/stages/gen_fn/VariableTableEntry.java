/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeduceLocalVariable;
import tripleo.elijah.stages.deduce.DeduceTypeResolve;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_VariableTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.PostBC_Processor;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 9/10/20 4:51 PM
 */
public class VariableTableEntry extends BaseTableEntry1 implements Constructable, TableEntryIV, DeduceTypes2.ExpectationBase {
	final @Nullable   VariableStatement                          _vs;
	private final     DeferredObject2<GenType, Void, Void>       typeDeferred             = new DeferredObject2<GenType, Void, Void>();
	private final     VariableTableType                          vtt;
	private final     int                                        index;
	private final     String                                     name;
	private final     DeferredObject<ProcTableEntry, Void, Void> _p_constructableDeferred = new DeferredObject<>();
	private final     GenType                                    genType                  = new GenTypeImpl();
	private final Eventual<EvaNode> _p_resolvedTypePromise = new Eventual<>();
	public            int                                        tempNum                  = -1;
	private           TypeTableEntry                             type;
	private @Nullable GenType                                    _resolveTypeCalled       = null;
	private @NotNull  Map<Integer, TypeTableEntry>               potentialTypes           = new HashMap<Integer, TypeTableEntry>();
	private           DeduceLocalVariable                        dlv                      = new DeduceLocalVariable(this);
	private           ProcTableEntry                             constructable_pte;
	private           DeduceElement3_VariableTableEntry          _de3;
	private           EvaNode                                    _resolvedType;

	public VariableTableEntry(final int aIndex, final VariableTableType aVtt, final String aName, final TypeTableEntry aTTE, final OS_Element el) {
		this.index = aIndex;
		this.name  = aName;
		this.vtt   = aVtt;
		this.type  = aTTE;
		if (el instanceof VariableStatement vs) {
			assert vtt == VariableTableType.VAR;

			this._vs = vs;
			this.setResolvedElement(vs.getNameToken());
		} else {
			switch (vtt) {
			case ARG -> {
				int y = 2;
			}
			case SELF, RESULT, TEMP -> {
			}
			}
			this.setResolvedElement(el);
			this._vs = null;
		}
		setupResolve();

		this.type.genType = new ForwardingGenType(this.type.genType, true);

		_p_resolvedTypePromise.then(aNode -> {
			getGenType().setNode(aNode);
			type.resolve(aNode); // TODO maybe this obviates above
		});
	}

	@Override
	protected void setupResolve() {
		super.setupResolve();

		addStatusListener(new StatusListener() {
			@Override
			public void onChange(final IElementHolder eh, final Status newStatus) {
				if (newStatus != Status.KNOWN) return;

				__gf.getIdent(VariableTableEntry.this).resolve();
			}
		});

	}

	public @NotNull GenType getGenType() {
		return genType;
	}

	@Override
	public void setGenType(@NotNull GenType aGenType) {
		genType.copy(aGenType);
		resolveType(aGenType);
	}

//	public DeferredObject<GenType, Void, Void> typeDeferred() {
//		return typeDeferred;
//	}

	public void addPotentialType(final int instructionIndex, final @NotNull TypeTableEntry tte) {
		if (!typeDeferred.isPending()) {
			SimplePrintLoggerToRemoveSoon.println_err_2("62 addPotentialType while typeDeferred is already resolved " + this);//throw new AssertionError();
			return;
		}
		//
		if (!getPotentialTypes().containsKey(instructionIndex))
			getPotentialTypes().put(instructionIndex, tte);
		else {
			TypeTableEntry v = getPotentialTypes().get(instructionIndex);
			if (v.getAttached() == null) {
				v.setAttached(tte.getAttached());
				type.genType.copy(tte.genType); // README don't lose information
			} else if (tte.lifetime == TypeTableEntry.Type.TRANSIENT && v.lifetime == TypeTableEntry.Type.SPECIFIED) {
				//v.attached = v.attached; // leave it as is
			} else if (tte.lifetime == v.lifetime && v.getAttached() == tte.getAttached()) {
				// leave as is
			} else if (v.getAttached().equals(tte.getAttached())) {
				// leave as is
			} else {
				assert false;
				//
				// Make sure you check the differences between USER and USER_CLASS types
				// May not be any
				//
//				tripleo.elijah.util.Stupidity.println_err_2("v.attached: " + v.attached);
//				tripleo.elijah.util.Stupidity.println_err_2("tte.attached: " + tte.attached);
				SimplePrintLoggerToRemoveSoon.println_out_2("72 WARNING two types at the same location.");
				if ((tte.getAttached() != null && tte.getAttached().getType() != OS_Type.Type.USER) || v.getAttached().getType() != OS_Type.Type.USER_CLASS) {
					// TODO prefer USER_CLASS as we are assuming it is a resolved version of the other one
					if (tte.getAttached() == null)
						tte.setAttached(v.getAttached());
					else if (tte.getAttached().getType() == OS_Type.Type.USER_CLASS)
						v.setAttached(tte.getAttached());
				}
			}
		}
	}

	public @NotNull Map<Integer, TypeTableEntry> getPotentialTypes() {
		return potentialTypes;
	}

	public void setPotentialTypes(@NotNull Map<Integer, TypeTableEntry> potentialTypes) {
		this.potentialTypes = potentialTypes;
	}

	@Override
	public Promise<ProcTableEntry, Void, Void> constructablePromise() {
		return _p_constructableDeferred.promise();
	}

	@Override
	public void resolveTypeToClass(final @NotNull EvaNode aNode) {
		if (!_p_resolvedTypePromise.isResolved()) {
			_p_resolvedTypePromise.resolve(aNode);
		} else {
			NotImplementedException.raise_stop();
		}

		getGenType().setNode(aNode);
		type.resolve(aNode); // TODO maybe this obviates above

		if (getGenType() instanceof ForwardingGenType)
			if (getGenType() instanceof ForwardingGenType) {
				((ForwardingGenType) getGenType()).unsparkled();
			}
	}

	@Override
	public void setConstructable(ProcTableEntry aPte) {
		if (getConstructable_pte() != aPte) {
			setConstructable_pte(aPte);
			_p_constructableDeferred.resolve(getConstructable_pte());
		}
	}

	// region constructable

	public ProcTableEntry getConstructable_pte() {
		return constructable_pte;
	}

	public void setConstructable_pte(ProcTableEntry constructable_pte) {
		this.constructable_pte = constructable_pte;
	}

	@Override
	public @NotNull String expectationString() {
		return "VariableTableEntry{" +
				"index=" + index +
				", name='" + name + '\'' +
				"}";
	}

	// endregion constructable

	public @NotNull Collection<TypeTableEntry> potentialTypes() {
		return getPotentialTypes().values();
	}

	public int getIndex() {
		return index;
	}

	public EvaNode resolvedType() {
		return _resolvedType;
	}

	public String getName() {
		return name;
	}

	public @NotNull DeduceElement3_VariableTableEntry getDeduceElement3() {
		if (_de3 == null) {
			_de3 = new DeduceElement3_VariableTableEntry(this);
			//_de3.generatedFunction = generatedFunction;
			//_de3.deduceTypes2 = deduceTypes2;
		}
		return _de3;
	}

	public void setDeduceTypes2(final DeduceTypes2 aDeduceTypes2, final Context aContext, final BaseEvaFunction aGeneratedFunction) {
		getDlv().setDeduceTypes2(aDeduceTypes2, aContext, aGeneratedFunction);
	}

	public DeduceLocalVariable getDlv() {
		return dlv;
	}

	public void setDlv(DeduceLocalVariable dlv) {
		this.dlv = dlv;
	}

	public void resolve_var_table_entry_for_exit_function() {
		getDlv().resolve_var_table_entry_for_exit_function();
	}

	public void setLikelyType(final GenType aGenType) {
		final GenType bGenType = type.genType;

		// 1. copy arg into member
		bGenType.copy(aGenType);

		((ForwardingGenType) bGenType).unsparkled();

		// 2. set node when available
		((ClassInvocation) bGenType.getCi()).resolvePromise().done(aGeneratedClass -> {
			bGenType.setNode(aGeneratedClass);
			resolveTypeToClass(aGeneratedClass);
			setGenType(bGenType); // TODO who knows if this is necessary?
		});

		((ForwardingGenType) bGenType).unsparkled();
	}

	public void resolveType(final @NotNull GenType aGenType) {
		try {
			if (_resolveTypeCalled != null) { // TODO what a hack
				if (_resolveTypeCalled.getResolved() != null) {
					if (!aGenType.equals(_resolveTypeCalled)) {
						SimplePrintLoggerToRemoveSoon.println_err_2(String.format("** 130 Attempting to replace %s with %s in %s", _resolveTypeCalled.asString(), aGenType.asString(), this));
						//					throw new AssertionError();
					}
				} else {
					_resolveTypeCalled = aGenType;
					typeDeferred.reset();
					typeDeferred.resolve(aGenType);
				}
				return;
			}
			if (typeDeferred.isResolved()) {
				SimplePrintLoggerToRemoveSoon.println_err_2("126 typeDeferred is resolved " + this);
			}
			_resolveTypeCalled = aGenType;
			typeDeferred.resolve(aGenType);

			var x = typeResolve;

//			var vte_ident = __gf.getIdent(this);
		} finally {
			getGenType().copy(aGenType);
			// FIXME do we want to setStatus to KNOWN even without knowing here what the element may be??
		}
	}

	@Override
	public @NotNull String toString() {
		return "VariableTableEntry{" +
				"index=" + index +
				", name='" + name + '\'' +
				", status=" + status +
				", type=" + type.index +
				", vtt=" + getVtt() +
				", potentialTypes=" + getPotentialTypes() +
				'}';
	}

	public VariableTableType getVtt() {
		return vtt;
	}

	public boolean typeDeferred_isPending() {
		return typeDeferred.isPending();
	}

	public boolean typeDeferred_isResolved() {
		return typeDeferred.isResolved();
	}

	public Promise<GenType, Void, Void> typePromise() {
		return typeDeferred.promise();
	}

	public DeduceTypeResolve typeResolve() {
		return typeResolve;
	}

	public @NotNull PostBC_Processor getPostBC_Processor(Context aFd_ctx, DeduceTypes2.DeduceClient1 aDeduceClient1) {
		return PostBC_Processor.make_VTE(this, aFd_ctx, aDeduceClient1);
	}

	public TypeTableEntry getType() {
		return type;
	}

	public void setType(@NotNull TypeTableEntry tte1) {
		type = tte1;

	}

	GenType get_resolveTypeCalled() {
		return _resolveTypeCalled;
	}

	void set_resolveTypeCalled(GenType _resolveTypeCalled) {
		this._resolveTypeCalled = _resolveTypeCalled;
	}

	public int getTempNum() {
		return tempNum;
	}

	public void setTempNum(int num) {
		tempNum = num;
	}

	public boolean _resolvedType__notNull() {
		return _resolvedType != null;
	}

	public DeduceElement3_VariableTableEntry getDeduceElement3(final DeduceTypes2 aDt2, final BaseEvaFunction aGf1) {
		if (_de3 == null) {
			_de3 = new DeduceElement3_VariableTableEntry(this);
			_de3.setDeduceTypes2(aDt2, aGf1);
		}
		return _de3;
	}

	public boolean isResolvedYet() {
		return !_p_resolvedTypePromise.isPending();
	}

	public Eventual<EvaNode> resolvedTypePromise() {
		return _p_resolvedTypePromise;
	}
}

//
//
//
