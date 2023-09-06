package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.ContextInfo;

import java.util.List;
import java.util.function.Predicate;

public interface LookupResultList {
	void add(String name, int level, OS_Element element, Context aContext);

	void add(String name, int level, OS_Element element, Context aContext, ContextInfo aImportInfo);

	@Nullable
	OS_Element chooseBest(List<Predicate<OS_Element>> l);

	List<LookupResult> getMaxScoredResults(List<Predicate<OS_Element>> l);

	List<LookupResult> results();
}
