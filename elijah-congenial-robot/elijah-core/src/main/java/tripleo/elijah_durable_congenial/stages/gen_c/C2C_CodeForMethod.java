package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;

import tripleo.elijah_durable_congenial.util.BufferTabbedOutputStream;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.util.buffer.Buffer;

import java.util.List;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

public class C2C_CodeForMethod implements Generate_Code_For_Method.C2C_Results {
	private final GenerateResult           gr;
	private final Generate_Code_For_Method generateCodeForMethod;
	private final GenerateResultEnv        fileGen;
	private       boolean                  _calculated;
	private       C2C_Result               buf;
	private       C2C_Result               bufHdr;
	private final WhyNotGarish_Function    whyNotGarishFunction;

	public C2C_CodeForMethod(final @NotNull Generate_Code_For_Method aGenerateCodeForMethod,
							 final @NotNull DeducedBaseEvaFunction aGf,
							 final GenerateResultEnv aFileGen) {
		generateCodeForMethod = aGenerateCodeForMethod;
		fileGen               = aFileGen;
		gr                    = fileGen.gr();

		final GenerateC gc = aGenerateCodeForMethod._gc();
		whyNotGarishFunction = gc.a_lookup(aGf);
	}

	public GenerateResult getGenerateResult() {
		return gr;
	}

	@Override
	public @NotNull List<C2C_Result> getResults() {
		calculate();
		return List_of(buf, bufHdr);
	}

	private void calculate() {
		if (!_calculated) {
			final BufferTabbedOutputStream tos    = generateCodeForMethod.tos;
			final BufferTabbedOutputStream tosHdr = generateCodeForMethod.tosHdr;


			_write_and_flush(tos, tosHdr);
			final Buffer buf1    = tos.getBuffer();
			final Buffer bufHdr1 = tosHdr.getBuffer();

			buf    = new Default_C2C_Result(buf1, GenerateResult.TY.IMPL, "C2C_CodeForMethod IMPL", whyNotGarishFunction);
			bufHdr = new Default_C2C_Result(bufHdr1, GenerateResult.TY.HEADER, "C2C_CodeForMethod HEADER", whyNotGarishFunction);

			_calculated = true;
		}
	}

	private void _write_and_flush(final BufferTabbedOutputStream tos, final BufferTabbedOutputStream tosHdr) {
		final Generate_Method_Header gmh = new Generate_Method_Header(whyNotGarishFunction.cheat(), generateCodeForMethod._gc(), generateCodeForMethod.LOG);

		tos.put_string_ln(String.format("%s {", gmh.header_string));
		tosHdr.put_string_ln(String.format("%s;", gmh.header_string));

		generateCodeForMethod.action_invariant(whyNotGarishFunction, gmh);

		tos.flush();
		tos.close();
		tosHdr.flush();
		tosHdr.close();
	}
}
