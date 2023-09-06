/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.typinf.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * A simple regex-based lexer/tokenizer.
 * <p>
 * See below for an example of usage.
 * <p>
 * Created 9/3/21 10:05 PM
 */
public class Lexer {

	private final HashMap<String, String> group_type;
	//		private final Regex regex;
	private final boolean                 skip_whitespace;
	private final Regex                   re_ws_skip;
	private final List<RegexPart>         regex_parts;
	private       int                     idx;
	private       int                     pos;
	private       String                  buf;

	/**
	 * Create a lexer.
	 *
	 * @param rules           A list of rules. Each rule is a `regex, type`
	 *                        pair, where `regex` is the regular expression used
	 *                        to recognize the token and `type` is the type
	 *                        of the token to return when it's recognized.
	 * @param skip_whitespace If True, whitespace (\s+) will be skipped and not
	 *                        reported by the lexer. Otherwise, you have to
	 *                        specify your rules for whitespace, or it will be
	 *                        flagged as an error.
	 */
	public Lexer(List<List<String>> rules, boolean skip_whitespace) {
		// All the regexes are concatenated into a single one
		// with named groups. Since the group names must be valid
		// Python identifiers, but the token types used by the
		// user are arbitrary strings, we auto-generate the group
		// names and map them to token types.
		//
		idx             = 1;
		regex_parts     = List_of();
		this.group_type = new HashMap<String, String>();

		for (List<String> r : rules) {
			String regex = r.get(0);
			String type  = r.get(1);

			String groupname = String.format("GROUP%s", idx);
//				regex_parts.add(String.format("(?<%s>%s)", groupname, regex));
			regex_parts.add(new RegexPart(groupname, Regex.compile(regex)));
			this.group_type.put(groupname, type);
			idx += 1;
		}

//			this.regex = Regex.compile(Helpers.String_join("|", regex_parts));
		this.skip_whitespace = skip_whitespace;
		this.re_ws_skip      = Regex.compile("\\S");
	}

	/**
	 * Initialize the lexer with a buffer as input.
	 */
	public void input(String aBuf) {
		buf = aBuf;
		pos = 0;
	}

	public Token token() throws LexerError {
		if (this.pos >= this.buf.length())
			return null;
		else {
			if (this.skip_whitespace) {
				Regex.MatchObject m = this.re_ws_skip.search(this.buf, this.pos);

				if (m != null)
					this.pos = m.start();
				else
					return null;
			}

			String            lastgroup = null;
			Regex.MatchObject m         = null;//this.regex.match(this.buf, this.pos);

			for (RegexPart regex_part : regex_parts) {
				lastgroup = regex_part.groupname;
				m         = regex_part.regex.match(buf, pos);
				if (m != null) break;
			}

			if (m != null) {
				String groupname = lastgroup;
//					String groupname = m.matcher.;
				String tok_type = this.group_type.get(groupname);
				Token  tok      = new Token(tok_type, m.getString(), this.pos);

				this.pos += m.end();
				return tok;
			}

			// if we're here, no rule matched
			throw new LexerError(this.pos);
		}
	}

	/**
	 * Returns an iterator to the tokens found in the buffer.
	 *
	 * @return
	 */
	List<Token> tokens() throws LexerError {
		List<Token> r = new ArrayList<Token>();

		while (true) {
			Token tok = this.token();
			if (tok == null) break;
			//	yield tok;
			r.add(tok);
		}

		return r;
	}

}

//
//
//
