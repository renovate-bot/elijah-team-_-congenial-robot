package tripleo.elijah.nextgen.outputstatement;

import org.junit.Test;

import java.util.List;

public class EG_CompoundStatementTest {

	@Test
	public void testIndent() {
		EG_Statement         b = EG_Statement.of("a\n", EX_Explanation.withMessage("b"));
		EG_Statement         m = EG_Statement.of("mm\nmm\n", EX_Explanation.withMessage("m"));
		EG_Statement         e = EG_Statement.of("e\n", EX_Explanation.withMessage("e"));

		EG_CompoundStatement c = new EG_CompoundStatement(b, e, List_of(m), true, EX_Explanation.withMessage("c"));

		System.err.println(c.getText());
	}

	private <T> List<T> List_of(final T aM) {
		return List.of(aM);
	}
}
