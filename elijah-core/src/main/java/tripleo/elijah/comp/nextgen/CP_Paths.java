package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.nextgen.i.CP_RootType;
import tripleo.elijah.nextgen.ER_Node;

import java.util.ArrayList;
import java.util.List;

public class CP_Paths {
	private final          Compilation   _c;
	private final @NotNull CP_StdlibPath stdlibRoot;
	private                CP_OutputPath outputRoot;
	private @NotNull       List<ER_Node> outputNodes = new ArrayList<>();

	public CP_Path outputRoot() {
		return outputRoot;
	}

	public CP_Paths(final Compilation aC) {
		_c         = aC;
		outputRoot = new CP_OutputPath(_c);
		stdlibRoot = new CP_StdlibPath(_c);
	}

	public void signalCalculateFinishParse() {
		outputRoot.signalCalculateFinishParse();
	}

	public void renderNodes() {
		outputRoot._renderNodes(outputNodes);
	}

	public void addNode(CP_RootType t, final ER_Node aNode) {
//		if (aNode.getPath().)
		if (t == CP_RootType.OUTPUT) {
			outputNodes.add(aNode);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public @NotNull CP_StdlibPath stdlibRoot() {
		return stdlibRoot;
	}
}
