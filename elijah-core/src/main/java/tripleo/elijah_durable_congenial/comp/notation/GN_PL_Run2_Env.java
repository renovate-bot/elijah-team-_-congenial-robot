package tripleo.elijah_durable_congenial.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import java.util.function.Consumer;

public record GN_PL_Run2_Env(@NotNull PipelineLogic pipelineLogic,
							 @NotNull WorldModule mod,
							 @NotNull CompilationEnclosure ce,
							 @NotNull Consumer<WorldModule> worldConsumer) implements GN_Env {
}
