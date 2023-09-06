package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.util.Helpers;

import java.util.List;

class FnCallArgs_Statement implements EG_Statement {
	private final GenerateC                    generateC;
	private final GenerateC.GetAssignmentValue getAssignmentValue;
	private final Instruction                  inst;
	private final BaseEvaFunction              gf;
	private final ProcTableEntry               pte;

	public FnCallArgs_Statement(final GenerateC aGenerateC, final GenerateC.GetAssignmentValue aGetAssignmentValue, final ProcTableEntry aPte, final Instruction aInst, final BaseEvaFunction aGf) {
		generateC          = aGenerateC;
		getAssignmentValue = aGetAssignmentValue;
		pte                = aPte;
		inst               = aInst;
		gf                 = aGf;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FnCallArgs_Statement");
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		// VERIFY computed. immediate
		final IdentExpression ptex = (IdentExpression) pte.__debug_expression;

		// VERIFY template usage
		sb.append(ptex.getText());

		// VERIFY template push
		sb.append(Emit.emit("/*671*/") + "(");

		// VERIFY alias evaluation
		final List<String> sll = getAssignmentValue.getAssignmentValueArgs(inst, gf, generateC.LOG);
		// VERIFY template usage
		sb.append(Helpers.String_join(", ", sll));

		// VERIFY template push
		sb.append(")");

		// VERIFY EG_St:  <here> && getText() -> <~>
		return sb.toString();
	}
}
