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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.deduce.NamespaceInvocation;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;

/**
 * Created 5/31/21 3:01 AM
 */
public class WlGenerateNamespace implements WorkJob {
	private final @Nullable GeneratedClasses coll;
	private final          NamespaceStatement                     namespaceStatement;
	private                boolean                                _isDone = false;
	private final @NotNull GenerateFunctions                      generateFunctions;
	private final @NotNull NamespaceInvocation namespaceInvocation;
	private final          ICodeRegistrar      cr;
	private                EvaNamespace        result;

	public WlGenerateNamespace(@NotNull GenerateFunctions aGenerateFunctions,
							   @NotNull NamespaceInvocation aNamespaceInvocation,
							   @Nullable GeneratedClasses aColl,
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
		final var resolvePromise = namespaceInvocation.resolveDeferred();
		if (resolvePromise.isPending()) {
			@NotNull EvaNamespace ns = generateFunctions.generateNamespace(namespaceStatement);
			//ns.setCode(generateFunctions.module.getCompilation().nextClassCode());


			cr.registerNamespace(ns);


			if (coll != null)
				coll.add(ns);

			resolvePromise.resolve(ns);
			result = ns;
		} else if (resolvePromise.isResolved()) {
			resolvePromise.then(new DoneCallback<EvaNamespace>() {
				@Override
				public void onDone(EvaNamespace result) {
					WlGenerateNamespace.this.result = result;
				}
			});
		} else {
			throw new NotImplementedException();
		}

		_isDone = true;
//		tripleo.elijah.util.Stupidity.println_out_2(String.format("** GenerateNamespace %s at %s", namespaceInvocation.getNamespace().getName(), this));
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
