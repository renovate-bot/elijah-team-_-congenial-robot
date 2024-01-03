package tripleo.elijah.stages.gen_c;

import tripleo.elijah.stages.gen_c.statements.GCR_Rule;

public interface GenerateC_Statement {
	String getText();

	GCR_Rule rule();
}
