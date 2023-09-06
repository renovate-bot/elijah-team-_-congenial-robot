package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/*
 * Created on 5/18/2019 at 16:36
 *
 * $$Id$
 *
 */
public class Helpers {
	@NotNull
	static ExpressionList LocalAgnTmpNodeToListVarRef(final @NotNull List<LocalAgnTmpNode> of) {
		final ExpressionList expl = new ExpressionListImpl();
		for (final LocalAgnTmpNode node : of) {
			final VariableReferenceImpl vr = new VariableReferenceImpl();
			vr.setMain(node.genName());
			expl.add(vr);
//			NotImplementedException.raise();
		}
		return expl;
	}

	@NotNull
	static ExpressionKind ExpressionOperatorToExpressionType(final @NotNull ExpressionOperators middle) {
		final ExpressionKind middle1;
		switch (middle) {
		case OP_MINUS:
			middle1 = ExpressionKind.SUBTRACTION;
			break;
		case OP_MULT:
			middle1 = ExpressionKind.MULTIPLY;
			break;
		default:
			throw new NotImplementedException();
		}
		return middle1;
	}

	@NotNull
	public static String getFunctionName(final int code, final String aStr, final @NotNull ExpressionList expressionList) {
		final StringBuilder sb = new StringBuilder();
		sb.append("z");
		sb.append(code);
		sb.append(aStr);
		sb.append("(");
/*
		boolean x=false;
		for (IExpression e : expr.exprList()) {
			sb.append(e.toString());
			sb.append(", ");
			x=true;
		}
		if (x==true) {
			sb.deleteCharAt(sb.length());
			sb.deleteCharAt(sb.length());
		}
*/
		final List<String> ls = new ArrayList<String>();
		for (final IExpression e : expressionList) {
			ls.add(e.toString());
		}
		sb.append(tripleo.elijah.util.Helpers.String_join(", ", ls));
		sb.append(")");
		return sb.toString();
	}

	//	public static List<String> List_of(String string, String string2, String string3) {
//		List<String> r = new ArrayList<String>();
//		r.add(string);
//		r.add(string2);
//		r.add(string3);
//		return r;
//	}

}

//
//
//
