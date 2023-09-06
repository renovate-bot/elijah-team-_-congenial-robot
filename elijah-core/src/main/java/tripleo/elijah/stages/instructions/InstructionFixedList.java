package tripleo.elijah.stages.instructions;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.util.IFixedList;

public class InstructionFixedList implements IFixedList<InstructionArgument> {
	private final Instruction instruction;

	@Contract(pure = true)
	public InstructionFixedList(final Instruction aInstruction) {
		instruction = aInstruction;
	}

	@Override
	public InstructionArgument get(final int at) {
		return instruction.getArg(at);
	}

	@Override
	public int size() {
		return instruction.getArgsSize();
	}
}
