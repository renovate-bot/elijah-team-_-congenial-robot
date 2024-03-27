package tripleo.elijah_congenial_durable.deduce2;

import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;

import java.util.HashSet;
import java.util.Set;

public class WAITS {
	private final Set<DeduceTypes2> waits = new HashSet<>();

	public void add(final DeduceTypes2 aDeduceTypes2) {
		waits.add(aDeduceTypes2);
	}

	public Iterable<DeduceTypes2> iterator() {
		return waits;
	}
}
