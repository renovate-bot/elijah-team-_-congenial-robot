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
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.util.Helpers;

import java.util.Collection;
import java.util.List;

/**
 * Created 9/10/20 3:36 PM
 */
public class FnCallArgs implements InstructionArgument {
	private                TypeTableEntry  _type; // the return type of the function call
	public final           Instruction     expression_to_call;
	private final @NotNull BaseEvaFunction gf;

	public FnCallArgs(final Instruction expression_to_call, final @NotNull BaseEvaFunction generatedFunction) {
		this.expression_to_call = expression_to_call;
		this.gf                 = generatedFunction;
	}

	public InstructionArgument getArg(final int i) {
		return expression_to_call.getArg(i);
	}

	public Instruction getExpression() {
		return expression_to_call;
	}

	@NotNull
	public List<InstructionArgument> getInstructionArguments() {
		final List<InstructionArgument> args = this.getArgs();
		return args.subList(1, args.size());
	}

	private List<InstructionArgument> getArgs() {
		return expression_to_call.args;
	}

	public void setType(TypeTableEntry tte2) {
		_type = tte2;
	}

	@Override
	public String toString() {
		final int                       index                = DeduceTypes2.to_int(expression_to_call.args.get(0));
		final List<InstructionArgument> instructionArguments = getInstructionArguments();

		final Collection<String> collect2        = Helpers.mapCollectionElementsToString(instructionArguments);
		final @NotNull String    commaed_strings = Helpers.String_join(" ", collect2);
		
		final ProcTableEntry procTableEntry = gf.prte_list.get(index);

		return String.format("(call %d [%s(%s)] %s)",
							 index, procTableEntry.__debug_expression, procTableEntry.args,
							 commaed_strings);
	}

}

//
//
//
