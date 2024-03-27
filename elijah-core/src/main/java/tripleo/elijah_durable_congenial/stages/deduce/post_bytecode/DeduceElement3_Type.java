package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.TypeTableEntry;

/**
 * Also {@link DeduceType3}
 */
public interface DeduceElement3_Type {
	GenType genType();

	Operation2<GenType> resolved(Context ectx);

	TypeTableEntry typeTableEntry();
}
