package tripleo.elijah_durable_congenial.stages.gen_c.statements;

import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_c.Generate_Code_For_Method;
import tripleo.elijah_durable_congenial.stages.gen_c.WhyNotGarish_BaseFunction;
import tripleo.elijah_durable_congenial.stages.gen_c.statements.ArgumentStringStatement;
import tripleo.elijah_durable_congenial.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.ConstTableIA;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_c.Generate_Code_For_Method;
import tripleo.elijah_durable_congenial.stages.gen_c.WhyNotGarish_BaseFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.ConstTableIA;

public class ASS_CONSTIA extends ArgumentStringStatement {
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
