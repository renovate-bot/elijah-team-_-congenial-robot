package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.deduce.tastic.ITastic;

interface ITasticMap {

	boolean containsKey(Object aO);

	ITastic get(Object aO);

	void put(Object aO, ITastic aR);

}
