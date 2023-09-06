package tripleo.vendor.mal;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import tripleo.vendor.mal.types.MalList;
import tripleo.vendor.mal.types.MalVal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class printer {

	public static @NotNull String join(final @NotNull Map<String, MalVal> value,
									   final String delim, final Boolean print_readably) {
		final ArrayList<String> strs = new ArrayList<String>();
		for (final Map.Entry<String, MalVal> entry : value.entrySet()) {
			if (entry.getKey().length() > 0 &&
					entry.getKey().charAt(0) == '\u029e') {
				strs.add(":" + entry.getKey().substring(1));
			} else if (print_readably) {
				strs.add("\"" + entry.getKey() + "\"");
			} else {
				strs.add(entry.getKey());
			}
			strs.add(entry.getValue().toString(print_readably));
		}
		return Joiner.on(" ").join(strs);
	}

	public static String _pr_str(final @NotNull MalVal mv,
								 final Boolean print_readably) {
		return mv.toString(print_readably);
	}

	public static @NotNull String _pr_str_args(final @NotNull MalList args,
											   final @NotNull String sep, final Boolean print_readably) {
		return join(args.getList(), sep, print_readably);
	}

	public static @NotNull String join(final @NotNull List<MalVal> value,
									   final @NotNull String delim, final Boolean print_readably) {
		final ArrayList<String> strs = new ArrayList<String>();
		for (final MalVal mv : value) {
			strs.add(mv.toString(print_readably));
		}
		return Joiner.on(delim).join(strs);
	}

	public static String escapeString(final String value) {
		return StringEscapeUtils.escapeJava(value);
	}
}
