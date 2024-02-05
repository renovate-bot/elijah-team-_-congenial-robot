package tripleo.elijah.comp.internal;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.internal.CR_State.PipelinePlugin;
import tripleo.elijah.sanaa.ElIntrinsics;
import tripleo.vendor.mal.stepA_mal;
import tripleo.vendor.mal.types;
import tripleo.vendor.mal.stepA_mal.MalEnv2;

public class MalBulge {
	private stepA_mal.MalEnv2 env;
	private CompilationEnclosure ce;

	public @NotNull MalEnv2 getEnv() {
		return env;
	}

	public MalBulge(CompilationEnclosure ce) {
		this.env = new stepA_mal.MalEnv2(null); // TODO what does null mean?
		this.ce = ce;
		
		ce.getAccessBusPromise()
		.then(ab -> {
			Preconditions.checkNotNull(ab);
					
			Consumer<PipelinePlugin> ppl = new Consumer<>() {
				@Override
				public void accept(PipelinePlugin t) {
					ab.add(t::instance);					
				}
			};
			
			env.set(new types.MalSymbol("add-pipeline"), new _AddPipeline__MAL(ppl, ab));
		});
	}
	
	
	private static class _AddPipeline__MAL extends types.MalFunction {
		private final AccessBus                ab;
		private final Consumer<PipelinePlugin> pipelinePluginConsumer;

		public _AddPipeline__MAL(Consumer<PipelinePlugin> ppl, final AccessBus aAccessBus) {
			ElIntrinsics.checkNotNull(ppl);
			ElIntrinsics.checkNotNull(aAccessBus);

			pipelinePluginConsumer = ppl;
			ab                     = aAccessBus;
		}

		@Override
		public types.MalVal apply(final types.@NotNull MalList args) throws types.MalThrowable {
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
