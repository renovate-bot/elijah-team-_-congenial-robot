package tripleo.elijah_durable_congenial.stages.gen_c.statements;

import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.stages.gen_c.*;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.IntegerIA;

public class GCFM_Inst_AGNK implements GenerateC_Statement {

	private final GenerateC                 gc;
	private final Generate_Code_For_Method  generateCodeForMethod;
	private final WhyNotGarish_BaseFunction gf;
	private final Instruction               instruction;

	public GCFM_Inst_AGNK(final Generate_Code_For_Method aGenerateCodeForMethod, final GenerateC aGc, final WhyNotGarish_BaseFunction aGf, final Instruction aInstruction) {
		generateCodeForMethod = aGenerateCodeForMethod;
		gc                    = aGc;
		gf                    = aGf;
		instruction           = aInstruction;
	}

	@Override
	public String getText() {
		final InstructionArgument target = instruction.getArg(0);
		final InstructionArgument value  = instruction.getArg(1);

		final String realTarget      = gc.getRealTargetName(gf, (IntegerIA) target, Generate_Code_For_Method.AOG.ASSIGN);
		final String assignmentValue = gc.getAssignmentValue(gf.getSelf(), value, gf.cheat());
		final String s               = String.format(Emit.emit("/*278*/") + "%s = %s;", realTarget, assignmentValue);

		return s;
	}

	@Override
	public GCR_Rule rule() {
		//return null;
		throw new NotImplementedException();
	}
}
