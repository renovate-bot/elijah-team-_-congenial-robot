package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;

public interface OS_Module extends OS_Element {
	void add(ModuleItem anElement);

	@NotNull List<EntryPoint> entryPoints();

	@Nullable OS_Element findClass(String aClassName);

	void finish();

	@NotNull Compilation getCompilation();

	@Override Context getContext();

	String getFileName();

	@Override @Nullable OS_Element getParent();

	LibraryStatementPart getLsp();

	@Override void visitGen(@NotNull ElElementVisitor visit);

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

	@Override void serializeTo(SmallWriter sw);

	@NotNull Collection<ModuleItem> getItems();
}
