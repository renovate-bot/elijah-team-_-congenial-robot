package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tripleo.elijah_durable_congenial.stages.gen_c.CReference._getIdentIAPathList;

public class Zone {
	private final Map<Object, ZoneMember> members = new HashMap<Object, ZoneMember>();

	public ZoneVTE get(final VariableTableEntry aVarTableEntry, final BaseEvaFunction aGf) {
		if (members.containsKey(aVarTableEntry)) {
			return (ZoneVTE) members.get(aVarTableEntry);
		}

		final ZoneVTE r = new ZoneVTE__1(aVarTableEntry, aGf);
		members.put(aVarTableEntry, r);
		return r;
	}

	public ZonePath getPath(final @NotNull IdentIA aIdentIA) {
		if (members.containsKey(aIdentIA)) {
			return (ZonePath) members.get(aIdentIA);
		}

		final List<InstructionArgument> s = _getIdentIAPathList(aIdentIA);
		final ZonePath r = new ZonePath(aIdentIA, s);
		members.put(aIdentIA, r);
		return r;
	}

	//public GI_Item get(final EvaNode aGeneratedNode) {
	//}
}
