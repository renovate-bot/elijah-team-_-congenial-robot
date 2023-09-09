/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.lang.impl.VariableSequenceImpl;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.HashMap;
import java.util.Map;

import static tripleo.elijah.contexts.ClassInfo.ClassInfoType.GENERIC;
import static tripleo.elijah.contexts.ClassInfo.ClassInfoType.INHERITED;


/**
 * @author Tripleo
 * <p>
 * Created 	Mar 26, 2020 at 6:04:02 AM
 */
public class ClassContext extends ContextImpl implements Context {

	private final Context _parent;

	private         boolean                       _didInheritance;
	private final   ClassStatement                carrier;
	public @NotNull Map<TypeName, ClassStatement> _inheritance = new HashMap<>();

	public ClassStatement getCarrier() {
		return carrier;
	}

	public ClassContext(final Context aParent, final ClassStatement cls) {
		_parent = aParent;
		carrier = cls;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	public Map<TypeName, ClassStatement> inheritance() {
		if (!_didInheritance) {
			for (final TypeName tn1 : carrier.classInheritance().tns()) {
//				tripleo.elijah.util.Stupidity.println_out_2("1001 "+tn);
				final NormalTypeName   tn  = (NormalTypeName) tn1;
				final OS_Element       best;
				final LookupResultList tnl = tn.getContext().lookup(tn.getName());
//	    		tripleo.elijah.util.Stupidity.println_out_2("1002 "+tnl.results());
				best = tnl.chooseBest(null);

				if (best != null) {
					_inheritance.put(tn1, (ClassStatement) best);
				}

//				tripleo.elijah.util.Stupidity.println_out_2("1003 "+name+" "+Result.results());
				_didInheritance = true;
			}
		}
		return _inheritance;
	}

	@Override
	public LookupResultList lookup(final @NotNull String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());
		for (final ClassItem item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) &&
					!(item instanceof NamespaceStatement) &&
					!(item instanceof BaseFunctionDef) &&
					!(item instanceof VariableSequenceImpl) &&
					!(item instanceof AliasStatement) &&
					!(item instanceof PropertyStatement)
			) continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().sameName(name)) {
					Result.add(name, level, item, this);
				}
			}
			if (item instanceof VariableSequenceImpl) {
//				tripleo.elijah.util.Stupidity.println_out_2("102 "+item);
				for (final VariableStatement vs : ((VariableSequenceImpl) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
				}
			}
		}

		for (Map.Entry<TypeName, ClassStatement> entry : inheritance().entrySet()) {
			final ClassStatement   best  = entry.getValue();
			final LookupResultList lrl2  = best.getContext().lookup(name);
			final OS_Element       best2 = lrl2.chooseBest(null);

			if (best2 != null)
				Result.add(name, level, best2, this, new ClassInfo(best, INHERITED));
		}

		for (TypeName tn1 : carrier.getGenericPart()) {
			if (tn1 instanceof final @NotNull NormalTypeName tn) {
				final String name1 = tn.getName(); // TODO this may return a string with DOTs in it.
				if (name1.equals(name)) {
//					LookupResultList lrl = tn.getContext().lookup(name);
//					OS_Element best = lrl.chooseBest(null);
//					if (best == null) {
//						throw new AssertionError();
//					} else
					Result.add(name, level, new OS_TypeNameElement(tn1), this, new ClassInfo(tn, GENERIC));
				}
			} else {
				// TODO probable error
			}
		}
		if (getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one)
				return context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;
	}

	/**
	 * An Element that only holds a {@link TypeName}.
	 * <p>
	 * NOTE: It seems to be connected to {@link ClassContext}
	 */
	public class OS_TypeNameElement implements OS_Element {
		private final TypeName typeName;

		public OS_TypeNameElement(TypeName aTypeName) {
			typeName = aTypeName;
		}

		@Override
		public @NotNull Context getContext() {
			return ClassContext.this;
		}

		@Override
		public OS_Element getParent() {
			return carrier;
		}

		@Override
		public void visitGen(@NotNull ElElementVisitor visit) {
			visit.visitTypeNameElement(this);
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnsupportedOperationException();
		}

		public TypeName getTypeName() {
			return typeName;
		}
	}
}

//
//
//
