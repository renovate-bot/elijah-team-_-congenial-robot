package tripleo.elijah_durable_congenial.lang.nextgen.names.impl;

import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Name;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Name;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;

public record EN_NameUsage(
		EN_Name theName,
		DeduceElement3_IdentTableEntry deduceElement3_identTableEntry
) implements EN_Usage {
}
