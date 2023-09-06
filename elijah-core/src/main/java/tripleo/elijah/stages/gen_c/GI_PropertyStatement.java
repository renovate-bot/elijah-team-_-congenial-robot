package tripleo.elijah.stages.gen_c;

import tripleo.elijah.lang.i.PropertyStatement;
import tripleo.elijah.stages.gen_fn.EvaNode;

class GI_PropertyStatement implements GenerateC_Item {
	private final PropertyStatement _e;
	private final GI_Repo           _epo;
	private       EvaNode           _evaNaode;

	public GI_PropertyStatement(final PropertyStatement aE, final GI_Repo aGIRepo) {
		_e   = aE;
		_epo = aGIRepo;
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNaode;
	}

	@Override
	public void setEvaNode(final EvaNode aEvaNode) {
		_evaNaode = aEvaNode;
	}
}
