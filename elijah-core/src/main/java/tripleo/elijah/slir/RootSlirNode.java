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
import tripleo.elijah.comp.i.Compilation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/6/21 8:23 AM
 */
public class RootSlirNode {
	private final Compilation          compilation;
	private final List<SlirSourceNode> sourceNodes = new ArrayList<>();

	public RootSlirNode(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	public @NotNull SlirSourceNode newSourceNode(final SlirSourceFile aSourceFile) {
		final SlirSourceNode sourceNode = new SlirSourceNode(aSourceFile);
		sourceNodes.add(sourceNode);
		return sourceNode;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
