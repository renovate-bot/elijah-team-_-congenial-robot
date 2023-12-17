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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_FuncExprType;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.lang.types.OS_UserClassType;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.DR_Type;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created 5/31/21 1:32 PM
 */
public class GenTypeImpl implements GenType {
	private IInvocation        ci;
	private FunctionInvocation functionInvocation;
	private EvaNode            node;
	private TypeName           nonGenericTypeName;
	private OS_Type            resolved;
	private NamespaceStatement resolvedn;
	private OS_Type            typeName; // TODO or just TypeName ??
	private DR_Type            drType;

	@Contract(pure = true)
	public GenTypeImpl() {
	}

	public GenTypeImpl(@NotNull ClassStatement aClassStatement) {
		resolved = aClassStatement.getOS_Type();
	}

	@Contract(pure = true)
	public GenTypeImpl(NamespaceStatement aNamespaceStatement) {
		resolvedn = /*new OS_Type*/(aNamespaceStatement);
	}

	public GenTypeImpl(final OS_Type aAttached,
					   final OS_Type aOS_type,
					   final boolean aB,
					   final TypeName aTypeName,
					   final @NotNull DeduceTypes2 deduceTypes2,
					   final ErrSink errSink,
					   final @NotNull DeducePhase phase) {
		typeName = aAttached;
		resolved = aOS_type;
		if (aB) {
			ci = genCI(aTypeName, deduceTypes2, errSink, phase);
		}
	}

	@Override
	public ClassInvocation genCI(final TypeName aGenericTypeName,
								 final @NotNull DeduceTypes2 deduceTypes2,
								 final ErrSink errSink,
								 final @NotNull DeducePhase phase) {
		SetGenCI              sgci = new SetGenCI();
		final ClassInvocation ci   = sgci.call(this, aGenericTypeName, deduceTypes2, phase);
		final ClassInvocation ci1  = ci;
		final ClassInvocation ci11 = ci1;
		return ci11;
	}

	@Override
	public @NotNull String asString() {
		final String sb = "GenType{" + "resolvedn=" + resolvedn +
				", typeName=" + typeName +
				", nonGenericTypeName=" + nonGenericTypeName +
				", resolved=" + resolved +
				", ci=" + ci +
				", node=" + node +
				", functionInvocation=" + functionInvocation +
				'}';
		return sb;
	}

	@Override
	public void copy(@NotNull GenType aGenType) {
		if (resolvedn == null) resolvedn = aGenType.getResolvedn();
		if (typeName == null) typeName = aGenType.getTypeName();
		if (nonGenericTypeName == null) nonGenericTypeName = aGenType.getNonGenericTypeName();
		if (resolved == null) resolved = aGenType.getResolved();
		if (ci == null) ci = aGenType.getCi();
		if (node == null) node = aGenType.getNode();
	}

