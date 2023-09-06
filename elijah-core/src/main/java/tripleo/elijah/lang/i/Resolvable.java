package tripleo.elijah.lang.i;

/**
 * Created 8/20/20 7:24 PM
 */
public interface Resolvable {
	OS_Element getResolvedElement();

	boolean hasResolvedElement();

	void setResolvedElement(OS_Element element);
}
