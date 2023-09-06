/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.DependencyRef;

/**
 * Created 9/13/21 4:01 AM
 */
public class CDependencyRef implements DependencyRef {
	// TODO change to CP_File (output: SOURCES, lsp: DIRNAME, filename: STRING)
	private final String headerFile;

	@Contract(pure = true)
	public CDependencyRef(String aHeaderFile) {
		headerFile = aHeaderFile;
	}

	public String getHeaderFile() {
		return headerFile;
	}

	@Override
	public @NotNull String jsonString() {
		return "{type: \"CDependencyRef\", headerFile: " + headerFile + "}";
	}
}

//
//
//
