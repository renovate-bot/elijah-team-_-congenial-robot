/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;

/**
 * Created 5/31/21 12:00 PM
 */
public class NamespaceInvocation implements IInvocation {

	private final NamespaceStatement                       namespaceStatement;
	private final Eventual<EvaNamespace> resolveDeferred = new Eventual<>();

	public NamespaceInvocation(NamespaceStatement aNamespaceStatement) {
		namespaceStatement = aNamespaceStatement;
	}

	public NamespaceStatement getNamespace() {
		return namespaceStatement;
	}

	public @NotNull Eventual<EvaNamespace> resolvePromise() {
		return resolveDeferred();
	}

	public @NotNull Eventual<EvaNamespace> resolveDeferred() {
		return resolveDeferred;
	}

	@Override
	public void setForFunctionInvocation(@NotNull FunctionInvocation aFunctionInvocation) {
		aFunctionInvocation.setNamespaceInvocation(this);
	}

	@Override
	public String asString() {
		var sb = new StringBuilder();
		sb.append("[NamespaceInvocation ");
		sb.append(namespaceStatement.name().asString());
		sb.append(" ");
		//if (_generated != null)
		//	sb.append(_generated.identityString());
		//else sb.append("null");
		sb.append("]");
		return sb.toString();
	}
}

//
//
//
