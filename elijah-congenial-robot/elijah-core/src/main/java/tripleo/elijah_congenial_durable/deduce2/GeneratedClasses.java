package tripleo.elijah_congenial_durable.deduce2;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;

public class GeneratedClasses implements Iterable<EvaNode> {
	private final          DeducePhase   deducePhase;
	private final @NotNull List<EvaNode> generatedClasses = new ArrayList<>();//new ConcurrentLinkedQueue<>();
	private                int           generation;

	public GeneratedClasses(final DeducePhase aDeducePhase) {
		deducePhase = aDeducePhase;
	}

	@Override
	public String toString() {
		return "GeneratedClasses{size=%d, generation=%d}".formatted(generatedClasses.size(), generation);
	}

	public void add(EvaNode aClass) {
		Preconditions.checkArgument(aClass instanceof EvaClass);

		deducePhase.pa._send_GeneratedClass(aClass);

		generatedClasses.add(aClass);
	}

	public @NotNull List<EvaNode> copy() {
		++generation;
		return new ArrayList<>(generatedClasses);
	}

	@Override
	public @NotNull Iterator<EvaNode> iterator() {
		return generatedClasses.iterator();
	}

	public int size() {
		return generatedClasses.size();
	}

	public List<EvaNode> getList() {
		return ImmutableList.copyOf(generatedClasses);
	}
}
