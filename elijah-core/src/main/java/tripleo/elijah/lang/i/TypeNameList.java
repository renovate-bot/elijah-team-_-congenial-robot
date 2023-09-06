package tripleo.elijah.lang.i;

import java.util.List;

public interface TypeNameList {
	void add(TypeName tn);

	TypeName get(int index);

	List<TypeName> p();

	int size();

	@Override
	String toString();
}
