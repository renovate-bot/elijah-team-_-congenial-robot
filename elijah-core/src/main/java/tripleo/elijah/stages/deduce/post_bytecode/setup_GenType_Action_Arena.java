package tripleo.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class setup_GenType_Action_Arena {
	private final Map<String, Object> arenaVars = new HashMap<>();

	public <T> @Nullable T get(String a) {
		if (arenaVars.containsKey(a)) {
			return (T) arenaVars.get(a);
		}
		return null;
	}

	public <T> void put(String k, T v) {
		arenaVars.put(k, v);
	}

	public void clear() {
		arenaVars.clear();
	}
}
