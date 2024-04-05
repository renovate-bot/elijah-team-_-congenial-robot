package tripleo.elijah_durable_congenial.stages.gen_c.statements;


import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC_Statement;
import tripleo.elijah_durable_congenial.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;

import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_c.Generate_Code_For_Method;
import tripleo.elijah_durable_congenial.stages.gen_c.WhyNotGarish_BaseFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.*;

public class GCFM_Inst_JE implements GenerateC_Statement {

	private final GenerateC                 gc;
	private final Generate_Code_For_Method  generateCodeForMethod;
	private final WhyNotGarish_BaseFunction gf;
	private final Instruction               instruction;

	public GCFM_Inst_JE(final Generate_Code_For_Method aGenerateCodeForMethod, final GenerateC aGc, final WhyNotGarish_BaseFunction aGf, final Instruction aInstruction) {
		generateCodeForMethod = aGenerateCodeForMethod;
		gc                    = aGc;
		gf                    = aGf;
		instruction           = aInstruction;
	}

	@Override
	public String getText() {
		final InstructionArgument lhs = instruction.getArg(0);
		final InstructionArgument rhs = instruction.getArg(1);
		final InstructionArgument target = instruction.getArg(2);

		final Label realTarget = (Label) target;

		final VariableTableEntry vte = gf.getVarTableEntry(((IntegerIA) lhs).getIndex());
		assert rhs != null;

		String s1, s2;

		if (rhs instanceof ConstTableIA) {
			final ConstantTableEntry cte            = gf.getConstTableEntry(((ConstTableIA) rhs).getIndex());
			final String             realTargetName = gc.getRealTargetName(gf, (IntegerIA) lhs, Generate_Code_For_Method.AOG.GET);
			s1 = (String.format("vsb = %s == %s;", realTargetName, gc.getAssignmentValue(gf.getSelf(), rhs, gf)));
			s2 = (String.format("if (!vsb) goto %s;", realTarget.getName()));
		} else {
			//
			// TODO need to lookup special __eq__ function
			//
			final String realTargetName = gc.getRealTargetName(gf, (IntegerIA) lhs, Generate_Code_For_Method.AOG.GET);
			s1 = (String.format("vsb = %s == %s;", realTargetName, gc.getAssignmentValue(gf.getSelf(), rhs, gf)));
			s2 = (String.format("if (!vsb) goto %s;", realTarget.getName()));

			final int y = 2;
		}

		return String.format("%s%n%s%n", s1, s2);
	}

	@Override
	public GCR_Rule rule() {
		throw new NotImplementedException();
	}
}
