package tripleo.elijah.stages.gen_generic;

import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.util.buffer.Buffer;

import java.util.List;

public interface GenerateResultItem {

	Buffer buffer();

	List<DependencyRef> dependencies();

	Dependency getDependency();

	String jsonString();

	LibraryStatementPart lsp();

	EvaNode node();

	String output();

	@Override
	String toString();

	GenerateResult.TY __ty();

	int _counter();
}