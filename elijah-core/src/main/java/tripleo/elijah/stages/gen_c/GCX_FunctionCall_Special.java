package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.deduce.DeduceElement;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.Instruction;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.util.Helpers;

import java.util.List;
import java.util.Map;

public class GCX_FunctionCall_Special {
	private final GenerateC       gc;
	//private final BaseEvaFunction gf;
	private final Instruction     instruction;
	private final ProcTableEntry  pte;

	public GCX_FunctionCall_Special(final ProcTableEntry aPte, final @NotNull WhyNotGarish_BaseFunction aGf, final GenerateC aGc, final Instruction aInstruction) {
		pte         = aPte;
		//gf          = aGf.cheat();
		gc          = aGc;
		instruction = aInstruction;

		yf = aGf;
	}
	private final @NotNull WhyNotGarish_BaseFunction yf;

	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		final Map<OS_Element, DeduceElement> e = yf.pt_elements();

		CReference reference = null;
		if (pte.expression_num == null) {
			final int                           y    = 2;
			final IdentExpression               ptex = (IdentExpression) pte.__debug_expression;
			final String                        text = ptex.getText();
			@Nullable final InstructionArgument xx   = yf.pt_vte_lookup(text);
			final String                        xxx;

			if (xx != null) {
				final ZoneVTE zone_vte = yf.zoneHelper((IntegerIA) xx);
				xxx = zone_vte.getRealTargetName();
			} else {
				xxx = Emit.emit("/*5052*/") + text;

				gc.elLog().err("xxx is null " + text);
			}

			sb.append(Emit.emit("/*460*/") + xxx);
		} else {
			final IdentIA ia2 = (IdentIA) pte.expression_num;
			reference = new CReference(gc.repo(), gc._ce());
			reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
			final List<String> sl3;
			{
				//sl3 = gc.getArgumentStrings(gf, instruction);
				sl3 =  yf.getArgumentStrings(instruction).getLeft();
			}
			reference.args(sl3);
			final String path = reference.build();
			sb.append(Emit.emit("/*463*/") + path);
		}
		if (reference == null) {
			sb.append('(');

			final Pair<List<String>, List<WhyNotGarish_BaseFunction.ArgumentString>> argumentStrings = yf.getArgumentStrings(instruction);
			final List<String>                                                       sl3             = argumentStrings.getLeft();
			sb.append(Helpers.String_join(", ", sl3));

			sb.append(");");
		}

		return sb.toString();
	}
}
