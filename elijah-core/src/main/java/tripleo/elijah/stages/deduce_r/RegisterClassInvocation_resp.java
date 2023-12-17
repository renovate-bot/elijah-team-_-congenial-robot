package tripleo.elijah.stages.deduce_r;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.Eventual;
import tripleo.elijah.stages.deduce.ClassInvocation;

public class RegisterClassInvocation_resp {
	private Eventual<ClassInvocation> ciPromise = new Eventual<>();

	public void onSuccess(final DoneCallback<ClassInvocation> cb) {
		ciPromise.then(cb);
	}

	public void succeed(final ClassInvocation aCi2) {
		ciPromise.resolve(aCi2);
	}
}
