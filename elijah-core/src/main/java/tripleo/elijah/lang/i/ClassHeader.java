package tripleo.elijah.lang.i;

import java.util.List;

public interface ClassHeader {
	List<AnnotationClause> annos();


	ClassInheritance inheritancePart();

	IdentExpression nameToken();

	void setName(IdentExpression aNameToken);


	void setConst(boolean aIsConst);


	void setGenericPart(TypeNameList aTypeNameList);

	TypeNameList genericPart();


	void setType(ClassTypes ct);

	ClassTypes type();
}
