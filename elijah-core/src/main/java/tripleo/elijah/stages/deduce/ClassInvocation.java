/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.lang.types.OS_UserClassType;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created 3/5/21 3:51 AM
 */
public class ClassInvocation implements IInvocation {
	private final @NotNull ClassStatement                       cls;
	private final @NotNull Supplier<DeduceTypes2>               _dt2s;
	private final          String                               constructorName;
	private final          DeferredObject<EvaClass, Void, Void> resolvePromise = new DeferredObject<EvaClass, Void, Void>();
	private                CI_GenericPart                       genericPart_;
	public                 CI_Hint                              hint;

	public CI_GenericPart genericPart() {
		if (_dt2s instanceof NULL_DeduceTypes2) {
			return null;
		}

		if (genericPart_ == null) {
			genericPart_ = _inj().new_CI_GenericPart(cls.getGenericPart(), this);
		}
		return genericPart_;
	}

	public ClassInvocation(@NotNull ClassStatement aClassStatement, String aConstructorName, final @NotNull Supplier<DeduceTypes2> aDeduceTypes2) {
		this._dt2s = aDeduceTypes2;

		cls = aClassStatement;

		constructorName = aConstructorName;
	}

	public String getConstructorName() {
		return constructorName;
	}

	public @NotNull ClassStatement getKlass() {
		return cls;
	}

	public @NotNull Promise<EvaClass, Void, Void> resolvePromise() {
		return resolvePromise.promise();
	}

	public @NotNull DeferredObject<EvaClass, Void, Void> resolveDeferred() {
		return resolvePromise;
	}

	public @NotNull String finalizedGenericPrintable() {
		final String        name = getKlass().getName();
		final StringBuilder sb   = new StringBuilder();

		sb.append(name);
		sb.append('[');

		final CI_GenericPart ciGenericPart = genericPart();
		if (ciGenericPart != null) {
			for (Map.Entry<TypeName, OS_Type> entry : ciGenericPart.entrySet()) {
				var y = entry.getValue();

				if (y instanceof OS_UnknownType unk) {
				} else {
					if (y instanceof OS_UserClassType uct) {
						assert uct.getClassOf() != null;
						sb.append("%s,".formatted(uct.getClassOf().getName()));
					}
				}
			}
		}

		sb.append(']');

		return sb.toString();
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return _dt2s.get()._inj();
	}

	public class CI_GenericPart {
		private final @NotNull Map<TypeName, OS_Type> genericPart;
		private final          boolean                isEmpty;

		public CI_GenericPart(final @NotNull Collection<TypeName> genericPart1) {
			if (!genericPart1.isEmpty()) {
				genericPart = new HashMap<>(genericPart1.size());
				for (TypeName typeName : genericPart1) {
					genericPart.put(typeName, _inj().new_OS_UnknownType(null)); // FIXME 08/27 remove usage of UnknownType
				}
				this.isEmpty = false;
			} else {
				genericPart  = Collections.emptyMap();
				this.isEmpty = true;
			}
		}

		public OS_Type get(final TypeName aTypeName) {
			return genericPart.get(aTypeName);
		}

		public @Nullable Map<TypeName, OS_Type> getMap() {
			return genericPart;
		}

		public boolean hasGenericPart() {
			return this.isEmpty;
		}

		public void put(final TypeName aTypeName, final OS_Type aType) {
			assert genericPart != null;
			genericPart.put(aTypeName, aType);
		}

		public void record(final TypeName aKey, final EvaContainer.@NotNull VarTableEntry aVarTableEntry) {
			assert genericPart != null;
			genericPart.put(aKey, aVarTableEntry.varType);
		}

		public @Nullable OS_Type valueForKey(final TypeName tn) {
			OS_Type realType = null;
			for (final Map.Entry<TypeName, OS_Type> entry : this.entrySet()) {
				if (entry.getKey().equals(tn)) {
					realType = entry.getValue();
					break;
				}
			}
			return realType;
		}

		public @NotNull Iterable<? extends Map.Entry<TypeName, OS_Type>> entrySet() {
			//if (isEmpty) {
			//	return new HashMap<TypeName, OS_Type>().entrySet();
			//}
			return genericPart.entrySet();
		}
	}

	public void set(int aIndex, TypeName aTypeName, @NotNull OS_Type aType) {
		assert aType.getType() == OS_Type.Type.USER_CLASS;
		genericPart().put(aTypeName, aType);
	}

	@Override
	public void setForFunctionInvocation(@NotNull FunctionInvocation aFunctionInvocation) {
		aFunctionInvocation.setClassInvocation(this);
	}
}

//
//
//
