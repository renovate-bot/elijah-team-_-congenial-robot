package tripleo.elijah_durable_congenial.stages.gen_c.statements;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetAssignmentValueArgsStatement implements EG_Statement {
	private final List<String> sll = new ArrayList<>();
	private final Instruction inst;

	public GetAssignmentValueArgsStatement(final Instruction aInst) {
		inst = aInst;
	}

	public void add_string(final String aS) {
		sll.add(aS);
	}

	@Override
	public EX_Explanation getExplanation() {
		throw new UnintendedUseException();
		// return null;
	}

	@Override
	public @Nullable String getText() {
		final EG_SequenceStatement getAssignmentValueArgsStatement = new EG_SequenceStatement(
				new EG_Naming("GetAssignmentValueArgsStatement"),
				sll.stream()
						.map(x -> EG_Statement.of(x, null))
						.collect(Collectors.toList()
								)
		);
		return getAssignmentValueArgsStatement.getText();
	}

	public List<String> stringList() {
		return sll;
	}
}
