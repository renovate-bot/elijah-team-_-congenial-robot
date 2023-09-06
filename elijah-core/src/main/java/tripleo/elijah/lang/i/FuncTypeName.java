package tripleo.elijah.lang.i;

import java.io.File;

public interface FuncTypeName extends TypeName {
	void argList(FormalArgList op);

	void argList(TypeNameList tnl);

	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	File getFile();

	@Override
	Context getContext();

	@Override
	int getLine();

	@Override
	int getLineEnd();

	@Override
	boolean isNull();

	boolean argListIsGeneric();

	@Override
	Type kindOfType();

	@Override
	void setContext(Context context);


	void returnValue(TypeName rtn);


	// @Override
	void type(TypeModifiers typeModifiers);


}
