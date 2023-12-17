package tripleo.elijah.stages.gen_fn;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.NotImplementedException;

class __GenerateClass {
	private final ElLog                       LOG;
	private final RegisterClassInvocation_env passthruEnv;

	@Contract(pure = true)
	__GenerateClass(final ElLog aLOG) {
		LOG         = aLOG;
		passthruEnv = null;
	}

	public __GenerateClass(final ElLog aLOG, final RegisterClassInvocation_env aPassthruEnv) {
		LOG         = aLOG;
		passthruEnv = aPassthruEnv;
	}

	void processItem(@NotNull ClassStatement klass, final @NotNull ClassItem item, final @NotNull EvaClass gc) {
		@Nullable AccessNotation an = null;

		if (item instanceof AliasStatement) {
			LOG.info("Skip alias statement for now");
//				throw new NotImplementedException();
		} else if (item instanceof ClassStatement) {
//				final ClassStatement classStatement = (ClassStatement) item;
//				@NotNull EvaClass gen_c = generateClass(classStatement);
//				gc.addClass(classStatement, gen_c);
		} else if (item instanceof ConstructorDef) {
//				final ConstructorDef constructorDef = (ConstructorDef) item;
//				@NotNull GeneratedConstructor f = generateConstructor(constructorDef, klass, null); // TODO remove this null
//				gc.addConstructor(constructorDef, f);
		} else if (item instanceof DestructorDef) {
			throw new NotImplementedException();
		} else if (item instanceof DefFunctionDef) {
//				@NotNull EvaFunction f = generateFunction((DefFunctionDef) item, klass);
//				gc.addFunction((DefFunctionDef) item, f);
		} else if (item instanceof FunctionDef) {
			// README handled in WlGenerateFunction
//				@NotNull EvaFunction f = generateFunction((FunctionDef) item, klass);
//				gc.addFunction((FunctionDef) item, f);
		} else if (item instanceof NamespaceStatement) {
			throw new NotImplementedException();
		} else if (item instanceof @NotNull final VariableSequence vsq) {
			for (VariableStatement vs : vsq.items()) {
//					LOG.info("6999 "+vs);
				gc.addVarTableEntry(an, vs, passthruEnv);
			}
		} else if (item instanceof AccessNotation) {
			//
			// TODO two AccessNotationImpl's can be active at once, for example if the first
			//  one defined only classes and the second one defined only a category
			//
			an = (AccessNotation) item;
//				gc.addAccessNotation(an);
		} else if (item instanceof @NotNull final PropertyStatement ps) {
			LOG.err("307 Skipping property for now");
		} else {
			LOG.err("305 " + item.getClass().getName());
			throw new NotImplementedException();
		}

		gc.createCtor0();

//			klass._a.setCode(nextClassCode());
	}
}
