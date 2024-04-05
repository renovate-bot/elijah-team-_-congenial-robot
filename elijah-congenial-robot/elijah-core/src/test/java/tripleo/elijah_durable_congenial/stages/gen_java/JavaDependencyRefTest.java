package tripleo.elijah_durable_congenial.stages.gen_java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaDependencyRefTest {
	JavaDependencyRef jdr;

	@Before
	public void setUp() {
		jdr = new JavaDependencyRef();
		jdr.setClassName("className");
		jdr.setFieldName("fieldName");
		jdr.setPackageName("packageName");
	}

	@Test
	public void jsonString() {
		final String jsonString = jdr.jsonString();

		System.err.println(jsonString);

		assertEquals("{\"className\":\"className\",\"fieldName\":\"fieldName\",\"packageName\":\"packageName\"}", jsonString);
	}
}