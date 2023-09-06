/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.instructions;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.stages.deduce.DeduceElement;

import java.util.List;

/**
 * Created 9/10/20 3:16 PM
 */
public class Instruction {
	public DeduceElement deduceElement;
	List<InstructionArgument> args;
	private Context         context;
	private int             index = -1;
	private InstructionName name;

	public Instruction() {

	}

	public Instruction(final InstructionName aInstructionName) {
		setName(aInstructionName);
	}

	public Instruction(final InstructionName aInstructionName, final List<InstructionArgument> aLi) {
		setName(aInstructionName);
		setArgs(aLi);
	}

	public InstructionArgument getArg(final int i) {
		return args.get(i);
	}

	public int getArgsSize() {
		return args.size();
	}

	public Context getContext() {
		return context;
	}

	public int getIndex() {
		return index;
	}

	public void setContext(final Context context) {
		this.context = context;
	}

	public InstructionName getName() {
		return name;
	}

	public void setName(final InstructionName aName) {
		name = aName;
	}

	public void setIndex(final int l) {
		index = l;
	}

	public void setArgs(final List<InstructionArgument> args_) {
		args = args_;
	}

	@Override
	public @NotNull String toString() {
		return "Instruction{" +
				"name=" + name +
				", index=" + index +
				", args=" + args +
				'}';
	}

//	public List<InstructionArgument> getArgs() {
//		return args;
//	}
}

//
//
//
