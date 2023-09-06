/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

public class VariableReferenceNode {
	private final CompilerContext       _cctx;
	private final VariableReferenceImpl _varref;

	public VariableReferenceNode(final CompilerContext cctx, final VariableReferenceImpl varref) {
		NotImplementedException.raise();
		this._cctx   = cctx;
		this._varref = varref;
	}

	public @Nullable TypeNameNode getType() {
		NotImplementedException.raise();
		return null;
	}

	public @NotNull String genText() {
		NotImplementedException.raise();
		return "vtn"; // TODO hardcoded
	}
}

//
//
//
