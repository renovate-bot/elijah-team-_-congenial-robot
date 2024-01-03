package tripleo.elijah.factory.comp;

import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;

public interface NextgenFactory {

	ER_Node createERNode(CP_Path aPp, EG_Statement aSeq);
}
