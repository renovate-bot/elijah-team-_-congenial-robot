/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.contexts.MatchConditionalContext;
import tripleo.elijah.contexts.MatchContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.lang.xlang.GenerateFunctions3;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.stages.instructions.*;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo
 * <p>
 * Created Apr 15, 2020 at 10:11:16 PM
 */
public class MatchConditionalImpl implements MatchConditional, OS_Element, StatementItem, FunctionItem {

	// private final SingleIdentContext _ctx;
	private final List<MC1>    parts = new ArrayList<MC1>();
	private       MatchContext __ctx;
	private       IExpression  expr;
	private       OS_Element   parent;

	public MatchConditionalImpl(final OS_Element parent, final Context parentContext) {
		this.parent = parent;
//		this._ctx = new SingleIdentContext(parentContext, this);
	}

	@Override
	public void expr(final IExpression expr) {
		this.expr = expr;
	}

	@Override
	public Context getContext() {
		return __ctx;
	}

	/**
	 * @category OS_Element
	 */
	@Override
	public OS_Element getParent() {
		return this.parent;
	}

	@Override
	public IExpression getExpr() {
		return expr;
	}

	// region OS_Element

	@Override
	public void setParent(final OS_Element aParent) {
		this.parent = aParent;
	}

	@Override
	public @NotNull List<MatchConditional.MC1> getParts() {
		return parts;
	}

	// endregion

	/**
	 * @category OS_Element
	 */
	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitMatchConditional(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	//
	// EXPR
	//

	@Override
	public void postConstruct() {
	}

	@Override
	public void setContext(final MatchContext ctx) {
		__ctx = ctx;
	}

	@Override
	public @NotNull MatchConditionalPart2 normal() {
		final MatchConditionalPart2 p = new MatchConditionalPart2_();
		parts.add(p);
		return p;
	}

	//
	//
	//
	@Override
	public @NotNull MatchConditional.MatchArm_TypeMatch typeMatch() {
		final MatchArm_TypeMatch_ p = new MatchArm_TypeMatch_();
		parts.add(p);
		return p;
	}

	@Override
	public @NotNull MatchConditionalPart3 valNormal() {
		final MatchConditionalPart3 p = new MatchConditionalPart3_();
		parts.add(p);
		return p;
	}

	public class MatchArm_TypeMatch_ implements MatchArm_TypeMatch {
		private final Context              ___ctx;
		private final MatchConditionalImpl matchConditional;
		private       TypeName             tn;
		private       Scope3               scope3;
		private       IdentExpression      ident;

		public MatchArm_TypeMatch_() {
			___ctx                = new MatchConditionalContext(getParent().getParent().getContext(), this);
			this.matchConditional = MatchConditionalImpl.this;
		}

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
			scope3.addDocString(text);
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public IdentExpression getIdent() {
			return ident;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			return _MC1_Utils.filterFunctionItemsFrom(scope3.items());
		}

		@Override
		public @NotNull OS_Element getParent() {
			return matchConditional;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("24j3");
		}

		@Override
		public TypeName getTypeName() {
			return tn;
		}

		@Override
		public void setTypeName(final TypeName typeName) {
			tn = typeName;
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}

		@Override
		public void ident(final IdentExpression i1) {
			this.ident = i1;
		}

		@NotNull
		public Label generate_match_conditional(final @NotNull BaseEvaFunction gf,
												final @NotNull Context cctx,
												final @NotNull InstructionArgument i,
												final @NotNull Label label_next,
												final @NotNull Label label_end,
												final @NotNull GenerateFunctions3 generateFunctions) {
			final TypeName              tn      = getTypeName();
			final IdentExpression       id      = getIdent();
			final int                   begin0  = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
			final int                   tmp     = generateFunctions.addTempTableEntry(new OS_UserType(tn), id, gf, id); // TODO no context!
			@NotNull VariableTableEntry vte_tmp = gf.getVarTableEntry(tmp);
			final TypeTableEntry        t       = vte_tmp.getType();
			generateFunctions.add_i(gf, InstructionName.IS_A, List_of(i, new IntegerIA(t.getIndex(), gf), /*TODO not*/new LabelIA(label_next)), cctx);
			final Context context = getContext();

			generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("tmp"), new IntegerIA(tmp, gf)), context);
			final int cast_inst = generateFunctions.add_i(gf, InstructionName.CAST_TO, List_of(new IntegerIA(tmp, gf), new IntegerIA(t.getIndex(), gf), i), context);
			vte_tmp.addPotentialType(cast_inst, t); // TODO in the future instructionIndex may be unsigned

			for (final FunctionItem item : getItems()) {
				generateFunctions.generate_item(item, gf, context);
			}

			generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_end), context);
			generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin0, gf)), cctx);
			gf.place(label_next);
			var __ = /*label_next =*/ gf.addLabel();
			return label_next;
		}
	}

	private enum _MC1_Utils {
		;

		@NotNull
		private static List<FunctionItem> filterFunctionItemsFrom(final List<OS_Element> items) {
			final List<FunctionItem> collection2 = items.stream()
					.filter(element -> element instanceof FunctionItem)
					.map(functionItem -> (FunctionItem) functionItem)
					.toList();
			return collection2;
		}
	}

	public class MatchConditionalPart2_ implements MatchConditionalPart2 {
		private final Context     ___ctx;
		private       IExpression matching_expression;
		private       Scope3      scope3;

		public MatchConditionalPart2_() {
			___ctx = new MatchConditionalContext(MatchConditionalImpl.this.getContext(), this);
		}

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
			scope3.addDocString(text);
		}

		@Override
		public void expr(final IExpression expr) {
			this.matching_expression = expr;
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			return _MC1_Utils.filterFunctionItemsFrom(scope3.items());
		}

		@Override
		public IExpression getMatchingExpression() {
			return matching_expression;
		}

		@Override
		public @NotNull OS_Element getParent() {
			return MatchConditionalImpl.this;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("24j3");
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}
	}

	public class MatchConditionalPart3_ implements MatchConditionalPart3 {
		private final           Context              ___ctx     = new MatchConditionalContext(MatchConditionalImpl.this.getContext(), this);
		private final           MatchConditionalImpl matchConditional;
		private final @Nullable List<Token>          docstrings = null;
		private                 IdentExpression      matching_expression;
		private                 Scope3               scope3;

		public MatchConditionalPart3_() {
			matchConditional = MatchConditionalImpl.this;
		}

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
			scope3.addDocString(text);
		}

		@Override
		public void expr(final IdentExpression expr) {
			this.matching_expression = expr;
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			return _MC1_Utils.filterFunctionItemsFrom(scope3.items());
		}

		@Override
		public @NotNull OS_Element getParent() {
			return matchConditional;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("24j3");
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}

		@Override public void generate_match_conditional(final GenerateFunctions aGenerateFunctions, final Object aO, final int aI, final Object aObject, final Object aO1, final GenerateFunctions3 aGenerateFunctions3) {
			aGenerateFunctions3.logErr("Don't know what this is");
		}
	}
}

//
//
//
