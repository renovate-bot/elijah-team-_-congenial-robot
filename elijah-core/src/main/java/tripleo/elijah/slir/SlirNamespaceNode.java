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
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/6/21 8:38 AM
 */
public class SlirNamespaceNode implements SlirElement {
	private final           String             namespaceName;
	private final           NamespaceStatement namespaceStatement;
	private final @Nullable SlirElement        parent;
	private final @Nullable SlirSourceFile     sourceFile;
	private final @Nullable SlirSourceNode     sourceNode;
	private final           List<SlirElement>  used = new ArrayList<SlirElement>();

	public SlirNamespaceNode(final SlirElement aParent,
							 final String aNamespaceName,
							 final NamespaceStatement aNamespaceStatement) {
		parent             = aParent;
		sourceFile         = null; // aSourceNode.sourceFile(); // TODO for now
		sourceNode         = null; //aSourceNode; // TODO for now
		namespaceName      = aNamespaceName;
		namespaceStatement = aNamespaceStatement;
	}

	public SlirNamespaceNode(final SlirSourceFile aSourceFile,
							 final String aNamespaceName,
							 final NamespaceStatement aNamespaceStatement) {
		parent             = null;
		sourceFile         = aSourceFile;
		sourceNode         = null; // TODO
		namespaceName      = aNamespaceName;
		namespaceStatement = aNamespaceStatement;
	}

	public SlirNamespaceNode(final @NotNull SlirSourceNode aSourceNode,
							 final String aNamespaceName,
							 final NamespaceStatement aNamespaceStatement) {
		parent             = null;
		sourceFile         = aSourceNode.sourceFile();
		sourceNode         = aSourceNode;
		namespaceName      = aNamespaceName;
		namespaceStatement = aNamespaceStatement;
	}

	@Override
	public OS_Element element() {
		return namespaceStatement;
	}

	public void markUsed(final String aName, final SlirPos aPartOfSpeech) {
		used.add(new SlirAbstractElement(aName, aPartOfSpeech));
	}

	@Override
	public String name() {
		return namespaceName;
	}

	@Override
	public @NotNull SlirPos partOfSpeech() {
		return SlirPos.NAMESPACE;
	}

	public SlirSourceFile sourceFile() {
		return sourceFile;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
