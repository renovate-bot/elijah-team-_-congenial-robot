package tripleo.elijah_durable_congenial.stages.gdm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.FoundElement;
import tripleo.elijah_durable_congenial.stages.deduce.Resolve_Ident_IA2;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;

import java.util.List;
import java.util.function.Consumer;

// TODO 11/10 GDM_ITE too
public class GDM_IdentExpression implements GDM_Item {
	private final GenerateFunctions         generateFunctions;
	private final IdentExpression           identExpression;
	private final Eventual<IdentTableEntry> _p_IdentTableEntry = new Eventual<>();
	private final Eventual<BaseEvaFunction> _p_bef             = new Eventual<>();

	public GDM_IdentExpression(final GenerateFunctions aGenerateFunctions, final IdentExpression aIdentExpression) {
		generateFunctions = aGenerateFunctions;
		identExpression   = aIdentExpression;
	}

	public void resolveIdentTableEntry(final IdentTableEntry ite) {
		//assert
		//		_p_IdentTableEntry.isPending();

		if(_p_IdentTableEntry.isPending()) {
			_p_IdentTableEntry.resolve(ite);
		}
	}

	public void trigger_resolve(final Context aContext,
								final List<InstructionArgument> aSs,
								final FoundElement aFoundElement,
								final DeduceTypes2 aDeduceTypes2,
								final @Nullable BaseEvaFunction aEvaFunction) {
		if (aEvaFunction != null && _p_bef.isPending()) {
			_p_bef.resolve(aEvaFunction);
		}
		_p_bef.then(bef -> {
			@NotNull Resolve_Ident_IA2 ria2 = aDeduceTypes2._inj().new_Resolve_Ident_IA2(aDeduceTypes2, aDeduceTypes2._errSink(), aDeduceTypes2._phase(), bef, aFoundElement);
			ria2.resolveIdentIA2_(aContext, null, aSs);
		});
	}

	public void resolveIdentIA(final BaseEvaFunction aBaseEvaFunction, final int aIndex) {
		// README this should always be true (if attached to a ...)
		if (false) {
			final IdentIA identIA1 = new IdentIA(aIndex, aBaseEvaFunction);
			onIdentTableEntry(ite1 -> {
				assert identIA1.getEntry() == ite1;
			});
		}

		if (_p_bef.isPending()) {
			_p_bef.resolve(aBaseEvaFunction);
		}
	}

	public void onIdentTableEntry(final Consumer<IdentTableEntry> ic) {
		_p_IdentTableEntry.then(ic::accept);
	}

	public void resolveContext(final Context aContext) {
		NotImplementedException.raise_stop();
	}

	public void resolveDrIdent(final DR_Ident aDrIdent) {
		NotImplementedException.raise_stop();
	}

	public void resolveVariableTableEntry(final VariableTableEntry aVariableTableEntry) {
/*
		// README this should always be true (if attached to a ...)
		if (false) {
			final IdentIA identIA1 = new IdentIA(aIndex, aBaseEvaFunction);
			onIdentTableEntry(ite1 -> {
				assert identIA1.getEntry() == ite1;
			});
		}

		if (_p_bef.isPending()) {
			_p_bef.resolve(aBaseEvaFunction);
		}
*/
		NotImplementedException.raise_stop();
	}
}
