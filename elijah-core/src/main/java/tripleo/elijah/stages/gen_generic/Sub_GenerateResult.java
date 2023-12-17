package tripleo.elijah.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.stages.gen_c.OutputFileC;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.util.buffer.Buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//@Deprecated
public class Sub_GenerateResult implements GenerateResult {
	final         List<Old_GenerateResultItem> _res           = new ArrayList<Old_GenerateResultItem>();
	private final List<IGenerateResultWatcher> _watchers      = new ArrayList<>();
	private       int                          bufferCounter  = 0;
	private final Subject<GenerateResultItem>  completedItems = ReplaySubject.<GenerateResultItem>create();
	private       Map<String, OutputFileC>     outputFiles;

	public Sub_GenerateResult() {
		//System.err.println("** ctor Sub_GenerateResult");
	}

	@Override
	public void close() {
		throw new NotImplementedException("asdasldbhajk");
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#add(tripleo.util.buffer.Buffer, tripleo.elijah.stages.gen_fn.EvaNode, tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.ci.LibraryStatementPart, tripleo.elijah.stages.gen_generic.Dependency)
	 */
	@Override
	public void add(@NotNull Buffer b, @NotNull EvaNode n, @NotNull TY ty, @Nullable LibraryStatementPart aLsp, @NotNull Dependency d) {
		if (aLsp == null) {
			SimplePrintLoggerToRemoveSoon.println_err_2("*************************** buffer --> " + b.getText());
			return;
		}

		++bufferCounter;
		final Old_GenerateResultItem item = new Old_GenerateResultItem(ty, b, n, aLsp, d, bufferCounter);
		_res.add(item);

		for (IGenerateResultWatcher w : _watchers) {
			w.item(item);
		}
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addClass(tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.stages.gen_fn.EvaClass, tripleo.util.buffer.Buffer, tripleo.elijah.ci.LibraryStatementPart)
	 */
	@Override
	public void addClass(@NotNull TY ty, @NotNull EvaClass aClass, @NotNull Buffer aBuf, LibraryStatementPart aLsp) {
		add(aBuf, aClass, ty, aLsp, aClass.getDependency());
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addConstructor(tripleo.elijah.stages.gen_fn.EvaConstructor, tripleo.util.buffer.Buffer, tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.ci.LibraryStatementPart)
	 */
	@Override
	public void addConstructor(@NotNull EvaConstructor aEvaConstructor, @NotNull Buffer aBuffer, @NotNull TY aTY, LibraryStatementPart aLsp) {
		addFunction(aEvaConstructor, aBuffer, aTY, aLsp);
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addFunction(tripleo.elijah.stages.gen_fn.BaseEvaFunction, tripleo.util.buffer.Buffer, tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.ci.LibraryStatementPart)
	 */
	@Override
	public void addFunction(@NotNull BaseEvaFunction aGeneratedFunction, @NotNull Buffer aBuffer, @NotNull TY aTY, LibraryStatementPart aLsp) {
		add(aBuffer, aGeneratedFunction, aTY, aLsp, aGeneratedFunction.getDependency());
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#additional(tripleo.elijah.stages.gen_generic.GenerateResult)
	 */
	@Override
	public void additional(final @NotNull GenerateResult aGenerateResult) {
		// TODO find something better
		//results()
		_res.addAll(aGenerateResult.results());
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addNamespace(tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.stages.gen_fn.EvaNamespace, tripleo.util.buffer.Buffer, tripleo.elijah.ci.LibraryStatementPart)
	 */
	@Override
	public void addNamespace(@NotNull TY ty, @NotNull EvaNamespace aNamespace, @NotNull Buffer aBuf, LibraryStatementPart aLsp) {
		add(aBuf, aNamespace, ty, aLsp, aNamespace.getDependency());
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addWatcher(tripleo.elijah.stages.gen_generic.IGenerateResultWatcher)
	 */
	@Override
	public void addWatcher(IGenerateResultWatcher w) {
		_watchers.add(w);
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#completeItem(tripleo.elijah.stages.gen_generic.GenerateResultItem)
	 */
	@Override
	public void completeItem(GenerateResultItem aGenerateResultItem) {
		completedItems.onNext(aGenerateResultItem);
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#observe(io.reactivex.rxjava3.core.Observer)
	 */
	@Override
	public void observe(final @NotNull Observer<GenerateResultItem> obs) {
		for (Old_GenerateResultItem item : results()) {
			obs.onNext(item);
		}

		obs.onComplete();
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#signalDone()
	 */
	@Override
	public void signalDone() {
		completedItems.onComplete();

		for (IGenerateResultWatcher w : _watchers) {
			w.complete();
		}
	}

	// region REACTIVE

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#outputFiles(java.util.function.Consumer)
	 */
	@Override
	public void outputFiles(final @NotNull Consumer<Map<String, OutputFileC>> cmso) {
		cmso.accept(outputFiles);
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#results()
	 */
	@Override
	public @NotNull List<Old_GenerateResultItem> results() {
		return _res;
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#signalDone(java.util.Map)
	 */
	@Override
	public void signalDone(final Map<String, OutputFileC> aOutputFiles) {
		outputFiles = aOutputFiles;

		signalDone();
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#subscribeCompletedItems(io.reactivex.rxjava3.core.Observer)
	 */
	@Override
	public void subscribeCompletedItems(@NotNull Observer<GenerateResultItem> aGenerateResultItemObserver) {
		completedItems.subscribe(aGenerateResultItemObserver);
	}

	// endregion
}
