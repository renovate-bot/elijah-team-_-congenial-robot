/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.instructions;

import tripleo.elijah.stages.gen_fn.BaseEvaFunction;

/**
 * Created 9/10/20 3:17 PM
 */
public class Label implements InstructionArgument {
	private final BaseEvaFunction gf;
	long   index;
	String name;
	private int number;

//	public Label(String name) {
//		this.name = name;
//	}

	public Label(final BaseEvaFunction gf) {
		this.gf = gf;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("<Label %s index:%d number:%d>", getName(), getIndex(), getNumber());
	}

	/**
	 * Corresponds to pc
	 *
	 * @param index pc
	 */
	public void setIndex(final long index) {
		this.index = index;
	}

	public long getIndex() {
		return index;
	}

	/**
	 * Corresponds to the number of labels
	 *
	 * @param number
	 */
	public void setNumber(final int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
}

//
//
//
