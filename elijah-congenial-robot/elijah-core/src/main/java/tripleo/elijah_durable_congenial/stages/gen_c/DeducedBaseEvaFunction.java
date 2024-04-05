package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.deduce.OnGenClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.IEvaFunctionBase;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;

public interface DeducedBaseEvaFunction extends DeducedEvaFunctionBase {
	@Override
	void onGenClass(@NotNull OnGenClass aOnGenClass);

	@Override
	IEvaFunctionBase getCarrier();

	@Override
	OS_Module getModule__();

	@Override
	Eventual<GenType> typeDeferred();

	@Override
	Eventual<GenType> typePromise();

	@Override
	void generateCodeForMethod(Generate_Code_For_Method aGcfm, GenerateResultEnv aFileGen);

	@Override
	LibraryStatementPart evaLayer_module_lsp();

	@Override
	EvaNode getEvaNodeEscapeHatch();

	@Override
	BaseEvaFunction_Reactive reactive();

	WhyNotGarish_Function getWhyNotGarishFunction(GenerateC aGc);
}
