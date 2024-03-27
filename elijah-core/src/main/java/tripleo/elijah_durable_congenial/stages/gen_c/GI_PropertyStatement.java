package tripleo.elijah_durable_congenial.stages.gen_c;

import tripleo.elijah_durable_congenial.lang.i.PropertyStatement;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.lang.i.PropertyStatement;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;

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
