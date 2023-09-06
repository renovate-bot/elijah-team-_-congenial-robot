package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.CharLitExpression;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.NumericExpression;
import tripleo.elijah.lang.i.StringExpression;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.util.NotImplementedException;

public class GCX_ConstantString implements EG_Statement {

	private final IExpression                  expression;
	private final GenerateC                    generateC;
	private final GenerateC.GetAssignmentValue getAssignmentValue;

	public GCX_ConstantString(final GenerateC aGenerateC, final GenerateC.GetAssignmentValue aGetAssignmentValue, IExpression expression) {
		generateC          = aGenerateC;
		getAssignmentValue = aGetAssignmentValue;
		this.expression    = expression;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("GCX_ConstantString >> GetAssignmentValue.const_to_string");
	}

	@Override
	public String getText() {
		if (expression instanceof NumericExpression) {
			return String.format("%d", ((NumericExpression) expression).getValue());
		}
		if (expression instanceof CharLitExpression) {
			return String.format("'%s'", expression);
		}
		if (expression instanceof StringExpression) {
			// TODO triple quoted strings and other escaping concerns
			return String.format("\"%s\"", ((StringExpression) expression).getText());
		}

		// FloatLitExpression
		// BooleanExpression
		throw new NotImplementedException();
	}

}
