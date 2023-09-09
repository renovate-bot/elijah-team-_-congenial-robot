package tripleo.vendor.mal;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.vendor.mal.types.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reader {
	public static @NotNull ArrayList<String> tokenize(final @NotNull String str) {
		final ArrayList<String> tokens  = new ArrayList<String>();
		final Pattern           pattern = Pattern.compile("[\\s ,]*(~@|[\\[\\]{}()'`~@]|\"(?:[\\\\].|[^\\\\\"])*\"?|;.*|[^\\s \\[\\]{}()'\"`~@,;]*)");
		final Matcher           matcher = pattern.matcher(str);
		while (matcher.find()) {
			final String token = matcher.group(1);
			if (token != null &&
                    !token.isEmpty() &&
					!(token.charAt(0) == ';')) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	public static MalVal read_atom(final @NotNull Reader rdr)
	throws ParseError {
		final String  token   = rdr.next();
		final Pattern pattern = Pattern.compile("(^-?[0-9]+$)|(^-?[0-9][0-9.]*$)|(^nil$)|(^true$)|(^false$)|^\"((?:[\\\\].|[^\\\\\"])*)\"$|^\"(.*)$|:(.*)|(^[^\"]*$)");
		final Matcher matcher = pattern.matcher(token);
		if (!matcher.find()) {
			throw new ParseError("unrecognized token '" + token + "'");
		}
		if (matcher.group(1) != null) {
			return new MalInteger(Integer.parseInt(matcher.group(1)));
		} else if (matcher.group(3) != null) {
			return types.Nil;
		} else if (matcher.group(4) != null) {
			return types.True;
		} else if (matcher.group(5) != null) {
			return types.False;
		} else if (matcher.group(6) != null) {
			return new MalString(StringEscapeUtils.unescapeJava(matcher.group(6)));
		} else if (matcher.group(7) != null) {
			throw new ParseError("expected '\"', got EOF");
		} else if (matcher.group(8) != null) {
			return new MalString("\u029e" + matcher.group(8));
		} else if (matcher.group(9) != null) {
			return new MalSymbol(matcher.group(9));
		} else {
			throw new ParseError("unrecognized '" + matcher.group(0) + "'");
		}
	}

	public static MalVal read_list(final @NotNull Reader rdr, final @NotNull MalList lst, final char start, final char end)
	throws MalContinue, ParseError {
		String token = rdr.next();
		if (token.charAt(0) != start) {
			throw new ParseError("expected '" + start + "'");
		}

		while ((token = rdr.peek()) != null && token.charAt(0) != end) {
			lst.conj_BANG(read_form(rdr));
		}

		if (token == null) {
			throw new ParseError("expected '" + end + "', got EOF");
		}
		rdr.next();

		return lst;
	}

	public static @NotNull MalVal read_hash_map(final @NotNull Reader rdr)
	throws MalContinue, ParseError {
		final MalList lst = (MalList) read_list(rdr, new MalList(), '{', '}');
		return new MalHashMap(lst);
	}

	public static MalVal read_form(final @NotNull Reader rdr)
	throws MalContinue, ParseError {
		final String token = rdr.peek();
		if (token == null) {
			throw new MalContinue();
		}
		final MalVal form;

		switch (token.charAt(0)) {
		case '\'':
			rdr.next();
			return new MalList(new MalSymbol("quote"),
							   read_form(rdr));
		case '`':
			rdr.next();
			return new MalList(new MalSymbol("quasiquote"),
							   read_form(rdr));
		case '~':
			if (token.equals("~")) {
				rdr.next();
				return new MalList(new MalSymbol("unquote"),
								   read_form(rdr));
			} else {
				rdr.next();
				return new MalList(new MalSymbol("splice-unquote"),
								   read_form(rdr));
			}
		case '^':
			rdr.next();
			final MalVal meta = read_form(rdr);
			return new MalList(new MalSymbol("with-meta"),
							   read_form(rdr),
							   meta);
		case '@':
			rdr.next();
			return new MalList(new MalSymbol("deref"),
							   read_form(rdr));
		case '(':
			form = read_list(rdr, new MalList(), '(', ')');
			break;
		case ')':
			throw new ParseError("unexpected ')'");
		case '[':
			form = read_list(rdr, new MalVector(), '[', ']');
			break;
		case ']':
			throw new ParseError("unexpected ']'");
		case '{':
			form = read_hash_map(rdr);
			break;
		case '}':
			throw new ParseError("unexpected '}'");
		default:
			form = read_atom(rdr);
		}
		return form;
	}

	public static MalVal read_str(final @NotNull String str)
	throws MalContinue, ParseError {
		return read_form(new Reader(tokenize(str)));
	}

	public static class ParseError extends MalThrowable {
		public ParseError(final String msg) {
			super(msg);
		}
	}

	public static class Reader {
		ArrayList<String> tokens;
		Integer           position;

		public Reader(final ArrayList<String> t) {
			tokens   = t;
			position = 0;
		}

		public @Nullable String peek() {
			if (position >= tokens.size()) {
				return null;
			} else {
				return tokens.get(position);
			}
		}

		public String next() {
			return tokens.get(position++);
		}
	}
}
