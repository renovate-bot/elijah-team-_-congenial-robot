package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;

public interface OS_Module extends OS_Element {
	void add(OS_Element anElement);

	@NotNull
	List<EntryPoint> entryPoints();

	@org.jetbrains.annotations.Nullable
	OS_Element findClass(String aClassName);

	void finish();

	@NotNull
	Compilation getCompilation();

	@Override
	Context getContext();

	String getFileName();

	@NotNull
	Collection<ModuleItem> getItems();

	LibraryStatementPart getLsp();

	@Override
	@org.jetbrains.annotations.Nullable
	OS_Element getParent();

	boolean hasClass(String className); // OS_Container

	boolean isPrelude();

	void postConstruct();

	OS_Module prelude();

	OS_Package pullPackageName();

	OS_Package pushPackageNamed(Qualident aPackageName);

	void setContext(ModuleContext mctx);

	void setFileName(String fileName);

	void setIndexingStatement(IndexingStatement idx);

	void setLsp(@NotNull LibraryStatementPart lsp);

	void setParent(@NotNull Compilation parent);

	void setPrelude(OS_Module success);

	@Override
	void visitGen(@NotNull ElElementVisitor visit);

	@Override
	void serializeTo(SmallWriter sw);
}
