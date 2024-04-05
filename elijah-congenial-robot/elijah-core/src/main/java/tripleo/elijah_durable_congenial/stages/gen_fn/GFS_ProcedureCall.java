package tripleo.elijah_durable_congenial.stages.gen_fn;

import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;

import java.util.List;

public interface GFS_ProcedureCall {
	List<InstructionArgument> getIdentIAPathList();

	InstructionArgument simplify();
}
