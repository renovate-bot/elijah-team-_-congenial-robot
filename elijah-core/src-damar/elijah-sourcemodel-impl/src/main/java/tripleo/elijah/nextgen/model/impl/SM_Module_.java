package tripleo.elijah.nextgen.model.impl;

//import tripleo.elijah.lang.i.ModuleItem;
//import tripleo.elijah.lang.i.OS_Module;
//import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;

import tripleo.elijah.nextgen.model.SM_Module;
import tripleo.elijah.nextgen.model.SM_ModuleItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SM_Module_ implements SM_Module {
	@Override
	public List<SM_ModuleItem> items() {
		return null;
	}
	//private final EIT_ModuleInput EITModuleInput;

	//public SM_Module_(final EIT_ModuleInput aEITModuleInput) {
	//	EITModuleInput = aEITModuleInput;
	//}
	//
	//@Override
	//public @NotNull List<SM_ModuleItem> items() {
	//	final List<SM_ModuleItem> items = new ArrayList<>();
	//
	//	final OS_Module module = EITModuleInput.module();
	//
	//	for (final ModuleItem item : module.getItems()) {
	//		items.add(new SM_DefaultModuleItem(/*item*/));
	//	}
	//
	//	return items;
	//}
}
