/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_java;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.DependencyRef;

/**
 * Created 9/13/21 4:26 AM
 */
public class JavaDependencyRef implements DependencyRef {
	private String className;
	private String fieldName; // for static fields
	private String packageName;

	@Override
	public @NotNull String jsonString() {
		return "";
	}
}

//
//
//
