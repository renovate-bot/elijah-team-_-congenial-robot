package tripleo.elijah.lang.nextgen.names.i;

import tripleo.elijah.Eventual;

import java.util.List;

public interface EN_Name {
	String getText();

	Eventual<EN_Type> getType();

	List<EN_Usage> getUsages();

	List<EN_Understanding> getUnderstandings();

	void addUnderstanding(EN_Understanding u);

	boolean hasUnderstanding(Class className);

	void addUsage(EN_Usage deduceUsage);
}
