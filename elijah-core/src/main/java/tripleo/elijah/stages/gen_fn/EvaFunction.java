/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.nextgen.DR_Item;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 6/27/21 9:40 AM
 */
public class EvaFunction extends BaseEvaFunction implements GNCoded {
	public final @Nullable FunctionDef fd;

	public EvaFunction(final @Nullable FunctionDef functionDef) {
		fd = functionDef;
	}

	//
	// region toString
	//

	@Override
	public @NotNull FunctionDef getFD() {
		if (fd != null) return fd;
		throw new IllegalStateException("No function");
	}

	@Override
	public @NotNull Role getRole() {
		return Role.FUNCTION;
	}

	@Override
	public void register(final @NotNull ICodeRegistrar aCr) {
		aCr.registerFunction1(this);
	}

	// endregion

	@Override
	public @Nullable VariableTableEntry getSelf() {
		if (getFD().getParent() instanceof ClassStatement)
			return getVarTableEntry(0);
		else
			return null;
	}

	@Override
	public String identityString() {
		return String.valueOf(fd);
	}

	@Override
	public @NotNull OS_Module module() {
		return getFD().getContext().module();
	}

	public String name() {
		if (fd == null) {
            throw new IllegalArgumentException("null fd");
        }
		return fd.name().asString();
	}

	@Override
	public String toString() {
		String pte_string = fd.getArgs().toString(); // TODO wanted PTE.getLoggingString
		return String.format("<EvaFunction %s %s %s>", fd.getParent(), fd.name(), pte_string);
	}
}

//
//
//
