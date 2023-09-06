package tripleo.elijah.stages.deduce.fluffy.i;

import java.util.List;

public interface FluffyModule {
	void find_all_entry_points();

	void find_multiple_items(FluffyComp aFc);

	FluffyLsp lsp();

	List<FluffyMember> members();

	String name();
}
