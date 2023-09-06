package tripleo.elijah.stages.gen_generic;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaConstructor;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.Collection;
import java.util.List;

public interface GenerateFiles extends CodeGenerator {
	void generate_constructor(EvaConstructor aGf, GenerateResult aGr, WorkList aWl, GenerateResultSink aResultSink, final WorkManager aWorkManager, final @NotNull GenerateResultEnv aFileGen);

	void generate_function(EvaFunction aEvaFunction, GenerateResult aGenerateResult, WorkList aWorkList, GenerateResultSink aResultSink);

	GenerateResult generateCode(Collection<EvaNode> lgn, @NotNull GenerateResultEnv aFileGen);

	<T> GenerateResultEnv getFileGen();

	GenerateResult resultsFromNodes(@NotNull List<EvaNode> aNodes, WorkManager wm, GenerateResultSink grs, @NotNull GenerateResultEnv fg);

	ElLog elLog();

	void finishUp(final GenerateResult aGenerateResult, final WorkManager wm, final WorkList aWorkList);

	@NotNull
	static Collection<EvaNode> classes_to_list_of_generated_nodes(@NotNull Collection<EvaClass> aEvaClasses) {
		return Collections2.transform(aEvaClasses, new Function<EvaClass, EvaNode>() {
			@org.checkerframework.checker.nullness.qual.Nullable
			@Override
			public @Nullable EvaNode apply(@org.checkerframework.checker.nullness.qual.Nullable EvaClass input) {
				return input;
			}
		});
	}

	@NotNull
	static Collection<EvaNode> constructors_to_list_of_generated_nodes(@NotNull Collection<EvaConstructor> aEvaConstructors) {
		return Collections2.transform(aEvaConstructors, new Function<EvaConstructor, EvaNode>() {
			@org.checkerframework.checker.nullness.qual.Nullable
			@Override
			public @Nullable EvaNode apply(@org.checkerframework.checker.nullness.qual.Nullable EvaConstructor input) {
				return input;
			}
		});
	}

	@NotNull
	static Collection<EvaNode> functions_to_list_of_generated_nodes(@NotNull Collection<EvaFunction> generatedFunctions) {
		return Collections2.transform(generatedFunctions, new Function<EvaFunction, EvaNode>() {
			@org.checkerframework.checker.nullness.qual.Nullable
			@Override
			public @Nullable EvaNode apply(@org.checkerframework.checker.nullness.qual.Nullable EvaFunction input) {
				return input;
			}
		});
	}
}
