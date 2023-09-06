/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.eclipse.jdt.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.lang.i.NumericExpression;

public class CaseChoiceNode {

	public final            CaseHdrNode            header;
	private final @Nullable VariableReferenceNode  _varref;
	private final @Nullable VariableReferenceNode3 varref3;
	public @Nullable        ExpressionNode         left;
	public @Nullable        ScopeNode              right;
	private @Nullable       String                 _defaultName;

	public CaseChoiceNode(final @NotNull NumericExpression expr1, final CaseHdrNode header) {
		// TODO Auto-generated constructor stub
		left         = new ExpressionNode(expr1);
		this.header  = header;
		right        = null;
		_defaultName = null;
		_varref      = null;
		varref3      = null;
	}

//	/**
//	 * Used for defaults where there is a varname
//	 * Also can be used for _, tho not designed at this point.
//	 *
//	 * @param cctx      unused
//	 * @param varref    the name specified
//	 * @param header
//	 */
//	public CaseChoiceNode(CompilerContext cctx, VariableReferenceImpl varref, CaseHdrNode header) {
//		this.header = header;
//		left  = null;
//		right = null;
//		setDefaultName(varref.getName());
//		_varref = new VariableReferenceNode(cctx, varref);
//	}

	public CaseChoiceNode(final CompilerContext cctx, final VariableReferenceNode3 varref, final CaseHdrNode header) {
		this.header = header;
		left        = null;
		right       = null;
		setDefaultName("----------FOO------------" /*varref.getName()*/);
		_varref = null/*varref*/; //new VariableReferenceNode3(cctx, varref);
		varref3 = varref;
	}

	public CaseChoiceNode(final ExpressionNode left, final ScopeNode right, final CaseHdrNode header) {
		super();
		this.right   = right;
		this.left    = left;
		this.header  = header;
		_defaultName = null;
		_varref      = null;
		varref3      = null;
	}

	public String getDefaultName() {
		return _defaultName;
	}

	public void setDefaultName(@NonNull final String _defaultName) {
		this._defaultName = _defaultName;
	}

	public boolean is_simple() {
		return _defaultName != null;
	}

	public boolean is_default() {
		return _defaultName != null && _defaultName != "_";
	}

	public VariableReferenceNode varref() {
		return _varref;
	}

	public VariableReferenceNode3 varref3() {
		return varref3;
	}
}

//
//
//
