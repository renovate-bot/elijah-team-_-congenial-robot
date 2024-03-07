package tripleo.elijah.stages.gen_c;

import org.jdeferred2.DoneCallback;

import tripleo.elijah.stages.gen_fn.EvaNode;

public interface GR_EvaNodeAble {
	void onResolve(DoneCallback<EvaNode> cb);
}
