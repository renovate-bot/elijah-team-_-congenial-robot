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
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;

import java.util.LinkedList;
import java.util.List;

public abstract class ContextImpl implements tripleo.elijah.lang.i.Context {

//	private OS_Container attached;

	public ContextImpl() {
	}

//	public Context(OS_Container attached) {
//		this.attached = attached;
//	}

	@Override
	public @NotNull Compilation compilation() {
		OS_Module module = module();
		return module.getCompilation();
	}

	@Override
	public LookupResultList lookup(@NotNull final String name) {
		final LookupResultList Result = new LookupResultListImpl();
		return lookup(name, 0, Result, new SearchList(), false);
	}

//	@Deprecated public void add(OS_Element element, String name) {
//		add(element, new IdentExpressionImpl(Helpers.makeToken(name)));
//	}
//
//	@Deprecated public void add(OS_Element element, String name, OS_Type dtype) {
//		add(element, new IdentExpressionImpl(Helpers.makeToken(name)), dtype);
//	}
//
//	public void add(OS_Element element, IExpression name) {
//		tripleo.elijah.util.Stupidity.println_out_2(String.format("104 Context.add: %s %s %s", this, element, name));
//		members.put(name, element);
//	}

//
//	Map<IExpression, OS_Element> members = new HashMap<IExpression, OS_Element>();
//	private NameTable nameTable = new NameTable();
//
//	public void add(OS_Element element, IExpression name, OS_Type dtype) {
//		tripleo.elijah.util.Stupidity.println_out_2(String.format("105 Context.add: %s %s %s %s", this, element, name, dtype));
////		element.setType(dtype);
//		members.put(name, element);
//	}
//
//	public NameTable nameTable() {
//		return this.nameTable ;
//	}

	@Override
	public @NotNull OS_Module module() {
		Context ctx = this;// getParent();
		while (!(ctx instanceof ModuleContext))
			ctx = ctx.getParent();
		return ((ModuleContext) ctx).getCarrier();
	}

	@Override
	public void addName(final EN_Name aName) {
		names.add(aName);
	}

	private final List<EN_Name> names = new LinkedList<>();
}

//
//
//
