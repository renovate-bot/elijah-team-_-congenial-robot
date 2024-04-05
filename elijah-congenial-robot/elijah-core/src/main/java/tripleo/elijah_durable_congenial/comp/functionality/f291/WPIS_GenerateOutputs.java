package tripleo.elijah_durable_congenial.comp.functionality.f291;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_congenial.pipelines.write.*;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_Paths;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.world.i.LivingClass;
import tripleo.elijah_durable_congenial.world.i.LivingFunction;
import tripleo.elijah_durable_congenial.world.i.LivingNamespace;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WPIS_GenerateOutputs implements WP_Indiviual_Step {
	private final List<NG_OutputRequest>   ors = new ArrayList<>();
	private WritePipelineSharedState st;
	private LivingRepo               world;
	private AmazingPart              ap;

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final WP_State_Control sc) {
		Preconditions.checkNotNull(st.getGr());
		Preconditions.checkNotNull(st.sys);

		world = st.c.world();

		final SPrintStream sps = new SPrintStream();
		//DebugBuffersLogic.debug_buffers_logic(result, sps);

		final Default_WPIS_GenerateOutputs_Behavior_PrintDBLString printDBLString = new Default_WPIS_GenerateOutputs_Behavior_PrintDBLString();
		printDBLString.print(sps.getString());

		var cs = st.pa.getActiveClasses();
		var ns = st.pa.getActiveNamespaces();
		var fs = st.pa.getActiveFunctions();

		this.st = st;

		final CP_Paths paths = st.c.paths();
		paths.signalCalculateFinishParse(); // TODO maybe move this 06/22

		final OutputItems itms = new OutputItems(this);

		final int totalCount = cs.size() + ns.size() + fs.size();
		itms.readyCount(totalCount); // looks like it should work, but also looks like it won't

		this.ap = new AmazingPart(this, itms, this.st.c.getCompilationEnclosure());

		for (EvaClass c : cs) {
			final LivingClass lc = world.getClass(c);
			lc.offer(ap);
		}
		for (BaseEvaFunction f : fs) {
			final LivingFunction lf = world.getFunction(f);
			lf.offer(ap);
		}
		for (EvaNamespace n : ns) {
			final LivingNamespace ln = world.getNamespace(n);
			ln.offer(ap);
		}

		ap.handle(S.RunSignal);
	}

	void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
		// eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		this.st.pa.waitGenC(mod, cb);
	}

	public WritePipelineSharedState __st() {
		return st;
	}

}
