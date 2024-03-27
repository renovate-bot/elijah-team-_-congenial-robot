package tripleo.elijah_durable_congenial.comp.internal;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.AccessBus;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.vendor.mal.stepA_mal;
import tripleo.vendor.mal.stepA_mal.MalEnv2;
import tripleo.vendor.mal.types;

import java.util.function.Consumer;

public class MalBulge {
	private final stepA_mal.MalEnv2    env;
	private final CompilationEnclosure ce;

	public @NotNull MalEnv2 getEnv() {
		return env;
	}

	public MalBulge(CompilationEnclosure ce) {
		this.env = new stepA_mal.MalEnv2(null); // TODO what does null mean?
		this.ce  = ce;

		ce.getAccessBusPromise()
				.then(ab -> {
					Preconditions.checkNotNull(ab);

					Consumer<CR_State.PipelinePlugin> ppl = t -> ab.add(t::instance);

					env.set(new types.MalSymbol("add-pipeline"), new _AddPipeline__MAL(ppl, ab));
				});
	}


	private static class _AddPipeline__MAL extends types.MalFunction {
		private final AccessBus                         ab;
		private final Consumer<CR_State.PipelinePlugin> pipelinePluginConsumer;

		public _AddPipeline__MAL(Consumer<CR_State.PipelinePlugin> ppl, final AccessBus aAccessBus) {
			pipelinePluginConsumer = ppl;
			ab                     = aAccessBus;
		}

		@Override
		public types.MalVal apply(final types.@NotNull MalList args) {
			final types.MalVal a0 = args.nth(0);

			if (a0 instanceof final types.@NotNull MalSymbol pipelineSymbol) {
				// 0. accessors
				final String pipelineName = pipelineSymbol.getName();

				// 1. observe side effect
				final CR_State.PipelinePlugin pipelinePlugin = ab.getPipelinePlugin(pipelineName);
				if (pipelinePlugin == null)
					return types.False;

				// 2. produce effect
				//pipelinePlugin::instance
				assert ab != null;
				pipelinePluginConsumer.accept(pipelinePlugin);
				return types.True;
			} else {
				// TODO exception? errSink??
				return types.False;
			}
		}
	}
}
