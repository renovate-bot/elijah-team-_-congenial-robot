package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaConstructor;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.util.buffer.Buffer;

import java.util.List;

public class GCFC implements Reactivable {

	private       Buffer          buf;
	private       Buffer          bufHdr;
	private final BaseEvaFunction gf;
	private final GenerateResult  gr;

	public GCFC(final @NotNull List<C2C_Result> rs, final BaseEvaFunction aGf, final GenerateResult aGr) {
		gf = aGf;
		gr = aGr;

		for (C2C_Result r : rs) {
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
			final LibraryStatementPart lsp = gf.module().getLsp();

			gr.addConstructor((EvaConstructor) gf, buf, GenerateResult.TY.IMPL, lsp);
			gr.addConstructor((EvaConstructor) gf, bufHdr, GenerateResult.TY.HEADER, lsp);

			var gr2 = generateC.generateResultProgressive();
			gr2.addConstructor((EvaConstructor) gf, buf, GenerateResult.TY.IMPL);
			gr2.addConstructor((EvaConstructor) gf, bufHdr, GenerateResult.TY.HEADER);
		}
	}
}
