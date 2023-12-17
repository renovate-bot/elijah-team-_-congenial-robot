/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.ParserClosure;
import tripleo.elijah.lang.impl.ParserClosureImpl;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.util.TabbedOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Out {

	private static @NotNull TabbedOutputStream getTOSLog() throws FileNotFoundException {
		final SimpleDateFormat sdf      = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		final String           filename = String.format("eljc-%s.out", sdf.format(new Date()));
		return new TabbedOutputStream(new FileOutputStream(filename));
	}

	@Contract(pure = true)
	public static void println(final String s) {
		SimplePrintLoggerToRemoveSoon.println_out_2(s);
	}

	private final boolean do_out;

	private final @NotNull ParserClosure pc;

	public Out(final String fn, final @NotNull Compilation aCompilation, final boolean aDoOut) {
		pc     = new ParserClosureImpl(fn, aCompilation);
		do_out = aDoOut;
	}

	public @NotNull ParserClosure closure() {
		return pc;
	}

	//@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NM_METHOD_NAMING_CONVENTION")
	public void FinishModule() {
/*
		final TabbedOutputStream tos;
		println("** FinishModule");
		try {
*/
//			pc.module.print_osi(tos);
		pc.module().finish();
		//
		if (do_out) {
/*
				tos = getTOSLog();
				tos.put_string_ln(pc.module.getFileName());
				Helpers.printXML(pc.module, tos);
				tos.close();
*/
		}
		//
		//
/*
		} catch (final FileNotFoundException fnfe) {
			println("&& FileNotFoundException");
		} catch (final IOException ioe) {
			println("&& IOException");
		}
*/
	}

	public @NotNull OS_Module module() {
		return pc.module();
	}
}

//
//
//
