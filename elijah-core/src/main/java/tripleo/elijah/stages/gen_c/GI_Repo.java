package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.PropertyStatement;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.instructions.ProcIA;

import java.util.HashMap;
import java.util.Map;

class GI_Repo {
	final GenerateC generateC;

	private final Map<Object, GenerateC_Item> items = new HashMap<>();

	GI_Repo(final GenerateC aGenerateC) {
		generateC = aGenerateC;
	}

	public @Nullable GenerateC_Item itemFor(final OS_Element e) {
		if (items.containsKey(e))
			return items.get(e);

		if (e instanceof ClassStatement) {
			final GI_ClassStatement gci = new GI_ClassStatement((ClassStatement) e, this);

			items.put(e, gci);

			return gci;
		} else if (e instanceof FunctionDef) {
			final GI_FunctionDef gfd = new GI_FunctionDef((FunctionDef) e, this);

			items.put(e, gfd);

			return gfd;
		} else if (e instanceof PropertyStatement) {
			final GI_PropertyStatement gps = new GI_PropertyStatement((PropertyStatement) e, this);

			items.put(e, gps);

			return gps;
		} else if (e instanceof VariableStatementImpl) {
			final GI_VariableStatement gvs = new GI_VariableStatement((VariableStatementImpl) e, this);

			items.put(e, gvs);

			return gvs;
		}

		return null;
	}

	public GI_ProcIA itemFor(final ProcIA aProcIA) {
		final GI_ProcIA gi_proc;
		if (items.containsKey(aProcIA)) {
			gi_proc = (GI_ProcIA) items.get(aProcIA);
		} else {
			gi_proc = new GI_ProcIA(aProcIA, generateC);
			items.put(aProcIA, gi_proc);
		}
		return gi_proc;
	}
}
