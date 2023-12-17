/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on May 19, 2019 23:47
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

public class FloatExpressionImpl implements tripleo.elijah.lang.i.FloatExpression {

	OS_Type _type;
	float   carrier;
	private final Token n;

	public FloatExpressionImpl(final @NotNull Token n) {
		this.n  = n;
		carrier = Float.parseFloat(n.getText());
	}


	public @NotNull List<FormalArgListItem> getArgs() {
		return null;
	}


	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.FLOAT; // TODO
	}

	@Override
	public @NotNull IExpression getLeft() {
		return this;
	}

	@Override
	public OS_Type getType() {
		return _type;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public void setKind(final @NotNull ExpressionKind aType) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of FloatExpression to " + aType.toString());
	}

	@Override
	public void setLeft(final IExpression aLeft) {
		throw new NotImplementedException(); // TODO
	}

	public void setArgs(final ExpressionList ael) {

	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public String toString() {
		return String.format("FloatExpression (%f)", carrier);
	}

}

//
//
//
