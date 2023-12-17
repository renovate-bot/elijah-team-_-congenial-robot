/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeduceElement3_Constructor;
import tripleo.elijah.stages.deduce.FunctionInvocation;

/**
 * Created 6/27/21 9:45 AM
 */
public class EvaConstructor extends BaseEvaFunction {
	public final @Nullable ConstructorDef                       cd;
	private final          Eventual<DeduceElement3_Constructor> _de3_Promise = new Eventual<>();

	public EvaConstructor(final @Nullable ConstructorDef aConstructorDef) {
		cd = aConstructorDef;
	}

	@Override
	public @NotNull FunctionDef getFD() {
		if (cd == null) throw new IllegalStateException("No function");
		return cd;
	}

	@Override
	public @Nullable VariableTableEntry getSelf() {
		if (getFD().getParent() instanceof ClassStatement)
			return getVarTableEntry(0);
		else
			return null;
	}

	@Override
	public String identityString() {
		return String.valueOf(cd);
	}

	@Override
	public @NotNull OS_Module module() {
		return cd.getContext().module();
	}

	public String name() {
		if (cd == null)
			throw new IllegalArgumentException("null cd");
		return cd.name().asString();
	}

	public void setFunctionInvocation(@NotNull FunctionInvocation fi) {
		GenType genType = new GenTypeImpl();

		final ClassInvocation classInvocation1 = fi.getClassInvocation();
		genType.setCi(classInvocation1); // TODO will fail on namespace constructors; next line too

		final ClassInvocation classInvocation2 = (ClassInvocation) genType.getCi();
		genType.setResolved((classInvocation2.getKlass()).getOS_Type());






		assert classInvocation1 == classInvocation2;






		genType.setNode(this);
		typeDeferred().resolve(genType);
	}

	@Override
	public String toString() {
		return String.format("<GeneratedConstructor %s>", cd);
	}

	public Eventual<DeduceElement3_Constructor> de3_Promise() {
		return _de3_Promise;
	}
}

//
//
//
