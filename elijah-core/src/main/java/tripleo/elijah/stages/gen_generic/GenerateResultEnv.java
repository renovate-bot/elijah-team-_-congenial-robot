package tripleo.elijah.stages.gen_generic;

import tripleo.elijah.comp.notation.GM_GenerateModule;

import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

public record GenerateResultEnv(GenerateResultSink resultSink,
								GenerateResult gr,
								WorkManager wm,
								WorkList wl,
								GM_GenerateModule generateModule) {

	public GenerateFiles getGenerateFiles() {
		return generateModule().gmr().getGenerateFiles(() -> this);
	}

}
