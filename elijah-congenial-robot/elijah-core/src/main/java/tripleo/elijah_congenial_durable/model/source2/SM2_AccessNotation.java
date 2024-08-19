package tripleo.elijah_congenial_durable.model.source2;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.nextgen.model.SM_Node;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.xlang.LocatableString;

import java.util.List;

public record SM2_AccessNotation(
		LocatableString shortHand,
		LocatableString category,
		List<SM2_TypeName> typeNameList
) implements SM_Node {
	@Override
	public @Nullable String jsonString() {
/*
		JsonAdapter<SM2_AccessNotation> jsonAdapter = getJsonAdapter();
		String                          json        = jsonAdapter.toJson(this);
		return json;
*/
		throw new UnintendedUseException();
	}

/*
	static JsonAdapter<SM2_AccessNotation> jsonAdapter;

	//@Once
	private static JsonAdapter<SM2_AccessNotation> getJsonAdapter() {
		if (jsonAdapter == null) {
			Moshi moshi = new Moshi.Builder().build();
			jsonAdapter = moshi.adapter(SM2_AccessNotation.class);
		}
		return jsonAdapter;
	}
*/
}
