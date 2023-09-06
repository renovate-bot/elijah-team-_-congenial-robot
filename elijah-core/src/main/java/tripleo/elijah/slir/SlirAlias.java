/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.slir;

import tripleo.elijah.lang.impl.AliasStatementImpl;

/**
 * Created 11/10/21 3:04 AM
 */
public class SlirAlias {
	private final String             aliasName;
	private final AliasStatementImpl aliasStatement;
	private final SlirElement        parent;

	public SlirAlias(final SlirElement aParent, final String aAliasName, final AliasStatementImpl aAliasStatement) {
		parent         = aParent;
		aliasName      = aAliasName;
		aliasStatement = aAliasStatement;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
