/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.builder;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.TypeAliasStatementImpl;

/**
 * Created 12/22/20 10:22 PM
 */
public class TypeAliasBuilder extends ElBuilder {
	private Context         _context;
	private OS_Element      _parent;
	private IdentExpression newAlias;
	private Qualident       oldElement;

	@Override
	public @NotNull TypeAliasStatement build() {
		TypeAliasStatement typeAliasStatement = new TypeAliasStatementImpl(_parent);
		typeAliasStatement.make(newAlias, oldElement);
		return typeAliasStatement;
	}

	public Qualident getBecomes() {
		return oldElement;
	}

	public void setBecomes(Qualident oldElement) {
		this.oldElement = oldElement;
	}

	public IdentExpression getIdent() {
		return newAlias;
	}

	@Override
	public void setContext(Context context) {
		_context = context;
		// TODO this is a very important potential bug
		//  where ident's may not be getting the right context
		//  because of non-use of Parser.cur in the Builders
		newAlias.setContext(context);
	}

	public void setIdent(IdentExpression newAlias) {
		this.newAlias = newAlias;
	}
}

//
//
//
