package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.util.BufferTabbedOutputStream;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class NG_OutputClass implements NG_OutputItem {
	private GarishClass garishClass;
	private GenerateC   generateC;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final EvaClass x = garishClass.getLiving().evaNode();

		final BufferTabbedOutputStream tos = garishClass.getClassBuffer(generateC);

		var implText = new NG_OutputClassStatement(tos, x.module(), GenerateResult.TY.IMPL);

		final BufferTabbedOutputStream tosHdr = garishClass.getHeaderBuffer(generateC);

		var headerText = new NG_OutputClassStatement(tosHdr, x.module(), GenerateResult.TY.HEADER);

		return List_of(implText, headerText);
	}

	@Override
	public EOT_OutputFile.FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC, final GenerateResult.@NotNull TY ty) {
		final EvaClass x = garishClass.getLiving().evaNode();

		return aOutputStrategyC.nameForClass1(x, ty);
	}

	public void setClass(final GarishClass aGarishClass, final GenerateC aGenerateC) {
		this.garishClass = aGarishClass;
		generateC        = aGenerateC;
	}
}
