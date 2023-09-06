package tripleo.elijah.stages.deduce.post_bytecode;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.stages.deduce.DeduceLookupUtils;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.ResolveError;
import tripleo.elijah.util.NotImplementedException;

public class DG_AliasStatement implements DG_Item {
	private final          AliasStatementImpl                          aliasStatement;
	private @NotNull       DeferredObject<DG_Item, ResolveError, Void> _resolvePromise = new DeferredObject<>();
	private                boolean                                     _resolveStarted;
	private final @NotNull DeduceTypes2                                __dt2;

	public DG_AliasStatement(final AliasStatementImpl aAliasStatement, final @NotNull DeduceTypes2 aDt2) {
		aliasStatement = aAliasStatement;
		__dt2          = aDt2;
	}

	public Promise<DG_Item, ResolveError, Void> resolvePromise() {
		if (!_resolveStarted) {
			_resolveStarted = true;
			try {
				final @Nullable OS_Element ra = DeduceLookupUtils._resolveAlias2(aliasStatement, __dt2);

				if (ra instanceof ClassStatement) {
					_resolvePromise.resolve(__dt2.DG_ClassStatement((ClassStatement) ra));
				} else if (ra instanceof FunctionDef) {
					_resolvePromise.resolve(__dt2.DG_FunctionDef((FunctionDef) ra));
				} else {
					throw new NotImplementedException();
				}
			} catch (ResolveError aE) {
				_resolvePromise.reject(aE);
			}
		}
		return _resolvePromise;
	}
}
