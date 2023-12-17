/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci.i;

import antlr.Token;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CiIndexingStatement;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.ci.LibraryStatementPart;

import java.util.List;

public interface CompilerInstructions {
	void add(GenerateStatement generateStatement);

	void add(LibraryStatementPart libraryStatementPart);

	@Nullable String genLang();

	String getFilename();

	String getName();

	void setFilename(String filename);

	CiIndexingStatement indexingStatement();

	void setName(String name);

	void setName(Token name);

	List<LibraryStatementPart> lsps();
}
