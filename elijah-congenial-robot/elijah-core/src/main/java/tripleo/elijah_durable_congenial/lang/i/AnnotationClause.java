package tripleo.elijah_durable_congenial.lang.i;

import java.util.Collection;

public interface AnnotationClause {
	void add(AnnotationPart ap);

	Collection<? extends AnnotationPart> aps();
}
