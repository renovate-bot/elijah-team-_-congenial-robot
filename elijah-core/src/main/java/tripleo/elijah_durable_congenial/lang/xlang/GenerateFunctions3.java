package tripleo.elijah_durable_congenial.lang.xlang;

import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionName;
import tripleo.elijah_durable_congenial.lang.i.*;

import java.util.List;

public interface GenerateFunctions3 {
	int add_i(BaseEvaFunction aGf, InstructionName aInstructionName, List<InstructionArgument> aO, Context aCctx);

	int addTempTableEntry(OS_Type aOSUserType, IdentExpression aId, BaseEvaFunction aGf, IdentExpression aId1);

	void generate_item(FunctionItem aItem, BaseEvaFunction aGf, Context aContext);

	InstructionArgument simplify_expression(IExpression aId, BaseEvaFunction aGf, Context aCctx);

	void logErr(String aS);
}
