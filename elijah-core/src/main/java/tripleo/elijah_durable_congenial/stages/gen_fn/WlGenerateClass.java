/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.nextgen.rosetta.Rosetta;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.gen_fn_r.GenerateEvaClassResponse;
import tripleo.elijah_durable_congenial.stages.gen_fn_r.GenerateEvaClassRequest;
import tripleo.elijah_durable_congenial.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Holder;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

/**
 * Created 5/16/21 12:41 AM
 */
public class WlGenerateClass implements WorkJob {
	private final @NotNull ClassStatement               classStatement;
	private final @NotNull ClassInvocation              classInvocation;
	private final          GenerateFunctions           generateFunctions;
	private final          GeneratedClasses            coll;
	private final @NotNull RegisterClassInvocation_env __passthru_env;
	private final          ICodeRegistrar               cr;
	//private       EvaClass       Result;
	private final          Eventual<EvaClass>           resultPromise = new Eventual<>();
	private                boolean                      _isDone = false;

	public WlGenerateClass(GenerateFunctions aGenerateFunctions,
						   @NotNull ClassInvocation aClassInvocation,
						   GeneratedClasses coll,
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
						   final GeneratedClasses coll,
						   final ICodeRegistrar aCodeRegistrar,
						   final RegisterClassInvocation_env aEnv) {
		classStatement    = aClassInvocation.getKlass();
		generateFunctions = aGenerateFunctions;
		classInvocation   = aClassInvocation;
		this.coll         = coll;

		cr = aCodeRegistrar;

		__passthru_env = aEnv;
	}

	//public EvaClass getResult() {
	//	return Result;
	//}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		final Eventual<EvaClass> resolvePromise = classInvocation.resolveDeferred();

		resolvePromise.then(resultPromise::resolve);

		// README 11/10 Could uncomment, but failure is Void, not Diagnostic
		//resolvePromise.fail(resultPromise::fail(x));

		if (resolvePromise.isPending()) {
			GenerateEvaClassRequest  rq      = new GenerateEvaClassRequest(generateFunctions, classStatement, classInvocation, __passthru_env);
			GenerateEvaClassResponse rsp     = new GenerateEvaClassResponse();
			Rosetta.GECR             rosetta = Rosetta.create(rq, rsp);

			rosetta.apply();

			rsp.getEvaClassPromise().then(kl -> {
				//kl.setCode(generateFunctions.module.getCompilation().nextClassCode());

				cr.registerClass1(kl);

				if (coll != null)
					coll.add(kl);

				resolvePromise.resolve(kl);
				//Result = kl;
			});
		}

		if (resolvePromise.isPending()) {
			Holder<EvaClass> hgc = new Holder<EvaClass>();
			resolvePromise.then(hgc::set);
			//Result = hgc.get();
		}

		_isDone = true;
	}

	public void resultPromise(final DoneCallback<EvaClass> cb) {
		resultPromise.then(cb);
	}
}

//
//
//
