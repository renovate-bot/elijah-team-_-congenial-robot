/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.work;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 4/26/21 4:24 AM
 */
public class WorkList {
	private       boolean       _done;
	private final List<WorkJob> jobs = new ArrayList<>();

	public void addJob(final WorkJob aJob) {
		jobs.add(aJob);
	}

	public @NotNull ImmutableList<WorkJob> getJobs() {
		return ImmutableList.copyOf(jobs);
	}

	public boolean isDone() {
		return _done;
	}

	public boolean isEmpty() {
		return jobs.size() == 0;
	}

	public void setDone() {
		_done = true;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab://
