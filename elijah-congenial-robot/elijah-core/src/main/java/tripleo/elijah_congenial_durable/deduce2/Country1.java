package tripleo.elijah_congenial_durable.deduce2;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.Country;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;

import java.util.List;
import java.util.function.Consumer;

public class Country1 implements Country {
	private final DeducePhase deducePhase;

	public Country1(final DeducePhase aDeducePhase) {
		deducePhase = aDeducePhase;
	}

	@Override
	public void sendClasses(final @NotNull Consumer<List<EvaNode>> ces) {
		ces.accept(deducePhase.generatedClasses.copy());
	}
}
