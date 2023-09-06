package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.instructions.*;

import java.util.List;
import java.util.Map;

public abstract class WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	public @NotNull List<Instruction> instructions() {
		return getGf().instructions();
	}

	@Override
	public abstract void provideFileGen(GenerateResultEnv fg);

	public abstract BaseEvaFunction getGf();

	public @Nullable Label findLabel(final int aIndex) {
		return getGf().findLabel(aIndex);
	}

	public boolean pointsToConstructor() {
		return getGf().getFD() instanceof ConstructorDef;
	}

	public boolean pointsToConstructor2() {
		return getGf() instanceof EvaConstructor;
	}

	public EvaNode getGenClass() {
		return getGf().getGenClass();
	}

	public @NotNull TypeTableEntry tte_for_self() {
		@Nullable final InstructionArgument result_index = getGf().vte_lookup("self");
		final IntegerIA                     resultIA     = (IntegerIA) result_index;
		@NotNull final VariableTableEntry   vte          = resultIA.getEntry();
		assert vte.getVtt() == VariableTableType.SELF;

		var tte1 = getGf().getTypeTableEntry(resultIA.getIndex());

		return tte1;
	}

	public @NotNull Pair<String, TypeTableEntry> tte_for_result() {
		@Nullable InstructionArgument result_index = getGf().vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = getGf().vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			@NotNull final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null)
				return Pair.of("void", null); // README Assuming Unit
		}

		// Get it from resolved
		var tte1 = getGf().getTypeTableEntry(((IntegerIA) result_index).getIndex());
		return Pair.of(null, tte1);
	}

	public @Nullable Map<TypeName, OS_Type> classInvcationGenericPart() {
		return getGf().fi.getClassInvocation().genericPart().getMap();
	}

	@Deprecated
	public BaseEvaFunction cheat() {
		return getGf();
	}

	public VariableTableEntry getSelf() {
		return getGf().getSelf();
	}

	public @NotNull VariableTableEntry getVarTableEntry(final int aIndex) {
		return getGf().getVarTableEntry(aIndex);
	}

	public @NotNull ConstantTableEntry getConstTableEntry(final int aIndex) {
		return getGf().getConstTableEntry(aIndex);
	}

	public @NotNull ProcTableEntry getProcTableEntry(final int aIndex) {
		return getGf().getProcTableEntry(aIndex);
	}

	public @NotNull FunctionDef getFD() {
		return getGf().getFD();
	}

	public @NotNull TypeTableEntry getTypeTableEntry(final int aIndex) {
		return getGf().getTypeTableEntry(aIndex);
	}

	public @Nullable InstructionArgument vte_lookup(final String aText) {
		return getGf().vte_lookup(aText);
	}
}
