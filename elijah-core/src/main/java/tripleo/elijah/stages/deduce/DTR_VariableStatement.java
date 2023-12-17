package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.Map;

public class DTR_VariableStatement {
	public void run(DeduceTypes2 dt2, final IElementHolder eh, final GenType genType) {
		final TypeName typeName1 = variableStatement.typeName();

		if (!(typeName1 instanceof final @NotNull NormalTypeName normalTypeName)) {
			throw new IllegalStateException();
		}

		int state = 0;

		if (normalTypeName.getGenericPart() != null) {
			state = 1;
		} else {
			if (!normalTypeName.isNull()) {
				state = 2;
			}
		}

		//DTR_VS_Ctx ctx = _inj().new_DTR_VS_Ctx(eh, genType, normalTypeName);
		DTR_VS_Ctx ctx = new DTR_VS_Ctx(eh, genType, normalTypeName);

		switch (state) {
		case 1 -> normalTypeName_notGeneric(ctx);
		case 2 -> normalTypeName_generic_butNotNull(ctx);
		default -> {
			if (eh instanceof DeduceElement3_IdentTableEntry.DE3_EH_GroundedVariableStatement grounded) {
				final DeduceElement3_IdentTableEntry ground = grounded.getGround();

				final InstructionArgument bl = ground.principal.getBacklink();
				if (bl instanceof final @NotNull ProcIA procIA) {
					@NotNull final ProcTableEntry pte_bl = procIA.getEntry();

					assert pte_bl.getStatus() == BaseTableEntry.Status.KNOWN;

					pte_bl.typeResolvePromise().then(gt -> {
						// README when pte_bl has gets a type (GenType),
						//   - it will only have resolved (OS_UserClassType
						//     with ClassStatement).
						//   - we then get the ci and node
						//   - use the node (to an EvaClass) to look at varTableEntries
						//   - pick the first one that matches variableStatement
						//   - wait for it to get an OS_Type
						//      * this actually never happens

						assert (dt2 == ground.principal._deduceTypes2());
						gt.genCIForGenType2(ground.principal._deduceTypes2()); // README any will do

						assert gt.getCi() != null;
						assert gt.getNode() != null;

						for (EvaContainer.VarTableEntry entry : ((EvaContainerNC) gt.getNode()).varTable) {
							if (entry.nameToken.getText().equals(variableStatement.getName())) {
								entry.resolve_varType_cb(result -> {
									int y = 2;
									System.err.println("7676 DTR_VariableStatement >> " + result);
								});
								break;
							}
						}
					});

					final OS_Element re1 = pte_bl.getResolvedElement();

					final LookupResultList     lrl = re1.getContext().lookup(variableStatement.getName());
					@Nullable final OS_Element e2  = lrl.chooseBest(null);

					if (e2 == null) {
						int y = 2;
					} else {
						int y = 2;
					}
				}
			} else {
				SimplePrintLoggerToRemoveSoon.println_err("Unexpected value: " + state + "for " + variableStatement.getName());
			}
		}
		}
	}

	private final DeduceTypeResolve deduceTypeResolve;

	private final VariableStatement variableStatement;

	public DTR_VariableStatement(final DeduceTypeResolve aDeduceTypeResolve, final VariableStatement aVariableStatement) {
		deduceTypeResolve = aDeduceTypeResolve;
		variableStatement = aVariableStatement;
	}

	private void normalTypeName_notGeneric(final @NotNull DTR_VS_Ctx ctx) {
		final IElementHolder          eh             = ctx.eh();
		final GenType                 genType        = ctx.genType();
		final @NotNull NormalTypeName normalTypeName = ctx.normalTypeName();

		final TypeNameList genericPart = normalTypeName.getGenericPart();
		if (eh instanceof GenericElementHolderWithType) {
			normalTypeName_notGeneric_typeProvided(ctx);
		} else
			normalTypeName_notGeneric_typeNotProvided(ctx);
	}

