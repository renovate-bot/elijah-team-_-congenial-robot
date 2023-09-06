package tripleo.elijah.stages.deduce.tastic;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.FormalArgListItem;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;

public class FT_FCA_FormalArgListItem {
	private final FormalArgListItem fali;
	private final BaseEvaFunction   generatedFunction;

	public FT_FCA_FormalArgListItem(final FormalArgListItem aFali, final BaseEvaFunction aGeneratedFunction) {
		fali              = aFali;
		generatedFunction = aGeneratedFunction;
	}

	public void doLogic0(final @NotNull VariableTableEntry vte, final @NotNull VariableTableEntry vte1, final @NotNull ErrSink errSink) {
		final @NotNull OS_Type osType = new OS_UserType(fali.typeName());
		if (!osType.equals(vte.getType().getAttached())) {
			@NotNull TypeTableEntry tte1 = generatedFunction.newTypeTableEntry(
					TypeTableEntry.Type.SPECIFIED,
					osType,
					fali.getNameToken(),
					vte1);
			/*if (p.isResolved())
				System.out.printf("890 Already resolved type: vte1.type = %s, gf = %s, tte1 = %s %n", vte1.type, generatedFunction, tte1);
			else*/
			{
				final OS_Type attached = tte1.getAttached();
				switch (attached.getType()) {
				case USER:
					vte1.getType().setAttached(attached); // !!
					break;
				case USER_CLASS:
					final GenType gt = vte1.getGenType();
					gt.setResolved(attached);
					vte1.resolveType(gt);
					break;
				default:
					errSink.reportWarning("2853 Unexpected value: " + attached.getType());
					//throw new IllegalStateException("Unexpected value: " + attached.getType());
				}
			}
		}

		//vte.type = tte1;
		//tte.attached = tte1.attached;
		//vte.setStatus(BaseTableEntry.Status.KNOWN, best);
	}

}
