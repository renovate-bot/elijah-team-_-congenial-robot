package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.DecideElObjectType;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;

import java.util.function.Consumer;

public class IdentIA_Ops {
	public static @NotNull IdentIA_Ops get(final IdentIA aIdentIA) {
		return new IdentIA_Ops(aIdentIA);
	}

	private final IdentIA identIA;

	public IdentIA_Ops(final IdentIA aIdentIA) {
		identIA = aIdentIA;
	}

	public @NotNull ConstructorPathOp getConstructorPath() {
		final IdentTableEntry idte             = identIA.getEntry();
		final OS_Element      resolved_element = idte.getResolvedElement();

		final ConstructorPathOp[] cpo = {null};
		idte.onResolvedType(_resolved -> cpo[0]=ConstructorPathOp__1(_resolved, resolved_element));

		if (cpo[0] == null) {
			cpo[0] = new ConstructorPathOp() {
				@Override
				public @Nullable String getCtorName() {
					return null;
				}

				@Override
				public @Nullable EvaNode getResolved() {
					return null;
				}
			};
		}

		return cpo[0];
	}
	public void onConstructorPath(Consumer<ConstructorPathOp> cpo0) {
		final IdentTableEntry idte             = identIA.getEntry();
		final OS_Element      resolved_element = idte.getResolvedElement();

		final ConstructorPathOp[] cpo = {null};
		idte.onResolvedType(_resolved -> {
			var xxx = ConstructorPathOp__1(_resolved, resolved_element);
			cpo0.accept(xxx);
		});
		idte.failingResolvedType((f)->{
			cpo0.accept(new ConstructorPathOp() {
				@Override
				public @Nullable String getCtorName() {
					return null;
				}

				@Override
				public @Nullable EvaNode getResolved() {
					return null;
				}
			});
		});
	}

	private static ConstructorPathOp ConstructorPathOp__1(final EvaNode _resolved, final OS_Element resolved_element) {
		ConstructorPathOp cpo=null;
		String        ctorName  = null;

		if (resolved_element != null) { // FIXME stop accepting null here
			switch (DecideElObjectType.getElObjectType(resolved_element)) {
			case CONSTRUCTOR -> {
				ctorName = ((ConstructorDef) resolved_element).name().asString();
			}
			case CLASS -> {
				int y = 2;
				ctorName = ""; //((ClassStatement) resolved_element).name();
			}
			}
		} else ctorName = "";

		final String finalCtorName = ctorName;
		cpo = new ConstructorPathOp() {
			@Override
			public @Nullable String getCtorName() {
				return finalCtorName;
			}

			@Override
			public EvaNode getResolved() {
				return _resolved;
			}
		};
		return cpo;
	}
}
