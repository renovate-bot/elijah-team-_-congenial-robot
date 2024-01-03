package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;

public class __Plugins {
	public static class DeducePipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new DeducePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "DeducePipeline";
		}
	}

	public static class EvaPipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new EvaPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "EvaPipeline";
		}
	}

	public static class HooliganPipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new HooliganPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "HooliganPipeline";
		}
	}

	public static class WriteMakefilePipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteMakefilePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteMakefilePipeline";
		}
	}

	public static class WriteMesonPipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteMesonPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteMesonPipeline";
		}
	}

	public static class WriteOutputTreePipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteOutputTreePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteOutputTreePipeline";
		}
	}

	public static class WritePipelinePlugin implements CR_State.PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WritePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WritePipeline";
		}
	}
}
