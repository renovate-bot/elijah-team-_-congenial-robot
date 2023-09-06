/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.PackageContext;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.Qualident;

import java.util.ArrayList;
import java.util.List;

/*
 * Created on 5/3/2019 at 21:41
 *
 * $Id$
 *
 */
public class OS_PackageImpl implements tripleo.elijah.lang.i.OS_Package {
	private final List<OS_Element> elements = new ArrayList<OS_Element>();
	int       _code;
	Qualident _name;
	private PackageContext _ctx;

	// TODO packages, elements

	public OS_PackageImpl(final Qualident aName, final int aCode) {
		_code = aCode;
		_name = aName;
	}

	@Override
	public void addElement(final OS_Element element) {
		elements.add(element);
	}

	//
	// ELEMENTS
	//

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public @NotNull List<OS_Element> getElements() {
		return elements;
	}

	//
	// NAME
	//

	@Override
	public String getName() {
		if (_name == null) {
//			tripleo.elijah.util.Stupidity.println_err_2("*** name is null for package");
			return "";
		}
		return _name.toString();
	}

	@Override
	public Qualident getName2() {
		return _name;
	}

	@Override
	public void setContext(PackageContext cur) {
		_ctx = cur;
	}
}

//
//
//
