/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.ClassContext;

/**
 * Created 6/22/21 12:59 AM
 */
public enum DecideElObjectType {
	;

	@Contract(pure = true)
	@NotNull
	public static ElObjectType getElObjectType(@NotNull OS_Element input) {
		// Chain of instanceof checks indicates abstraction failure

		if (input instanceof ClassStatement)
			return ElObjectType.CLASS;
		if (input instanceof NamespaceStatement)
			return ElObjectType.NAMESPACE;
		if (input instanceof AliasStatement)
			return ElObjectType.ALIAS;
		if (input instanceof VariableSequence)
			return ElObjectType.VAR_SEQ;
		if (input instanceof VariableStatement)
			return ElObjectType.VAR;
		if (input instanceof ConstructorDef)
			return ElObjectType.CONSTRUCTOR;
		if (input instanceof FunctionDef)
			return ElObjectType.FUNCTION;
		if (input instanceof FormalArgListItem)
			return ElObjectType.FORMAL_ARG_LIST_ITEM;
		if (input instanceof OS_Module)
			return ElObjectType.MODULE;
		if (input instanceof ClassContext.OS_TypeNameElement)
			return ElObjectType.TYPE_NAME_ELEMENT;

		return ElObjectType.UNKNOWN;
	}
}

//
//
//
