package tripleo.elijah.nextgen.reactive;


import java.util.function.Consumer;

public interface Reactive {
	void add(Reactivable aReactivable);

	<T> void addListener(Consumer<T> t);

	void join(ReactiveDimension aDimension);
}
