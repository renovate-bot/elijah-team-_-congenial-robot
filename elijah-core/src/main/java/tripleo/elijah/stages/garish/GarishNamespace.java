package tripleo.elijah.stages.garish;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.BufferTabbedOutputStream;
import tripleo.elijah.world.i.LivingNamespace;
import tripleo.elijah.world.impl.DefaultLivingNamespace;
import tripleo.util.buffer.Buffer;

public class GarishNamespace {
	public @NotNull BufferTabbedOutputStream getImplBuffer(final @NotNull EvaNamespace ignoredX,
														   final String class_name,
														   final int class_code) {
		final BufferTabbedOutputStream tos = new BufferTabbedOutputStream();

		tos.put_string_ln(String.format("%s* ZNC%d() {", class_name, class_code));
		tos.incr_tabs();

		// TODO multiple calls of namespace function (need if/else statement)
		tos.put_string_ln(String.format("%s* R = GC_malloc(sizeof(%s));", class_name, class_name));
		//tos.put_string_ln(String.format("R->_tag = %d;", class_code));
		tos.put_string_ln("");
		tos.put_string_ln(String.format("zN%d_instance = R;", class_code));
		tos.put_string_ln("return R;");
		tos.dec_tabs();
		tos.put_string_ln(String.format("} // namespace `%s'", class_name /*x.getName()*/));
		tos.put_string_ln("");

		tos.flush();
		tos.close();
		return tos;
	}

	private final LivingNamespace _lc;

	@Contract(pure = true)
	public GarishNamespace(final LivingNamespace aLivingClass) {
		_lc = aLivingClass;
		//_lc.setGarish(this);
	}

	public void garish(final GenerateC aGenerateC, final GenerateResult gr, final @NotNull GenerateResultSink aResultSink) {
		final DefaultLivingNamespace dln = (DefaultLivingNamespace) _lc;
		final EvaNamespace           x   = dln.evaNode();

		if (x.generatedAlready)
			throw new IllegalStateException("Error");

		// TODO do we need `self' parameters for namespace?

		if (x.varTable.size() > 0) { // TODO should we let this through?
//			aResultSink.addNamespace_0(this, result.tos().getBuffer(), result.tosHdr().getBuffer());
			aResultSink.addNamespace_1(this, gr, aGenerateC);
		}

		x.generatedAlready = true;
	}

	public @NotNull BufferTabbedOutputStream getHeaderBuffer(final @NotNull GenerateC aGenerateC,
															 final @NotNull EvaNamespace x,
															 final String class_name,
															 final int class_code) {
		final BufferTabbedOutputStream tosHdr = new BufferTabbedOutputStream();

		tosHdr.put_string_ln("typedef struct {");
		tosHdr.incr_tabs();
		//tosHdr.put_string_ln("int _tag;");
		for (EvaNamespace.VarTableEntry o : x.varTable) {
			final String typeName = aGenerateC.getTypeNameGNCForVarTableEntry(o);

			tosHdr.put_string_ln(String.format("%s* vm%s;", o.varType == null ? "void " : typeName, o.nameToken));
		}

		tosHdr.dec_tabs();
		tosHdr.put_string_ln("");
		tosHdr.put_string_ln(String.format("} %s; // namespace `%s' in %s", class_name, x.getName(), x.module().getFileName()));
		// TODO "instance" namespaces
		tosHdr.put_string_ln("");
		tosHdr.put_string_ln(String.format("%s* zN%d_instance; // namespace `%s'", class_name, class_code, x.getName()));
		tosHdr.put_string_ln("");

		tosHdr.put_string_ln("");
		tosHdr.put_string_ln("");
		for (EvaNamespace.VarTableEntry o : x.varTable) {
			//final String typeName = getTypeNameForVarTableEntry(o);
			tosHdr.put_string_ln(String.format("R->vm%s = 0;", o.nameToken));
		}

		tosHdr.close();
		return tosHdr;
	}

	@Contract(pure = true)
	public void logProgress(final GARISH_NAMESPACE_LOG_PROGRESS aCode, final EvaNamespace aEvaNamespace, final Buffer aBuffer) {
		if (aCode == GARISH_NAMESPACE_LOG_PROGRESS.IMPL) {
			// impl
		} else if (aCode == GARISH_NAMESPACE_LOG_PROGRESS.HEADER) {
			// impl
		}
		//aEvaNamespace.getName();
		//aBuffer.getText();
	}

	public LivingNamespace getLiving() {
		return _lc;
	}

	public enum GARISH_NAMESPACE_LOG_PROGRESS {
		HEADER(68), IMPL(62);

		GARISH_NAMESPACE_LOG_PROGRESS(final int aI) {

		}
	}
}
