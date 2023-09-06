package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Coder {
	private static void extractNodes_toResolvedNodes(@NotNull final Map<FunctionDef, EvaFunction> aFunctionMap, @NotNull final List<EvaNode> resolved_nodes) {
		aFunctionMap.values().stream().map(generatedFunction -> (generatedFunction.idte_list)
						.stream()
						.filter(IdentTableEntry::isResolved)
						.map(IdentTableEntry::resolvedType)
						.collect(Collectors.toList()))
				.forEach(resolved_nodes::addAll);
	}

	private final ICodeRegistrar codeRegistrar;

	@Contract(pure = true)
	public Coder(final ICodeRegistrar aCodeRegistrar) {
		codeRegistrar = aCodeRegistrar;
	}

	public void codeNode(final EvaNode generatedNode, final OS_Module mod) {
		final Coder coder = this;

		if (generatedNode instanceof final @NotNull EvaFunction generatedFunction) {
			coder.codeNodeFunction(generatedFunction, mod);
		} else if (generatedNode instanceof final @NotNull EvaClass generatedClass) {
			coder.codeNodeClass(generatedClass, mod);
		} else if (generatedNode instanceof final @NotNull EvaNamespace generatedNamespace) {
			coder.codeNodeNamespace(generatedNamespace, mod);
		}
	}

	public void codeNodeFunction(@NotNull final BaseEvaFunction generatedFunction, final OS_Module mod) {
//		if (generatedFunction.getCode() == 0)
//			generatedFunction.setCode(mod.parent.nextFunctionCode());
		codeRegistrar.registerFunction(generatedFunction);
	}

	public void codeNodeClass(@NotNull final EvaClass generatedClass, final OS_Module mod) {
//		if (generatedClass.getCode() == 0)
//			generatedClass.setCode(mod.parent.nextClassCode());
		codeRegistrar.registerClass(generatedClass);
	}

	public void codeNodeNamespace(@NotNull final EvaNamespace generatedNamespace, final OS_Module mod) {
//		if (generatedNamespace.getCode() == 0)
//			generatedNamespace.setCode(mod.parent.nextClassCode());
		codeRegistrar.registerNamespace(generatedNamespace);
	}

	public void codeNodes(final OS_Module mod, final @NotNull List<EvaNode> resolved_nodes, final EvaNode generatedNode) {
		if (generatedNode instanceof final @NotNull EvaFunction generatedFunction) {
			codeNodeFunction(generatedFunction, mod);
		} else if (generatedNode instanceof final @NotNull EvaClass generatedClass) {
			//			assert generatedClass.getCode() == 0;
			if (generatedClass.getCode() == 0)
				codeNodeClass(generatedClass, mod);

			setClassmapNodeCodes(generatedClass.classMap, mod);

			extractNodes_toResolvedNodes(generatedClass.functionMap, resolved_nodes);
		} else if (generatedNode instanceof final @NotNull EvaNamespace generatedNamespace) {

			if (generatedNamespace.getCode() != 0)
				codeNodeNamespace(generatedNamespace, mod);

			setClassmapNodeCodes(generatedNamespace.classMap, mod);

			extractNodes_toResolvedNodes(generatedNamespace.functionMap, resolved_nodes);
		}
	}

	private void setClassmapNodeCodes(@NotNull final Map<ClassStatement, EvaClass> aClassMap, final OS_Module mod) {
		aClassMap.values().forEach(generatedClass -> codeNodeClass(generatedClass, mod));
	}
}
