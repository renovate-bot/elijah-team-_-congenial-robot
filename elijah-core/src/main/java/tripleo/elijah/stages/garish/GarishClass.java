package tripleo.elijah.stages.garish;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.lang.types.OS_UserClassType;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.gen_c.CClassDecl;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.BufferTabbedOutputStream;
import tripleo.elijah.world.i.LivingClass;

import java.util.Map;

public class GarishClass {

	public void garish(final GenerateC aGenerateC, final GenerateResult gr, final @NotNull GenerateResultSink aResultSink) {
		final LivingClass dlc = _lc;
		final EvaClass    x   = dlc.evaNode();

		if (x.generatedAlready)
			return; ///////////////////////////////////////////////////////////////////////////////////////throw new IllegalStateException("Error");

		switch (x.getKlass().getType()) {
		// Don't generate class definition for these three
		case INTERFACE:
		case SIGNATURE:
		case ABSTRACT:
			return;
		}

		//aResultSink.addClass_0(this, tos.getBuffer(), tosHdr.getBuffer());
		aResultSink.addClass_1(this, gr, aGenerateC);
		x.generatedAlready = true;
	}

	public @NotNull BufferTabbedOutputStream getClassBuffer(final @NotNull GenerateC aGenerateC) {
		final EvaClass evaClass = getLiving().evaNode();

		final CClassDecl decl = new CClassDecl(evaClass);
		decl.evaluatePrimitive();

		final String class_name = aGenerateC.getTypeName(evaClass);
		final int    class_code = evaClass.getCode();

		return getClassBuffer(evaClass, decl, class_name, class_code);
	}

	private final LivingClass _lc;

	@Contract(pure = true)
	public GarishClass(final LivingClass aLivingClass) {
		_lc = aLivingClass;
		//_lc.setGarish(this);
	}

	public @NotNull BufferTabbedOutputStream getClassBuffer(final @NotNull EvaClass x,
															final @NotNull CClassDecl decl,
															final String class_name,
															final int class_code) {
		final BufferTabbedOutputStream tos = new BufferTabbedOutputStream();

		// TODO remove this block when constructors are added in dependent functions, etc in Deduce

		// TODO what about named constructors and ctor$0 and "the debug stack"
		tos.put_string_ln(String.format("%s* ZC%d() {", class_name, class_code));
		tos.incr_tabs();
		tos.put_string_ln(String.format("%s* R = GC_malloc(sizeof(%s));", class_name, class_name));
		tos.put_string_ln(String.format("R->_tag = %d;", class_code));
		if (decl.prim) {
			// TODO consider NULL, and floats and longs, etc
			if (!decl.prim_decl.equals("bool"))
				tos.put_string_ln("R->vsv = 0;");
			else
				tos.put_string_ln("R->vsv = false;");
		} else {
			for (EvaClass.VarTableEntry o : x.varTable) {
//					final String typeName = getTypeNameForVarTableEntry(o);
				// TODO this should be the result of getDefaultValue for each type
				tos.put_string_ln(String.format("R->vm%s = 0;", o.nameToken));
			}
		}
		tos.put_string_ln("return R;");
		tos.dec_tabs();
		tos.put_string_ln(String.format("} // class %s%s", decl.prim ? "box " : "", x.getName()));
		tos.put_string_ln("");

		tos.flush();

		tos.close();
		return tos;
	}

	public @NotNull BufferTabbedOutputStream getHeaderBuffer(final @NotNull GenerateC aGenerateC) {
		final EvaClass evaClass = getLiving().evaNode();

		final CClassDecl decl = new CClassDecl(evaClass);
		decl.evaluatePrimitive();

		final String class_name = aGenerateC.getTypeName(evaClass);
		final int    class_code = evaClass.getCode();

		return getHeaderBuffer(aGenerateC, evaClass, decl, class_name);
	}

	public @NotNull BufferTabbedOutputStream getHeaderBuffer(final @NotNull GenerateC aGenerateC,
															 final @NotNull EvaClass x,
															 final @NotNull CClassDecl decl,
															 final String class_name) {
		final BufferTabbedOutputStream tosHdr = new BufferTabbedOutputStream();

		tosHdr.put_string_ln("typedef struct {");
		tosHdr.incr_tabs();
		tosHdr.put_string_ln("int _tag;");
		if (!decl.prim) {
			for (EvaClass.VarTableEntry o : x.varTable) {
				final String typeName = aGenerateC.getTypeNameGNCForVarTableEntry(o);
				tosHdr.put_string_ln(String.format("%s vm%s;", typeName, o.nameToken));
			}
		} else {
			tosHdr.put_string_ln(String.format("%s vsv;", decl.prim_decl));
		}

		tosHdr.dec_tabs();
		tosHdr.put_string_ln("");

		var xx = finalizedGenericPrintable(x);
		tosHdr.put_string_ln(String.format("} %s;  // class %s%s", class_name, decl.prim ? "box " : "", xx
										   // x.getName()
										  ));


		tosHdr.put_string_ln("");
		//tosHdr.put_string_ln("");

		tosHdr.flush();
		tosHdr.close();
		return tosHdr;
	}

	public @NotNull String finalizedGenericPrintable(final @NotNull EvaClass evaClass) {
		final ClassStatement                 klass = evaClass.getKlass();
		final ClassInvocation.CI_GenericPart x     = evaClass.ci.genericPart();

		final String        name = klass.getName();
		final StringBuilder sb   = new StringBuilder();
		sb.append(name);

		sb.append('[');

		if (x != null) {
			for (Map.Entry<TypeName, OS_Type> entry : x.entrySet()) {
				final OS_Type y = entry.getValue();

				if (y instanceof OS_UnknownType unk) {
				} else {
					if (y instanceof OS_UserClassType uct) {
						sb.append("%s,".formatted(uct.getClassOf().getName()));
					}
				}
			}
		}

		sb.append(']');

		return sb.toString();
	}

	public LivingClass getLiving() {
		return _lc;
	}

/*
	@Contract(pure = true)
	public void logProgress(final GARISH_CLASS_LOG_PROGRESS aCode, final EvaClass aEvaClass, final Buffer aBuffer) {
		if (aCode == GARISH_CLASS_LOG_PROGRESS.IMPL) {
			// implementation
		} else if (aCode == GARISH_CLASS_LOG_PROGRESS.HEADER) {
			// declaration
		}
		//aEvaClass.getName();
		//aBuffer.getText();
	}

	public enum GARISH_CLASS_LOG_PROGRESS {
		HEADER(59), IMPL(53);

		GARISH_CLASS_LOG_PROGRESS(final int aI) {

		}
	}
*/
}
