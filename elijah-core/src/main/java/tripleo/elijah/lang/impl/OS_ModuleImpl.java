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
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/*
 * Created on Sep 1, 2005 8:16:32 PM
 *
 * $Id$
 *
 */
public class OS_ModuleImpl implements OS_Element, OS_Container, tripleo.elijah.lang.i.OS_Module {

	private final   Stack<Qualident>     packageNames_q = new Stack<Qualident>();
	public @NotNull Attached             _a             = new AttachedImpl();
	public @NotNull List<EntryPoint>     entryPoints    = new ArrayList<EntryPoint>();
	public @NotNull List<ModuleItem>     items          = new ArrayList<ModuleItem>();
	public          OS_Module            prelude;
	private         IndexingStatement    indexingStatement;
	private         String               _fileName;
	private         LibraryStatementPart lsp;
	private         Compilation          parent;

	@Override
	public void add(final ModuleItem anElement) {
		items.add(anElement);
	}

	@Override
	public void addToContainer(final OS_Element anElement) {
		if (!(anElement instanceof ModuleItem)) {
			parent.getErrSink()
					.info(String.format("[Module#add] not adding %s to OS_Module", anElement.getClass().getName()));
			return; // TODO FalseAddDiagnostic
		}
		add((ModuleItem) anElement);
	}

	@Override // OS_Container
	public @NotNull List<OS_NamedElement> items() {
		final var c = getItems().stream().filter(input -> input instanceof OS_NamedElement);

		return c.collect(Collectors.toList());
	}

	@Override
	public void addDocString(final Token s1) {
		throw new NotImplementedException();
	}

	@Override
	public @NotNull List<EntryPoint> entryPoints() {
		return entryPoints;
	}

	@Override
	public @org.jetbrains.annotations.Nullable OS_Element findClass(final String aClassName) {
		for (final ModuleItem item : items) {
			if (item instanceof ClassStatement) {
				if (((ClassStatement) item).getName().equals(aClassName))
					return item;
			}
		}
		return null;
	}

	@Override
	public void finish() {
//		parent.put_module(_fileName, this);
	}

	private void find_multiple_items() {
		getCompilation().getFluffy().find_multiple_items(this);
	}

	@Override
	public String getFileName() {
		return _fileName;
	}

	@Override
	public @NotNull Collection<ModuleItem> getItems() {
		return items;
	}

	@Override
	public LibraryStatementPart getLsp() {
		return lsp;
	}

	@Override
	public void setLsp(LibraryStatementPart aLsp) {
		lsp = aLsp;
	}

