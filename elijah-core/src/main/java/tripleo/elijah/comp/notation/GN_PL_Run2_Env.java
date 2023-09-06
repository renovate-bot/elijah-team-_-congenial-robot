package tripleo.elijah.comp.notation;

import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.world.i.WorldModule;

import java.util.function.Consumer;

public record GN_PL_Run2_Env(PipelineLogic pipelineLogic,
							 OS_Module mod,
							 CompilationEnclosure ce,
							 Consumer<WorldModule> worldConsumer) implements GN_Env {
}
