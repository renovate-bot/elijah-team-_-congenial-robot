/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.LoopContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;

public class LoopImpl implements tripleo.elijah.lang.i.Loop {

	private final Attached   _a = new AttachedImpl();
	private final OS_Element parent;
	IdentExpression iterName;
	private IExpression expr;
	private Scope3      scope3;
	private IExpression topart, frompart;
	/**
	 * @category type
	 */
	private LoopTypes type;

	@Deprecated
	public LoopImpl(final OS_Element aParent) {
		// document assumption
		if (!(aParent instanceof FunctionDef) && !(aParent instanceof Loop))
			SimplePrintLoggerToRemoveSoon.println_out_2("parent is not FunctionDef or Loop");
		parent = aParent;
	}

	public LoopImpl(final OS_Element aParent, final Context ctx) {
		// document assumption
		if (!(aParent instanceof FunctionDef) && !(aParent instanceof Loop))
			SimplePrintLoggerToRemoveSoon.println_out_2("parent is not FunctionDef or Loop");
		parent = aParent;
		_a.setContext(new LoopContext(ctx, this));
	}

	@Override
	public void expr(final IExpression aExpr) {
		expr = aExpr;
	}

	@Override
	public void frompart(final IExpression aExpr) {
		frompart = aExpr;
	}

	@Override
	public Context getContext() {
		return _a.getContext();
	}

	@Override
	public @NotNull IExpression getFromPart() {
		return frompart;
	}

	@Override
	public @NotNull List<StatementItem> getItems() {
		List<StatementItem> collection = new ArrayList<StatementItem>();
		for (OS_Element element : scope3.items()) {
			if (element instanceof FunctionItem)
				collection.add((StatementItem) element);
		}
		return collection;
//		return items;
	}

	@Override
	public @NotNull String getIterName() {
		return iterName.getText();
	}

	@Override
	public IdentExpression getIterNameToken() {
		return iterName;
	}

	@Override
	public LoopTypes getType() {
		return type;
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public @NotNull IExpression getToPart() {
		return topart;
	}

	@Override
	public void iterName(final IdentExpression s) {
//		assert type == ITER_TYPE;
		iterName = s;
	}

	@Override
	public void setContext(final LoopContext ctx) {
		_a.setContext(ctx);
	}

	@Override
	public void scope(Scope3 sco) {
		scope3 = sco;
	}

	@Override
	public void topart(final IExpression aExpr) {
		topart = aExpr;
	}

	@Override
	public void type(final LoopTypes aType) {
		type = aType;
	}

	@Override // OS_Element
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitLoop(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	public IExpression getExpr() {
		return expr;
	}

	//	@Override
	public void visitGen1(@NotNull ElElementVisitor visit) {
		visit.visitLoop(this);
	}

}

//
//
//
