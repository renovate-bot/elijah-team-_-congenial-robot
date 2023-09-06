package tripleo.elijah.comp.nextgen;

import org.jdeferred2.Promise;

import java.io.File;
import java.nio.file.Path;

public interface CP_Path {
	CP_SubFile subFile(String aFile);

	CP_Path child(String aPath0);

	Path getPath();

	Promise<Path, Void, Void> getPathPromise();

	File toFile();

	File getRootFile();

	CP_Path getParent();

	String getName();

	_CP_RootPath getRootPath();
}
