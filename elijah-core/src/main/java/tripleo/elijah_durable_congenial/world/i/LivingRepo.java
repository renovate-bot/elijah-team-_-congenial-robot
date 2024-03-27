package tripleo.elijah_durable_congenial.world.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.lang.i.Qualident;
import tripleo.elijah_durable_congenial.lang.impl.BaseFunctionDef;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah_durable_congenial.world.impl.DefaultLivingClass;
import tripleo.elijah_durable_congenial.world.impl.DefaultLivingFunction;
import tripleo.elijah_durable_congenial.world.impl.DefaultLivingNamespace;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.lang.i.Qualident;
import tripleo.elijah_durable_congenial.world.impl.DefaultLivingClass;
import tripleo.elijah_durable_congenial.world.impl.DefaultLivingFunction;

import java.util.Collection;
import java.util.List;

public interface LivingRepo {
	DefaultLivingClass addClass(EvaClass aClass, Add addFlag);

	LivingClass addClass(ClassStatement cs);

	DefaultLivingFunction addFunction(BaseEvaFunction aFunction, Add aMainFunction);

	void addModule(OS_Module mod, String aFilename, final Compilation aC);

	LivingFunction addFunction(BaseFunctionDef fd);

	LivingPackage addPackage(OS_Package pk);

	//DefaultLivingClass addClass(EvaClass aClass, Add aMainClass);

	DefaultLivingNamespace addNamespace(EvaNamespace aNamespace, Add aNone);

	LivingNamespace getNamespace(EvaNamespace aEvaNamespace);

	LivingClass getClass(EvaClass aEvaClass);

	OS_Package getPackage(String aPackageName);

	boolean hasPackage(String aPackageName);

	LivingFunction getFunction(BaseEvaFunction aBaseEvaFunction);

	void addModuleProcess(CompletableProcess<WorldModule> wmcp);

	Collection<WorldModule> modules();

	void addModule2(WorldModule aMod1);

	@Nullable WorldModule getModule(OS_Module aModule);

	enum Add {MAIN_CLASS, MAIN_FUNCTION, NONE}

	OS_Package makePackage(Qualident aPkgName);

	List<LivingClass> getClassesForClassStatement(ClassStatement cls);

	List<LivingClass> getClassesForClassNamed(String string);
}
