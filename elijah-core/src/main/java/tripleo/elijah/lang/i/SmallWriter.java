package tripleo.elijah.lang.i;

import antlr.Token;

import java.util.List;

public interface SmallWriter {

	void fieldToken(String aFieldName, Token aFieldValue);

	void fieldExpression(String aFieldName, IExpression aFieldValue);

	void fieldElement(String aFieldName, OS_Element _parent);

	void fieldString(String aFieldName, String aFieldValue);

	void fieldInteger(String aFieldName, int aFieldValue);

	<E> void fieldList(String aFieldName, List<E> aFieldValue);

	SW_Ref createRef(OS_Element aFieldValue);

	SW_List createList();

	void fieldIdent(String aFieldName, IdentExpression aFieldValue);

	SW_TypenameList createTypeNameList();

	void fieldTypenameList(String aInheritance, SW_TypenameList aInh);

	void fieldRef(String aParent, SW_Ref aPr);

	String getString();

	interface SW_Ref {
		String name();

		OS_Element get();
	}

	interface SW_List {
		void add(OS_Element el); // ??

		List<OS_Element> items();
	}

	interface SW_TypenameList {
		void add(TypeName el); // ??

		List<TypeName> items();
	}
}
