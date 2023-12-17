/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.OS_Expression;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

public class VariableReferenceImpl extends AbstractExpression
		implements OS_Expression, tripleo.elijah.lang.i.VariableReference {

	// List<VR_Parts> parts = new ArrayList<VR_Parts>();
	OS_Type _type;
	String  main;

	/**
	 * Called from ElijahParser.variableReference.  Will `setMain' later
	 */
	public VariableReferenceImpl() {
		//NotImplementedException.raise();
		setLeft(this); // TODO is this better left null?
		// no contract specifies NotNull...
		setKind(ExpressionKind.VARREF);
	}

	@Override
	public String getName() {
//		if (parts.size() >0) throw new IllegalStateException();
		return main;
	}

	@Override
	public OS_Type getType() {
		return _type;
	}

	/**
	 * * no parts, just an ident ' * qualident not implemented * all parts is
	 * dotpart array can be simple too, depending and so can proccall
	 *
	 * @return if no parts specified
	 */
	@Override
	public boolean is_simple() {
		return false; // parts.size() == 0; // TODO ; || type==VARREF_SIMPLE??
	}

	@Override
	public void setMain(final String s) {
		main = s;
		SimplePrintLoggerToRemoveSoon.println_out_2(repr_());
	}

	@Override
	public String repr_() {
		return String.format("VariableReference (%s)", main);
	}

	@Override
	public void setMain(final @NotNull Token t) {
		final String s = t.getText();
		main = s;
		SimplePrintLoggerToRemoveSoon.println_out_2(repr_());
	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public String toString() {
		return repr_();
	}

//	interface VR_Parts {
//
//	}

}

//
//
//
