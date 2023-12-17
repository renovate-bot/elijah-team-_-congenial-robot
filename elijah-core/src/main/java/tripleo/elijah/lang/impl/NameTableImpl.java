/**
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tripleo
 * <p>
 * Created Mar 29, 2020 at 7:00:10 PM
 */
public class NameTableImpl implements tripleo.elijah.lang.i.NameTable {

	@NotNull Map<String, TypedElement> members = new HashMap<String, TypedElement>();

	class TypedElement {
		OS_Element element;
		OS_Type    type;

		public TypedElement(final OS_Element element2, final OS_Type dtype) {
			this.element = element2;
			this.type    = dtype;
		}

		@Override
		public @NotNull String toString() {
			return "TypedElement{" + "element=" + element + ", type=" + type + '}';
		}
	}

	@Override
	public void add(final OS_Element element, final String name, final OS_Type dtype) {
//		element.setType(dtype);
		members.put(name, new TypedElement(element, dtype));
		SimplePrintLoggerToRemoveSoon.println_err_2("[NameTable#add] " + members);
	}
}

//
//
//
