/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.stages.gen_fn.EvaNamespace;

/**
 * Created 5/31/21 12:00 PM
 */
public class NamespaceInvocation implements IInvocation {

	private final NamespaceStatement                       namespaceStatement;
	private final DeferredObject<EvaNamespace, Void, Void> resolveDeferred = new DeferredObject<EvaNamespace, Void, Void>();

	public NamespaceInvocation(NamespaceStatement aNamespaceStatement) {
		namespaceStatement = aNamespaceStatement;
	}

	public NamespaceStatement getNamespace() {
		return namespaceStatement;
	}

	public @NotNull DeferredObject<EvaNamespace, Void, Void> resolveDeferred() {
		return resolveDeferred;
	}

	public @NotNull Promise<EvaNamespace, Void, Void> resolvePromise() {
		return resolveDeferred.promise();
	}

	@Override
	public void setForFunctionInvocation(@NotNull FunctionInvocation aFunctionInvocation) {
		aFunctionInvocation.setNamespaceInvocation(this);
	}
}

//
//
//
