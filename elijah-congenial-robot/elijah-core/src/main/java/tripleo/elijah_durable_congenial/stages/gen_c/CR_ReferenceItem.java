package tripleo.elijah_durable_congenial.stages.gen_c;

import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;

public interface CR_ReferenceItem {
	String getArg();

	CReference.Connector getConnector();

	GenerateC_Item getGenerateCItem();

	InstructionArgument getInstructionArgument();

	Eventual<GenerateC_Item> getPrevious();

	CReference.Reference getReference();

	void setReference(CReference.Reference aReference);

	String getText();

	Operation2<EG_Statement> getStatement();

	void setConnector(CReference.Connector aConnector);

	void setArg(String aArg);

	void setGenerateCItem(GenerateC_Item aGenerateCItem);

	void setInstructionArgument(InstructionArgument aInstructionArgument);

	void setPrevious(Eventual<GenerateC_Item> aPrevious);

	void setStatement(Operation2<EG_Statement> aStatement);

	void setText(String aText);

	BaseTableEntry getTableEntry();
}
