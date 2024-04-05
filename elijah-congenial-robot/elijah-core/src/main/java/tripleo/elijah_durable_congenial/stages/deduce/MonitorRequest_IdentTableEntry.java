package tripleo.elijah_durable_congenial.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.stages.gdm.GDM_IdentExpression;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;

import java.util.List;

public class MonitorRequest_IdentTableEntry {
	private final IdentIA                       identIA;
	private final Eventual<GDM_IdentExpression> eg = new Eventual<>();

	public MonitorRequest_IdentTableEntry(final IdentIA aIdentIA) {
		identIA = aIdentIA;
	}

	public void trigger(final Context aEctx,
						final List<InstructionArgument> ss,
						final FoundElement aFoundElement,
						final DeduceTypes2 deduceTypes2) {
		eg.then(mie -> mie.trigger_resolve(aEctx, ss, aFoundElement, deduceTypes2, null));
	}

	public void backstage_trigger(final @NotNull BaseEvaFunction aGeneratedFunction) {
		aGeneratedFunction.onInformGF(gf -> {
			final @NotNull IdentTableEntry entry = identIA.getEntry();
			final GDM_IdentExpression      mie   = gf.monitor(entry.getIdent());

			eg.resolve(mie);
		});
	}
}
