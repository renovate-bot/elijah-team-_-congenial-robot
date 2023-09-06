/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.NormalTypeName;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.i.TypeModifiers;

import java.util.Objects;

public abstract class AbstractTypeName implements NormalTypeName {

	protected boolean       pr_out;
	protected boolean       pr_constant;
	protected boolean       pr_in;
	protected Qualident     pr_name;
	protected boolean       pr_reference;
	protected TypeModifiers tm;
	private   boolean       isNullable = false;

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof NormalTypeName))
			return false;
		final NormalTypeName that = (NormalTypeName) o;
		return getConstant() == that.getConstant() && getReference() == that.getReference() && getOut() == that.getOut()
				&& getIn() == that.getIn() &&
//				type == that.type &&
				getModifiers().containsAll(that.getModifiers()) && getName().equals(that.getName());
	}

	@Override
	public boolean getConstant() {
		return pr_constant;
	}

	@Override
	public boolean getIn() {
		return pr_in;
	}

	@Override
	public String getName() {
		return pr_name.toString();
	}

	@Override
	public boolean getOut() {
		return pr_out;
	}

	@Override
	public boolean getReference() {
		return pr_reference;
	}

	@Override
	public void setConstant(final boolean s) {
		pr_constant = s;
	}

	@Override
	public void setOut(final boolean s) {
		pr_out = s;
	}

	@Override
	public void setReference(final boolean s) {
		pr_reference = s;
	}

	@Override
	public void setIn(final boolean s) {
		pr_in = s;
	}

	@Override
	public void setName(final Qualident s) {
		pr_name = s;
	}

	@Override
	public void setNullable() {
		this.isNullable = true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tm, pr_constant, pr_reference, pr_out, pr_in, pr_name, isNullable);
	}

	@Override
	public boolean isNull() {
		return !pr_constant && !pr_reference && !pr_out && !pr_in && (pr_name == null);
	}
}

//
//
//
