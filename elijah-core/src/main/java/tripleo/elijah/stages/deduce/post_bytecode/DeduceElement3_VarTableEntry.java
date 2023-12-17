package tripleo.elijah.stages.deduce.post_bytecode;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

public class DeduceElement3_VarTableEntry implements IDeduceElement3 {
	private final EvaContainer.VarTableEntry _principal;
	private final DeduceTypes2                _deduceTypes2;
	public        RegisterClassInvocation_env __passthru;

	@Contract(pure = true)
	public DeduceElement3_VarTableEntry(final @NotNull EvaContainer.VarTableEntry aVarTableEntry,
										final @NotNull DeduceTypes2 aDeduceTypes2) {
		_principal    = aVarTableEntry;
		_deduceTypes2 = aDeduceTypes2;
	}

	@Contract(pure = true)
	public DeduceElement3_VarTableEntry(final EvaContainer.VarTableEntry aVarTableEntry) {
		_principal = aVarTableEntry;
		_deduceTypes2 = null;
	}

	private void __one_potential(final @NotNull DeducePhase aDeducePhase,
								 final EvaContainer.@NotNull VarTableEntry varTableEntry,
								 final @NotNull List<TypeTableEntry> potentialTypes,
								 final TypeName typeName,
								 final @NotNull ClassInvocation ci) throws STOP {
		boolean sc = false;

		TypeTableEntry potentialType = potentialTypes.get(0);
		if (!potentialType.isResolved()) {
			assert potentialType.getAttached() != null;

			final OS_Type.Type attachedType = potentialType.getAttached().getType();
			//assert attachedType == OS_Type.Type.USER_CLASS;
			if (attachedType != OS_Type.Type.USER_CLASS) {
				final OS_Type att = potentialType.getAttached();
				noteNonsense(105, String.valueOf(att));
			}

			{
				//
				// HACK
				//

				if (attachedType != OS_Type.Type.USER_CLASS) {
					final TypeName tn = potentialType.getAttached().getTypeName();
					if (ci.genericPart().hasGenericPart()) {
						var v = ci.genericPart().valueForKey(tn);

						if (v != null) {
							potentialType.setAttached(v);
							assert attachedType == OS_Type.Type.USER_CLASS; // FIXME logical fallacy
						}
					}
				}
			}

			var dt2 = deduceTypes2();

			//System.err.println((__passthru));

			if (attachedType == OS_Type.Type.USER_CLASS) {
				var xci = dt2._inj().new_ClassInvocation(potentialType.getAttached().getClassOf(), null, new ReadySupplier_1<>(dt2));
				var v = xci.genericPart().valueForKey(typeName);

				if (v != null) {
					xci.genericPart().record(typeName, varTableEntry);
				}

				xci = aDeducePhase.registerClassInvocation(xci);
				@NotNull GenerateFunctions gf  = aDeducePhase.generatePhase.getGenerateFunctions(xci.getKlass().getContext().module());
				WlGenerateClass            wgc = dt2._inj().new_WlGenerateClass(gf, xci, aDeducePhase.generatedClasses, aDeducePhase.codeRegistrar);
				wgc.run(null); // !
				potentialType.genType.setCi(xci); // just for completeness
				wgc.resultPromise(potentialType::resolve);
				sc = true;
			} else if (attachedType == OS_Type.Type.BUILT_IN) {
				// README be explicit about doing nothing
			} else {
				NotImplementedException.raise();
				noteNonsense(177, "not a USER_CLASS " + potentialType.getAttached());
			}
		}
		if (potentialType.isResolved())
			varTableEntry.resolve(potentialType.resolved());
		else
			noteNonsense(114, "Can't resolve " + varTableEntry);

		if (!sc)
			throw new STOP();
	}

	@Contract("_, null -> fail")
	private void __zero_potential(final EvaContainer.@NotNull VarTableEntry varTableEntry, final TypeName tn) {
		Preconditions.checkNotNull(tn);
		assert tn instanceof NormalTypeName;

		if (tn != null) {
			if (tn instanceof final @NotNull NormalTypeName tn2) {

				if (tn2.isNull()) return;

				__zero_potential__1(varTableEntry, tn2);
			} else
				assert false;
		} else {
			// must be unknown
			assert false;
		}
	}

	private void __zero_potential__1(final @NotNull EvaContainer.VarTableEntry varTableEntry,
									 final @NotNull NormalTypeName aNormalTypeName) {
		// 0. preflight
		if (aNormalTypeName.isNull())
			throw new NotImplementedException();

		// 1. st...
		final String typeNameName = aNormalTypeName.getName();

		// 2. stage 1
		final LookupResultList lrl  = aNormalTypeName.getContext().lookup(typeNameName);
		OS_Element             best = lrl.chooseBest(null);

		// 3. validation
		if (best != null) {
			// A)

			//  4. handle special case here
			while (best instanceof AliasStatementImpl) {
				NotImplementedException.raise();
				//assert false;
				best = DeduceLookupUtils._resolveAlias((AliasStatementImpl) best, deduceTypes2());
			}

			// 5. effect
			assert best instanceof ClassStatement;
			varTableEntry.resolve_varType(((ClassStatement) best).getOS_Type());
		} else {
			// B)

			//  4. do later...

			// TODO shouldn't this already be calculated?
			throw new NotImplementedException();
		}

	}

	@Override
	public DeduceTypes2 deduceTypes2() {
		return _deduceTypes2;
	}

	@Override
	public @NotNull DED elementDiscriminator() {
		return DED.dispatch(_principal);
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		throw new NotImplementedException();
		//return null;
	}

	@Override
	public GenType genType() {
		throw new NotImplementedException();
		//return null;
	}

	@Override
	public OS_Element getPrincipal() {
		return _principal.vs;
	}

	@Override
	public @NotNull DeduceElement3_Kind kind() {
		return DeduceElement3_Kind.GEN_FN__GC_VTE;
	}

	@Override
	public void resolve(final Context aContext, final DeduceTypes2 dt2) {
		throw new NotImplementedException();
	}

	@Override
	public void resolve(final IdentIA aIdentIA, final Context aContext, final FoundElement aFoundElement) {
		throw new NotImplementedException();
	}

	public void resolve_var_table_entries(final @NotNull DeducePhase aDeducePhase, final @NotNull ClassInvocation ci) {
		final EvaContainer.VarTableEntry varTableEntry = _principal;

		final List<TypeTableEntry> potentialTypes = varTableEntry.potentialTypes;
		final TypeName             typeName       = varTableEntry.typeName;

		try {
			if (potentialTypes.size() == 0 && (varTableEntry.varType == null || typeName.isNull())) {
				__zero_potential(varTableEntry, typeName);
			} else {
				noteNonsenseErr(108, String.format("%s %s", varTableEntry.nameToken, potentialTypes));

				if (potentialTypes.size() == 1) {
					__one_potential(aDeducePhase, varTableEntry, potentialTypes, typeName, ci);
				}
			}
		} catch (STOP stop) {
			NotImplementedException.raise();
		}
	}

	private static class STOP extends Exception {

	}

	private static void noteNonsense(int code, String message) {
		SimplePrintLoggerToRemoveSoon.println_out_2(String.format("%d %s%n", code, message));
	}

	private static void noteNonsenseErr(int code, String message) {
		SimplePrintLoggerToRemoveSoon.println_err2(String.format("** [noteNonsenseErr] %d %s%n", code, message));
	}
}
