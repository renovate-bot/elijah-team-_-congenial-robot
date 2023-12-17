package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.BaseTableEntry;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.logging.ElLog;

import java.util.List;

class FnCallArgs_Statement2 implements EG_Statement {
	private final GenerateC                    generateC;
	private final ProcTableEntry               pte;
	private final GenerateC.GetAssignmentValue getAssignmentValue;
	private final Instruction                  inst;
	private final BaseEvaFunction              gf;
	private final ElLog                        LOG;

	public FnCallArgs_Statement2(final GenerateC aGenerateC, final BaseEvaFunction aGf, final ElLog aLOG, final Instruction aInst, final ProcTableEntry aPte, final GenerateC.GetAssignmentValue aGetAssignmentValue) {
		generateC          = aGenerateC;
		gf                 = aGf;
		LOG                = aLOG;
		inst               = aInst;
		pte                = aPte;
		getAssignmentValue = aGetAssignmentValue;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FnCallArgs_Statement2");
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		if (pte.expression_num instanceof IntegerIA) {
		} else if (pte.expression_num instanceof IdentIA ia2) {
			final IdentTableEntry idte = ia2.getEntry();
			if (idte.getStatus() == BaseTableEntry.Status.UNCHECKED) {

				//final DeduceTypes2                   deduceTypes2 = pte.getDeduceElement3().deduceTypes2();
				//final BaseEvaFunction                gf1          = ia2.gf;

				final DeduceTypes2    deduceTypes2 = idte._deduceTypes2();
				final BaseEvaFunction gf1          = idte._generatedFunction();


				final DeduceElement3_IdentTableEntry de3_idte = idte.getDeduceElement3(deduceTypes2, gf1);
				de3_idte.sneakResolve();

			}

			if (idte.getStatus() == BaseTableEntry.Status.KNOWN) {
				final CReference         reference          = new CReference(generateC.get_repo(), generateC._ce());
				final FunctionInvocation functionInvocation = pte.getFunctionInvocation();
				if (functionInvocation == null || functionInvocation.getFunction() == LangGlobals.defaultVirtualCtor) {
					reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
					final List<String> sll = getAssignmentValue.getAssignmentValueArgs(inst, gf, generateC.elLog()).stringList();
					reference.args(sll);
					String path = reference.build();
					sb.append(Emit.emit("/*829*/") + path);
				} else {
					final BaseEvaFunction pte_generated = functionInvocation.getEva();
					if (idte.resolvedType() == null && pte_generated != null)
						idte.resolveTypeToClass(pte_generated);
					reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
					final List<String> sll = getAssignmentValue.getAssignmentValueArgs(inst, gf, generateC.elLog()).stringList();
					reference.args(sll);
					String path = reference.build();
					sb.append(Emit.emit("/*827*/") + path);
				}
			} else {
				ZonePath zone_path = generateC.get_zone().getPath(ia2);

				//08/13 System.out.println("763 " + zone_path);

				final String path = gf.getIdentIAPathNormal(ia2);
				sb.append(Emit.emit("/*828*/") + String.format("%s is UNKNOWN", path));
			}
		}

		return sb.toString();
	}
}
