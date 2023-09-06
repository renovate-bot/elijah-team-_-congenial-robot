/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

// Referenced classes of package pak2:
//			TypeNameList

import java.util.Collection;

public interface NormalTypeName extends TypeName, Resolvable {

//	@Override
//	boolean isNull();

	void addGenericPart(TypeNameList tn2);

	boolean getConstant();

	TypeNameList getGenericPart();

	boolean getIn();

	Collection<TypeModifiers> getModifiers();

	String getName();

	boolean getOut();

	Qualident getRealName();

	boolean getReference();

	void setConstant(boolean flag);

	@Override
	void setContext(Context cur);

	void setIn(boolean flag);

	void setName(Qualident s);

	void setNullable();

	void setOut(boolean flag);

	void setReference(boolean flag);
}

//
//
//
