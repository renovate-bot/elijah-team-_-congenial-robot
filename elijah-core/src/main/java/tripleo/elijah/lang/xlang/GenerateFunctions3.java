package tripleo.elijah.lang.xlang;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.InstructionName;

import java.util.List;

public interface GenerateFunctions3 {
	int add_i(BaseEvaFunction aGf, InstructionName aInstructionName, List<InstructionArgument> aO, Context aCctx);

	int addTempTableEntry(OS_Type aOSUserType, IdentExpression aId, BaseEvaFunction aGf, IdentExpression aId1);

	void generate_item(FunctionItem aItem, BaseEvaFunction aGf, Context aContext);

	InstructionArgument simplify_expression(IExpression aId, BaseEvaFunction aGf, Context aCctx);

	void logErr(String aS);
}
