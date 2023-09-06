package tripleo.elijah.stages.deduce.tastic;

import org.jdeferred2.Promise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;

public class FT_FCA_VariableStatement {

	private final @NotNull BaseEvaFunction       generatedFunction;
	private final          VariableStatementImpl vs;

	public FT_FCA_VariableStatement(final VariableStatementImpl aVs, final @NotNull BaseEvaFunction aGeneratedFunction) {
		vs                = aVs;
		generatedFunction = aGeneratedFunction;
	}

	public void doLogic0(final @NotNull String e_text,
						 final @NotNull Promise<GenType, Void, Void> p,
						 final @NotNull VariableTableEntry vte1,
						 final @NotNull VariableTableEntry vte) {
		assert vs.getName().equals(e_text);

		@Nullable InstructionArgument vte2_ia = generatedFunction.vte_lookup(vs.getName());

		assert vte2_ia != null;

		@NotNull VariableTableEntry vte2 = ((IntegerIA) vte2_ia).getEntry();
		if (p.isResolved())
			System.out.printf("915 Already resolved type: vte2.type = %s, gf = %s %n", vte1.getType(), generatedFunction);
		else {
			final GenType gt = vte1.getGenType();
			gt.setResolved(vte2.getType().getAttached());
			vte1.resolveType(gt);
		}

//			vte.type = vte2.type;
//			tte.attached = vte.type.attached;

		vte.setStatus(BaseTableEntry.Status.KNOWN, new GenericElementHolder(vs));
		vte2.setStatus(BaseTableEntry.Status.KNOWN, new GenericElementHolder(vs)); // TODO ??
	}
}
