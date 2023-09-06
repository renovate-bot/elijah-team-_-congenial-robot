/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.util;

import org.jetbrains.annotations.NotNull;
import tripleo.util.buffer.Buffer;
import tripleo.util.buffer.DefaultBuffer;
import tripleo.util.buffer.TextBuffer;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 4/26/21 11:36 PM
 */
public class BufferTabbedOutputStream {

	int tabwidth = 0;
	@NotNull TextBuffer   b       = new DefaultBuffer("");
	@NotNull List<AttStr> las     = new ArrayList<>();
	private  boolean      _closed = false;

	public void close() {
		if (!is_connected())
			throw new IllegalStateException("is_connected assertion failed; closing twice");

//		b = null;
		_closed = true;
	}

	public boolean is_connected() {
		return !_closed;
	}

	public void dec_tabs() {
		tabwidth--;
	}

	public void flush() {
//		b.flush();
	}

	public Buffer getBuffer() {
		return b;
	}

	public void incr_tabs() {
		tabwidth++;
	}

	public void put_newline() {
		doIndent();
	}

	private boolean do_tabs = false;

	public void put_string(final String s) {
		if (!is_connected())
			throw new IllegalStateException("is_connected assertion failed");

//		if (do_tabs)
//			doIndent();
		b.append(s);
//		do_tabs = false;

		las.add(new AttStr(String.format("%s", s), List_of()));
	}

	public void put_string_ln(final String s) {
		if (!is_connected())
			throw new IllegalStateException("is_connected assertion failed");

		String q = null;

		if (do_tabs)
			q = doIndent();
		b.append(s);
		b.append("\n");
//		doIndent();
		do_tabs = true;

		las.add(new AttStr(String.format("%s%s\n", q == null ? "" : q, s), List_of()));
	}

	public void put_string_ln_no_tabs(final String s) {
		if (!is_connected())
			throw new IllegalStateException("is_connected assertion failed");

		b.append(s);
		b.append("\n");
//		do_tabs = true;

		las.add(new AttStr(String.format("%s\n", s), List_of()));
	}

	public void quote_string(final String s) {
		if (!is_connected())
			throw new IllegalStateException("is_connected assertion failed");

		b.append("\"");
		b.append(s);
		b.append("\"");

		las.add(new AttStr(String.format("\"%s\"", s), List_of()));
	}

	public int t() {
		return tabwidth;
	}

	@NotNull String doIndent() {
		var sb = new StringBuilder();

		for (int i = 0; i < tabwidth; i++)
			 b.append("\t");

		for (int i = 0; i < tabwidth; i++)
			 sb.append("\t");
		return sb.toString();
	}

	record AttStr(String s, List<Att> atts) {
	}

	interface Att {
	}

}

//
//
//