	@Override
	public void genCIForGenType2(final @NotNull DeduceTypes2 deduceTypes2) {
		final List<setup_GenType_Action> list  = new ArrayList<>();
		final setup_GenType_Action_Arena arena = new setup_GenType_Action_Arena();

		genCI(nonGenericTypeName, deduceTypes2, deduceTypes2._errSink(), deduceTypes2.phase);
		final IInvocation invocation = ci;
		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(new DoneCallback<EvaNamespace>() {
				@Override
				public void onDone(final EvaNamespace result) {
					node = result;
				}
			});
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(final EvaClass result) {
					node = result;
				}
			});
		} else {
			if (resolved instanceof OS_BuiltinType bit) {
				//
			} else
				throw new IllegalStateException("invalid invocation");
		}

		for (setup_GenType_Action action : list) {
			action.run(this, arena);
		}
	}

	/**
	 * Sets the invocation ({@code genType#ci}) and the node for a GenType
	 *
	 * @param aDeduceTypes2
	 */
	@Override
	public void genCIForGenType2__(final @NotNull DeduceTypes2 aDeduceTypes2) {
		genCI(nonGenericTypeName, aDeduceTypes2, aDeduceTypes2._errSink(), aDeduceTypes2.phase);
		final IInvocation invocation = ci;
		if (invocation instanceof final @NotNull NamespaceInvocation namespaceInvocation) {
			namespaceInvocation.resolveDeferred().then(new DoneCallback<EvaNamespace>() {
				@Override
				public void onDone(final EvaNamespace result) {
					node = result;
				}
			});
		} else if (invocation instanceof final @NotNull ClassInvocation classInvocation) {
			classInvocation.resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(final EvaClass result) {
					node = result;
				}
			});
		} else {
			if (resolved instanceof final @NotNull OS_FuncExprType funcExprType) {
				final @NotNull GenerateFunctions genf = aDeduceTypes2.getGenerateFunctions(funcExprType.getElement().getContext().module());
				final FunctionInvocation fi = aDeduceTypes2._phase().newFunctionInvocation((BaseFunctionDef) funcExprType.getElement(),
																						   null,
																						   null);
				WlGenerateFunction gen = new WlGenerateFunction(genf, fi, aDeduceTypes2._phase().codeRegistrar);
				gen.run(null);
				node = gen.getResult();
			} else if (resolved instanceof final @NotNull OS_FuncType funcType) {
				int y = 2;
			} else if (resolved instanceof OS_BuiltinType) {
				// passthrough
			} else
				throw new IllegalStateException("invalid invocation");
		}
	}

	@Override
	public int hashCode() {
		int result = resolvedn != null ? resolvedn.hashCode() : 0;
		result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
		result = 31 * result + (nonGenericTypeName != null ? nonGenericTypeName.hashCode() : 0);
		result = 31 * result + (resolved != null ? resolved.hashCode() : 0);
		result = 31 * result + (ci != null ? ci.hashCode() : 0);
		result = 31 * result + (node != null ? node.hashCode() : 0);
		result = 31 * result + (functionInvocation != null ? functionInvocation.hashCode() : 0);
		return result;
	}

	public static @NotNull GenType genCIFrom(final ClassStatement best, final @NotNull DeduceTypes2 deduceTypes2) {
		var genType = new GenTypeImpl();
		genType.setResolved(new OS_UserClassType(best));
		// ci, typeName, node
		// genType.
		genType.genCI(null, deduceTypes2, deduceTypes2._errSink(), deduceTypes2._phase());
		return genType;
	}

	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(final @Nullable Object aO) {
		if (this == aO) return true;
		if (aO == null || getClass() != aO.getClass()) return false;

		final GenType genType = (GenTypeImpl) aO;

		if (!Objects.equals(resolvedn, genType.getResolvedn())) return false;
		if (!Objects.equals(typeName, genType.getTypeName())) return false;
		if (!Objects.equals(nonGenericTypeName, genType.getNonGenericTypeName()))
			return false;
		if (!Objects.equals(resolved, genType.getResolved())) return false;
		if (!Objects.equals(ci, genType.getCi())) return false;
		if (!Objects.equals(node, genType.getNode())) return false;
		return Objects.equals(functionInvocation, genType.getFunctionInvocation());
	}

	@Override
	public boolean isNull() {
		if (resolvedn != null) return false;
		if (typeName != null) return false;
		if (nonGenericTypeName != null) return false;
		if (resolved != null) return false;
		if (ci != null) return false;
		return node == null;
	}

	@Override
	public void set(@NotNull OS_Type aType) {
		switch (aType.getType()) {
		case USER:
			typeName = aType;
			break;
		case USER_CLASS:
			resolved = aType;
		default:
			SimplePrintLoggerToRemoveSoon.println_err_2("48 Unknown in set: " + aType);
		}
	}

	@Override
	public IInvocation getCi() {
		return ci;
	}

	@Override
	public void setCi(IInvocation aCi) {
		ci = aCi;
	}

	@Override
	public FunctionInvocation getFunctionInvocation() {
		return functionInvocation;
	}

	@Override
	public void setFunctionInvocation(FunctionInvocation aFunctionInvocation) {
		functionInvocation = aFunctionInvocation;
	}

	@Override
	public EvaNode getNode() {
		return node;
	}

	@Override
	public void setNode(EvaNode aNode) {
		node = aNode;
	}

	@Override
	public TypeName getNonGenericTypeName() {
		return nonGenericTypeName;
	}

	@Override
	public void setNonGenericTypeName(TypeName aNonGenericTypeName) {
		nonGenericTypeName = aNonGenericTypeName;
	}

	@Override
	public OS_Type getResolved() {
		return resolved;
	}

	@Override
	public void setResolved(OS_Type aResolved) {
		resolved = aResolved;
	}

	@Override
	public NamespaceStatement getResolvedn() {
		return resolvedn;
	}

	@Override
	public void setResolvedn(NamespaceStatement aResolvedn) {
		resolvedn = aResolvedn;
	}

	@Override
	public void setDrType(final DR_Type aDrType) {
		drType = aDrType;
	}

	@Override
	public OS_Type getTypeName() {
		return typeName;
	}

	@Override
	public void setTypeName(OS_Type aTypeName) {
		typeName = aTypeName;
	}

	public DR_Type getDrType() {
		return drType;
	}

	static class SetGenCI {

		public @Nullable ClassInvocation call(@NotNull GenType genType, TypeName aGenericTypeName, final @NotNull DeduceTypes2 deduceTypes2, final @NotNull DeducePhase phase) {
			if (genType.getNonGenericTypeName() != null) {
				return nonGenericTypeName(genType, deduceTypes2, phase);
			}
			if (genType.getResolved() != null) {
				final OS_Type.Type resolvedType = genType.getResolved().getType();

				switch (resolvedType) {
				case USER_CLASS:
					return resolvedUserClass(genType, aGenericTypeName, phase, deduceTypes2);
				case FUNCTION:
					return resolvedFunction(genType, aGenericTypeName, deduceTypes2, phase);
				case FUNC_EXPR:
					// TODO what to do here?
					NotImplementedException.raise();
					break;
				case BUILT_IN:
					// README no invocation for a builtin type
					return null;
				}
			}
			return null;
		}

		private @NotNull ClassInvocation nonGenericTypeName(final @NotNull GenType genType, final @NotNull DeduceTypes2 deduceTypes2, final @NotNull DeducePhase phase) {
			@NotNull NormalTypeName aTyn1           = (NormalTypeName) genType.getNonGenericTypeName();
			@Nullable String        constructorName = null; // FIXME this comes from nowhere

			switch (genType.getResolved().getType()) {
			case GENERIC_TYPENAME:
				// TODO seems to not be necessary
				assert false;
				throw new NotImplementedException();
			case USER_CLASS:
				ClassStatement best = genType.getResolved().getClassOf();
				//
				final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(best, constructorName, aTyn1, deduceTypes2);
				assert oi.mode() == Mode.SUCCESS;

				ClassInvocation clsinv2 = oi.success();
				clsinv2 = phase.registerClassInvocation(clsinv2);
				genType.setCi(clsinv2);
				return clsinv2;
			default:
				throw new IllegalStateException("Unexpected value: " + genType.getResolved().getType());
			}
		}

		private @NotNull ClassInvocation resolvedUserClass(final @NotNull GenType genType, final TypeName aGenericTypeName, final @NotNull DeducePhase phase, final @NotNull DeduceTypes2 deduceTypes2) {
			ClassStatement   best            = genType.getResolved().getClassOf();
			@Nullable String constructorName = null; // TODO what to do about this, nothing I guess

			@NotNull List<TypeName> gp = best.getGenericPart();
			assert gp.size() == 0 || gp.size() > 0;

			@Nullable ClassInvocation clsinv;
			if (genType.getCi() == null) {
				final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(best, constructorName, (NormalTypeName) aGenericTypeName, deduceTypes2);
				assert oi.mode() == Mode.SUCCESS;
				clsinv = oi.success();
				if (clsinv == null) return null;
				clsinv = phase.registerClassInvocation(clsinv);
				genType.setCi(clsinv);
			} else
				clsinv = (ClassInvocation) genType.getCi();
			return clsinv;
		}

		private @NotNull ClassInvocation resolvedFunction(final @NotNull GenType genType, final TypeName aGenericTypeName, final @NotNull DeduceTypes2 deduceTypes2, final @NotNull DeducePhase phase) {
			// TODO what to do here?
			OS_Element       ele             = genType.getResolved().getElement();
			ClassStatement   best            = (ClassStatement) ele.getParent();//genType.resolved.getClassOf();
			@Nullable String constructorName = null; // TODO what to do about this, nothing I guess

			@NotNull List<TypeName>   gp = best.getGenericPart();
			@Nullable ClassInvocation clsinv;
			if (genType.getCi() == null) {
				final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(best, constructorName, (NormalTypeName) aGenericTypeName, deduceTypes2);
				assert oi.mode() == Mode.SUCCESS;
				clsinv = oi.success();
				if (clsinv == null) return null;
				clsinv = phase.registerClassInvocation(clsinv);
				genType.setCi(clsinv);
			} else
				clsinv = (ClassInvocation) genType.getCi();

			assert clsinv != null;

			if (ele instanceof FunctionDef fd) {
				//deduceTypes2.newFunctionInvocation(fd, pte, clsinv, phase);
				int y = 10000;
			}
			return clsinv;
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
