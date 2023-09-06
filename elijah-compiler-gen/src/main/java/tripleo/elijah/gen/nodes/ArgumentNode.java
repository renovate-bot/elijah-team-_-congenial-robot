/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 *
 */
package tripleo.elijah.gen.nodes;

import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.IdentExpression;

/**
 * @author Tripleo(acer)
 *
 */
public class ArgumentNode {

	private final TypeRef         typeRef;
	private       IdentExpression ident;
	private       String          genType;
	private       String          varName;

	public ArgumentNode(final String varName, final TypeRef typeRef) {
		this.varName = varName;
		this.typeRef = typeRef;
	}

	public TypeRef getTypeRef() {
		return typeRef;
	}

	public IdentExpression getIdent() {
		return ident;
	}

	public void setIdent(final IdentExpression ident) {
		this.ident = ident;
	}

	public String getGenType() {
		// return typeRef.genGenType(); // TODO ??
		return typeRef.genType();
	}

	public void setGenType(final String genType) {
		this.genType = genType;
	}

	public String getGenName() {
		return String.format("va%s", getVarName());
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(final String _varName) {
		this.varName = _varName;
	}
}

//
//
//
