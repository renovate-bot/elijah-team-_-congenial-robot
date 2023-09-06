package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.util.BufferTabbedOutputStream;

class C2C_CodeForConstructor_Statement implements EG_Statement {

	private final String     class_name;
	private final int        class_code;
	private final String     constructorName;
	private final CClassDecl decl;
	private final EvaClass   x;

	C2C_CodeForConstructor_Statement(final String aClassName,
									 final int aClassCode,
									 final String aConstructorName,
									 final CClassDecl aDecl,
									 final EvaClass aX) {
		class_name      = aClassName;
		class_code      = aClassCode;
		constructorName = aConstructorName;
		decl            = aDecl;
		x               = aX;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("C2C_CodeForConstructor_Statement");
	}

	@Override
	public String getText() {
		final BufferTabbedOutputStream tos = new BufferTabbedOutputStream();

		getTextInto(tos);

		return tos.toString();
	}

	public void getTextInto(final @NotNull BufferTabbedOutputStream tos) {
		tos.put_string_ln(String.format("%s* ZC%d%s() {", class_name, class_code, constructorName));
		tos.incr_tabs();
		tos.put_string_ln(String.format("%s* R = GC_malloc(sizeof(%s));", class_name, class_name));
		tos.put_string_ln(String.format("R->_tag = %d;", class_code));
		if (decl.prim) {
			// TODO consider NULL, and floats and longs, etc
			if (!decl.prim_decl.equals("bool"))
				tos.put_string_ln("R->vsv = 0;");
			else if (decl.prim_decl.equals("bool"))
				tos.put_string_ln("R->vsv = false;");
		} else {
			for (final EvaClass.VarTableEntry o : x.varTable) {
//					final String typeName = getTypeNameForVarTableEntry(o);
				// TODO this should be the result of getDefaultValue for each type
				tos.put_string_ln(String.format("R->vm%s = 0;", o.nameToken));
			}
		}

		tos.dec_tabs();
	}
}
