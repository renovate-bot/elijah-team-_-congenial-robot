package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.deduce.tastic.ITastic;

import java.util.HashMap;
import java.util.Map;

class TasticMap_1 implements ITasticMap {
	private final Map<Object, ITastic> _map_tasticMap = new HashMap<>();


	@Override
	public boolean containsKey(final Object aO) {
		return _map_tasticMap.containsKey(aO);
	}

	@Override
	public ITastic get(final Object aO) {
		return _map_tasticMap.get(aO);
	}

	@Override
	public void put(final Object aO, final ITastic aR) {
		_map_tasticMap.put(aO, aR);
	}

}
