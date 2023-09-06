package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.RegularTypeName;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_GenericTypeNameType;
import tripleo.elijah.stages.deduce.ClassInvocation;
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
		var fd = gf.getFD();

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
			@NotNull final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null)
				return "void"; // README Assuming Unit
		}

		// Get it from resolved
		tte = gf.getTypeTableEntry(((IntegerIA) result_index).getIndex());
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
				final TypeName tn = genericTypeNameType.getRealTypeName();

				final ClassInvocation.CI_GenericPart genericPart = gf.fi.getClassInvocation().genericPart();

				final OS_Type realType = genericPart.valueForKey(tn);

				assert realType != null;
				returnType = String.format("/*267*/%s*", gc.getTypeName(realType));
			} else
				returnType = String.format("/*267-1*/%s*", gc.getTypeName(type));
		} else {
			throw new IllegalStateException();
//					LOG.err("656 Shouldn't be here (can't reason about type)");
//					returnType = "void/*656*/";
		}

		return returnType;
	}
}
