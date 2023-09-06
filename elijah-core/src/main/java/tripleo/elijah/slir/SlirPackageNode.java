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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Package;

/**
 * Created 11/10/21 2:17 AM
 */
public class SlirPackageNode implements SlirElement {
	private final OS_Package     packageStatement;
	private final SlirSourceNode slirSourceNode;

	public SlirPackageNode(final SlirSourceNode aSlirSourceNode, final OS_Package aPackageStatement) {
		slirSourceNode   = aSlirSourceNode;
		packageStatement = aPackageStatement;
	}

	@Override
	public @Nullable OS_Element element() {
		return null; //packageStatement; // TODO OS_Package is not an Element!!
	}

	@Override
	public String name() {
		return packageStatement.getName();
	}

	@Override
	public @NotNull SlirPos partOfSpeech() {
		return SlirPos.PACKAGE;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
