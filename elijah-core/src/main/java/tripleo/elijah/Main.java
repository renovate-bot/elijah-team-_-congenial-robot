/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Main {
	;

	public static void main(final String[] args) throws Exception {
		final Compilation  comp = new CompilationImpl(new StdErrSink(), new IO());
		final List<String> ls1  = new ArrayList<String>();

		ls1.addAll(Arrays.asList(args));

		comp.feedCmdLine(ls1);
	}

}

//
//
//
