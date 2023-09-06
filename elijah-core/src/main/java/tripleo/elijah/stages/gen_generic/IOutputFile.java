/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.stages.gen_generic;

import tripleo.util.buffer.Buffer;

import java.util.List;

/**
 * Created 9/13/21 11:12 PM
 */
public interface IOutputFile {
	String getOutput();

	void putBuffer(Buffer aBuffer);

	void putDependencies(List<DependencyRef> aDependencies);
}

//
//
//
