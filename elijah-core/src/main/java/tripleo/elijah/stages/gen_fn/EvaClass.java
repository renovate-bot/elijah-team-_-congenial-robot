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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.lang.i.AccessNotation;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.Scope3;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.impl.ConstructStatementImpl;
import tripleo.elijah.lang.impl.ExpressionBuilder;
import tripleo.elijah.lang.impl.FunctionDefImpl;
import tripleo.elijah.lang.impl.Scope3Impl;
import tripleo.elijah.lang.impl.StatementWrapperImpl;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.gen_generic.CodeGenerator;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.world.impl.DefaultLivingClass;

/**
 * Created 10/29/20 4:26 AM
 */
public class EvaClass extends EvaContainerNC implements GNCoded {
	public          DefaultLivingClass                  _living;
	private final   ClassStatement                      klass;
	private final   OS_Module                           module;
	public          ClassInvocation                     ci;
	public @NotNull Map<ConstructorDef, EvaConstructor> constructors                      = new HashMap<ConstructorDef, EvaConstructor>();
	private         boolean                             resolve_var_table_entries_already = false;

	public EvaClass(ClassStatement aClassStatement, OS_Module aModule) {
		klass  = aClassStatement;
		module = aModule;
	}

	public void addAccessNotation(AccessNotation an) {
		throw new NotImplementedException();
	}

	public void addConstructor(ConstructorDef aConstructorDef, @NotNull EvaConstructor aGeneratedFunction) {
		constructors.put(aConstructorDef, aGeneratedFunction);
	}

	public void createCtor0() {
		// TODO implement me
		FunctionDef fd = new FunctionDefImpl(klass, klass.getContext());
		fd.setName(Helpers.string_to_ident("<ctor$0>"));
		Scope3 scope3 = new Scope3Impl(fd);
		fd.scope(scope3);
		for (VarTableEntry varTableEntry : varTable) {
			if (varTableEntry.initialValue != IExpression.UNASSIGNED) {
				IExpression left  = varTableEntry.nameToken;
				IExpression right = varTableEntry.initialValue;

				IExpression e = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);
				scope3.add(new StatementWrapperImpl(e, fd.getContext(), fd));
			} else {
				if (getPragma("auto_construct")) {
					scope3.add(new ConstructStatementImpl(fd, fd.getContext(), varTableEntry.nameToken, null, null));
				}
			}
		}
	}

	public void fixupUserClasses(final @NotNull DeduceTypes2 aDeduceTypes2, final Context aContext) {
		for (VarTableEntry varTableEntry : varTable) {
			varTableEntry.updatePotentialTypesCB = new VarTableEntry_UpdatePotentialTypesCB(aDeduceTypes2,
					varTableEntry, aContext, this);
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			/*=======================================*/
			if (!varTableEntry._p_updatePotentialTypesCBPromise.isResolved()) {
				varTableEntry._p_updatePotentialTypesCBPromise.resolve(varTableEntry.updatePotentialTypesCB);
			}
		}
	}

	@Override
	public void generateCode(GenerateResultEnv aFileGen, @NotNull CodeGenerator aCodeGenerator) {
		aCodeGenerator.generate_class(aFileGen, this);
	}

	@Override
	public OS_Element getElement() {
		return getKlass();
	}

	public ClassStatement getKlass() {
		return this.klass;
	}

	@NotNull
	public String getName() {
		StringBuilder sb = new StringBuilder();
		sb.append(klass.getName());
		final ClassInvocation.CI_GenericPart ciGenericPart = ci.genericPart();
		if (ciGenericPart != null) {
			if (ciGenericPart.hasGenericPart()) {
				final Map<TypeName, OS_Type> map = ci.genericPart().getMap();

				if (map != null) {
					sb.append("[");
					final String joined = getNameHelper(map);
					sb.append(joined);
					sb.append("]");
				}
			}
		}
		return sb.toString();
	}

	@NotNull
	private static String getNameHelper(@NotNull Map<TypeName, OS_Type> aGenericPart) {
		final List<String> ls = new ArrayList<String>();
		for (Map.Entry<TypeName, OS_Type> entry : aGenericPart.entrySet()) { // TODO Is this guaranteed to be in order?
			final OS_Type value = entry.getValue(); // This can be another ClassInvocation using GenType
			final String  name;

			if (value instanceof OS_UnknownType) {
				name = "?";
			} else {
				name = value.getClassOf().getName();
			}
			ls.add(name); // TODO Could be nested generics
		}
		return Helpers.String_join(", ", ls);
	}

	@NotNull
	public String getNumberedName() {
		return getKlass().getName() + "_" + getCode();
	}

	private boolean getPragma(String auto_construct) { // TODO this should be part of ContextImpl
		return false;
	}

	@Override
	public @NotNull Role getRole() {
		return Role.CLASS;
	}

	@Override
	public void register(final @NotNull ICodeRegistrar aCr) {
		aCr.registerClass1(this);
	}

	@Override
	public String identityString() {
		return String.valueOf(klass);
	}

	@Override
	public OS_Module module() {
		return module;
	}

	public boolean isGeneric() {
		return klass.getGenericPart().size() > 0;
	}

	public boolean resolve_var_table_entries(@NotNull DeducePhase aDeducePhase) {
		boolean Result = false;

		if (resolve_var_table_entries_already) return true;

		for (VarTableEntry varTableEntry : varTable) {
			varTableEntry.getDeduceElement3().resolve_var_table_entries(aDeducePhase, ci);
		}

		resolve_var_table_entries_already = true; // TODO is this right?
		return Result;
	}

	@Override
	public @NotNull String toString() {
		return "EvaClass{" +
				"klass=" + klass +
				", code=" + getCode() +
				", module=" + module.getFileName() +
				", ci=" + ci.finalizedGenericPrintable() +
				'}';
	}
}

//
//
//
