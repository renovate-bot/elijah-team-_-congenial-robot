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
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.ImportStatement;
import tripleo.elijah.lang.i.OS_Package;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/6/21 8:31 AM
 */
public class SlirSourceNode {
	private final List<SlirElement> nodes = new ArrayList<SlirElement>();
	private final SlirSourceFile    sourceFile;

	public SlirSourceNode(final SlirSourceFile aSourceFile) {
		sourceFile = aSourceFile;
	}

	public void add(final SlirElement aNode) {
		nodes.add(aNode);
	}

	public @NotNull SlirClass addClass(final String aClassName, final ClassStatement aClassStatement) {
		final SlirClass slirClass = new SlirClass(sourceFile, aClassName, aClassStatement);
		add(slirClass);
		return slirClass;
	}

	public @NotNull SlirImportNode addImport(final ImportStatement aImportStatement) {
		final SlirImportNode slirImportNode = new SlirImportNode(this, aImportStatement);
		add(slirImportNode);
		return slirImportNode;
	}

	public @NotNull SlirPackageNode addPackage(final OS_Package aPackageStatement) {
		final SlirPackageNode slirPackageNode = new SlirPackageNode(this, aPackageStatement);
		add(slirPackageNode);
		return slirPackageNode;
	}

	public SlirSourceFile sourceFile() {
		return sourceFile;
	}

	public void useClass(final SlirClass aClass) {
		add(aClass);
	}

	public void useFunction(final SlirFunctionNode aFunctionNode) {
		add(aFunctionNode);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
