package tripleo.elijah.comp.i;

import java.util.List;
import java.util.function.Consumer;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.Eventual;
import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.EvaPipeline;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.WritePipeline;
import tripleo.elijah.comp.internal.Provenance;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.ElLog;

public interface IPipelineAccess {
	void _setAccessBus(AccessBus ab);

	void addFunctionStatement(EvaPipeline.FunctionStatement aFunctionStatement);

	void addLog(ElLog aLOG);

	void addOutput(NG_OutputItem aO);

	AccessBus getAccessBus();

	Compilation getCompilation();

	CompilationEnclosure getCompilationEnclosure();

	GenerateResultSink getGenerateResultSink();

	//List<NG_OutputItem> getOutputs();

	@NotNull Eventual<PipelineLogic> getPipelineLogicPromise();

	ProcessRecord getProcessRecord();

	WritePipeline getWitePipeline();

	void notate(Provenance provenance, GN_Notable aNotable);

	PipelineLogic pipelineLogic();

	void registerNodeList(DoneCallback<List<EvaNode>> done);

	void setEvaPipeline(@NotNull EvaPipeline agp);

	void setGenerateResultSink(GenerateResultSink aGenerateResultSink);

	void setNodeList(List<EvaNode> aEvaNodeList);

	void setWritePipeline(WritePipeline aWritePipeline);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeClass(EvaClass aEvaClass);

	void activeNamespace(EvaNamespace aEvaNamespace);

	List<EvaNamespace> getActiveNamespaces();

	List<BaseEvaFunction> getActiveFunctions();

	List<EvaClass> getActiveClasses();

	void _send_GeneratedClass(EvaNode aClass);

	void waitGenC(OS_Module mod, Consumer<GenerateC> aCb);

	void resolveWaitGenC(OS_Module mod, GenerateC gc);

	void install_notate(Provenance aProvenance, Class<? extends GN_Notable> aRunClass, Class<? extends GN_Env> aEnvClass);

	void notate(Provenance aProvenance, GN_Env aPlRun2);

	void resolvePipelinePromise(PipelineLogic aPipelineLogic);

	DeducePhase getDeducePhase();

	EIT_ModuleList getModuleList();

	void _ModuleList_add(OS_Module aM);

	Eventual<EvaPipeline> getEvaPipelinePromise();
}
