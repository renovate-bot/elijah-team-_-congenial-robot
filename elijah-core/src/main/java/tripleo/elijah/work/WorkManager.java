/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.work;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created 4/26/21 4:22 AM
 */
public class WorkManager {
	@NotNull List<WorkList> doneWork = new ArrayList<WorkList>();
	@NotNull List<WorkList> jobs     = new ArrayList<WorkList>();

	public void addJobs(final WorkList aList) {
		jobs.add(aList);
	}

	public void drain() {
		while (true) {
			@Nullable WorkJob w = next();
			if (w == null) break;
			w.run(this);
		}
	}

	@Nullable
	public WorkJob next() {
		Iterator<WorkList> workListIterator = jobs.iterator();
		while (true) {
			if (workListIterator.hasNext()) {
				WorkList workList = workListIterator.next();
//			for (WorkList workList :jobs) {
				if (!workList.isDone()) {
					for (WorkJob w : workList.getJobs()) {
						if (!w.isDone())
							return w;
					}
					workList.setDone();
				} else {
					workListIterator.remove();
					doneWork.add(workList);
					return next();
				}
			} else
				return null;
		}
//		return null;
	}

	public int totalSize() {
//		final Integer x = jobs.stream().collect(new Collector<WorkList, List<Integer>, Integer>() {
//			final List<Integer> li = new ArrayList<Integer>();
//
//			@Override
//			public Supplier<List<Integer>> supplier() {
//				return () -> li;
//			}
//
//			@Override
//			public BiConsumer<List<Integer>, WorkList> accumulator() {
//				return (a, b) -> a.add(b.getJobs().size());
//			}
//
//			@Override
//			public BinaryOperator<List<Integer>> combiner() {
//				return null;
//			}
//
//			@Override
//			public Function<List<Integer>, Integer> finisher() {
//				return null;
//			}
//
//			@Override
//			public Set<Characteristics> characteristics() {
//				return Set.of(Characteristics.UNORDERED);
//			}
//		});

//		final int reduce = jobs.stream()
//				.reduce(0, (Integer a, WorkList b) -> {
//					return a + b.getJobs().size();
//				});
		int totalSize = 0;
		for (WorkList job : jobs) {
			totalSize += job.getJobs().size();
		}
		return totalSize;
	}
}

//
//
//
