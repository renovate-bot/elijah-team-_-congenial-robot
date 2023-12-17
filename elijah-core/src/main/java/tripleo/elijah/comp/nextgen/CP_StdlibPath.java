package tripleo.elijah.comp.nextgen;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;

import java.io.File;
import java.nio.file.Path;

public class CP_StdlibPath implements CP_Path, _CP_RootPath {
	private final Compilation                      c;
	private final DeferredObject<Path, Void, Void> _pathPromise = new DeferredObject<>();

	public CP_StdlibPath(final Compilation aC) {
		c = aC;
	}

	@Override
	public @Nullable CP_SubFile subFile(final String aFile) {
		return null;
	}

	@Override
	public CP_Path child(final String aSubPath) {
		return new CP_SubFile(this, aSubPath).getPath();
	}

	@Override
	public @NotNull Path getPath() {
		return Path.of("lib_elijjah");
	}

	@Override
	public @NotNull Promise<Path, Void, Void> getPathPromise() {
		return _pathPromise;
	}

	@Override
	public @NotNull File toFile() {
		return getPath().toFile();
	}

	@Override
	public @Nullable File getRootFile() {
		return null;
	}

	@Override
	public @Nullable CP_Path getParent() {
		return null;
	}

	@Override
	public @Nullable String getName() {
		return null;
	}

	@Override
	public @NotNull _CP_RootPath getRootPath() {
		return this;
	}
}
