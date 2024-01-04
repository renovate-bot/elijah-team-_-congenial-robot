/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce.declarations;

import lombok.Getter;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah.stages.deduce.DeduceElementWrapper;
import tripleo.elijah.stages.deduce.IInvocation;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.GenType;

/**
 * Created 6/27/21 1:41 AM
 */
public class DeferredMember {
	@Getter private final IInvocation                               invocation;
	@Getter private final DeduceElementWrapper                      parent;
	@Getter private final VariableStatement                     variableStatement;
	private final Eventual<EvaNode>                         externalRef = new Eventual<>();
	private final DeferredObject<GenType, Diagnostic, Void> typePromise = new DeferredObject<>();

	public DeferredMember(DeduceElementWrapper aParent, IInvocation aInvocation, VariableStatement aVariableStatement) {
		parent            = aParent;
		invocation        = aInvocation;
		variableStatement = aVariableStatement;
	}

	public Eventual<EvaNode> externalRef() {
		return externalRef;
	}

	public @NotNull Eventual<EvaNode> externalRefDeferred() {
		return externalRef;
	}

	@Override
	public @NotNull String toString() {
		return "DeferredMember{" +
				"parent=" + parent +
				", variableName=" + variableStatement.getName() +
				'}';
	}

	public @NotNull Promise<GenType, Diagnostic, Void> typePromise() {
		return typePromise;
	}

	// for DeducePhase
	public @NotNull DeferredObject<GenType, Diagnostic, Void> typeResolved() {
		return typePromise;
	}

	public IInvocation getInvocation() {
		// antilombok
		return invocation;
	}

	public DeduceElementWrapper getParent() {
		// antilombok
		return parent;
	}

	public VariableStatement getVariableStatement() {
		// antilombok
		return variableStatement;
	}
}

//
//
//
