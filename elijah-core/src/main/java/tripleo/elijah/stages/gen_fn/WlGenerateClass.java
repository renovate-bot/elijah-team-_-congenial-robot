/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.RegisterClassInvocation_env;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Holder;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

/**
 * Created 5/16/21 12:41 AM
 */
public class WlGenerateClass implements WorkJob {
	private final @NotNull ClassStatement               classStatement;
	private final @NotNull ClassInvocation              classInvocation;
	private final          GenerateFunctions            generateFunctions;
	private final          DeducePhase.GeneratedClasses coll;
	private final @NotNull RegisterClassInvocation_env  __passthru_env;
	private       boolean        _isDone = false;
	private final ICodeRegistrar cr;
	private       EvaClass       Result;

	public WlGenerateClass(GenerateFunctions aGenerateFunctions,
						   @NotNull ClassInvocation aClassInvocation,
						   DeducePhase.GeneratedClasses coll,
						   final ICodeRegistrar aCodeRegistrar) {
		classStatement    = aClassInvocation.getKlass();
		generateFunctions = aGenerateFunctions;
		classInvocation   = aClassInvocation;
		this.coll         = coll;

		cr = aCodeRegistrar;

		__passthru_env = null;
	}

	public WlGenerateClass(final GenerateFunctions aGenerateFunctions,
						   final ClassInvocation aClassInvocation,
						   final DeducePhase.GeneratedClasses coll,
						   final ICodeRegistrar aCodeRegistrar,
						   final RegisterClassInvocation_env aEnv) {
		classStatement    = aClassInvocation.getKlass();
		generateFunctions = aGenerateFunctions;
		classInvocation   = aClassInvocation;
		this.coll         = coll;

		cr = aCodeRegistrar;

		__passthru_env = aEnv;
	}

	public EvaClass getResult() {
		return Result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		final DeferredObject<EvaClass, Void, Void> resolvePromise = classInvocation.resolveDeferred();
		switch (resolvePromise.state()) {
		case PENDING:
			@NotNull EvaClass kl = generateFunctions.generateClass(classStatement, classInvocation, __passthru_env);
			//kl.setCode(generateFunctions.module.getCompilation().nextClassCode());

			cr.registerClass1(kl);

			if (coll != null)
				coll.add(kl);

			resolvePromise.resolve(kl);
			Result = kl;
			break;
		case RESOLVED:
			Holder<EvaClass> hgc = new Holder<EvaClass>();
			resolvePromise.then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(EvaClass result) {
//					assert result == kl;
					hgc.set(result);
				}
			});
			Result = hgc.get();
			break;
		case REJECTED:
			throw new NotImplementedException();
		}
		_isDone = true;
	}
}

//
//
//