	@Override
	public boolean hasClass(final String className) {
		for (final ModuleItem item : items) {
			if (item instanceof ClassStatement) {
				if (((ClassStatement) item).getName().equals(className))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPrelude() {
		return prelude == this;
	}

	@Override
	public void postConstruct() {
		find_multiple_items();
		//
		// FIND ALL ENTRY POINTS (should only be one per module)
		//
		for (final ModuleItem item : items) {
			if (item instanceof ClassStatement) {
				ClassStatement classStatement = (ClassStatement) item;
				if (MainClassEntryPoint.isMainClass(classStatement)) {
					Collection<ClassItem> x = classStatement.findFunction("main");
					Collection<ClassItem> found = Collections2.filter(x, new Predicate<ClassItem>() {
						@Override
						public boolean apply(@org.checkerframework.checker.nullness.qual.Nullable ClassItem input) {
							assert input != null;
							FunctionDef fd = (FunctionDef) input;
							return MainClassEntryPoint.is_main_function_with_no_args(fd);
						}
					});
//					Iterator<ClassStatement> zz = x.stream()
//							.filter(ci -> ci instanceof FunctionDef)
//							.filter(fd -> is_main_function_with_no_args((FunctionDef) fd))
//							.map(found1 -> (ClassStatement) found1.getParent())
//							.iterator();

					/*
					 * List<ClassStatement> entrypoints_stream = x.stream() .filter(ci -> ci
					 * instanceof FunctionDef) .filter(fd ->
					 * is_main_function_with_no_args((FunctionDef) fd)) .map(found1 ->
					 * (ClassStatement) found1.getParent()) .collect(Collectors.toList());
					 */

					final int eps = entryPoints.size();
					for (ClassItem classItem : found) {
						entryPoints.add(new MainClassEntryPoint((ClassStatement) classItem.getParent()));
					}
					assert entryPoints.size() == eps || entryPoints.size() == eps + 1; // TODO this will fail one day

					SimplePrintLoggerToRemoveSoon.println_out_2("243 " + entryPoints + " " + _fileName);
//					break; // allow for "extend" class
				}
			}

		}
	}

	@Override
	public OS_Module prelude() {
		return prelude;
	}

	/**
	 * The last package declared in the source file
	 *
	 * @return a new OS_Package instance or default_package
	 */
	@Override
	@NotNull
	public OS_Package pullPackageName() {
		if (packageNames_q.empty())
			return OS_Package.default_package;
		// Dont know if this is correct behavior
		return parent.makePackage(packageNames_q.peek());
	}

	@Override
	public OS_Package pushPackageNamed(final Qualident aPackageName) {
		packageNames_q.push(aPackageName);
		return parent.makePackage(aPackageName);
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		// TODO Auto-generated method stub

		//public @NotNull Attached             _a             = new AttachedImpl();
		//private final   Stack<Qualident>     packageNames_q = new Stack<Qualident>();
		//public @NotNull List<EntryPoint>     entryPoints    = new ArrayList<EntryPoint>();
		//private         IndexingStatement    indexingStatement;
		//private LibraryStatementPart lsp;


		sw.fieldString("filename", _fileName);
		sw.fieldString("prelude", prelude != null ? prelude.getFileName() : "<unknown>");
		sw.fieldString("parent", getCompilation().getCompilationNumberString());


		//var l = sw.createList();int i=1;
		//for (ModuleItem item : items) {
		//	var r = sw.createRef(item);
		//	sw.fieldRef("item%i".formatted(i++), r);
		//}
		sw.fieldList("items", items);
	}

	@Override
	public void setPrelude(OS_Module success) {
		prelude = success;
	}

	@Override
	public void setFileName(final String fileName) {
		this._fileName = fileName;
	}

	@Override
	public void setIndexingStatement(final IndexingStatement i) {
		indexingStatement = i;
	}

	/**
	 * A module has no parent which is an element (not even a package - this is not
	 * Java).<br>
	 * If you want the Compilation use the member {@link #parent}
	 *
	 * @return null
	 */

	@Override
	public Context getContext() {
		return _a.getContext();
	}

	/**
	 * @ ensures \result == null
	 */
	@Override
	public @org.jetbrains.annotations.Nullable OS_Element getParent() {
		return null;
	}

	@Override
	public void setParent(@NotNull final Compilation parent) {
		this.parent = parent;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.addModule(this); // visitModule
	}

	@Override
	public @NotNull Compilation getCompilation() {
		return parent;
	}

	@Override
	public void setContext(final ModuleContext mctx) {
		_a.setContext(mctx);
	}

	public void remove(ClassStatement cls) {
		items.remove(cls);
	}

	/**
	 * Get a class byImpl name. Must not be qualified. Wont return a
	 * {@link NamespaceStatement} Same as {@link #findClass(String)}
	 *
	 * @param name the class weImpl are looking for
	 * @return either the class orImpl null
	 */
	@Nullable
	public ClassStatement getClassByName(final String name) {
		for (final ModuleItem item : items) {
			if (item instanceof ClassStatement)
				if (((ClassStatement) item).getName().equals(name))
					return (ClassStatement) item;
		}
		return null;
	}

	@Override
	public String toString() {
		return "<OS_Module %s>".formatted(_fileName);
	}
}

//
//
//
