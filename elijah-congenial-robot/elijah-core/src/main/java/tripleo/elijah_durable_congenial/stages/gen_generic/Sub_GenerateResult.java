package tripleo.elijah_durable_congenial.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_congenial.pp.IPP_Function;
import tripleo.elijah_congenial.pp.PP_Constructor;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_c.DeducedEvaConstructor;
import tripleo.elijah_durable_congenial.stages.gen_c.OutputFileC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
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

	@Override
	public void addClass(@NotNull TY ty, @NotNull EvaClass aClass, @NotNull Buffer aBuf, LibraryStatementPart aLsp) {
		add(aBuf, aClass, ty, aLsp, aClass.getDependency());
	}

	@Override
	public void addFunction(IPP_Function aGeneratedFunction, @NotNull Buffer aBuffer, @NotNull TY aTY, LibraryStatementPart aLsp) {
		final DeducedBaseEvaFunction carrier            = aGeneratedFunction.get2Carrier();

		final EvaNode                evaNodeEscapeHatch = carrier.getEvaNodeEscapeHatch();
		final Dependency             dependency         = aGeneratedFunction.get2Carrier().getDependency();

		add(aBuffer, evaNodeEscapeHatch, aTY, aLsp, dependency);
	}

	@Override
	public void additional(final @NotNull GenerateResult aGenerateResult) {
		// TODO find something better
		//results()
		_res.addAll(aGenerateResult.results());
	}

	@Override
	public void addConstructor(final PP_Constructor aEvaConstructor, final Buffer aBuffer, final TY aTY, final LibraryStatementPart aLsp) {
		final DeducedEvaConstructor carrier = aEvaConstructor.get2Carrier();
		add(aBuffer, carrier.getEvaNodeEscapeHatch(), aTY, aLsp, carrier.getDependency());
	}

	@Override
	public void addNamespace(@NotNull TY ty, @NotNull EvaNamespace aNamespace, @NotNull Buffer aBuf, LibraryStatementPart aLsp) {
		add(aBuf, aNamespace, ty, aLsp, aNamespace.getDependency());
	}

	@Override
	public void addWatcher(IGenerateResultWatcher w) {
		_watchers.add(w);
	}

	@Override
	public void completeItem(GenerateResultItem aGenerateResultItem) {
		completedItems.onNext(aGenerateResultItem);
	}

	@Override
	public void observe(final @NotNull Observer<GenerateResultItem> obs) {
		for (Old_GenerateResultItem item : results()) {
			obs.onNext(item);
		}

		obs.onComplete();
	}

	@Override
	public void signalDone() {
		completedItems.onComplete();

		for (IGenerateResultWatcher w : _watchers) {
			w.complete();
		}
	}

	@Override
	public void outputFiles(final @NotNull Consumer<Map<String, OutputFileC>> cmso) {
		cmso.accept(outputFiles);
	}

	@Override
	public @NotNull List<Old_GenerateResultItem> results() {
		return _res;
	}

	@Override
	public void signalDone(final Map<String, OutputFileC> aOutputFiles) {
		outputFiles = aOutputFiles;

		signalDone();
	}

	@Override
	public void subscribeCompletedItems(@NotNull Observer<GenerateResultItem> aGenerateResultItemObserver) {
		completedItems.subscribe(aGenerateResultItemObserver);
	}
}
