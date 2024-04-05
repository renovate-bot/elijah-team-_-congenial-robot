package tripleo.elijah.nextgen.model;

import org.jetbrains.annotations.Nullable;

public interface SM_Node {
	@Nullable
	default String jsonString() {
		return null;
	}
}
