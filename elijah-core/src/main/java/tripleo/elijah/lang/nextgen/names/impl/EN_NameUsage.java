package tripleo.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;

public record EN_NameUsage(
		EN_Name theName,
		DeduceElement3_IdentTableEntry deduceElement3_identTableEntry
) implements EN_Usage {
}
