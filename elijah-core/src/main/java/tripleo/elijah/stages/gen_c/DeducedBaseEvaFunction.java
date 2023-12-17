package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.OnGenClass;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;

public interface DeducedBaseEvaFunction extends IEvaFunctionBase {
	void onGenClass(@NotNull OnGenClass aOnGenClass);

	BaseEvaFunction_Reactive reactive();

	IEvaFunctionBase getCarrier();

	OS_Module getModule__();

	WhyNotGarish_Function getWhyNotGarishFunction(GenerateC aGc);

	Eventual<GenType> typeDeferred();

	Eventual<GenType> typePromise();
}
