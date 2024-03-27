package tripleo.elijah_durable_congenial.stages.deduce.calculate.rider;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ProcTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ProcTableEntry;

public class __Rider__Implement_construct {
	private final          InstructionArgument expression;
	private final          BaseEvaFunction     generatedFunction;
	private final          Instruction         instruction;
	private final @NotNull ProcTableEntry      pte;

	public __Rider__Implement_construct(BaseEvaFunction generatedFunction2, Instruction instruction2,
										InstructionArgument expression2, @NotNull ProcTableEntry pte2) {
		generatedFunction = generatedFunction2;
		instruction       = instruction2;
		expression        = expression2;
		pte               = pte2;
	}

	public InstructionArgument getExpression() {
		return expression;
	}

	public BaseEvaFunction getGeneratedFunction() {
		return generatedFunction;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public @NotNull ProcTableEntry getPte() {
		return pte;
	}
}