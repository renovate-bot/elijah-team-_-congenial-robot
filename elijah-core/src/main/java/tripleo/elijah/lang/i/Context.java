package tripleo.elijah.lang.i;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;

import java.util.ArrayList;
import java.util.List;

public interface Context {
	ContextImpl.Expectation expect(String aName, OS_Element aElement);

	List<ContextImpl.Expectation> getExpectations();

	@NotNull
	Compilation compilation();

	@Nullable Context getParent();

	LookupResultList lookup(@NotNull String name);

	LookupResultList lookup(String name, int level, LookupResultList Result, SearchList alreadySearched,
							boolean one);

	@NotNull
	OS_Module module();

	void addName(EN_Name aName);

	class SearchList {
		@NotNull List<Context> alreadySearched = new ArrayList<>();

		public void add(Context c) {
			alreadySearched.add(c);
		}

		public boolean contains(Context context) {
			return alreadySearched.contains(context);
		}

		public @NotNull ImmutableList<Context> getList() {
			return ImmutableList.copyOf(alreadySearched);
		}
	}
}
