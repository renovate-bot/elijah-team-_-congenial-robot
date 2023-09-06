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
package tripleo.elijah.comp;

import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.FactorialR;

import java.io.IOException;

/**
 * @author tripleoacer
 *
 */
public enum FindBothSourceFilesMain {
	;

	public static void main(final String[] args) {
		final FindBothSourceFiles f=new FindBothSourceFiles(/*"xx"*/);
		try {
			f.compilerShouldFindBothParseFiles();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final GenBuffer gbn = new GenBuffer();
		final CompilerContext cctx = new CompilerContext("fact.elijah");
		new FactorialR().factorial_r(cctx , gbn);
		try {
			gbn.writeBuffers();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

//
//
//
