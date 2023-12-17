package tripleo.elijah.lang.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.util.Operation;

import java.text.MessageFormat;
import java.util.List;

public class OS_UserClassType extends __Abstract_OS_Type {
	private final ClassStatement _classStatement;

	public OS_UserClassType(final ClassStatement aClassStatement) {
		_classStatement = aClassStatement;
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_UserClassType {0}>", _classStatement);
	}

	@Override
	public ClassStatement getClassOf() {
		return _classStatement;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.USER_CLASS && _classStatement.equals(((OS_UserClassType) aType)._classStatement);
	}

	@Override
	public OS_Element getElement() {
		return _classStatement;
	}

	@Override
	public @NotNull Type getType() {
		return Type.USER_CLASS;
	}

	@NotNull
	public ClassInvocation resolvedUserClass(final @NotNull GenType genType, final TypeName aGenericTypeName, final @NotNull DeducePhase phase, final DeduceTypes2 deduceTypes2) {
		final ClassStatement   best            = _classStatement;
		@Nullable final String constructorName = null; // TODO what to do about this, nothing I guess

		@NotNull final List<TypeName> gp = best.getGenericPart();
		@Nullable ClassInvocation     clsinv;
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

	@Override
	public String toString() {
		return asString();
	}
}
