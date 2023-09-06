/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.Dependency;
import tripleo.elijah.stages.gen_generic.DependencyRef;
import tripleo.elijah.stages.gen_generic.IOutputFile;
import tripleo.util.buffer.Buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created 9/13/21 10:50 PM
 */
public class OutputFileC implements IOutputFile {
	private final List<Buffer>        buffers      = new ArrayList<>(); // LinkedList??
	private final List<DependencyRef> dependencies = new ArrayList<>();
	private final List<Dependency>    notedDeps    = new ArrayList<>();
	private final String              output;

	public OutputFileC(String aOutput) {
		output = aOutput;
	}

	@Override
	public @NotNull String getOutput() {
		final StringBuilder sb = new StringBuilder();

		final Predicate<Dependency> dependencyPredicate = next -> {
			for (DependencyRef dependency : dependencies) {
				if (next.dref == dependency) {
					return true;
				}
			}

			return false;
		};

		final List<Dependency> wnd = notedDeps.stream()
				.filter(dependencyPredicate)
				.collect(Collectors.toList());

/*
		//new ArrayList<Dependency>(notedDeps);
		final Iterator<Dependency> iterator = wnd.iterator();

		// TODO figure this dumb shht out
		while (iterator.hasNext()) {
			Dependency next = iterator.next();
			for (DependencyRef dependency : dependencies) {
				if (next.dref == dependency) {
					iterator.remove();
				}
			}
		}
*/

		assert wnd.size() == dependencies.size();

		for (DependencyRef dependencyRaw : dependencies) {
			CDependencyRef dependency = (CDependencyRef) dependencyRaw;
			String         headerFile = dependency.getHeaderFile();
			String         output     = String.format("#include \"%s\"\n", headerFile.substring(1));
			sb.append(output);
		}

		sb.append('\n');

		for (Dependency dependency : wnd) {
			String resolvedString = String.valueOf(dependency.resolved);
			String output         = String.format("//#include \"%s\" // for %s\n", "nothing.h", resolvedString);
			sb.append(output);
		}

		sb.append('\n');

		for (Buffer buffer : buffers) {
			sb.append(buffer.getText());
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public void putBuffer(Buffer aBuffer) {
		buffers.add(aBuffer);
	}

	@Override
	public void putDependencies(@NotNull List<DependencyRef> aDependencies) {
		dependencies.addAll(aDependencies);
	}

	public void putDependencies(@NotNull Set<Dependency> aNotedDeps) {
		notedDeps.addAll(aNotedDeps);
	}
}

//
//
//
