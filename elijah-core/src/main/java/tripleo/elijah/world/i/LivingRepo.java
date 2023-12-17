package tripleo.elijah.world.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah.world.impl.DefaultLivingClass;
import tripleo.elijah.world.impl.DefaultLivingFunction;
import tripleo.elijah.world.impl.DefaultLivingNamespace;

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
