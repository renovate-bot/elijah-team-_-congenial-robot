package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;

class GI_ClassStatement implements GenerateC_Item {
	private       EvaNode         _evaNaode;
	private       IdentTableEntry _ite;
	private final ClassStatement  e;
	private final GI_Repo         giRepo;

	public GI_ClassStatement(final ClassStatement aE, final GI_Repo aGIRepo) {
		e      = aE;
		giRepo = aGIRepo;
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNaode;
	}

	@Override
	public void setEvaNode(final EvaNode a_evaNode) {
		_evaNaode = a_evaNode;
	}

	public void setITE(final @NotNull IdentTableEntry ite) {
		EvaNode resolved = null;

		if (ite.type != null)
			resolved = ite.type.resolved();
		if (resolved == null)
			resolved = ite.resolvedType();
		if (resolved == null) {
			final DeduceElement3_IdentTableEntry de3_idte = ite.getDeduceElement3();
			resolved = de3_idte.getResolved();
		}

		//assert resolved != null;

		_ite      = ite;
		_evaNaode = resolved;
	}
}
