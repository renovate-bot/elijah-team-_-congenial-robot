package tripleo.elijah.stages.gen_fn;

import tripleo.elijah.stages.instructions.InstructionArgument;

import java.util.List;

interface GFS_ProcedureCall {
	List<InstructionArgument> getIdentIAPathList();

	InstructionArgument simplify();
}
