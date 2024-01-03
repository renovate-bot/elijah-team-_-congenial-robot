package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.util.BufferTabbedOutputStream;
import tripleo.util.buffer.Buffer;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class NG_OutputNamespace implements NG_OutputItem {
	private GarishNamespace garishNamespace;
	private GenerateC       generateC;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final EvaNamespace x = garishNamespace.getLiving().evaNode();
		var                m = x.module();

		final String class_name;

		if (x.getCode() != 0) {
			class_name = "ZN%d".formatted(x.getCode());
		} else {
			class_name = x.getName();
		}

		final BufferTabbedOutputStream tos = garishNamespace.getImplBuffer(x, class_name, x.getCode());
		final Buffer                   buf = tos.getBuffer();

		var implText = new NG_OutputNamespaceStatement(buf, GenerateResult.TY.IMPL, m);

		final BufferTabbedOutputStream tosHdr    = garishNamespace.getHeaderBuffer(generateC, x, class_name, x.getCode());
		final Buffer                   hdrBuffer = tosHdr.getBuffer();

		var headerText = new NG_OutputNamespaceStatement(hdrBuffer, GenerateResult.TY.HEADER, m);

		return List_of(implText, headerText);
	}

	@Override
	public EOT_FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC, final GenerateResult.@NotNull TY ty) {
		final EvaNamespace x = garishNamespace.getLiving().evaNode();

		return aOutputStrategyC.nameForNamespace1(x, ty);
	}

	public void setNamespace(final GarishNamespace aGarishNamespace, final GenerateC aGenerateC) {
		garishNamespace = aGarishNamespace;
		generateC       = aGenerateC;
	}
}
