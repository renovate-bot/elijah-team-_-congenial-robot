package tripleo.elijah.lang.i;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.MatchContext;
import tripleo.elijah.lang.xlang.GenerateFunctions3;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.InstructionName;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.Label;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public interface MatchConditional extends OS_Element, StatementItem, FunctionItem {
	void expr(IExpression expr);

	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	IExpression getExpr();

	void setParent(OS_Element aParent);

	List<MC1> getParts();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void postConstruct();

	void setContext(MatchContext ctx);

	MatchConditionalPart2 normal();

	//
	//
	//
	MatchConditional.MatchArm_TypeMatch typeMatch();

	MatchConditionalPart3 valNormal();

	interface MC1 extends OS_Element, Documentable {
		void add(FunctionItem aItem);

		@Override
		Context getContext();

		@Override
		default void visitGen(@NotNull ElElementVisitor visit) {
			visit.visitMC1(this);
		}

		@Override
		default void serializeTo(SmallWriter sw) {

		}

		Iterable<? extends FunctionItem> getItems();
	}

	interface MatchArm_TypeMatch extends MC1 {
		IdentExpression getIdent();

		void ident(IdentExpression aI1);

		void scope(Scope3 aSco);

		void setTypeName(TypeName aTn);

		TypeName getTypeName();
	}

	interface MatchConditionalPart3 extends MatchConditional.MC1 {
		@Override
		void add(FunctionItem aItem);

		@Override
		void addDocString(Token text);

		void expr(IdentExpression expr);

		@Override
		@NotNull Context getContext();

		@Override
		@NotNull List<FunctionItem> getItems();

		@Override
		@NotNull OS_Element getParent();

		@Override
		void serializeTo(SmallWriter sw);

		void scope(Scope3 sco);

		void generate_match_conditional(GenerateFunctions aGenerateFunctions, final Object aO, final int aI, final Object aObject, final Object aO1, final GenerateFunctions3 aGenerateFunctions3);
	}

	interface MatchConditionalPart2 extends MatchConditional.MC1 {
		@Override
		void add(FunctionItem aItem);

		@Override
		void addDocString(Token text);

		void expr(IExpression expr);

		@Override
		@NotNull Context getContext();

		@Override
		@NotNull List<FunctionItem> getItems();

		IExpression getMatchingExpression();

		@Override
		@NotNull OS_Element getParent();

		@Override
		void serializeTo(SmallWriter sw);

		void scope(Scope3 sco);

		default void generate_match_conditional(@NotNull BaseEvaFunction gf,
												Context cctx,
												InstructionArgument i,
												@NotNull Label label_next,
												@NotNull Label label_end,
												final GenerateFunctions3 generateFunctions) {
			final IExpression id = getMatchingExpression();

			final int begin0 = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);

			final InstructionArgument i2 = generateFunctions.simplify_expression(id, gf, cctx);
			generateFunctions.add_i(gf, InstructionName.JNE, List_of(i, i2, label_next), cctx);
			final Context context = getContext();

			for (final FunctionItem item : getItems()) {
				generateFunctions.generate_item(item, gf, context);
			}

			generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_end), context);
			generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin0, gf)), cctx);
			gf.place(label_next);
	//							label_next = gf.addLabel();
		}
	}
}
