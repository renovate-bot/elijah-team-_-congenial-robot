/**
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;

/**
 * @author Tripleo
 * <p>
 * Created Mar 23, 2020 at 12:40:27 AM
 */
public interface OS_NamedElement {
	/**
	 * <p>The name of the element</p>
	 * <p>Returns an {@link OS_ElementName}, that has #asString()</p>
	 *
	 * <p>TODO Should this be a {@link IdentExpression}?</p>
	 *
	 * @return an {@link OS_ElementName}
	 */
	@Contract(pure = true)
	OS_ElementName name();

	EN_Name getEnName();
}

//
//
//
