package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.stages.logging.ElLog;

class GCM_GC implements GCM_D {
	private final GenerateC      gc;
	private final EvaConstructor gf;
	private final ElLog          LOG;

	public GCM_GC(final EvaConstructor aGf, final ElLog aLOG, final GenerateC aGc) {
		gf  = aGf;
		LOG = aLOG;
		gc  = aGc;
	}

	@Override
	public String find_return_type(final Generate_Method_Header aGenerate_method_header__) {
		final OS_Type        type;
		final TypeTableEntry tte;
		String               returnType = null;

		@Nullable final InstructionArgument result_index = gf.vte_lookup("self");
		if (result_index instanceof IntegerIA integerIA) {
			@NotNull final VariableTableEntry vte = integerIA.getEntry();
			assert vte.getVtt() == VariableTableType.SELF;

			// Get it from resolved
			tte = gf.getTypeTableEntry(integerIA.getIndex());
			final EvaNode res = tte.resolved();
			if (res instanceof final @NotNull EvaContainerNC nc) {
				final int code = nc.getCode();
				return String.format("Z%d*", code);
			}

			// Get it from type.attached
			type = tte.getAttached();

			LOG.info("228-1 " + type);
			if (type.isUnitType()) {
				assert false;
			} else if (type != null) {
				returnType = String.format("/*267*/%s*", gc.getTypeName(type));
			} else {
				LOG.err("655 Shouldn't be here (type is null)");
				returnType = "void/*2*/";
			}

			return returnType;
		} else {
			return "<<cant find self for ctor:57>>";
		}
	}
}
