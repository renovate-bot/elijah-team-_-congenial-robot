/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.gen_c.c_ast1.C_Assignment;
import tripleo.elijah.stages.gen_c.c_ast1.C_ProcedureCall;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaContainerNC;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;

/**
 * Created 3/7/21 1:22 AM
 */
public class CtorReference {

	@NotNull List<CReference.Reference> refs     = new ArrayList<CReference.Reference>();
	private  EvaNode                    _resolved;
	private  List<String>               args;
	private  String                     ctorName = "";

	void addRef(String text, CReference.Ref type) {
		refs.add(new CReference.Reference(text, type));
	}

	/**
	 * Call before you call build
	 *
	 * @param sl3
	 */
	public void args(List<String> sl3) {
		args = sl3;
	}

	public String build(@NotNull ClassInvocation aClsinv) {
		StringBuilder sb   = new StringBuilder();
		boolean       open = false, needs_comma = false;
//		List<String> sl = new ArrayList<String>();
		String text = "";
		for (CReference.Reference ref : refs) {
			switch (ref.type) {
			case LOCAL:
				text = "vv" + ref.text;
				sb.append(text);
				break;
			case MEMBER:
				text = "->vm" + ref.text;
				sb.append(text);
				break;
			case INLINE_MEMBER:
				text = Emit.emit("/*2190*/") + ".vm" + ref.text;
				sb.append(text);
				break;
			case DIRECT_MEMBER:
				text = Emit.emit("/*1240*/") + "vsc->vm" + ref.text;
				sb.append(text);
				break;
			case FUNCTION: {
				final String s = sb.toString();
				text = String.format("%s(%s", ref.text, s);
				sb   = new StringBuilder();
				open = true;
				if (!s.equals("")) needs_comma = true;
				sb.append(text);
				break;
			}
			case CONSTRUCTOR: {
				final String s = sb.toString();
				text = String.format("%s(%s", ref.text, s);
				sb   = new StringBuilder();
				open = true;
				if (!s.equals("")) needs_comma = true;
				sb.append(text);
				break;
			}
			case PROPERTY_GET: {
				final String s = sb.toString();
				text = String.format("%s(%s", ref.text, s);
				sb   = new StringBuilder();
				open = true;
				if (!s.equals("")) needs_comma = true;
				sb.append(text);
				break;
			}
			default:
				throw new IllegalStateException("Unexpected value: " + ref.type);
			}
//			sl.add(text);
		}
		{
			// Assuming constructor call
			int code;
			if (_resolved != null) {
				code = ((EvaContainerNC) _resolved).getCode();
			} else {
				code = -3;
			}
			if (code == 0) {
				SimplePrintLoggerToRemoveSoon.println_err_2("** 32135 ClassStatement with 0 code " + aClsinv.getKlass());
			}


			final String n = sb.toString();


			// TODO Garish(?)Constructor.calculateCtorName(?)/Code
			String text2 = String.format("ZC%d%s", code, ctorName); // TODO what about named constructors
			sb.append(" = ");
			sb.append(text2);
			sb.append("(");
			assert !open;
			open = true;

			final C_ProcedureCall pc = new C_ProcedureCall();
			pc.setTargetName(text2);
			pc.setArgs(args);
			final C_Assignment cas = new C_Assignment();
			cas.setLeft(n);
			cas.setRight(pc);

			return cas.getString();
		}

/*
		if (needs_comma && args != null && args.size() > 0)
			sb.append(", ");
		if (open) {
			if (args != null) {
				sb.append(Helpers.String_join(", ", args));
			}
			sb.append(")");
		}
		return sb.toString();
*/
	}

	public void getConstructorPath(@NotNull InstructionArgument ia2, @NotNull BaseEvaFunction gf) {
		final List<InstructionArgument> s = CReference._getIdentIAPathList(ia2);

		for (int i = 0, sSize = s.size(); i < sSize; i++) {
			InstructionArgument ia = s.get(i);
			if (ia instanceof IntegerIA) {
				// should only be the first element if at all
				assert i == 0;
				final VariableTableEntry vte = gf.getVarTableEntry(to_int(ia));

				final ConstructorPathOp op = IntegerIA_Ops.get((IntegerIA) ia, sSize).getConstructorPath();
				_resolved = op.getResolved();
				ctorName  = op.getCtorName();

				addRef(vte.getName(), CReference.Ref.LOCAL);
			} else if (ia instanceof IdentIA) {
				final ConstructorPathOp op = IdentIA_Ops.get((IdentIA) ia).getConstructorPath();
				_resolved = op.getResolved();
				ctorName  = op.getCtorName();

				addRef(((IdentIA) ia).getEntry().getIdent().getText(), CReference.Ref.LOCAL); // TDOO check correctness
			} else if (ia instanceof ProcIA) {
//				final ProcTableEntry prte = generatedFunction.getProcTableEntry(to_int(ia));
//				text = (prte.expression.getLeft()).toString();
////				assert i == sSize-1;
//				addRef(text, Ref.FUNCTION); // TODO needs to use name of resolved function
				throw new NotImplementedException();
			} else {
				throw new NotImplementedException();
			}
//			sl.add(text);
		}
	}
}

//
//
//
