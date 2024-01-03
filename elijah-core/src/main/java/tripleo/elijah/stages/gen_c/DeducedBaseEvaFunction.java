package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.OnGenClass;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;

public interface DeducedBaseEvaFunction extends DeducedEvaFunctionBase {
	@Override
	void onGenClass(@NotNull OnGenClass aOnGenClass);

	@Override
	BaseEvaFunction_Reactive reactive();

	@Override
	IEvaFunctionBase getCarrier();

	@Override
	OS_Module getModule__();

	WhyNotGarish_Function getWhyNotGarishFunction(GenerateC aGc);

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
}
