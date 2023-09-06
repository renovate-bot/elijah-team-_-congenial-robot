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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.NamespaceInvocation;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

/**
 * Created 5/31/21 3:01 AM
 */
public class WlGenerateNamespace implements WorkJob {
	private final          DeducePhase.@Nullable GeneratedClasses coll;
	private final          NamespaceStatement                     namespaceStatement;
	private                boolean                                _isDone = false;
	private final @NotNull GenerateFunctions                      generateFunctions;
	private final @NotNull NamespaceInvocation                    namespaceInvocation;
	private                ICodeRegistrar                         cr;
	private                EvaNamespace                           result;

	public WlGenerateNamespace(@NotNull GenerateFunctions aGenerateFunctions,
							   @NotNull NamespaceInvocation aNamespaceInvocation,
							   @Nullable DeducePhase.GeneratedClasses aColl,
							   final ICodeRegistrar aCr) {
		generateFunctions   = aGenerateFunctions;
		namespaceStatement  = aNamespaceInvocation.getNamespace();
		namespaceInvocation = aNamespaceInvocation;
		coll                = aColl;

		cr = aCr;
	}

	public EvaNode getResult() {
		return result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		final DeferredObject<EvaNamespace, Void, Void> resolvePromise = namespaceInvocation.resolveDeferred();
		switch (resolvePromise.state()) {
		case PENDING:
			@NotNull EvaNamespace ns = generateFunctions.generateNamespace(namespaceStatement);
			//ns.setCode(generateFunctions.module.getCompilation().nextClassCode());


			cr.registerNamespace(ns);


			if (coll != null)
				coll.add(ns);

			resolvePromise.resolve(ns);
			result = ns;
			break;
		case RESOLVED:
			resolvePromise.then(new DoneCallback<EvaNamespace>() {
				@Override
				public void onDone(EvaNamespace result) {
					WlGenerateNamespace.this.result = result;
				}
			});
			break;
		case REJECTED:
			throw new NotImplementedException();
		}
		_isDone = true;
//		tripleo.elijah.util.Stupidity.println_out_2(String.format("** GenerateNamespace %s at %s", namespaceInvocation.getNamespace().getName(), this));
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
