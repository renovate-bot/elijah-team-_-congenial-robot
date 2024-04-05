package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_congenial.pp.IPP_Namespace;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.Deduce_CreationClosure;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.NamespaceInvocation;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;

/**
 * Created 5/16/21 12:46 AM
 */
public class WlGenerateFunction implements WorkJob {
	private final          ICodeRegistrar     cr;
	private final          GenerateFunctions  generateFunctions;
	private final          FunctionDef        functionDef;
	private final @NotNull FunctionInvocation functionInvocation;
	private                boolean            _isDone = false;
	private @Nullable      EvaFunction        result;
	private @NotNull Eventual<BaseEvaFunction> _p_Result =new Eventual<BaseEvaFunction>();

	public WlGenerateFunction(GenerateFunctions aGenerateFunctions, @NotNull FunctionInvocation aFunctionInvocation, final ICodeRegistrar aCr) {
		functionDef        = aFunctionInvocation.getFunction();
		generateFunctions  = aGenerateFunctions;
		functionInvocation = aFunctionInvocation;
		cr                 = aCr;
	}

	public WlGenerateFunction(final OS_Module aModule, final FunctionInvocation aFunctionInvocation, final @NotNull Deduce_CreationClosure aCl) {
		this(aCl.generatePhase().getGenerateFunctions(aModule), aFunctionInvocation, aCl.deducePhase().getCodeRegistrar());
	}

	public EvaFunction getResult() {
		return result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (_isDone) return;

		if (functionInvocation.getGenerated() == null) {
			OS_Element           parent = functionDef.getParent();
			@NotNull EvaFunction gf     = generateFunctions.generateFunction(functionDef, parent, functionInvocation);

			{
				int i = 0;
				for (TypeTableEntry tte : functionInvocation.getArgs()) {
					i = i + 1;
					if (tte.getAttached() == null) {
						SimplePrintLoggerToRemoveSoon.println_err_2(String.format("4949 null tte #%d %s in %s", i, tte, gf));
					}
				}
			}

//			lgf.add(gf);

			if (parent instanceof NamespaceStatement) {
				final NamespaceInvocation nsi = functionInvocation.getNamespaceInvocation();
				assert nsi != null;
				nsi.resolveDeferred().then(new DoneCallback<EvaNamespace>() {
					@Override
					public void onDone(@NotNull EvaNamespace result) {
						IPP_Namespace ns;

						if (result.getFunction(functionDef) == null) {
							cr.registerFunction1(gf);
							//gf.setCode(generateFunctions.module.getCompilation().nextFunctionCode());
							result.addFunction(functionDef, gf);
						}
						gf.setClass(result);
					}
				});
			} else {
				final ClassInvocation ci = functionInvocation.getClassInvocation();
				ci.resolvePromise().then((EvaClass result) -> {
					if (result.getFunction(functionDef) == null) {
						cr.registerClass1(result);
//							gf.setCode(generateFunctions.module.getCompilation().nextFunctionCode());
						result.addFunction(functionDef, gf);
					}
					gf.setClass(result);
				});
			}
			result = gf;
			functionInvocation.setGenerated(gf);
			functionInvocation.generateDeferred().resolve(gf);
			_p_Result=functionInvocation.generateDeferred();
		} else {
			result = (EvaFunction) functionInvocation.getGenerated();
			_p_Result=functionInvocation.generateDeferred();
		}
		_isDone = true;
	}

	public Eventual<BaseEvaFunction> getResultPromise() {
		return _p_Result;
	}
}
