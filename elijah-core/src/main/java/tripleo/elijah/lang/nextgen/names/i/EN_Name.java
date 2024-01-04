package tripleo.elijah.lang.nextgen.names.i;

import tripleo.elijah.Eventual;

import java.util.List;

public interface EN_Name {
	String getText();

	default String asString() { return getText(); }

	Eventual<EN_Type> getType();


	void addUnderstanding(EN_Understanding u);

	List<EN_Understanding> getUnderstandings();

	/**
	 * TODO 24j3 Do we want @{link {@link tripleo.small.ES_Symbol}} here?
	 */
	boolean hasUnderstanding(Class<?> className);


	void addUsage(EN_Usage deduceUsage);

	List<EN_Usage> getUsages();
}
