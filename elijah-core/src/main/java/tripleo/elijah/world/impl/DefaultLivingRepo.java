package tripleo.elijah.world.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.lang.impl.OS_PackageImpl;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah.util.ObservableCompletableProcess;
import tripleo.elijah.world.i.*;

import java.util.*;

public class DefaultLivingRepo implements LivingRepo {
	private final          Map<String, OS_Package>                          _packages     = new HashMap<String, OS_Package>();
	private final @NotNull ObservableCompletableProcess<WorldModule>        wmo           = new ObservableCompletableProcess<>();
	private final @NotNull List<LivingNode>                                 repo          = new ArrayList<>();
	private final @NotNull Multimap<BaseEvaFunction, DefaultLivingFunction> functionMap   = ArrayListMultimap.create();
	private final          Set<WorldModule>                                 _modules    = new HashSet<>();
	private                int                                              _classCode    = 101;
	private                int                                              _functionCode = 1001;
	private                int      _packageCode  = 1;

	@Override
	public @NotNull DefaultLivingClass addClass(final @NotNull EvaClass aClass, final @NotNull Add addFlag) {
		switch (addFlag) {
		case NONE -> {
			if (aClass.getCode() == 0) {
				aClass.setCode(nextClassCode());
			} else {
				if (2 == 3) {
					assert true;
				}
			}
		}
		case MAIN_FUNCTION -> {
			throw new IllegalArgumentException("not a function");
		}
		case MAIN_CLASS -> {
			final boolean isMainClass = MainClassEntryPoint.isMainClass(aClass.getKlass());
			if (!isMainClass) {
				throw new IllegalArgumentException("not a main class");
			}
			aClass.setCode(100);
		}
		}

		final DefaultLivingClass living = new DefaultLivingClass(aClass);
		aClass._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public @Nullable LivingClass addClass(final ClassStatement cs) {
		return null;
	}

	@Override
	public @NotNull DefaultLivingFunction addFunction(final @NotNull BaseEvaFunction aFunction, final @NotNull Add addFlag) {
		switch (addFlag) {
		case NONE -> {
			aFunction.setCode(nextFunctionCode());
		}
		case MAIN_FUNCTION -> {
			if (aFunction.getFD() instanceof FunctionDef &&
					MainClassEntryPoint.is_main_function_with_no_args((FunctionDef) aFunction.getFD())) {
				aFunction.setCode(1000);
				//compilation.notifyFunction(code, aFunction);
			} else {
				throw new IllegalArgumentException("not a main function");
			}
		}
		case MAIN_CLASS -> {
			throw new IllegalArgumentException("not a class");
		}
		}

		final DefaultLivingFunction living = new DefaultLivingFunction(aFunction);
		aFunction._living = living;

		functionMap.put(aFunction, living);

		return living;
	}

	@Override
	public void addModule(final @NotNull OS_Module mod, final @NotNull String aFilename, final @NotNull Compilation aC) {
		aC.addModule__(mod, aFilename);
	}

	@Override
	public @Nullable LivingFunction addFunction(final BaseFunctionDef fd) {
		return null;
	}

	@Override
	public @Nullable LivingPackage addPackage(final OS_Package pk) {
		return null;
	}

	@Override
	public @NotNull DefaultLivingNamespace addNamespace(final @NotNull EvaNamespace aNamespace, final @NotNull Add addFlag) {
		switch (addFlag) {
		case NONE -> {
			aNamespace.setCode(nextClassCode());
		}
		case MAIN_FUNCTION -> {
			throw new IllegalArgumentException("not a function");
		}
		case MAIN_CLASS -> {
			throw new IllegalArgumentException("not a main class");
		}
		}

		final DefaultLivingNamespace living = new DefaultLivingNamespace(aNamespace);
		aNamespace._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public @NotNull LivingNamespace getNamespace(final EvaNamespace aEvaNamespace) {
		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingNamespace livingNamespace) {
				if (livingNamespace.evaNode().equals(aEvaNamespace))
					return livingNamespace;
			}
		}

		final DefaultLivingNamespace living = new DefaultLivingNamespace(aEvaNamespace);
		//klass._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public @NotNull LivingClass getClass(final @NotNull EvaClass aEvaClass) {
		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.evaNode().equals(aEvaClass))
					return livingClass;
			}
		}

		final DefaultLivingClass living = new DefaultLivingClass(aEvaClass);
		//klass._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public OS_Package getPackage(final String aPackageName) {
		return _packages.get(aPackageName);
	}

	@Override
	public boolean hasPackage(final @NotNull String aPackageName) {
		if (aPackageName.equals("C")) {
			int y = 2;
		}
		return _packages.containsKey(aPackageName);
	}

	@Override
	public @Nullable LivingFunction getFunction(final BaseEvaFunction aBaseEvaFunction) {
		var c = functionMap.get(aBaseEvaFunction);

		if (c.size() > 0)
			return c.iterator().next();

		return null;
	}

	@Override
	public void addModuleProcess(CompletableProcess<WorldModule> wmcp) {
		wmo.subscribe(wmcp);
	}

	@Override
	public Collection<WorldModule> modules() {
		return _modules;
	}

	@Override
	public void addModule2(final WorldModule aWorldModule) {
		_modules.add(aWorldModule);

		wmo.onNext(aWorldModule);
	}

	@Override
	public OS_Package makePackage(final @NotNull Qualident pkg_name) {
		final String pkg_name_s = pkg_name.toString();
		if (!isPackage(pkg_name_s)) {
			final OS_Package newPackage = new OS_PackageImpl(pkg_name, nextPackageCode());
			_packages.put(pkg_name_s, newPackage);
			return newPackage;
		} else
			return _packages.get(pkg_name_s);
	}

	public boolean isPackage(final String pkg) {
		return _packages.containsKey(pkg);
	}

	@Contract(mutates = "this")
	private int nextPackageCode() {
		int i = _packageCode;
		_packageCode++;
		return i;
	}

	public int nextClassCode() {
		int i = _classCode;
		_classCode++;
		return i;
	}

	public int nextFunctionCode() {
		int i = _functionCode;
		_functionCode++;
		return i;
	}

	@Override
	public @NotNull List<LivingClass> getClassesForClassStatement(ClassStatement cls) {
		List<LivingClass> lcs = new LinkedList<>();

		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.getElement().equals(cls))
					lcs.add(livingClass);
			}
		}

		return lcs;
	}

	@Override
	public @NotNull List<LivingClass> getClassesForClassNamed(final String className) {
		List<LivingClass> lcs = new LinkedList<>();

		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.getElement().name().sameName(className))
					lcs.add(livingClass);
			}
		}

		return lcs;
	}

	@Override
	public @Nullable WorldModule getModule(final OS_Module aModule) {
		return _modules.stream()
				.filter(module -> module.module() == aModule)
				.findFirst()
				.orElse(null);
	}

}
