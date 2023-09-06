package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.WritePipeline;
import tripleo.elijah.util.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WP_Flow {
	private final HashMap<WP_Indiviual_Step, Pair<FlowStatus, Operation<Boolean>>> ops   = new HashMap<WP_Indiviual_Step, Pair<FlowStatus, Operation<Boolean>>>();
	private final List<WP_Indiviual_Step>                                          steps = new ArrayList<>();
	private final WritePipeline                                                    writePipeline;

	public enum FlowStatus {
		FAILED, NOT_TRIED, TRIED
	}

	public WP_Flow(final WritePipeline aWritePipeline, final @NotNull Collection<? extends WP_Indiviual_Step> s) {
		writePipeline = aWritePipeline;
		steps.addAll(s);
	}

	public @NotNull HashMap<WP_Indiviual_Step, Pair<FlowStatus, Operation<Boolean>>> act() {
		final WP_State_Control_1 sc = new WP_State_Control_1();

		for (final WP_Indiviual_Step step : steps) {
			ops.put(step, Pair.of(FlowStatus.NOT_TRIED, null));
		}

		for (final WP_Indiviual_Step step : steps) {
			sc.clear();

			step.act(writePipeline.st, sc);

			if (sc.hasException()) {
				ops.put(step, Pair.of(FlowStatus.FAILED, Operation.failure(sc.getException())));
				break;
			} else {
				ops.put(step, Pair.of(FlowStatus.TRIED, Operation.success(true)));
			}
		}

		return ops;
	}
}
