package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.ResolveError;

import java.io.File;

public interface TypeOfTypeName extends TypeName {
	// TODO what about keyword
	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	File getFile();

	// TODO what about keyword
	@Override
	int getLine();	@Override
	Context getContext();

	@Override
	int getLineEnd();




	@Override
	boolean isNull();

	@Override
	Type kindOfType();

	TypeName resolve(Context ctx, DeduceTypes2 deduceTypes2) throws ResolveError;

	void set(TypeModifiers modifiers_);

	@Override
	void setContext(Context context);

	Qualident typeOf();

	void typeOf(Qualident xy);
}
