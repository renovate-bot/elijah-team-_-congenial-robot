package tripleo.elijah_durable_congenial.stages.gen_c.statements;

import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.stages.gen_c.Emit;
import tripleo.elijah_durable_congenial.stages.gen_c.Generate_Code_For_Method;
import tripleo.elijah_durable_congenial.stages.gen_c.WhyNotGarish_BaseFunction;
import tripleo.elijah_durable_congenial.stages.gen_c.ZoneVTE;
import tripleo.elijah_durable_congenial.stages.gen_c.statements.ArgumentStringStatement;
import tripleo.elijah_durable_congenial.stages.instructions.IntegerIA;
import tripleo.elijah_durable_congenial.stages.gen_c.Generate_Code_For_Method;
import tripleo.elijah_durable_congenial.stages.gen_c.WhyNotGarish_BaseFunction;
import tripleo.elijah_durable_congenial.stages.gen_c.ZoneVTE;
import tripleo.elijah_durable_congenial.stages.instructions.IntegerIA;

public class ASS_IA extends ArgumentStringStatement {
	private final WhyNotGarish_BaseFunction    yf;
	private final IntegerIA                    ia;
	private final Generate_Code_For_Method.AOG aog;

	public ASS_IA(final WhyNotGarish_BaseFunction aWhyNotGarishBaseFunction,
				  final IntegerIA aIa,
				  final Generate_Code_For_Method.AOG aAOG) {
		yf  = aWhyNotGarishBaseFunction;
		ia  = aIa;
		aog = aAOG;
	}

	@Override
	public EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("ArgumentString::integerIA");
	}

	@Override
	public String getText() {
		final ZoneVTE zone_vte       = yf.zoneHelper(ia);
		final String  realTargetName = zone_vte.getRealTargetName();

		final String t = Emit.emit("/*669*/") + realTargetName;
		return t;
	}
}
