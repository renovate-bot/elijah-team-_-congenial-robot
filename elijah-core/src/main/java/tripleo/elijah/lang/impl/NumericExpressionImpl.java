/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 1, 2005 8:16:32 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.io.File;
import java.util.List;

public class NumericExpressionImpl implements tripleo.elijah.lang.i.NumericExpression {

	OS_Type _type;
	int     carrier;
	private Token n;

	public NumericExpressionImpl(final int aCarrier) {
		carrier = aCarrier;
	}

	public NumericExpressionImpl(final @NotNull Token n) {
		this.n  = n;
		carrier = Integer.parseInt(n.getText());
	}


	public @NotNull List<FormalArgListItem> getArgs() {
		return null;
	}


	@Override
	public int getColumn() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	@Override
	public int getColumnEnd() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	// region kind

	@Override
	public @Nullable File getFile() {
		if (token() != null) {
			String filename = token().getFilename();
			if (filename != null)
				return new File(filename);
		}
		return null;
	}

	@Override // IExpression
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.NUMERIC; // TODO
	}

	// endregion

	// region representation

	@Override
	public @NotNull IExpression getLeft() {
		return this;
	}

	@Override
	public int getLineEnd() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	// endregion

	@Override
	public int getLine() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	// region type

	@Override // IExpression
	public OS_Type getType() {
		return _type;
	}

	@Override
	public int getValue() {
		return carrier;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	// endregion

	@Override
	public String repr_() {
		return toString();
	}

	// region Locatable

	@Override // IExpression
	public void setKind(final @NotNull ExpressionKind aType) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of NumericExpression to " + aType.toString());
	}

	@Override
	public void setLeft(final IExpression aLeft) {
		throw new NotImplementedException(); // TODO
	}

	@Override // IExpression
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public String toString() {
		return String.format("NumericExpression (%d)", carrier);
	}

	private Token token() {
		return n;
	}

	public void setArgs(final ExpressionList ael) {

	}

	// endregion
}

//
//
//
