package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tripleo.elijah.stages.gen_c.CReference._getIdentIAPathList;

class Zone {
	private final Map<Object, ZoneMember> members = new HashMap<Object, ZoneMember>();

	public ZoneVTE get(final VariableTableEntry aVarTableEntry, final BaseEvaFunction aGf) {
		if (members.containsKey(aVarTableEntry))
			return (ZoneVTE) members.get(aVarTableEntry);

		final ZoneVTE r = new ZoneVTE__1(aVarTableEntry, aGf);
		members.put(aVarTableEntry, r);
		return r;
	}

	public ZonePath getPath(final @NotNull IdentIA aIdentIA) {
		final List<InstructionArgument> s = _getIdentIAPathList(aIdentIA);

		if (members.containsKey(aIdentIA))
			return (ZonePath) members.get(aIdentIA);

		final ZonePath r = new ZonePath(aIdentIA, s);
		members.put(aIdentIA, r);
		return r;
	}

	//public GI_Item get(final EvaNode aGeneratedNode) {
	//}
}
