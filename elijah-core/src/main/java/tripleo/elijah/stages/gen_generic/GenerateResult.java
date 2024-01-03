package tripleo.elijah.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.stages.gen_c.OutputFileC;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.pp.IPP_Function;
import tripleo.elijah.stages.pp.PP_Constructor;
import tripleo.elijah.stages.pp.PP_Function;
import tripleo.util.buffer.Buffer;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface GenerateResult {

	void close();

	void add(Buffer b, EvaNode n, TY ty, LibraryStatementPart aLsp, Dependency d);

	void addClass(TY ty, EvaClass aClass, Buffer aBuf, LibraryStatementPart aLsp);

	/* (non-Javadoc)
	 * @see tripleo.elijah.stages.gen_generic.GenerateResult#addFunction(tripleo.elijah.stages.gen_fn.BaseEvaFunction, tripleo.util.buffer.Buffer, tripleo.elijah.stages.gen_generic.Old_GenerateResult.TY, tripleo.elijah.ci.LibraryStatementPart)
	 */
	void addFunction(IPP_Function aGeneratedFunction, @NotNull Buffer aBuffer, @NotNull TY aTY, LibraryStatementPart aLsp);

	void additional(GenerateResult aGenerateResult);

	void addConstructor(PP_Constructor aEvaConstructor, Buffer aBuffer, TY aTY, LibraryStatementPart aLsp);

	void completeItem(GenerateResultItem aGenerateResultItem);

	void addNamespace(TY ty, EvaNamespace aNamespace, Buffer aBuf, LibraryStatementPart aLsp);

	void addWatcher(IGenerateResultWatcher w);

	void observe(Observer<GenerateResultItem> obs);

	void signalDone();

	void outputFiles(Consumer<Map<String, OutputFileC>> cmso);

	List<Old_GenerateResultItem> results();

	void signalDone(Map<String, OutputFileC> aOutputFiles);

	public enum TY {
		HEADER, IMPL, PRIVATE_HEADER
	}

	void subscribeCompletedItems(Observer<GenerateResultItem> aGenerateResultItemObserver);
}
