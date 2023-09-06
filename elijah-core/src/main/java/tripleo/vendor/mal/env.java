package tripleo.vendor.mal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.vendor.mal.types.*;

import java.util.HashMap;

public class env {
	public static class Env {
		@Nullable Env                     outer = null;
		@NotNull  HashMap<String, MalVal> data  = new HashMap<String, MalVal>();

		public Env(final Env outer) {
			this.outer = outer;
		}

		public Env(final Env outer, final @NotNull MalList binds, final @NotNull MalList exprs) {
			this.outer = outer;
			for (Integer i = 0; i < binds.size(); i++) {
				final String sym = ((MalSymbol) binds.nth(i)).getName();
				if (sym.equals("&")) {
					data.put(((MalSymbol) binds.nth(i + 1)).getName(),
							 exprs.slice(i));
					break;
				} else {
					data.put(sym, exprs.nth(i));
				}
			}
		}

		public @Nullable Env find(final @NotNull MalSymbol key) {
			if (data.containsKey(key.getName())) {
				return this;
			} else if (outer != null) {
				return outer.find(key);
			} else {
				return null;
			}
		}

		public MalVal get(final @NotNull MalSymbol key) throws MalThrowable {
			final Env e = find(key);
			if (e == null) {
				throw new MalException(
						"'" + key.getName() + "' not found");
			} else {
				return e.data.get(key.getName());
			}
		}

		public @NotNull Env set(final @NotNull MalSymbol key, final MalVal value) {
			data.put(key.getName(), value);
			return this;
		}
	}
}
