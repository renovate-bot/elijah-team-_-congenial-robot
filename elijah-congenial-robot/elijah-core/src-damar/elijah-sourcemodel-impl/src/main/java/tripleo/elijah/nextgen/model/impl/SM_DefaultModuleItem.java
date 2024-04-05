package tripleo.elijah.nextgen.model.impl;

import tripleo.elijah.g.GModuleItem;
import tripleo.elijah.nextgen.model.SM_ModuleItem;

final class SM_DefaultModuleItem implements SM_ModuleItem {
	private final GModuleItem item;

	SM_DefaultModuleItem(GModuleItem item) {
		this.item = item;
	}

	@Override
	public GModuleItem _carrier() {
		return item;
	}
}
