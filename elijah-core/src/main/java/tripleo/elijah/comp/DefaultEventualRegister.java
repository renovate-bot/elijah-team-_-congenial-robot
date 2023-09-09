package tripleo.elijah.comp;

import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;

import java.util.ArrayList;
import java.util.List;

public class DefaultEventualRegister implements EventualRegister {
	final List<Eventual<?>> _eventuals = new ArrayList<Eventual<?>>();

	public DefaultEventualRegister() {
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
			} else {
				System.err.println("[PipelineLogic::checkEventual] failed for " + eventual.description());
			}
		}
	}
}