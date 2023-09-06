package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.stages.instructions.IntegerIA;

public class IntegerIA_Ops {
	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull IntegerIA_Ops get(final IntegerIA aIntegerIA, final int aSSize) {
		return new IntegerIA_Ops(aIntegerIA, aSSize);
	}

	private final IntegerIA integerIA;

	private final int sSize;

	public IntegerIA_Ops(final IntegerIA aIntegerIA, final int aSSize) {
		integerIA = aIntegerIA;
		sSize     = aSSize;
	}

	public @NotNull ConstructorPathOp getConstructorPath() {
		return new ConstructorPathOp1();
	}

	private class ConstructorPathOp1 implements ConstructorPathOp {
		private boolean _calculated = false;

		@Nullable EvaNode _resolved = null;

		@Override
		public EvaNode getResolved() {
			if (!_calculated) {
				calculate();
			}

			return _resolved;
		}

		@Override
		public @Nullable String getCtorName() {
			return null;
		}

		private void calculate() {
			final VariableTableEntry vte = integerIA.getEntry();

			if (sSize == 1) {
				final EvaNode resolved = vte.getType().resolved();
				if (resolved != null) {
					_resolved = resolved;
				} else {
					_resolved = vte.resolvedType();
				}
			}

			_calculated = true;
		}
	}
}
