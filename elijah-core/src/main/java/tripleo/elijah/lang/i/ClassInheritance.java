package tripleo.elijah.lang.i;

import java.util.Collection;
import java.util.List;

public interface ClassInheritance {
	void add(TypeName tn);

	void addAll(Collection<TypeName> tns);

	List<TypeName> tns();
}
