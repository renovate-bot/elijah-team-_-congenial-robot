/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.typinf.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created 9/4/21 5:18 PM
 */
public class Regex {

	private Pattern pat;

	public MatchObject search(String aBuf, int aPos) {
		Matcher m = pat.matcher(aBuf);
		if (!m.find(aPos)) ;//return null;
		return new MatchObject(m);
	}

	public MatchObject match(String aBuf, int aPos) {
		final String substring = aBuf.substring(aPos);
		Matcher      m         = pat.matcher(substring);
		if (!m.lookingAt()) return null;
		return new MatchObject(m);
	}

	static class MatchObject {

		private final Matcher matcher;

		public MatchObject(Matcher aMatcher) {
			matcher = aMatcher;
		}

		public int start() {
			return matcher.start();
		}

		public int end() {
			return matcher.end();
		}

		public String getString() {
			return matcher.group(0);
		}
	}

	public static Regex compile(String aS) {
//			Object x = org.python.modules._sre();
		Regex r = new Regex();
		r.pat = Pattern.compile(aS);
		return r;
	}
}

//
//
//
