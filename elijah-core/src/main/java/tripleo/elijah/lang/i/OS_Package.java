package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.PackageContext;
import tripleo.elijah.lang.impl.OS_PackageImpl;

import java.util.List;

public interface OS_Package {
	// TODO Living?
	OS_Package default_package = new OS_PackageImpl(null, 0);

	void addElement(OS_Element element);

	Context getContext();

	List<OS_Element> getElements();

	String getName();

	Qualident getName2();

	void setContext(PackageContext cur);
}
