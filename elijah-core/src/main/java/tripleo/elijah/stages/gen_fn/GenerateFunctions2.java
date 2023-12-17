package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassItem;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah.stages.logging.ElLog;

import java.util.ArrayList;

public class GenerateFunctions2 {
	private final ElLog LOG;
	private final OS_Module module;

	@Contract(pure = true)
	public GenerateFunctions2(GenerateFunctions gf1) {
		// TODO 11/10 scared to register a new "stage" or whateva, should (have) be(en) easy
		this.module = gf1.module;
		this.LOG = gf1.LOG;
	}

	public EvaClass generateClass(final ClassStatement aClassStatement,
								  final ClassInvocation aClassInvocation,
								  final RegisterClassInvocation_env aPassthruEnv) {
		final EvaClass        gc   = new EvaClass(aClassStatement, module);
		final __GenerateClass gcgc = new __GenerateClass(LOG, aPassthruEnv);

		for (final ClassItem item : new ArrayList<>(aClassStatement.getItems())) {
			gcgc.processItem(aClassStatement, item, gc);
		}

		@NotNull EvaClass Result = gc;
		Result.ci = aClassInvocation;
		return Result;
	}
}
