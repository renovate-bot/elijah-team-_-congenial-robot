package tripleo.elijah_congenial.pipelines.eva;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.lang.i.FormalArgListItem;
import tripleo.elijah.lang.i.OS_NamedElement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputstatement.ReasonedStringListStatement;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.world.i.LivingFunction;
import tripleo.elijah.world.i.LivingRepo;

public class FunctionStatement implements EG_Statement {
	private final IEvaFunctionBase evaFunction;
	private       String           filename;

	public FunctionStatement(final IEvaFunctionBase aEvaFunction) {
		evaFunction = aEvaFunction;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FunctionStatement");
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();
		final var           z  = new ReasonedStringListStatement();

		final String str = "FUNCTION %d %s %s\n".formatted(evaFunction.getCode(),
														   evaFunction.getFunctionName(),
														   ((OS_NamedElement) evaFunction.getFD().getParent()).name());
		z.append(str, "function-statement-header");

		z.append("Instructions \n", "function-statement-header2");
		final EvaFunction gf = (EvaFunction) evaFunction;
		for (Instruction instruction : gf.instructionsList) {
			z.append("\t", "function-statement-instruction-spacer-left");
			z.append("" + instruction, "function-statement-instruction-text");
			z.append("\n", "function-statement-instruction-spacer-right");
		}

		{
			//	EvaFunction.printTables(gf);
			{
				for (FormalArgListItem formalArgListItem : gf.getFD().getArgs()) {
					z.append("ARGUMENT " + formalArgListItem.name() + " " + formalArgListItem.typeName() + "\n", "...fali");
				}
				sb.append("VariableTable \n");
				for (VariableTableEntry variableTableEntry : gf.vte_list) {
					z.append("\t" + variableTableEntry + "\n", "...vte-list-item");
				}
				sb.append("ConstantTable \n");
				for (ConstantTableEntry constantTableEntry : gf.cte_list) {
					z.append("\t" + constantTableEntry + "\n", "...cte-list-item");
				}
				z.append("ProcTable     \n", "...pte-header");
				for (ProcTableEntry procTableEntry : gf.prte_list) {
					z.append("\t" + procTableEntry + "\n", "...prte-list-item");
				}
				z.append("TypeTable     \n", "...tte-header");
				for (TypeTableEntry typeTableEntry : gf.tte_list) {
					z.append("\t" + typeTableEntry + "\n", "...tte-list-item");
				}
				z.append("IdentTable    \n", "...idte-list-header");
				for (IdentTableEntry identTableEntry : gf.idte_list) {
					z.append("\t" + identTableEntry + "\n", "...idte-list-item");
				}
			}
		}

		return sb.toString();
	}

	public @NotNull String getFilename(@NotNull IPipelineAccess pa) {
		// HACK 07/07 register if not registered
		if (filename == null) {
			EvaFunction v    = (EvaFunction) evaFunction;
			int         code = v.getCode();

			final var            ce    = pa.getCompilationEnclosure();
			final var            world = ce.getCompilation().world();
			final LivingFunction funct = world.addFunction(v, LivingRepo.Add.NONE);
			funct.codeRegistration((final EvaFunction ef, final Eventual<Integer> ccb) -> {
				int code1 = ef.getCode();
				assert code1 == 0;
				var cr = ce.getPipelineLogic().dp.codeRegistrar;
				cr.registerFunction1(ef);

				code1 = ef.getCode();
				assert code1 != 0;

				ccb.resolve(code1);
			});

			assert funct.isRegistered();

			funct.listenRegister((Integer code2) -> {
				assert code2.intValue() != 0;

				filename = String.format("F_%d%s", code2, evaFunction.getFunctionName());
			});
		}
		return filename;
	}
}
