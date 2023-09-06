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
import tripleo.elijah.lang.i.ExpressionList;
import tripleo.elijah.lang.i.OS_Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Created on Sep 1, 2005 8:28:55 PM
 *
 * $Id$
 *
 */
public class ListExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.ListExpression {

	public @NotNull SyntaxImpl syntax = new SyntaxImpl();

	ExpressionList contents;

	@Override
	public int getColumn() {
		if (syntax.startToken != null)
			return syntax.startToken.getColumn();
		return 0;
	}

	@Override
	public int getColumnEnd() {
		if (syntax.endToken != null)
			return syntax.endToken.getColumn();
		return 0;
	}

	@Override
	public @Nullable File getFile() {
		if (syntax.startToken != null) {
			String filename = syntax.startToken.getFilename();
			if (filename != null)
				return new File(filename);
		}
		return null;
	}

	// region Syntax

	@Override
	public void setContents(final ExpressionList aList) {
		contents = aList;
	}

	@Override
	public int getLine() {
		if (syntax.startToken != null)
			return syntax.startToken.getLine();
		return 0;
	}

	// endregion

	// region Locatable

	@Override
	public int getLineEnd() {
		if (syntax.endToken != null)
			return syntax.endToken.getLine();
		return 0;
	}

	@Override
	public @Nullable OS_Type getType() {
		return null;
	}

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public void setType(OS_Type deducedExpression) {

	}

	public class SyntaxImpl {
		@NotNull List<Token> commas = new ArrayList<Token>();
		Token endToken;
		Token startToken;

		public void comma(Token t) {
			commas.add(t);
		}

		public void start_and_end(Token startToken, Token endToken) {
			this.startToken = startToken;
			this.endToken   = endToken;
		}
	}

	// endregion

}
