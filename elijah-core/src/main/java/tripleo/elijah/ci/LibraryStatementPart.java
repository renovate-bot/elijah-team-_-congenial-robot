/*
 *   -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 *Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 *The contents of this library are released under the LGPL licence v3,
 *the GNU Lesser General Public License text was downloaded from
 *http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci;

import antlr.Token;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.lang.i.IExpression;

public interface LibraryStatementPart {
	void addDirective(Token token, IExpression iExpression);

	// TODO PossiblyQuotedString + getFileName + filenamepolicy.apply...
	String getDirName();

	CompilerInstructions getInstructions();

	void setDirName(Token dirName);

	String getName();

	void setInstructions(CompilerInstructions instructions);

	void setName(Token i1);
}
