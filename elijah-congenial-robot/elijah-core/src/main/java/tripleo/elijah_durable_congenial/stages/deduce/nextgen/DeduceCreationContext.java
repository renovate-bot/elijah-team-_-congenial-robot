package tripleo.elijah_durable_congenial.stages.deduce.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GeneratePhase;

public interface DeduceCreationContext {

	Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(FunctionInvocation aFunctionInvocation);

	DeduceTypes2 getDeduceTypes2();

	@NotNull DeducePhase getDeducePhase();

	@NotNull GeneratePhase getGeneratePhase();
}
