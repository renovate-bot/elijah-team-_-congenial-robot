package tripleo.elijah.stages.gen_c;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.DeduceElement;
import tripleo.elijah.stages.deduce.OnGenClass;
import tripleo.elijah.stages.gen_c.statements.ASS_CONSTIA;
import tripleo.elijah.stages.gen_c.statements.ASS_IA;
import tripleo.elijah.stages.gen_c.statements.ASS_ID;
import tripleo.elijah.stages.gen_c.statements.ArgumentString;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	protected final Eventual<GenerateResultEnv> fileGenPromise = new Eventual<>();

	@Deprecated
	public BaseEvaFunction cheat() {
		return getGf();
	}

	public @Nullable Map<TypeName, OS_Type> classInvcationGenericPart() {
		return getGf().fi.getClassInvocation().genericPart().getMap();
	}

	public @Nullable Label findLabel(final int aIndex) {
		return getGf().findLabel(aIndex);
	}

	@NotNull
	public Pair<java.util.List<String>, java.util.List<ArgumentString>> getArgumentStrings(
			final @NotNull Instruction instruction) {
		Preconditions.checkState(getGenerateC().isPresent());
		final GenerateC generateC = getGenerateC().get();
		Preconditions.checkNotNull(generateC);

		final List<String> sl3 = new ArrayList<>();
		final List<ArgumentString> sl4 = new ArrayList<>();

		final int args_size = instruction.getArgsSize();
		for (int i = 1; i < args_size; i++) {
			final InstructionArgument ia = instruction.getArg(i);
			if (ia instanceof IntegerIA) {
				final ASS_IA st = new ASS_IA(this, (IntegerIA) ia, Generate_Code_For_Method.AOG.GET);
				sl4.add(new ArgumentString(ia, st, null));
				sl3.add(st.getText());
			} else if (ia instanceof IdentIA) {
				final ASS_ID st = new ASS_ID(this, (IdentIA) ia, Generate_Code_For_Method.AOG.GET);
				sl4.add(new ArgumentString(ia, st, null));
				sl3.add(st.getText());
			} else if (ia instanceof final @NotNull ConstTableIA c) {
				final ASS_CONSTIA st = new ASS_CONSTIA(this, (ConstTableIA) ia, Generate_Code_For_Method.AOG.GET);
				sl4.add(new ArgumentString(ia, st, null));
				sl3.add(st.getText());
			} else if (ia instanceof ProcIA) {
				logProgress(740, "ProcIA");

				// @NotNull final EG_Statement st = EG_Statement.of(realTargetName,
				// EX_Explanation.withMessage("ArgumentString::integerIA"));
				sl4.add(new ArgumentString(ia, null, "ProcIA"));

				throw new NotImplementedException();
			} else {
				logProgress(131, ia.getClass().getName());

				// @NotNull final EG_Statement st = EG_Statement.of(realTargetName,
				// EX_Explanation.withMessage("ArgumentString::integerIA"));
				sl4.add(new ArgumentString(ia, null, "Invalid InstructionArgument"));

				throw new IllegalStateException("Invalid InstructionArgument");
			}
		}
		return Pair.of(sl3, sl4);
	}

	public @NotNull ConstantTableEntry getConstTableEntry(final int aIndex) {
		return getGf().getConstTableEntry(aIndex);
	}

	public @NotNull FunctionDef getFD() {
		return getGf().getFD();
	}

	public EvaNode getGenClass() {
		return getGf().getGenClass();
	}

	public abstract Optional<GenerateC> getGenerateC();

	public abstract BaseEvaFunction getGf();

	public @NotNull ProcTableEntry getProcTableEntry(final int aIndex) {
		return getGf().getProcTableEntry(aIndex);
	}

	public VariableTableEntry getSelf() {
		return getGf().getSelf();
	}

	public @NotNull TypeTableEntry getTypeTableEntry(final int aIndex) {
		return getGf().getTypeTableEntry(aIndex);
	}

	public @NotNull VariableTableEntry getVarTableEntry(final int aIndex) {
		return getGf().getVarTableEntry(aIndex);
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	public @NotNull List<Instruction> instructions() {
		return getGf().instructions();
	}

	private void logProgress(final int code, final String message) {
		getGenerateC().get().elLog().err(code + " " + message);
		// fileGenPromise in subclass
	}

	public boolean pointsToConstructor() {
		return getGf().getFD() instanceof ConstructorDef;
	}

	public boolean pointsToConstructor2() {
		return getGf() instanceof EvaConstructor;
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public Map<OS_Element, DeduceElement> pt_elements() {
		return this.getGf().elements();
	}

	public IdentExpression pt_getNameNode() {
		return getFD().getNameNode();
	}

	public void pt_onGenClass(final @NotNull OnGenClass ogc) {
		getGf().onGenClass(ogc);
	}

	public InstructionArgument pt_vte_lookup(final String aText) {
		return getGf().vte_lookup(aText);
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		fileGenPromise.resolve(aFileGen);
	}

	public @NotNull Pair<String, TypeTableEntry> tte_for_result() {
		@Nullable
		InstructionArgument result_index = getGf().vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = getGf().vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			@NotNull
			final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null)
				return Pair.of("void", null); // README Assuming Unit
		}

		// Get it from resolved
		var tte1 = getGf().getTypeTableEntry(((IntegerIA) result_index).getIndex());
		return Pair.of(null, tte1);
	}

	public @NotNull TypeTableEntry tte_for_self() {
		@Nullable
		final InstructionArgument result_index = getGf().vte_lookup("self");
		final IntegerIA resultIA = (IntegerIA) result_index;
		@NotNull
		final VariableTableEntry vte = resultIA.getEntry();
		assert vte.getVtt() == VariableTableType.SELF;

		var tte1 = getGf().getTypeTableEntry(resultIA.getIndex());

		return tte1;
	}

	public @Nullable InstructionArgument vte_lookup(final String aText) {
		return getGf().vte_lookup(aText);
	}

	public ZoneVTE zoneHelper(final IntegerIA ia) {
		final @NotNull BaseEvaFunction gf = this.getGf();
		final VariableTableEntry varTableEntry = this.getVarTableEntry(ia.getIndex());
		final ZoneVTE zone_vte = getGenerateC().get().get_zone().get(varTableEntry, gf);

		return zone_vte;
	}

	public ZoneVTE zoneHelper(final VariableTableEntry varTableEntry) {
		final @NotNull BaseEvaFunction gf = this.getGf();
		final ZoneVTE zone_vte = getGenerateC().get().get_zone().get(varTableEntry, gf);
		return zone_vte;
	}
}
