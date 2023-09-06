/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 *
 */
package tripleo.elijah.gen.nodes;

import tripleo.elijah.gen.CompilerContext;

/**
 * @author Tripleo(sb)
 *
 */
public class CloseTmpCtxNode {

	CompilerContext cctx;
	TmpSSACtxNode   tmpNode;

	public CloseTmpCtxNode(final CompilerContext cctx, final TmpSSACtxNode tmpNode) {
		this.cctx    = cctx;
		this.tmpNode = tmpNode;
	}
}
