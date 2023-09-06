package tripleo.elijah.lang.i;

import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.ResolveError;

import java.io.File;

public interface TypeOfTypeName extends TypeName {
	// TODO what about keyword
	int getColumn();

	int getColumnEnd();

	// TODO what about keyword
	int getLine();

	Context getContext();

	File getFile();

	int getLineEnd();


	boolean isNull();

	Type kindOfType();

	TypeName resolve(Context ctx, DeduceTypes2 deduceTypes2) throws ResolveError;

	void set(TypeModifiers modifiers_);

	void setContext(Context context);

	Qualident typeOf();

	void typeOf(Qualident xy);
}
