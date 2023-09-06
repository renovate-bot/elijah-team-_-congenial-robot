package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.ContextInfo;

public interface LookupResult {
	Context getContext();

	OS_Element getElement();

	ContextInfo getImportInfo();

	int getLevel();

	String getName();

	@Override
	String toString();
}
