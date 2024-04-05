package tripleo.vendor.mal;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import tripleo.vendor.mal.types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class core {
	public static ImmutableMap<String, MalVal> ns;

	// Local references for convenience
	static MalConstant Nil   = types.Nil;
	static MalConstant True  = types.True;
	static MalConstant False = types.False;


	// Errors/Exceptions
	static @NotNull MalFunction mal_throw = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			throw new MalException(a.nth(0));
		}
	};


	// Scalar functions
	static @NotNull MalFunction nil_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return args.nth(0) == Nil ? True : False;
		}
	};


	static @NotNull MalFunction true_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return args.nth(0) == True ? True : False;
		}
	};

	static @NotNull MalFunction false_Q  = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return args.nth(0) == False ? True : False;
		}
	};
	static @NotNull MalFunction number_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return args.nth(0) instanceof MalInteger ? True : False;
		}
	};
	static @NotNull MalFunction string_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			if (!(args.nth(0) instanceof MalString)) {
				return False;
			}
			final String s = ((MalString) args.nth(0)).getValue();
			if (s.length() != 0 && s.charAt(0) == '\u029e') {
				return False;
			}
			return True;
		}
	};

	static @NotNull MalFunction symbol    = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return new MalSymbol((MalString) args.nth(0));
		}
	};
	static @NotNull MalFunction symbol_Q  = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return args.nth(0) instanceof MalSymbol ? True : False;
		}
	};
	static @NotNull MalFunction keyword   = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			if (args.nth(0) instanceof MalString &&
					(((MalString) args.nth(0)).getValue().charAt(0) == '\u029e')) {
				return args.nth(0);
			} else {
				return new MalString(
						"\u029e" + ((MalString) args.nth(0)).getValue());
			}
		}
	};
	static @NotNull MalFunction keyword_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			if (!(args.nth(0) instanceof MalString)) {
				return False;
			}
			final String s = ((MalString) args.nth(0)).getValue();
			if (s.length() == 0 || s.charAt(0) != '\u029e') {
				return False;
			}
			return True;
		}
	};
	static @NotNull MalFunction fn_Q      = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			if (!(args.nth(0) instanceof MalFunction)) {
				return False;
			}
			return ((MalFunction) args.nth(0)).isMacro() ? False : True;
		}
	};
	static @NotNull MalFunction macro_Q   = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			if (!(args.nth(0) instanceof MalFunction)) {
				return False;
			}
			return ((MalFunction) args.nth(0)).isMacro() ? True : False;
		}
	};


	// String functions
	static @NotNull MalFunction pr_str = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return new MalString(printer._pr_str_args(args, " ", true));
		}
	};

	static @NotNull MalFunction str = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return new MalString(printer._pr_str_args(args, "", false));
		}
	};

	static @NotNull MalFunction prn = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			System.out.println(printer._pr_str_args(args, " ", true));
			return Nil;
		}
	};

	static @NotNull MalFunction println = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			System.out.println(printer._pr_str_args(args, " ", false));
			return Nil;
		}
	};


	static @NotNull MalFunction equal_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			return types._equal_Q(args.nth(0), args.nth(1)) ? True : False;
		}
	};

	static @NotNull MalFunction mal_readline = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			final String prompt = ((MalString) args.nth(0)).getValue();
			try {
				return new MalString(readline.readline(prompt));
			} catch (final IOException e) {
				throw new MalException(new MalString(e.getMessage()));
			} catch (final readline.EOFException e) {
				return Nil;
			}
		}
	};

	static @NotNull MalFunction read_string = new MalFunction() {
		public MalVal apply(final @NotNull MalList args) throws MalThrowable {
			try {
				return reader.read_str(((MalString) args.nth(0)).getValue());
			} catch (final MalContinue c) {
				return types.Nil;
			}
		}
	};

	static @NotNull MalFunction slurp = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList args) throws MalThrowable {
			final String fname = ((MalString) args.nth(0)).getValue();
			try {
				// Scanner drops final newline, so add it back
				return new MalString(
						new Scanner(new File(fname)).useDelimiter("\\Z").next()
								+ "\n");
			} catch (final FileNotFoundException e) {
				throw new MalError(e.getMessage());
			}
		}
	};


	// Number functions
	static @NotNull MalFunction add      = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).add((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction subtract = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).subtract((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction multiply = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).multiply((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction divide   = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).divide((MalInteger) a.nth(1));
		}
	};

	static @NotNull MalFunction lt  = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).lt((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction lte = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).lte((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction gt  = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).gt((MalInteger) a.nth(1));
		}
	};
	static @NotNull MalFunction gte = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalInteger) a.nth(0)).gte((MalInteger) a.nth(1));
		}
	};

	static @NotNull MalFunction time_ms = new MalFunction() {
		public @NotNull MalVal apply(final MalList a) throws MalThrowable {
			return new MalInteger((int) System.currentTimeMillis());
		}
	};


	// List functions
	static @NotNull MalFunction new_list     = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return new MalList(a.value);
		}
	};
	static @NotNull MalFunction list_Q       = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return _list_Q(a.nth(0)) ? True : False;
		}
	};
	// Vector functions
	static @NotNull MalFunction new_vector   = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return new MalVector(a.value);
		}
	};
	static @NotNull MalFunction vector_Q     = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return _vector_Q(a.nth(0)) ? True : False;
		}
	};
	// HashMap functions
	static @NotNull MalFunction new_hash_map = new MalFunction() {
		public @NotNull MalVal apply(final MalList a) throws MalThrowable {
			return new MalHashMap(a);
		}
	};
	static @NotNull MalFunction hash_map_Q   = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return a.nth(0) instanceof MalHashMap ? True : False;
		}
	};
	static @NotNull MalFunction contains_Q   = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final String                  key = ((MalString) a.nth(1)).getValue();
			final MalHashMap              mhm = (MalHashMap) a.nth(0);
			final HashMap<String, MalVal> hm  = (HashMap<String, MalVal>) mhm.value;
			return hm.containsKey(key) ? True : False;
		}
	};
	static @NotNull MalFunction assoc        = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalHashMap              mhm     = (MalHashMap) a.nth(0);
			final HashMap<String, MalVal> hm      = (HashMap<String, MalVal>) mhm.value;
			final MalHashMap              new_mhm = new MalHashMap((Map) hm.clone());
			new_mhm.assoc_BANG(a.slice(1));
			return new_mhm;
		}
	};
	static @NotNull MalFunction dissoc       = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalHashMap              mhm     = (MalHashMap) a.nth(0);
			final HashMap<String, MalVal> hm      = (HashMap<String, MalVal>) mhm.value;
			final MalHashMap              new_mhm = new MalHashMap((Map) hm.clone());
			new_mhm.dissoc_BANG(a.slice(1));
			return new_mhm;
		}
	};
	static @NotNull MalFunction get          = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			if (a.nth(0) == Nil) {
				return Nil;
			} else {
				final String                  key = ((MalString) a.nth(1)).getValue();
				final MalHashMap              mhm = (MalHashMap) a.nth(0);
				final HashMap<String, MalVal> hm  = (HashMap<String, MalVal>) mhm.value;
				if (hm.containsKey(key)) {
					return hm.get(key);
				} else {
					return Nil;
				}
			}
		}
	};
	static @NotNull MalFunction keys         = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalHashMap              mhm     = (MalHashMap) a.nth(0);
			final HashMap<String, MalVal> hm      = (HashMap<String, MalVal>) mhm.value;
			final MalList                 key_lst = new MalList();
			for (final String key : hm.keySet()) {
				key_lst.conj_BANG(new MalString(key));
			}
			return key_lst;
		}
	};
	static @NotNull MalFunction vals         = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalHashMap              mhm = (MalHashMap) a.nth(0);
			final HashMap<String, MalVal> hm  = (HashMap<String, MalVal>) mhm.value;
			//return new ArrayList<MalVal>(((HashMap<String,MalVal>)hm).values());
			final MalList val_lst = new MalList();
			for (final MalVal val : hm.values()) {
				val_lst.conj_BANG(val);
			}
			return val_lst;
		}
	};
	// Sequence functions
	static @NotNull MalFunction sequential_Q = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return a.nth(0) instanceof MalList ? True : False;
		}
	};
	static @NotNull MalFunction count        = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			if (a.nth(0) == Nil) {
				return new MalInteger(0);
			} else {
				return new MalInteger(((MalList) a.nth(0)).size());
			}
		}
	};
	static @NotNull MalFunction empty_Q      = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalVal exp = a.nth(0);
			if (exp == Nil || (exp instanceof MalList &&
					((MalList) exp).size() == 0)) {
				return True;
			} else {
				return False;
			}
		}
	};
	static @NotNull MalFunction cons         = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final List<MalVal> lst = new ArrayList<MalVal>();
			lst.add(a.nth(0));
			lst.addAll(((MalList) a.nth(1)).getList());
			return new MalList(lst);
		}
	};
	static @NotNull MalFunction concat       = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			if (a.size() == 0) {
				return new MalList();
			}
			final List<MalVal> lst = new ArrayList<MalVal>();
			lst.addAll(((MalList) a.nth(0)).value);
			for (Integer i = 1; i < a.size(); i++) {
				lst.addAll(((MalList) a.nth(i)).value);
			}
			return new MalList(lst);
		}
	};
	static @NotNull MalFunction vec          = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return new MalVector(((MalList) a.nth(0)).getList());
		}
	};
	static @NotNull MalFunction first        = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalVal exp = a.nth(0);
			if (exp == Nil) {
				return Nil;
			}
			final MalList ml = ((MalList) exp);
			return ml.size() > 0 ? ml.nth(0) : Nil;
		}
	};
	static @NotNull MalFunction rest         = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalVal exp = a.nth(0);
			if (exp == Nil) {
				return new MalList();
			}
			final MalList ml = ((MalList) exp);
			return ml.rest();
		}
	};
	static @NotNull MalFunction nth          = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final Integer idx = ((MalInteger) a.nth(1)).getValue();
			if (idx < ((MalList) a.nth(0)).size()) {
				return ((MalList) a.nth(0)).nth(idx);
			} else {
				throw new MalError("nth: index out of range");
			}
		}
	};
	// General sequence functions
	static @NotNull MalFunction apply        = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalFunction f    = (MalFunction) a.nth(0);
			final MalList     args = a.slice(1, a.size() - 1);
			args.value.addAll(((MalList) a.nth(a.size() - 1)).value);
			return f.apply(args);
		}
	};
	static @NotNull MalFunction map          = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalFunction f       = (MalFunction) a.nth(0);
			final MalList     src_lst = (MalList) a.nth(1);
			final MalList     new_lst = new MalList();
			for (Integer i = 0; i < src_lst.size(); i++) {
				new_lst.value.add(
						f.apply(new MalList(src_lst.nth(i))));
			}
			return new_lst;
		}
	};
	static @NotNull MalFunction conj         = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalList src_seq = (MalList) a.nth(0);
			final MalList new_seq;
			if (a.nth(0) instanceof MalVector) {
				new_seq = new MalVector();
				new_seq.value.addAll(src_seq.value);
				for (Integer i = 1; i < a.size(); i++) {
					new_seq.value.add(a.nth(i));
				}
			} else {
				new_seq = new MalList();
				new_seq.value.addAll(src_seq.value);
				for (Integer i = 1; i < a.size(); i++) {
					new_seq.value.add(0, a.nth(i));
				}
			}
			return new_seq;
		}
	};
	static @NotNull MalFunction seq          = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalVal mv = a.nth(0);
			if (mv instanceof MalVector) {
				if (((MalVector) mv).size() == 0) {
					return Nil;
				}
				return new MalList(((MalVector) mv).getList());
			} else if (mv instanceof MalList) {
				if (((MalList) mv).size() == 0) {
					return Nil;
				}
				return mv;
			} else if (mv instanceof MalString) {
				final String s = ((MalString) mv).getValue();
				if (s.length() == 0) {
					return Nil;
				}
				final List<MalVal> lst = new ArrayList<MalVal>();
				for (final String c : s.split("(?!^)")) {
					lst.add(new MalString(c));
				}
				return new MalList(lst);
			} else if (mv == Nil) {
				return Nil;
			} else {
				throw new MalError("seq: called on non-sequence");
			}
		}
	};
	static          MalFunction meta;
	static @NotNull MalFunction with_meta    = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList args) throws MalThrowable {
			final MalVal new_mv = args.nth(0).copy();
			new_mv.setMeta(args.nth(1));
			return new_mv;
		}
	};


	static @NotNull MalFunction deref      = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalAtom) a.nth(0)).value;
		}
	};
	static @NotNull MalFunction reset_BANG = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return ((MalAtom) a.nth(0)).value = a.nth(1);
		}
	};
	static @NotNull MalFunction swap_BANG  = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			final MalAtom     atm      = (MalAtom) a.nth(0);
			final MalFunction f        = (MalFunction) a.nth(1);
			final MalList     new_args = new MalList();
			new_args.value.addAll(a.slice(2).value);
			new_args.value.add(0, atm.value);
			atm.value = f.apply(new_args);
			return atm.value;
		}
	};

	// Metadata functions
	// Atom functions
	static @NotNull MalFunction new_atom = new MalFunction() {
		public @NotNull MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return new MalAtom(a.nth(0));
		}
	};
	static @NotNull MalFunction atom_Q   = new MalFunction() {
		public MalVal apply(final @NotNull MalList a) throws MalThrowable {
			return a.nth(0) instanceof MalAtom ? True : False;
		}
	};

	// types_ns is namespace of type functions
	static {
		meta = new MalFunction() {
			public MalVal apply(final @NotNull MalList args) throws MalThrowable {
				return args.nth(0).getMeta();
			}
		};

		ns = ImmutableMap.<String, MalVal>builder()
				.put("=", equal_Q)
				.put("throw", mal_throw)
				.put("nil?", nil_Q)
				.put("true?", true_Q)
				.put("false?", false_Q)
				.put("number?", number_Q)
				.put("string?", string_Q)
				.put("symbol", symbol)
				.put("symbol?", symbol_Q)
				.put("keyword", keyword)
				.put("keyword?", keyword_Q)
				.put("fn?", fn_Q)
				.put("macro?", macro_Q)

				.put("pr-str", pr_str)
				.put("str", str)
				.put("prn", prn)
				.put("println", println)
				.put("readline", mal_readline)
				.put("read-string", read_string)
				.put("slurp", slurp)
				.put("<", lt)
				.put("<=", lte)
				.put(">", gt)
				.put(">=", gte)
				.put("+", add)
				.put("-", subtract)
				.put("*", multiply)
				.put("/", divide)
				.put("time-ms", time_ms)

				.put("list", new_list)
				.put("list?", list_Q)
				.put("vector", new_vector)
				.put("vector?", vector_Q)
				.put("hash-map", new_hash_map)
				.put("map?", hash_map_Q)
				.put("assoc", assoc)
				.put("dissoc", dissoc)
				.put("contains?", contains_Q)
				.put("get", get)
				.put("keys", keys)
				.put("vals", vals)

				.put("sequential?", sequential_Q)
				.put("cons", cons)
				.put("concat", concat)
				.put("vec", vec)
				.put("nth", nth)
				.put("first", first)
				.put("rest", rest)
				.put("empty?", empty_Q)
				.put("count", count)
				.put("apply", apply)
				.put("map", map)

				.put("conj", conj)
				.put("seq", seq)

				.put("with-meta", with_meta)
				.put("meta", meta)
				.put("atom", new_atom)
				.put("atom?", atom_Q)
				.put("deref", deref)
				.put("reset!", reset_BANG)
				.put("swap!", swap_BANG)
				.build();
	}

	static public @NotNull Boolean _list_Q(final @NotNull MalVal mv) {
		return mv.getClass().equals(MalList.class);
	}

	static public @NotNull Boolean _vector_Q(final @NotNull MalVal mv) {
		return mv.getClass().equals(MalVector.class);
	}
}
