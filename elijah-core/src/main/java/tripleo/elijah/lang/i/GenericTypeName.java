package tripleo.elijah.lang.i;

import java.io.File;

public interface GenericTypeName extends TypeName {
	int getColumn();

	int getColumnEnd();

	Context getContext();

	File getFile();

	boolean isNull();

	Type kindOfType();

	void setContext(Context context);

	int getLine();

	void set(TypeModifiers modifiers_);

	void setConstraint(TypeName aConstraint);

	int getLineEnd();

	void typeName(Qualident xy);
}
