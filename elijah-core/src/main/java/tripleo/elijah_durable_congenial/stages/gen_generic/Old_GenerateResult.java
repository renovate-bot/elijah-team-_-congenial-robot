/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.stages.gen_c.OutputFileC;
import tripleo.elijah_congenial.pp.IPP_Function;
import tripleo.elijah_congenial.pp.PP_Constructor;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.util.buffer.Buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created 4/27/21 1:11 AM
 */
public class Old_GenerateResult implements GenerateResult {
	final         List<Old_GenerateResultItem> _res           = new ArrayList<Old_GenerateResultItem>();
	private final List<IGenerateResultWatcher> _watchers      = new ArrayList<>();
	private final Subject<GenerateResultItem>  completedItems = ReplaySubject.<GenerateResultItem>create();
	private       int                          bufferCounter  = 0;

	private Map<String, OutputFileC> outputFiles;
	private boolean                  _closed;

	public Old_GenerateResult() {
		//System.err.println("*** ctor Old_GenerateResult");
	}

	@Override
	public void close() {
		this._closed = true;
	}

	@Override
	public void add(@NotNull Buffer b, @NotNull EvaNode n, @NotNull TY ty, @Nullable LibraryStatementPart aLsp, @NotNull Dependency d) {
		if (_closed) throw new IllegalStateException("closed GenerateResult");

		if (aLsp == null) {
			SimplePrintLoggerToRemoveSoon.println_err_3("*************************** buffer --> " + b.getText());
			return;
		}

		++bufferCounter;
		final Old_GenerateResultItem item = new Old_GenerateResultItem(ty, b, n, aLsp, d, bufferCounter);
		_res.add(item);

		_watchers.forEach(w -> w.item(item));
	}

	@Override
	public void addClass(@NotNull TY ty, @NotNull EvaClass aClass, @NotNull Buffer aBuf, LibraryStatementPart aLsp) {
		add(aBuf, aClass, ty, aLsp, aClass.getDependency());
	}

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addFunction(tripleo.elijah.stages.gen_fn.BaseEvaFunction, tripleo.util.buffer.Buffer, tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.ci.LibraryStatementPart)
	 */
	@Override
	public void addFunction(IPP_Function aGeneratedFunction, @NotNull Buffer aBuffer, @NotNull TY aTY, LibraryStatementPart aLsp) {
		add(aBuffer, aGeneratedFunction.get2Carrier().getEvaNodeEscapeHatch(), aTY, aLsp, aGeneratedFunction.get2Carrier().getDependency());
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

	@Override
	public void addConstructor(final PP_Constructor aEvaConstructor, final Buffer aBuffer, final TY aTY, final LibraryStatementPart aLsp) {
//addFunction(aEvaConstructor.get2Carrier(), aBuffer, aTY, aLsp);
		System.err.println("9998-0095 "+aEvaConstructor.get2Carrier().getFunctionName());
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

	// region REACTIVE

	@Override
	public void subscribeCompletedItems(@NotNull Observer<GenerateResultItem> aGenerateResultItemObserver) {
		completedItems.subscribe(aGenerateResultItemObserver);
	}

	// endregion
}

//
//
//
