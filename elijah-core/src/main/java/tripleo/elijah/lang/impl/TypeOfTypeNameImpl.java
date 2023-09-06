package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.DeduceLookupUtils;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.ResolveError;

import java.io.File;

/**
 * Created 8/16/20 7:42 AM
 */
public class TypeOfTypeNameImpl implements TypeName, tripleo.elijah.lang.i.TypeOfTypeName {
	private Context       _ctx;
	private Qualident     _typeOf;
	private TypeModifiers modifiers;

	public TypeOfTypeNameImpl(final Context cur) {
		_ctx = cur;
	}

	// TODO what about keyword
	@Override
	public int getColumn() {
		return _typeOf.parts().get(0).getColumn();
	}

	@Override
	public int getColumnEnd() {
		return _typeOf.parts().get(_typeOf.parts().size()).getColumnEnd();
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public File getFile() {
		return _typeOf.parts().get(0).getFile();
	}

	// TODO what about keyword
	@Override
	public int getLine() {
		return _typeOf.parts().get(0).getLine();
	}

	@Override
	public int getLineEnd() {
		return _typeOf.parts().get(_typeOf.parts().size()).getLineEnd();
	}

	@Override
	public void set(final TypeModifiers modifiers_) {
		modifiers = modifiers_;
	}

	@Override
	public Qualident typeOf() {
		return _typeOf;
	}

	// region Locatable

	@Override
	public @Nullable TypeName resolve(@NotNull Context ctx, @NotNull DeduceTypes2 deduceTypes2) throws ResolveError {
//		tripleo.elijah.util.Stupidity.println_out_2(_typeOf.toString());
		LookupResultList lrl  = DeduceLookupUtils.lookupExpression(_typeOf, ctx, deduceTypes2);
		OS_Element       best = lrl.chooseBest(null);
		if (best instanceof VariableStatement)
			return ((VariableStatement) best).typeName();
		return null;
	}

	@Override
	public void typeOf(final Qualident xy) {
		_typeOf = xy;
	}

	@Override
	public void setContext(final Context context) {
		_ctx = context;
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public @NotNull Type kindOfType() {
		return Type.TYPE_OF;
	}

	// endregion
}

//
//
//
