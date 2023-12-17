package tripleo.elijah.stages.gen_fn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.NormalTypeName;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.i.TypeNameList;

import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_GenericTypeNameType;
import tripleo.elijah.lang.types.OS_UserClassType;

import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.ResolveError;
import tripleo.elijah.stages.deduce.ResolveType;

import tripleo.elijah.stages.gen_fn.EvaContainer.VarTableEntry;

import tripleo.elijah.util.Helpers;

import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

final class VarTableEntry_UpdatePotentialTypesCB implements VarTableEntry.UpdatePotentialTypesCB {
	private final @NotNull DeduceTypes2 deduceTypes2;
	private final VarTableEntry varTableEntry;
	private final Context       ctx;

	private final EvaClass evaClass;

	VarTableEntry_UpdatePotentialTypesCB(@NotNull DeduceTypes2 aDeduceTypes2,
										 VarTableEntry varTableEntry,
										 Context aContext,
										 EvaClass evaClass) {
		this.deduceTypes2  = aDeduceTypes2;
		this.varTableEntry = varTableEntry;
		this.ctx           = aContext;
		this.evaClass      = evaClass;
	}

	@Override
	public @NotNull Operation<Ok> call(final @NotNull EvaContainer aEvaContainer) {
		Operation<List<GenType>> potentialTypes00 = getPotentialTypes();

		assert potentialTypes00.mode() == Mode.SUCCESS;

		List<GenType> potentialTypes = getPotentialTypes().success();

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
					@NotNull final GenType rt = ResolveType.resolve_type(resolvedClass1.getContext().module(), resolved, resolvedClass1.getContext(), deduceTypes2._LOG(), deduceTypes2);
					int                    y  = 2;

					potentialTypes = Helpers.List_of(rt);
				} catch (ResolveError aE) {
					return Operation.failure(aE);
				}
			} else if (potentialTypes.get(0).getResolved() instanceof OS_BuiltinType && potentialTypes.get(1).getResolved() instanceof OS_UserClassType) {
				OS_BuiltinType resolved = (OS_BuiltinType) potentialTypes.get(0).getResolved();

				try {
					@NotNull final GenType rt = deduceTypes2.resolve_type(resolved, resolvedClass2.getContext());
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
					if (resolvedClass1.name().sameName("ConstString") && resolvedClass2.name().sameName("String")) {
						potentialTypes.remove(0);
					} else if (resolvedClass2.name().sameName("ConstString") && resolvedClass1.name().sameName("String")) {
						potentialTypes.remove(1);
					}
				}
			}
		}

		if (potentialTypes.size() == 1) {
			final ClassInvocation.CI_GenericPart genericPart = evaClass.ci.genericPart();
			if (genericPart != null) {
				if (genericPart.hasGenericPart()) {
					final OS_Type t = varTableEntry.varType;
					if (t.getType() == OS_Type.Type.USER) {
						try {
							final @NotNull GenType genType = deduceTypes2.resolve_type(t, t.getTypeName().getContext());
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
		return Operation.success(Ok.instance());
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

					genType = deduceTypes2.resolve_type(attached, ctx);
					if (genType.getResolved() == null && genType.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
						genType.setResolved(genType.getTypeName());
						genType.setTypeName(null);
					}
				} else {
					if (potentialType.genType.getResolved() == null && potentialType.genType.getResolvedn() == null) {
						final OS_Type attached = potentialType.genType.getTypeName();

						genType = deduceTypes2.resolve_type(attached, ctx);
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
				genType.genCIForGenType2(deduceTypes2);
				potentialTypes.add(genType);
			} catch (ResolveError aResolveError) {
				aResolveError.printStackTrace();
				return Operation.failure(aResolveError);
			}
		}

		Set<GenType> set = new HashSet<>(potentialTypes);
//					final Set<GenType> s = Collections.unmodifiableSet(set);
		return Operation.success(new ArrayList<>(set));
	}
}
