package tripleo.elijah.stages.gen_c;

import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;

import java.util.List;

public class ZonePath implements ZoneMember {
	private final IdentIA                   identIA;
	private final List<InstructionArgument> s;

	public ZonePath(final IdentIA aIdentIA, final List<InstructionArgument> aS) {
		identIA = aIdentIA;
		s       = aS;
	}
}
