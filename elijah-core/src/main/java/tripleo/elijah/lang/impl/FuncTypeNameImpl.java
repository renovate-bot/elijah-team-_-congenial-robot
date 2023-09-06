package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;

import java.io.File;
import java.util.ArrayList;

/**
 * Created 8/16/20 2:16 AM
 */
public class FuncTypeNameImpl implements tripleo.elijah.lang.i.FuncTypeName {
	private final     Context       _ctx;
	public @Nullable  TypeNameList  _arglist     = null/* new TypeNameList() */;
	private           TypeModifiers _modifiers;
	private @Nullable TypeName      _returnValue = null /* new RegularTypeNameImpl() */; // TODO warning

	public FuncTypeNameImpl(final Context cur) {
		_ctx = cur;
	}

	@Override
	public void argList(final @NotNull FormalArgList op) {
		TypeNameList tnl = new TypeNameListImpl();
		for (FormalArgListItem fali : op.falis()) {
			final TypeName tn = fali.typeName();
			if (tn != null)
				tnl.add(tn);
			else {
//				tnl.add(TypeName.Undeclared(fali)); // TODO implement me
			}
		}
		argList(tnl);
	}

	@Override
	public void argList(final TypeNameList tnl) {
		_arglist = tnl;
	}

	@Override
	public int getColumn() {
		return -1;
	}

	@Override
	public int getColumnEnd() {
		return -1;
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public @Nullable File getFile() {
		return null;
	}

	@Override
	public int getLine() {
		return -1;
	}

	@Override
	public boolean isNull() {
		return _arglist == null && _returnValue == null;
	}

	@Override
	public @NotNull Type kindOfType() {
		return Type.FUNCTION;
	}

	@Override
	public void setContext(final Context context) {
		throw new NotImplementedException();
	}

	@Override
	public void returnValue(final TypeName rtn) {
		_returnValue = rtn;
	}

	@Override
	public int getLineEnd() {
		return -1;
	}

	@Override
	public boolean argListIsGeneric() {
		return _arglist instanceof GenericTypeName;
	}

	// @Override
	@Override
	public void type(final TypeModifiers typeModifiers) {
		_modifiers = typeModifiers;
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var ls = new ArrayList<String>();

		for (TypeName typeName : _arglist.p()) {
			ls.add("%s".formatted(typeName.toString()));
		}
		return "function (" +
				Helpers.String_join(", ", ls) +
				//"_ctx=" + _ctx +
				//", _arglist=" + _arglist +
				//", _modifiers=" + _modifiers +
				//", _returnValue=" + _returnValue +
				')';
	}
}

//
//
//