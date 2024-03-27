package tripleo.elijah;

import org.junit.Assert;
import org.junit.Test;
import tripleo.elijah_durable_congenial.lang.i.*;

import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.Qualident;
import tripleo.elijah_durable_congenial.lang.impl.QualidentImpl;
import tripleo.elijah_durable_congenial.util.Helpers;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

public class QualidentToDotExpresstionTest {

	@Test
	public void qualidentToDotExpression2() {
		final Qualident q = new QualidentImpl();
		q.append(Helpers.string_to_ident("a"));
		q.append(Helpers.string_to_ident("b"));
		q.append(Helpers.string_to_ident("c"));
		final IExpression e = Helpers.qualidentToDotExpression2(q);
		SimplePrintLoggerToRemoveSoon.println_out_2(e);
		Assert.assertEquals("a.b.c", e.toString());
	}
}