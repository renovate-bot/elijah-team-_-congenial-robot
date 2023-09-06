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
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/6/21 8:57 AM
 */
public class SlirClass implements SlirElement {
	private final           String            className;
	private final           ClassStatement    classStatement;
	private final           List<SlirElement> nodes = new ArrayList<SlirElement>();
	private final @Nullable SlirElement       parent;
	private final @Nullable SlirSourceFile    sourceFile;
	private final           List<SlirElement> used  = new ArrayList<SlirElement>();

	public SlirClass(final SlirElement aParent, final String aClassName, final ClassStatement aClassStatement) {
		parent         = aParent;
		sourceFile     = null; // TODO for now
		className      = aClassName;
		classStatement = aClassStatement;
	}

	public SlirClass(final @NotNull SlirNamespaceNode aParent, final String aName, final ClassStatement aClassStatement) {
		parent         = aParent;
		sourceFile     = aParent.sourceFile();
		className      = aName;
		classStatement = aClassStatement;
	}

	public SlirClass(final SlirSourceFile aSourceFile, final String aClassName, final ClassStatement aClassStatement) {
		parent         = null;
		sourceFile     = aSourceFile;
		className      = aClassName;
		classStatement = aClassStatement;
	}

	public @NotNull SlirFunctionNode addFunction(final String aFunctionName, final FunctionDef aFunctionDef) {
		final SlirFunctionNode sfn = new SlirFunctionNode(this, aFunctionName, aFunctionDef);
		nodes.add(sfn);
		return sfn;
	}

	public void annotate(final SlirAnnotations aMain) {
		// TODO implement me
	}

	@Override
	public OS_Element element() {
		return classStatement;
	}

	public void markUsed(final SlirElement aSlirElement) {
		used.add(aSlirElement);
	}

	public void markUsed(final String aName, final SlirPos aPartOfSpeech) {
		used.add(new SlirAbstractElement(aName, aPartOfSpeech));
	}

	@Override
	public String name() {
		return className;
	}

	@Override
	public @NotNull SlirPos partOfSpeech() {
		return SlirPos.CLASS;
	}

	public void setSuperClass(final SlirClass aClass) {
		// TODO implement me
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
