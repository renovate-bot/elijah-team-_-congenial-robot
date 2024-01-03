package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaConstructor;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.stages.pp.IPP_Function;
import tripleo.elijah.stages.pp.PP_Function;

import java.util.ArrayList;
import java.util.List;

public class NG_OutputFunction implements NG_OutputItem {
	private List<C2C_Result> collect;
	//private GenerateFiles    generateFiles;
	private IPP_Function     ppf;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final List<NG_OutputStatement> r = new ArrayList<>();

		if (collect != null) {
			for (C2C_Result c2c : collect) {
				final EG_Statement      x = c2c.getStatement();
				final GenerateResult.TY y = c2c.ty();

				r.add(new NG_OutputFunctionStatement(c2c));
			}
		}

		return r;
	}

	@Override
	public EOT_FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC, final GenerateResult.@NotNull TY ty) {
		if (getGf() instanceof EvaFunction)
			return aOutputStrategyC.nameForFunction1((EvaFunction) getGf(), ty);
		else
			return aOutputStrategyC.nameForConstructor1((EvaConstructor) getGf(), ty);
	}

	public BaseEvaFunction getGf() {
		return ((PP_Function) ppf).getCarrier();
	}

	public void setFunction(final IPP_Function aGf, final GenerateFiles ignoredAGenerateFiles, final List<C2C_Result> aCollect) {
		ppf           = aGf;
		//generateFiles = aGenerateFiles;
		collect       = aCollect;
	}
}
