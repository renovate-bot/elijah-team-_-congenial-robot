package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.RegularTypeName;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_GenericTypeNameType;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.stages.logging.ElLog;

class GCM_GF implements GCM_D {
	private final GenerateC   gc;
	private final EvaFunction gf;
	private final ElLog       LOG;

	public GCM_GF(final EvaFunction aGf, final ElLog aLOG, final GenerateC aGc) {
		gf  = aGf;
		LOG = aLOG;
		gc  = aGc;
	}

	@Override
	public String find_return_type(final Generate_Method_Header aGenerate_method_header__) {
		var fd = getAssociatedFunctionDef();

		if (fd.returnType() instanceof RegularTypeName rtn) {
			var n = rtn.getName();
			if ("Unit".equals(n)) {
				return "void"; // why not??
			} else if ("SystemInteger".equals(n)) {
				return "int"; // FIXME doesn't seem like much to do, but
			}
		}

		String               returnType = null;
		final TypeTableEntry tte;
		final OS_Type        type;

		@Nullable InstructionArgument result_index = gf.vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = gf.vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			assert result_index != null;
			@NotNull final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null)
				return "void"; // README Assuming Unit
		}

		// Get it from resolved
		tte = getAssociatedTypeTableEntryOfIndex((IntegerIA) result_index);
		final EvaNode res = tte.resolved();
		if (res instanceof final @NotNull EvaContainerNC nc) {
			final int code = nc.getCode();
			return String.format("Z%d*", code);
		}

		// Get it from type.attached
		type = tte.getAttached();

		LOG.info("228 " + type);
		if (type == null) {
			// FIXME request.operation.fail(655) 06/16
			//  as opposed to current-operation
			LOG.err("655 Shouldn't be here (type is null)");
			returnType = "ERR_type_attached_is_null/*2*/";
		} else if (type.isUnitType()) {
			//returnType = "void/*Unit-74*/";
			returnType = "void";
		} else if (type != null) {
			if (type instanceof final @NotNull OS_GenericTypeNameType genericTypeNameType) {
				final TypeName           tn          = genericTypeNameType.getRealTypeName();
				final GCM_CI_GenericPart genericPart = getAssociatedClassInvocation().genericPart();
				final GCM_OS_Type        realType    = genericPart.valueForKey(tn);

				assert !realType.isNull();

				returnType = String.format("/*267*/%s*", realType.getTypeName());
			} else
				returnType = String.format("/*267-1*/%s*", gc.getTypeName(type));
		} else {
			throw new IllegalStateException();
//					LOG.err("656 Shouldn't be here (can't reason about type)");
//					returnType = "void/*656*/";
		}

		return returnType;
	}

	private GCM_ClassInvocation getAssociatedClassInvocation() {
		return new GCM_ClassInvocation(gf.fi.getClassInvocation());
	}

	@NotNull
	private TypeTableEntry getAssociatedTypeTableEntryOfIndex(final IntegerIA result_index) {
		return gf.getTypeTableEntry(result_index.getIndex());
	}

	@NotNull
	private FunctionDef getAssociatedFunctionDef() {
		return gf.getFD();
	}
}
