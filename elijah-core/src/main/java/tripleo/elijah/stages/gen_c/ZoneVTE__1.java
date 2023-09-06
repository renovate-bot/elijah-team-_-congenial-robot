package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang2.SpecialVariables;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;

class ZoneVTE__1 implements ZoneVTE {
	private final BaseEvaFunction    _g_gf;
	private final VariableTableEntry _g_varTableEntry;
	private       String             _realTargetName;

	public ZoneVTE__1(final VariableTableEntry aVarTableEntry, final BaseEvaFunction aGf) {
		_g_varTableEntry = aVarTableEntry;
		_g_gf            = aGf;
	}

	@NotNull
	private String calculate() {
		final String vte_name = _g_varTableEntry.getName();
		switch (_g_varTableEntry.getVtt()) {
		case TEMP -> {
			if (_g_varTableEntry.getName() == null) {
				return "vt" + _g_varTableEntry.getTempNum();
			} else {
				return "vt" + _g_varTableEntry.getName();
			}
		}
		case ARG -> {
			return "va" + vte_name;
		}
		case RESULT -> {
			return "vsr";
		}
		default -> {
			if (SpecialVariables.contains(vte_name)) {
				return SpecialVariables.get(vte_name);
			} else if (GenerateC.isValue(_g_gf, vte_name)) {
				return "vsc->vsv";
			} else {
				return "vv" + vte_name;
			}
		}
		}
	}

	@Override
	public @NotNull String getRealTargetName() {
		if (_realTargetName == null) {
			_realTargetName = calculate();
		}

		return Emit.emit("/*879*/") + _realTargetName;
	}
}
