package tripleo.elijah_durable_congenial.stages.gen_c;

import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;

import java.util.List;

public class ZonePath implements ZoneMember {
	private final IdentIA                   identIA;
	private final List<InstructionArgument> s;

	public ZonePath(final IdentIA aIdentIA, final List<InstructionArgument> aS) {
		identIA = aIdentIA;
		s       = aS;
	}
}
