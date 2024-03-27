package tripleo.elijah_durable_congenial.lang;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.lang.i.ConstructorDef;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;

import tripleo.elijah_durable_congenial.lang.impl.ConstructorDefImpl;
import tripleo.elijah_durable_congenial.lang.impl.OS_PackageImpl;

import tripleo.elijah_durable_congenial.util.Helpers;
import tripleo.elijah_durable_congenial.lang.i.ConstructorDef;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.lang.impl.ConstructorDefImpl;

public enum LangGlobals { ;

	public final static IdentExpression emptyConstructorName = Helpers.string_to_ident("<>");

	// TODO override name() ??
	public final static  ConstructorDef defaultVirtualCtor = new ConstructorDefImpl(null, null, null);
	private static final OS_Package     _dp                = new OS_PackageImpl(null, 0);

	public static @NotNull OS_Package defaultPackage() {
		return _dp;
	}

}
