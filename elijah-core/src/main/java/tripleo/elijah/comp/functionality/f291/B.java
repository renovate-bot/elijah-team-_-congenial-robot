package tripleo.elijah.comp.functionality.f291;

import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.gen_c.GenerateC;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class B {
	public static final B INSTANCE = new B();

	// FIXME 24j1 too much with instance??
	private final Map<OS_Module, Eventual<GenerateC>> gc2m_map = new HashMap<>();

	private B() {
	}

	public Map<OS_Module, Eventual<GenerateC>> __getMap() {
		return gc2m_map;
	}

	public void push(final OS_Module mod, final Consumer<GenerateC> cb) {
		final Eventual<GenerateC> v = gc2m_map.get(mod);
		assert v != null;
		v.then(ggc -> cb.accept(ggc));
	}

	public void resolve_GenerateC(final OS_Module aMod, final GenerateC aGenerateC) {
		final Eventual<GenerateC> p = new Eventual<>();
		p.resolve(aGenerateC);
		gc2m_map.put(aMod, p);
	}

	public Eventual<GenerateC> lookupModule(final OS_Module aMod) {
		return __getMap().get(aMod);
	}
}
