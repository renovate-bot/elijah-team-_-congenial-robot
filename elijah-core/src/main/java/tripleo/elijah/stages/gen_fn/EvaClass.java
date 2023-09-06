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
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_GenericTypeNameType;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.lang.types.OS_UserClassType;
import tripleo.elijah.nextgen.query.Mode;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_generic.CodeGenerator;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah.world.impl.DefaultLivingClass;

import java.util.*;

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
			varTableEntry.updatePotentialTypesCB = new VarTableEntry.UpdatePotentialTypesCB() {
				@Override
				public @NotNull Operation<Boolean> call(final @NotNull EvaContainer aEvaContainer) {
					Operation<List<GenType>> potentialTypes00 = getPotentialTypes();

					assert potentialTypes00.mode() == Mode.SUCCESS;

					List<GenType> potentialTypes = getPotentialTypes().success();
					//

					//
					// HACK TIME
					//
					if (potentialTypes.size() == 2) {
						final ClassStatement resolvedClass1 = potentialTypes.get(0).getResolved().getClassOf();
						final ClassStatement resolvedClass2 = potentialTypes.get(1).getResolved().getClassOf();
						final OS_Module      prelude;
						if (potentialTypes.get(1).getResolved() instanceof OS_BuiltinType && potentialTypes.get(0).getResolved() instanceof OS_UserClassType) {
							OS_BuiltinType resolved = (OS_BuiltinType) potentialTypes.get(1).getResolved();

							try {
								@NotNull final GenType rt = ResolveType.resolve_type(resolvedClass1.getContext().module(), resolved, resolvedClass1.getContext(), aDeduceTypes2._LOG(), aDeduceTypes2);
								int                    y  = 2;

								potentialTypes = Helpers.List_of(rt);
							} catch (ResolveError aE) {
								return Operation.failure(aE);
							}
						} else if (potentialTypes.get(0).getResolved() instanceof OS_BuiltinType && potentialTypes.get(1).getResolved() instanceof OS_UserClassType) {
							OS_BuiltinType resolved = (OS_BuiltinType) potentialTypes.get(0).getResolved();

							try {
								@NotNull final GenType rt = aDeduceTypes2.resolve_type(resolved, resolvedClass2.getContext());
								int                    y  = 2;

								potentialTypes = Helpers.List_of(rt);
							} catch (ResolveError aE) {
								return Operation.failure(aE);
							}
						} else {

							prelude = resolvedClass1.getContext().module().prelude();

							// TODO might not work when we split up prelude
							//  Thats why I was testing for package name before
							if (resolvedClass1.getContext().module() == prelude
									&& resolvedClass2.getContext().module() == prelude) {
								// Favor String over ConstString
								if (resolvedClass1.name().equals("ConstString") && resolvedClass2.name().equals("String")) {
									potentialTypes.remove(0);
								} else if (resolvedClass2.name().equals("ConstString") && resolvedClass1.name().equals("String")) {
									potentialTypes.remove(1);
								}
							}
						}
					}

					if (potentialTypes.size() == 1) {
						final ClassInvocation.CI_GenericPart genericPart = ci.genericPart();
						if (genericPart != null) {
							if (genericPart.hasGenericPart()) {
								final OS_Type t = varTableEntry.varType;
								if (t.getType() == OS_Type.Type.USER) {
									try {
										final @NotNull GenType genType = aDeduceTypes2.resolve_type(t, t.getTypeName().getContext());
										if (genType.getResolved() instanceof OS_GenericTypeNameType) {
											final ClassInvocation xxci = ((EvaClass) aEvaContainer).ci;

											var v = xxci.genericPart().valueForKey(t.getTypeName());
											if (v != null) {
												varTableEntry.varType = v;
											}

										}
									} catch (ResolveError aResolveError) {
										aResolveError.printStackTrace();
										//assert false;
										return Operation.failure(aResolveError);
									}
								}
							}
						} else {
							System.err.println("************************** no generic");
						}
					}
					return Operation.success(true);
				}

				@NotNull
				public Operation<List<GenType>> getPotentialTypes() {
					List<GenType> potentialTypes = new ArrayList<>();
					for (TypeTableEntry potentialType : varTableEntry.potentialTypes) {
						int                    y = 2;
						final @NotNull GenType genType;
						try {
							if (potentialType.genType.getTypeName() == null) {
								final OS_Type attached = potentialType.getAttached();
								if (attached == null) continue;

								genType = aDeduceTypes2.resolve_type(attached, aContext);
								if (genType.getResolved() == null && genType.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
									genType.setResolved(genType.getTypeName());
									genType.setTypeName(null);
								}
							} else {
								if (potentialType.genType.getResolved() == null && potentialType.genType.getResolvedn() == null) {
									final OS_Type attached = potentialType.genType.getTypeName();

									genType = aDeduceTypes2.resolve_type(attached, aContext);
								} else
									genType = potentialType.genType;
							}
							if (genType.getTypeName() != null) {
								final TypeName typeName = genType.getTypeName().getTypeName();
								if (typeName instanceof NormalTypeName) {
									final TypeNameList genericPart = ((NormalTypeName) typeName).getGenericPart();
									if (genericPart != null && genericPart.size() > 0) {
										genType.setNonGenericTypeName(typeName);
									}
								}
							}
							genType.genCIForGenType2(aDeduceTypes2);
							potentialTypes.add(genType);
						} catch (ResolveError aResolveError) {
							aResolveError.printStackTrace();
							return Operation.failure(aResolveError);
						}
					}
					//
					Set<GenType> set = new HashSet<>(potentialTypes);
//					final Set<GenType> s = Collections.unmodifiableSet(set);
					return Operation.success(new ArrayList<>(set));
				}
			};
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
	private String getNameHelper(@NotNull Map<TypeName, OS_Type> aGenericPart) {
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
