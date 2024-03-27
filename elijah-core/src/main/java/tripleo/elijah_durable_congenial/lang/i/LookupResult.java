package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.contexts.ContextInfo;

public interface LookupResult {
	Context getContext();

	OS_Element getElement();

	ContextInfo getImportInfo();

	int getLevel();

	String getName();

	@Override
	String toString();
}
