package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.DR_Type;
import tripleo.elijah.stages.deduce.nextgen.SGTA_SetDrType;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action_Arena;

import java.util.ArrayList;
import java.util.List;

public class ForwardingGenType implements GenType {
	enum Mode {GENERATIONAL, NORMAL}

	final @NotNull Mode    mode;
	private final  GenType base;

	private final           List<setup_GenType_Action> list = new ArrayList<>();
	private final @Nullable setup_GenType_Action_Arena g;

	public ForwardingGenType(final GenType aGenType, final boolean aB) {
		this.base = aGenType;

		if (aB) {
			mode = Mode.GENERATIONAL;
			g    = new setup_GenType_Action_Arena();
		} else {
			mode = Mode.NORMAL;
			g    = null;
		}
	}

	public @NotNull GenType unsparkled() {
		if (g != null) {
			for (setup_GenType_Action setupGenTypeAction : list) {
				setupGenTypeAction.run(base, g);
			}
			list.clear();
			g.clear();
		}
		return this;
	}

	@Override
	public String asString() {
		return base.asString();
	}

	@Override
	public void copy(final GenType aGenType) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_CopyGenType(aGenType));
		} else {
			base.copy(aGenType);
		}
	}

	@Override
	public ClassInvocation genCI(final TypeName aGenericTypeName, final DeduceTypes2 deduceTypes2, final ErrSink errSink, final DeducePhase phase) {
		return base.genCI(aGenericTypeName, deduceTypes2, errSink, phase);
	}

	@Override
	public void genCIForGenType2(final @Nullable DeduceTypes2 deduceTypes2) {
		if (deduceTypes2 == null) return;
		//if (base.getCi() == null) return;

		this.unsparkled();

		base.genCIForGenType2(deduceTypes2);
	}

	@Override
	public void genCIForGenType2__(final DeduceTypes2 aDeduceTypes2) {
		base.genCIForGenType2__(aDeduceTypes2);
	}

	@Override
	public IInvocation getCi() {
		return base.getCi();
	}

	@Override
	public FunctionInvocation getFunctionInvocation() {
		return base.getFunctionInvocation();
	}

	@Override
	public EvaNode getNode() {
		return base.getNode();
	}

	@Override
	public TypeName getNonGenericTypeName() {
		return base.getNonGenericTypeName();
	}

	@Override
	public OS_Type getResolved() {
		return base.getResolved();
	}

	@Override
	public NamespaceStatement getResolvedn() {
		return base.getResolvedn();
	}

	@Override
	public OS_Type getTypeName() {
		return base.getTypeName();
	}

	@Override
	public boolean isNull() {
		return base.isNull();
	}

	@Override
	public void set(@NotNull final OS_Type aType) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_Set(aType));
		} else {
			base.set(aType);
		}
	}

	@Override
	public void setCi(final IInvocation aInvocation) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetCi(aInvocation));
		} else {
			base.setCi(aInvocation);
		}
	}

	@Override
	public void setFunctionInvocation(final FunctionInvocation aFi) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetFunctionInvocation(aFi));
		} else {
			base.setFunctionInvocation(aFi);
		}
	}

	@Override
	public void setNode(final EvaNode aResult) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetNode(aResult));
		} else {
			base.setNode(aResult);
		}
	}

	@Override
	public void setNonGenericTypeName(@NotNull final TypeName typeName) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetNonGenericTypeName(typeName));
		} else {
			base.setNonGenericTypeName(typeName);
		}
	}

	@Override
	public void setResolved(final OS_Type aOSType) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetResolvedType(aOSType));
		} else {
			base.setResolved(aOSType);
		}
	}

	@Override
	public void setResolvedn(final NamespaceStatement parent) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_RestReolvedn(parent));
		} else {
			base.setResolvedn(parent);
		}
	}

	@Override
	public void setDrType(final DR_Type aDrType) {
		list.add(new SGTA_SetDrType(aDrType));
	}

	@Override
	public void setTypeName(final OS_Type aType) {
		if (mode == Mode.GENERATIONAL) {
			list.add(new SGTA_SetTypeNaeme(aType));
		} else {
			base.setTypeName(aType);
		}
	}
}
