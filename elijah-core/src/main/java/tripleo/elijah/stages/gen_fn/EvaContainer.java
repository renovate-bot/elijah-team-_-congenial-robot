/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.stages.gen_fn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_VarTableEntry;
import tripleo.elijah.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

/**
 * Created 2/28/21 3:23 AM
 */
public interface EvaContainer extends EvaNode {
	OS_Element getElement();

	@NotNull Maybe<VarTableEntry> getVariable(String aVarName);

	class VarTableEntry {
		public final           IExpression                                        initialValue;
		public final @NotNull  IdentExpression                                    nameToken;
		public final           VariableStatement                                  vs;
		private final          DeferredObject<OS_Type, Void, Void>                _p_resolve_varType               = new DeferredObject<>();
		private final @NotNull OS_Element                                         parent;
		private final          RegisterClassInvocation_env                        passthruEnv;
		public @NotNull        List<ConnectionPair>                               connectionPairs                  = new ArrayList<>();
		public @NotNull        List<TypeTableEntry>                               potentialTypes                   = new ArrayList<TypeTableEntry>();
		public                 TypeName                                           typeName;
		public @NotNull        DeferredObject<UpdatePotentialTypesCB, Void, Void> _p_updatePotentialTypesCBPromise = new DeferredObject<>();
		public                 OS_Type                                            varType;
		UpdatePotentialTypesCB updatePotentialTypesCB;
		private EvaNode _resolvedType;

		DeduceElement3_VarTableEntry _de3;

		public VarTableEntry(final VariableStatement aVs,
							 final IdentExpression aNameToken,
							 final IExpression aInitialValue,
							 final TypeName aTypeName,
							 final OS_Element aParent,
							 final RegisterClassInvocation_env aPassthruEnv) {
			vs           = aVs;
			nameToken    = aNameToken;
			initialValue = aInitialValue;
			typeName     = aTypeName;
			varType      = new OS_UserType(typeName);
			parent       = aParent;

			passthruEnv = aPassthruEnv;

			{
				final DeduceTypes2 deduceTypes2 = passthruEnv.getDeduceTypes2(); // NPE. yay kotlin!
				if (deduceTypes2 != null) {
					_de3 = new DeduceElement3_VarTableEntry(this, deduceTypes2);
				} else {
					_de3 = new DeduceElement3_VarTableEntry(this);
				}

				_de3.__passthru = passthruEnv;
			}
		}

		public void connect(final VariableTableEntry aVte, final EvaConstructor aConstructor) {
			connectionPairs.add(new ConnectionPair(aVte, aConstructor));
		}

		public @NotNull DeduceElement3_VarTableEntry getDeduceElement3() {
			return _de3;
		}

		public VarTableEntry(final VariableStatement aVs,
							 final @NotNull IdentExpression aNameToken,
							 final IExpression aInitialValue,
							 final @NotNull TypeName aTypeName,
							 final @NotNull OS_Element aElement) {
			vs           = aVs;
			nameToken    = aNameToken;
			initialValue = aInitialValue;
			typeName     = aTypeName;
			varType      = new OS_UserType(typeName);
			parent       = aElement;

			passthruEnv = null;
			_de3 = new DeduceElement3_VarTableEntry(this);
		}

		public void addPotentialTypes(@NotNull Collection<TypeTableEntry> aPotentialTypes) {
			potentialTypes.addAll(aPotentialTypes);
		}

		public @NotNull OS_Element getParent() {
			return parent;
		}

		public boolean isResolved() {
			return this._p_resolve_varType.isResolved();
		}

		public @Nullable EvaNode resolvedType() {
			return _resolvedType;
		}

		public interface UpdatePotentialTypesCB {
			@NotNull Operation<Ok> call(final @NotNull EvaContainer aEvaContainer);
		}

		public void resolve(@NotNull EvaNode aResolvedType) {
			SimplePrintLoggerToRemoveSoon.println_out_2(String.format("** [GeneratedContainer 56] resolving VarTableEntry %s to %s", nameToken, aResolvedType.identityString()));
			_resolvedType = aResolvedType;
		}

		public void resolve_varType(final OS_Type aOS_type) {
			_p_resolve_varType.resolve(aOS_type);
			varType = aOS_type;
		}

		public void resolve_varType_cb(final DoneCallback<OS_Type> aCallback) {
			_p_resolve_varType.then(aCallback);
		}

		public static class ConnectionPair {
			public final VariableTableEntry vte;
			final        EvaConstructor     constructor;

			@Contract(pure = true)
			public ConnectionPair(final VariableTableEntry aVte, final EvaConstructor aConstructor) {
				vte         = aVte;
				constructor = aConstructor;
			}
		}

		public void updatePotentialTypes(final @NotNull EvaContainer aEvaContainer) {
//			assert aGeneratedContainer == GeneratedContainer.this;
			_p_updatePotentialTypesCBPromise.then(new DoneCallback<UpdatePotentialTypesCB>() {
				@Override
				public void onDone(final @NotNull UpdatePotentialTypesCB result) {
					Operation<Ok> s;

					s = result.call(aEvaContainer);

					assert s.mode() == Mode.SUCCESS;
				}
			});
		}

		public VariableStatement vs() {
			return vs;
		}
	}
}

//
//
//
