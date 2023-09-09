/**
 *
 */
package tripleo.elijah.lang.i;

import java.util.List;

/**
 * @author Tripleo(sb)
 * <p>
 * Created Dec 9, 2019 at 3:32:14 PM
 */
public interface OS_Container extends Documentable {

	void addToContainer(OS_Element anElement);

	List<OS_NamedElement> items();
}

//
//
//
