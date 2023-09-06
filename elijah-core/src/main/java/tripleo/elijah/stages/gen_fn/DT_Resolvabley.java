package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.util.Helpers;

import java.util.LinkedList;
import java.util.List;

public class DT_Resolvabley {
	private final List<DT_Resolvable> x;

	public DT_Resolvabley(final List<DT_Resolvable> aX) {
		x = aX;
	}

	public @NotNull String getNormalPath(final @NotNull BaseEvaFunction generatedFunction, final IdentIA identIA) {
		final List<String> rr = new LinkedList<>();

		for (DT_Resolvable resolvable : x) {
			final OS_Element element = resolvable.element();
			if (element == null && resolvable.deduceItem() instanceof FunctionInvocation fi) {
				var fd = fi.getFunction();
				rr.add("%s".formatted(fd.getNameNode().getText()));
				//rr.add("%s()".formatted(fd.getNameNode().getText()));
				continue;
			}

			if (element instanceof ClassStatement cs) {
				if (resolvable.deduceItem() instanceof FunctionInvocation fi) {
					if (fi.getFunction() instanceof ConstructorDef cd) {
						rr.add("%s()".formatted(cs.getName()));
						continue;
					}
				}
			}
			if (element instanceof FunctionDef fd) {
				if (resolvable.deduceItem() == null) {
					// when ~ is folders.forEach, this is null (fi not set yet) 
					rr.add("%s".formatted(fd.getNameNode().getText()));
					continue;
				}

				if (resolvable.deduceItem() instanceof FunctionInvocation fi) {
					if (fi.getFunction() == fd) {
						rr.add("%s".formatted(fd.getNameNode().getText()));
//						rr.add("%s(...)".formatted(fd.getNameNode().getText()));
						continue;
					}
				}
			}
			if (element instanceof VariableStatement vs) {
				rr.add(vs.getName());
				continue;
			}
			if (element instanceof FormalArgListItem fali) {
				rr.add(fali.name());
				continue;
			}
			if (resolvable.instructionArgument() instanceof IdentIA identIA2) {
				var ite = identIA2.getEntry();

				if (ite._callable_pte() != null) {
					var cpte = ite._callable_pte();

					assert cpte.status != BaseTableEntry.Status.KNOWN;

					rr.add("%s".formatted(ite.getIdent().getText()));
					continue;
				}
			}
		}

		final String r = Helpers.String_join(".", rr);

		final String z = generatedFunction.getIdentIAPathNormal(identIA);

		//assert r.equals(z);
		if (!r.equals(z)) {
			//08/13
			System.err.println("----- 67 Should be " + z);
		}

		return r;
	}
}
