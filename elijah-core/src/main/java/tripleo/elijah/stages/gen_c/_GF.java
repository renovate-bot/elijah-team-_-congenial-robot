package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.EG_CompoundStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;

import java.util.List;

import static tripleo.elijah.stages.gen_c.Generate_Code_For_Method.AOG.GET;

public enum _GF {
	;

	@Contract("null, _ -> fail")
	public static @NotNull EG_Statement forDeduceElement3(final IDeduceElement3 deduceElement3, final @NotNull GenerateC gc) {
		//return deduceElement3.();
		if (deduceElement3 instanceof final @NotNull DeduceElement3_ProcTableEntry de_pte) {
			return forDeduceElement3_ProcTableEntry(de_pte, gc);
		}

		throw new NotImplementedException();
	}

	private static @NotNull EG_Statement forDeduceElement3_ProcTableEntry(@NotNull final DeduceElement3_ProcTableEntry de_pte, final @NotNull GenerateC gc) {
		final EG_SingleStatement beginning;
		final EG_SingleStatement ending;
		final EG_Statement       middle;
		final boolean            indent = false;
		final EX_Explanation     explanation;


		final ProcTableEntry pte = de_pte.getTablePrincipal();

		final BaseEvaFunction gf          = de_pte.getGeneratedFunction();
		final Instruction     instruction = de_pte.getInstruction();

		final StringBuilder sb = XXX_YYY.dispatch(pte, new XXX_YYY() {
			@Override
			public @NotNull StringBuilder itsABoy(final IExpression expression) {
				final IdentExpression ptex = (IdentExpression) expression;
				final String          text = ptex.getText();

				@Nullable final InstructionArgument xx = gf.vte_lookup(text);
				assert xx != null;

				final String       realTargetName = gc.getRealTargetName((IntegerIA) xx, GET);
				final List<String> sl3            = gc.getArgumentStrings(() -> new InstructionFixedList(instruction));

				final StringBuilder sb = new StringBuilder();
				sb.append(Emit.emit("/*424*/"));
				sb.append(realTargetName);
				sb.append('(');
				sb.append(Helpers.String_join(", ", sl3));
				sb.append(");");

				return sb;
			}

			@Override
			public @NotNull StringBuilder itsAGirl(final InstructionArgument expression_num) {
				final IdentIA identIA = (IdentIA) expression_num;

				final CReference reference = new CReference(gc.repo(), gc._ce());
				reference.getIdentIAPath(identIA, GET, null);
				final List<String> sl3 = gc.getArgumentStrings(() -> new InstructionFixedList(instruction));
				reference.args(sl3);
				final @NotNull String path = reference.build();

				final StringBuilder sb = new StringBuilder();
				sb.append(Emit.emit("/*427-1*/"));
				sb.append(path);
				sb.append(";");

				return sb;
			}
		});

		beginning   = new EG_SingleStatement("", EX_Explanation.withMessage("forDeduceElement3_ProcTableEntry >> beginning"));
		ending      = new EG_SingleStatement("", EX_Explanation.withMessage("forDeduceElement3_ProcTableEntry >> ending"));
		explanation = new EX_ProcTableEntryExplanation(de_pte);
		middle      = new EG_SingleStatement(sb.toString(), explanation);

		final EG_CompoundStatement stmt = new EG_CompoundStatement(beginning, ending, middle, indent, explanation);
		//new EX_TableEntryExplanation();
		return stmt;
	}

	interface XXX_YYY {
		static StringBuilder dispatch(@NotNull final ProcTableEntry pte, final @NotNull XXX_YYY xy) {
			if (pte.expression_num == null) {
				return xy.itsABoy(pte.__debug_expression);
			} else {
				return xy.itsAGirl(pte.expression_num);
			}
		}

		StringBuilder itsABoy(IExpression expression);

		StringBuilder itsAGirl(InstructionArgument expreesion_num);
	}
}
