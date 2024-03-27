package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah.nextgen.reactive.Reactive;
import tripleo.elijah_durable_congenial.stages.deduce.OnGenClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.IEvaFunctionBase;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

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
