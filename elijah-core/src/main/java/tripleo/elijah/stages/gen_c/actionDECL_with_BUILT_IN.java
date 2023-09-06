package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

public class actionDECL_with_BUILT_IN implements EG_Statement {
	private final WhyNotGarish_BaseFunction gf;
	private final String                    target_name;
	private final OS_Type                   x;
	private final GenerateC                 gc;

	public actionDECL_with_BUILT_IN(final WhyNotGarish_BaseFunction aGf, final String aTargetName, final OS_Type aX, final GenerateC aGc) {
		gf          = aGf;
		target_name = aTargetName;
		x           = aX;
		gc          = aGc;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("actionDECL with BUILT_IN");
	}

	@Override
	public String getText() {
		final Context context = gf.getFD().getContext();
		assert context != null;
		final OS_Type type = x.resolve(context);
		final String  s1;
		if (type.isUnitType()) {
			// TODO still should not happen
			s1 = String.format("/*%s is declared as the Unit type*/", target_name);
		} else {
			// LOG.err("Bad potentialTypes size " + type);
			final String z3 = gc.getTypeName(type);
			s1 = String.format("/*535*/Z<%s> %s; /*%s*/", z3, target_name, type.getClassOf());
		}
		return s1;
	}
}
