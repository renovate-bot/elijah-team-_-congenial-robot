package tripleo.elijah_durable_congenial.factory.comp;

import tripleo.elijah_durable_congenial.comp.nextgen.CP_Path;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_Path;

public interface NextgenFactory {

	ER_Node createERNode(CP_Path aPp, EG_Statement aSeq);
}
