/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created 4/13/21 5:46 AM
 */
public class CantDecideType implements Diagnostic {
	private final @NotNull Collection<TypeTableEntry> types;
	private final          VariableTableEntry         vte;

	public CantDecideType(VariableTableEntry aVte, @NotNull Collection<TypeTableEntry> aTypes) {
		vte   = aVte;
		types = aTypes;
	}

	@Override
	public @NotNull String code() {
		return "E1001";
	}

	@Override
	public void report(@NotNull PrintStream stream) {
		stream.printf("---[%s]---: %s%n", code(), message());
		// linecache.print(primary);
		for (Locatable sec : secondary()) {
			//linecache.print(sec)
		}
		stream.flush();
	}

	@Override
	public @NotNull Locatable primary() {
		@NotNull VariableStatementImpl vs = (VariableStatementImpl) vte.getResolvedElement();
		return vs;
	}

	private @NotNull String message() {
		return "Can't decide type";
	}

	@Override
	public @NotNull List<Locatable> secondary() {
		final List<Locatable> c = types.stream()
				.map((TypeTableEntry input) -> {
						 //return input.attached.getElement(); // TODO All elements should be Locatable
						 //return (TypeName)input.attached.getTypename();
						 return (Locatable) null;
					 }
					)
				.collect(Collectors.toList());

		return c;
	}

	@Override
	public @NotNull Severity severity() {
		return Severity.ERROR;
	}
}

//
//
//
