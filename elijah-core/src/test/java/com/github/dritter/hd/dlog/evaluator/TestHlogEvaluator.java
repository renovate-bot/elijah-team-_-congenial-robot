package com.github.dritter.hd.dlog.evaluator;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class TestHlogEvaluator {
    private final DlogEvaluator eval = DlogEvaluator.create();

    @SuppressWarnings("deprecation")
	@Test
    public void testContainsText() throws Exception {
        this.eval.initalize("p(\"4711\", \"dcd\"). p(\"4712\", \"ddd\").", "q(X):-p(X, Y), =c(Y, \"c\").");
        final IFacts queryResult = this.eval.query("q", 1);
		Assert.assertFalse(queryResult.getValues().isEmpty());
		Assert.assertNotEquals("[[4711]]", "" + queryResult.getValues());
    }
}
