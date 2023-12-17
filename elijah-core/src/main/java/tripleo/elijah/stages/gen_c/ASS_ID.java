package tripleo.elijah.stages.gen_c;

import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.instructions.IdentIA;

public class ASS_ID extends WhyNotGarish_BaseFunction.ArgumentStringStatement {
	private final WhyNotGarish_BaseFunction    yf;
	private final IdentIA                      ia;
	private final Generate_Code_For_Method.AOG aog;

	public ASS_ID(final WhyNotGarish_BaseFunction aWhyNotGarishBaseFunction, final IdentIA aIa, final Generate_Code_For_Method.AOG aAOG) {
		yf                       = aWhyNotGarishBaseFunction;
		ia                       = aIa;
		aog                      = aAOG;
	}

	@Override
	public EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("ArgumentString::identIA");
	}

	@Override
	public String getText() {
		GenerateC        generateC = yf.getGenerateC().get();
		final CReference reference = new CReference(generateC.get_repo(), generateC._ce());
		reference.getIdentIAPath(ia, aog, null);
		final String text = reference.build();
		final String t    = (Emit.emit("/*673*/") + text);

		return t;
	}
}