	private /*static*/ void normalTypeName_generic_butNotNull_resolveToNonGeneric(final @NotNull GenType genType, final @NotNull GenType resolved) {
		genType.setResolved(resolved.getResolved());
	}

	private void normalTypeName_generic_butNotNull(final @NotNull DTR_VS_Ctx ctx) {
		final IElementHolder          eh             = ctx.eh();
		final GenType                 genType        = ctx.genType();
		final @NotNull NormalTypeName normalTypeName = ctx.normalTypeName();

		DeduceTypes2 dt2 = null;
		if (eh instanceof final @NotNull GenericElementHolderWithType eh1) {
			dt2 = eh1.getDeduceTypes2();
			final OS_Type type = eh1.getType();

			genType.setTypeName(dt2._inj().new_OS_UserType(normalTypeName));
			try {
				final @NotNull GenType resolved = dt2.resolve_type(genType.getTypeName(), variableStatement.getContext());
				if (resolved.getResolved().getType() == OS_Type.Type.GENERIC_TYPENAME) {
					final BaseTableEntry backlink = deduceTypeResolve.backlink;

					normalTypeName_generic_butNotNull_resolveToGeneric(genType, resolved, backlink);
				} else {
					normalTypeName_generic_butNotNull_resolveToNonGeneric(genType, resolved);
				}
			} catch (ResolveError aResolveError) {
				aResolveError.printStackTrace();
				assert false;
			}
		} else if (eh instanceof DeduceElement3Holder) {
			NotImplementedException.raise();
		} else
			genType.setTypeName(dt2._inj().new_OS_UserType(normalTypeName));
	}

	private void normalTypeName_notGeneric_typeProvided(final @NotNull DTR_VS_Ctx ctx) {
		final GenType                 genType        = ctx.genType();
		final @NotNull NormalTypeName normalTypeName = ctx.normalTypeName();

		final GenericElementHolderWithType eh1  = (GenericElementHolderWithType) ctx.eh();
		final DeduceTypes2                 dt2  = eh1.getDeduceTypes2();
		final OS_Type                      type = eh1.getType();


		genType.setNonGenericTypeName(normalTypeName);

		assert normalTypeName == type.getTypeName();

		OS_Type typeName = dt2._inj().new_OS_UserType(normalTypeName);
		try {
			final @NotNull GenType resolved = dt2.resolve_type(typeName, variableStatement.getContext());
			genType.setResolved(resolved.getResolved());
		} catch (ResolveError aResolveError) {
			aResolveError.printStackTrace();
			assert false;
		}
	}

	private /*static*/ void normalTypeName_notGeneric_typeNotProvided(final @NotNull DTR_VS_Ctx ctx) {
		final GenType                 genType        = ctx.genType();
		final @NotNull NormalTypeName normalTypeName = ctx.normalTypeName();

		genType.setNonGenericTypeName(normalTypeName);
	}

	record DTR_VS_Ctx(IElementHolder eh, GenType genType, NormalTypeName normalTypeName) {
	}

	private /*static*/ void normalTypeName_generic_butNotNull_resolveToGeneric(final @NotNull GenType genType, final @NotNull GenType resolved, final @NotNull BaseTableEntry backlink) {
		backlink.typeResolvePromise().then(result_gt -> ((Constructable) backlink).constructablePromise().then((final @NotNull ProcTableEntry result_pte) -> {
			final ClassInvocation ci = result_pte.getClassInvocation();
			assert ci != null;
			final @Nullable Map<TypeName, OS_Type> gp  = ci.genericPart().getMap();
			final TypeName                         sch = resolved.getTypeName().getTypeName();

			// 05/23 24

			assert gp != null;
			for (Map.Entry<TypeName, OS_Type> entrySet : gp.entrySet()) {
				if (entrySet.getKey().equals(sch)) {
					genType.setResolved(entrySet.getValue());
					break;
				}
			}
		}));
	}
}
