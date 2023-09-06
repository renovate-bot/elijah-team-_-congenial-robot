/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Mar 13, 2019 at 11:04:59 AM
 */
package tripleo.elijah.gen.nodes;

import tripleo.elijah.comp.GenBuffer;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.util.NotImplementedException;

/**
 * To be used with {@link tripleo.elijah.comp.FindBothSourceFiles#CloseCaseChoice(CompilerContext, CloseCaseNode, GenBuffer)}
 * <p>
 * generates <code>break;}</code>
 *
 * @author Tripleo(sb)
 */
public class CloseCaseNode {

//	public CloseCaseNode(CaseHdrNode csn, ChoiceOptions break1) {
//		// TODO Auto-generated constructor stub
//	}

	public CaseChoiceNode hdr_node;

	public CloseCaseNode(final CaseChoiceNode csn, final ChoiceOptions break1) {
		// TODO Auto-generated constructor stub
		hdr_node = csn;
	}

//	public CloseCaseNode(CaseDefaultNode csn2, ChoiceOptions break1) {
//		// TODO Auto-generated constructor stub
//		NotImplementedException.raise();
////		hdr_node=csn2;
//	}

	public CloseCaseNode(final CaseChoiceNode csn2, final ChoiceOptions aBreak, final boolean b) {
		assert b == true; // means default
		NotImplementedException.raise();
	}
}
