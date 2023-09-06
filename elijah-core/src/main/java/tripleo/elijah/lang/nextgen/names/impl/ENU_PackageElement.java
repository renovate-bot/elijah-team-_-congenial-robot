package tripleo.elijah.lang.nextgen.names.impl;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;

/*
 * A package element will `resolve' to a Package or a Item
 *
 * $ (package/import) tripleo.elijah.lang.nextgen.names.impl.ENU_PackageElement
 *
 * >> ENU_PackageElement resolves to Item
 * >> all else to Package {/ENU_PackageName}
 * >> tripleo is PackageRoot
 * >> ENU_PackageElement is Package PackageTerminator
 *
 */
public class ENU_PackageElement implements EN_Understanding {
	interface PER {
	}

	class Package implements PER {
		private final OS_Package _package;

		Package(final OS_Package aPackage) {
			_package = aPackage;
		}

		public OS_Package getPackage() {
			return this._package;
		}
	}

	class Item implements PER {
		private final OS_Element _item;

		Item(final OS_Element aItem) {
			_item = aItem;
		}

		public OS_Element getItem() {
			return this._item;
		}
	}

	@NotNull DeferredObject<PER, Void, Void> resolved = new DeferredObject<>();

	public Promise<PER, Void, Void> resolved() {
		return resolved;
	}
}
