/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.diagnostic;

import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Locate a Token or Token range<br><br>
 * <p>
 * All methods return 0 or null for atrificial Tokens<br><br>
 * <p>
 * Created 12/26/20 5:32 AM
 */
public interface Locatable {
	int getColumn();

	int getColumnEnd();

	@Nullable File getFile();

	int getLine();

	int getLineEnd();
}

//
//
//
