package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.lang.i.IdentExpression;

import tripleo.elijah.nextgen.outputstatement.EG_CompoundStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

import tripleo.elijah.stages.deduce.ExpressionConfession;

import tripleo.elijah.stages.gen_fn.ProcTableEntry;

import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;

import tripleo.elijah.util.Helpers;

import java.util.List;

public class GCX_FunctionCall implements EG_Statement {
	private final          GenerateC                 gc;
	private final          WhyNotGarish_BaseFunction gf;
	private final @NotNull Instruction               instruction;
	private final          ProcTableEntry            pte;

	@Contract(pure = true)
	public GCX_FunctionCall(final WhyNotGarish_BaseFunction aGf,
							final GenerateC aGc,
							final @NotNull Instruction aInstruction) {
		gf          = aGf;
		gc          = aGc;
		instruction = aInstruction;

		final InstructionArgument x = aInstruction.getArg(0);

		assert x instanceof ProcIA;

		pte = gf.getProcTableEntry(((ProcIA) x).index());
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("GCX_FunctionCall >> action_CALL");
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		ExpressionConfession ec = pte.expressionConfession();

		switch (ec.getType()) {
		case exp_num -> {
			// FIME 07/20 why are we using expression in exp_num
			final IdentExpression               ptex = (IdentExpression) pte.__debug_expression;
			final String                        text = ptex.getText();
			final @Nullable InstructionArgument xx   = gf.vte_lookup(text);

			assert xx != null;

			final String realTargetName = gc.getRealTargetName(gf, (IntegerIA) xx, Generate_Code_For_Method.AOG.GET);

			sb.append(Emit.emit("/*424*/") + realTargetName);
			sb.append('(');

			final List<String> sl3;
			//sl3 = gc.getArgumentStrings(gf, instruction);
			final WhyNotGarish_Function yf = (WhyNotGarish_Function) gf;
			sl3 = yf.getArgumentStrings(instruction).getLeft();

			sb.append(Helpers.String_join(", ", sl3));
			sb.append(");");

			final EG_SingleStatement beg = new EG_SingleStatement("(", null);
			final EG_SingleStatement mid = new EG_SingleStatement(Helpers.String_join(", ", sl3), null);
			final EG_SingleStatement end = new EG_SingleStatement(");", null);
			final boolean            ind = false;
			final EX_Explanation     exp = EX_Explanation.withMessage("GCX_FunctionCall exp_num");

			final EG_CompoundStatement est = new EG_CompoundStatement(beg, mid, end, ind, exp);

			final String ss = est.getText();

			System.out.println(ss);
		}
		case exp -> {
			final CReference reference = new CReference(gc.repo(), gc._ce());
			final IdentIA    ia2       = (IdentIA) pte.expression_num;
			reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
			final List<String> sl3 = gc.getArgumentStrings(gf, instruction);
			reference.args(sl3);
			final String path = reference.build();

			reference.debugPath(ia2, path);

			sb.append(Emit.emit("/*427-2*/") + path + ";");
		}
		default -> throw new IllegalStateException("Unexpected value: " + ec.getType());
		}

		if (false) {
			if (pte.expression_num == null) {
				final IdentExpression               ptex = (IdentExpression) pte.__debug_expression;
				final String                        text = ptex.getText();
				@Nullable final InstructionArgument xx   = gf.vte_lookup(text);
				assert xx != null;
				final String realTargetName = gc.getRealTargetName(gf, (IntegerIA) xx, Generate_Code_For_Method.AOG.GET);
				sb.append(Emit.emit("/*424*/") + realTargetName);
				sb.append('(');
				final List<String> sl3 = gc.getArgumentStrings(gf, instruction);
				sb.append(Helpers.String_join(", ", sl3));
				sb.append(");");
			} else {
				final CReference reference = new CReference(gc.repo(), gc._ce());
				final IdentIA    ia2       = (IdentIA) pte.expression_num;
				reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
				final List<String> sl3 = gc.getArgumentStrings(gf, instruction);
				reference.args(sl3);
				final String path = reference.build();

				reference.debugPath(ia2, path);

				sb.append(Emit.emit("/*427-3*/") + path + ";");
			}
		}

		return sb.toString();
	}
}
