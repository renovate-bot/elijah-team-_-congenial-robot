package tripleo.elijah;

import org.junit.Assert;
import org.junit.Test;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

public class QualidentToDotExpresstionTest {

	@Test
	public void qualidentToDotExpression2() {
		final Qualident q = new QualidentImpl();
		q.append(tripleo.elijah.util.Helpers.string_to_ident("a"));
		q.append(tripleo.elijah.util.Helpers.string_to_ident("b"));
		q.append(tripleo.elijah.util.Helpers.string_to_ident("c"));
		final IExpression e = Helpers.qualidentToDotExpression2(q);
		SimplePrintLoggerToRemoveSoon.println_out_2(e);
		Assert.assertEquals("a.b.c", e.toString());
	}
}