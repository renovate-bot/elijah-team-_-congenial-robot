/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.ContextInfo;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.LookupResult;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Tripleo(sb)
 * <p>
 * Created Dec 9, 2019 at 3:30:44 PM
 */
public class LookupResultListImpl implements LookupResultList {

	private final List<LookupResult> _results = new ArrayList<LookupResult>();

	@Override
	public void add(final String name, final int level, final OS_Element element, final Context aContext) {
		for (final LookupResult result : _results) {
			if (result.getElement() == element)
				return; // TODO hack for bad algorithm
		}
		_results.add(new LookupResultImpl(name, element, level, aContext));
	}

	@Override
	public void add(final String name, final int level, final OS_Element element, final Context aContext,
					final ContextInfo aImportInfo) {
		for (final LookupResult result : _results) {
			if (result.getElement() == element)
				return; // TODO hack for bad algorithm
		}
		_results.add(new LookupResultImpl(name, element, level, aContext, aImportInfo));
	}

	@Override
	@Nullable
	public OS_Element chooseBest(final @Nullable List<Predicate<OS_Element>> l) {
		final List<LookupResult> r;
		if (l != null) {
			r = getMaxScoredResults(l);
		} else {
			r = results();
		}
		//
		if (r.size() == 1)
			return r.get(0).getElement();
//		else if (r.size() == 2) {
//			if (r.get(0).getElement() == r.get(1).getElement()) {
////				r.remove(1);
//				return r.get(0).getElement();
//			} else {
//				return null;
//			}
//		} else if (r.size() == 3) {
//			if (r.get(0).getElement() == r.get(1).getElement()
//			&& (r.get(1).getElement() == r.get(2).getElement())) {
////				r.remove(1);
////				r.remove(2);
//				return r.get(0).getElement();
//			}
//		}
		return null; // throw new NotImplementedException();
	}

	@Override
	public List<LookupResult> getMaxScoredResults(List<Predicate<OS_Element>> l) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Error");
	}

	@Override
	public @NotNull List<LookupResult> results() { // TODO want ImmutableList
		return _results;
	}

	private @NotNull List<LookupResult> getMaxScoredResults1(final @NotNull List<Predicate<OS_Element>> l) {
		final Map<LookupResult, Integer> new_results = new HashMap<LookupResult, Integer>();
		int                              maxScore    = 0;

		for (final LookupResult lookupResult : _results) {
			int score = 0;
			for (final Predicate<OS_Element> predicate : l) {
				if (predicate.test(lookupResult.getElement()))
					score++;
			}
			if (score >= maxScore /* && maxScore != 0 */) {
				maxScore = score;
				new_results.clear();
				new_results.put(lookupResult, score);
			} else
				new_results.put(lookupResult, score);
		}
		return new ArrayList<LookupResult>(new_results.keySet());
	}
}

//
//
//
