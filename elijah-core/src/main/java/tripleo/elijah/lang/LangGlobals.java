package tripleo.elijah.lang;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.OS_Package;

import tripleo.elijah.lang.impl.ConstructorDefImpl;
import tripleo.elijah.lang.impl.OS_PackageImpl;

import tripleo.elijah.util.Helpers;

public class LangGlobals {

	public final static IdentExpression emptyConstructorName = Helpers.string_to_ident("<>");

	// TODO override name() ??
	public final static  ConstructorDef defaultVirtualCtor = new ConstructorDefImpl(null, null, null);
	private static final OS_Package     _dp                = new OS_PackageImpl(null, 0);

	public static @NotNull OS_Package defaultPackage() {
		return _dp;
	}

}
