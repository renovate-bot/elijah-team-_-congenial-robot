/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.instructions;

/**
 * Created 9/13/20 6:50 AM
 */
public class LabelIA implements InstructionArgument {
	public final Label label;

	public LabelIA(Label label_next) {
		this.label = label_next;
	}
}

//
//
//
