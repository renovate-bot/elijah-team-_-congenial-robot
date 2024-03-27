package tripleo.elijah_durable_congenial.lang.i;

import java.io.File;

public interface GenericTypeName extends TypeName {
	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	File getFile();	@Override
	Context getContext();

	@Override
	int getLine();

	@Override
	int getLineEnd();	@Override
	boolean isNull();

	@Override
	Type kindOfType();

	@Override
	void setContext(Context context);



	void set(TypeModifiers modifiers_);

	void setConstraint(TypeName aConstraint);



	void typeName(Qualident xy);
}
