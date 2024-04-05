package tripleo.elijah_congenial_durable.deduce2;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Item;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

import java.util.ArrayList;
import java.util.List;

public class DRS {
	private final List<Pair<BaseEvaFunction, DR_Item>> drs = new ArrayList<>();

	public void add(final BaseEvaFunction bef, DR_Item aDri) {
		drs.add(Pair.of(bef, aDri));
	}

	public Iterable<Pair<BaseEvaFunction, DR_Item>> iterator() {
		return drs;
	}
}
