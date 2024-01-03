package tripleo.elijah.stages.gen_c.statements;

import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.stages.instructions.InstructionArgument;

public record ArgumentString(InstructionArgument ia, EG_Statement statement, String error) {
}
