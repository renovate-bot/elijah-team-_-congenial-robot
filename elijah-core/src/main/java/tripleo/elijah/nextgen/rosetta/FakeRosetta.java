package tripleo.elijah.nextgen.rosetta;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.Eventual;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.util.Ok;

public enum FakeRosetta {
	;

	public interface RCI {
		RCI on(DoneCallback<ClassInvocation> cb);

		void triggerOn(Eventual<Ok> e);

		void trigger();
	}

	public static RCI registerClassInvocation(final ClassStatement aClassStatement, final DeduceTypes2 aDeduceTypes2) {
		return new _RCI_0(aDeduceTypes2, aClassStatement);
	}

	private static class _RCI_0 implements RCI {
		private final Eventual<ClassInvocation> ev;
		private final DeduceTypes2              deduceTypes2;
		private final ClassStatement            classStatement;

		public _RCI_0(final DeduceTypes2 aDeduceTypes2, final ClassStatement aClassStatement) {
			ev             = new Eventual<>();
			deduceTypes2   = aDeduceTypes2;
			classStatement = aClassStatement;
		}

		@Override
		public RCI on(final DoneCallback<ClassInvocation> cb) {
			ev.then(cb);
			return this;
		}

		@Override
		public void triggerOn(Eventual<Ok> e) {
			e.then(ok1 -> this.trigger());
		}

		@Override
		public void trigger() {
			ClassInvocation cix = new ClassInvocation(classStatement,
													  null,
													  new ReadySupplier_1<>(deduceTypes2));
			cix = deduceTypes2.phase.registerClassInvocation(cix);
			ev.resolve(cix);
		}
	}
}
