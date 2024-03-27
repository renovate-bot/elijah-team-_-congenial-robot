/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_durable_congenial.comp;

import tripleo.elijah_durable_congenial.comp.internal.CB_Output;
import tripleo.elijah_durable_congenial.comp.internal.CR_State;

/**
 * Created 8/21/21 10:10 PM
 */
public interface PipelineMember {
	void run(final CR_State aSt, final CB_Output aOutput) throws Exception;
}

//
//
//
