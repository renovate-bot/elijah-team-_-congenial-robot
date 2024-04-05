package tripleo.elijah_durable_congenial.lang.nextgen.names.impl;

import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;

public class ENU_PackageRef implements EN_Understanding {
	private final OS_Package pkg;

	public ENU_PackageRef(final OS_Package aPkg) {
		pkg = aPkg;
	}

	public OS_Package getPackage() {
		return pkg;
	}
}
