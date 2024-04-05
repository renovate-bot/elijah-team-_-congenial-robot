package tripleo.elijah_congenial_durable.model.source2;

import tripleo.elijah.nextgen.model.SM_Node;
import tripleo.elijah_durable_congenial.lang.i.TypeName;

public record SM2_TypeName(TypeName cheatTypeName) implements SM_Node {
	public TypeName _cheat() {
		return cheatTypeName;
	}
}
