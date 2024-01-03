package tripleo.elijah.util;

import tripleo.elijah.diagnostic.Diagnostic;

public interface CompletableProcess<T> {
	void add(T item);

	void complete();

	void error(Diagnostic d);

	void preComplete();

	void start();
}
