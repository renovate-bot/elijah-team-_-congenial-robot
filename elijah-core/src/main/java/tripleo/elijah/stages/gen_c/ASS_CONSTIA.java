package tripleo.elijah.stages.gen_c;

import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah.stages.instructions.ConstTableIA;

public class ASS_CONSTIA extends WhyNotGarish_BaseFunction.ArgumentStringStatement {
	private final WhyNotGarish_BaseFunction    yf;
	private final ConstTableIA                 ia;
	private final Generate_Code_For_Method.AOG aog;

	public ASS_CONSTIA(final WhyNotGarish_BaseFunction aWhyNotGarishBaseFunction, final ConstTableIA aIa, final Generate_Code_For_Method.AOG aAOG) {
		yf                       = aWhyNotGarishBaseFunction;
		ia                       = aIa;
		aog                      = aAOG;
	}

	@Override
	public EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("ArgumentString::constTableIA");
	}

	@Override
	public String getText() {
		final ConstantTableEntry cte = yf.getConstTableEntry(ia.getIndex());
		final String             t   = new GenerateC.GetAssignmentValue(gc()).const_to_string(cte.initialValue);

		return t;
	}

	private GenerateC gc() {
		return yf.getGenerateC().get();
	}
}
