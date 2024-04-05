package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.util.buffer.Buffer;

import java.util.List;

public class GCFM implements Reactivable {

	private       Buffer          buf;
	private       Buffer          bufHdr;
	private final @NotNull DeducedBaseEvaFunction gf;
	private final          GenerateResult         gr;

	public GCFM(final @NotNull List<C2C_Result> aRs, final @NotNull DeducedBaseEvaFunction aGf, final GenerateResult aGr) {
		gf = aGf;
		gr = aGr;

		for (C2C_Result r : aRs) {
			// TODO store a Map<TY, Buffer/*GRI??*/> in rs
			switch (r.ty()) {
			case HEADER -> buf = r.getBuffer();
			case IMPL -> bufHdr = r.getBuffer();
			default -> throw new IllegalStateException();
			}
		}

	}

	@Override
	public void respondTo(final ReactiveDimension aDimension) {
		if (aDimension instanceof GenerateC generateC) {
			//final LibraryStatementPart lsp = gf.evaLayer_module_lsp();
			//
			//gr.addFunction(gf, buf, GenerateResult.TY.IMPL, lsp);
			//gr.addFunction(gf, bufHdr, GenerateResult.TY.HEADER, lsp);

			var gr2 = generateC.generateResultProgressive();

			gr2.addFunction_lagging(gf, buf, GenerateResult.TY.IMPL, gr);
			gr2.addFunction_lagging(gf, bufHdr, GenerateResult.TY.HEADER, gr);

			//gr2.addFunction((EvaFunction) gf, buf, GenerateResult.TY.IMPL);
			//gr2.addFunction((EvaFunction) gf, bufHdr, GenerateResult.TY.HEADER);
		}
	}
}
