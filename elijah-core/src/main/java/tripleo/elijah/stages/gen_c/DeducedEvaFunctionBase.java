package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.reactive.Reactive;
import tripleo.elijah.stages.deduce.OnGenClass;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;

public interface DeducedEvaFunctionBase extends IEvaFunctionBase, IGC_Deduced {
	void onGenClass(@NotNull OnGenClass aOnGenClass);

	IEvaFunctionBase getCarrier();

	OS_Module getModule__();

	Eventual<GenType> typeDeferred();

	Eventual<GenType> typePromise();

	void generateCodeForMethod(Generate_Code_For_Method aGcfm, GenerateResultEnv aFileGen);

	LibraryStatementPart evaLayer_module_lsp();

	EvaNode getEvaNodeEscapeHatch();

	Reactive reactive();
}
