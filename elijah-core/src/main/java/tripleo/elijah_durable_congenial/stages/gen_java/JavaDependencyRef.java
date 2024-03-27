package tripleo.elijah_durable_congenial.stages.gen_java;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.gen_generic.DependencyRef;
import tripleo.elijah_durable_congenial.stages.gen_generic.DependencyRef;

/**
 * Created 9/13/21 4:26 AM
 */
public class JavaDependencyRef implements DependencyRef {
	private String className;
	private String fieldName; // for static fields
	private String packageName;

	@Override
	public @NotNull String jsonString() {
		Moshi m;

		Moshi                          moshi       = new Moshi.Builder().build();
		JsonAdapter<JavaDependencyRef> jsonAdapter = moshi.adapter(JavaDependencyRef.class);
		String json = jsonAdapter.toJson(this);
		return json;
	}
}
