package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.checkerframework.checker.nullness.qual.Nullable;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.WorkList;

public interface IClassGenerator {
	ICodeRegistrar getCodeRegistrar();

	GeneratedClasses getGeneratedClasses();

	FunctionInvocation newFunctionInvocation(FunctionDef fd,
											 ProcTableEntry pte,
											 ClassInvocation ci);

	@Nullable @org.jetbrains.annotations.Nullable ClassInvocation registerClassInvocation(ClassStatement cs, String className);

	void submitGenerateClass(ClassInvocation ci, GenerateFunctions gf);

	void submitGenerateFunction(FunctionInvocation ci, GenerateFunctions gf);

	WorkList wl();
}
