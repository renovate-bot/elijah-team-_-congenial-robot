package tripleo.elijah.xlang;

import antlr.Token;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.util.UnintendedUseException;

import java.util.Objects;

public interface LocatableString {
	static LocatableString of(Token aToken) {
		return new LocatableString() {
			@Override public String asLocatableString() {
				return aToken.getText();
			}
		};
	}

	static LocatableString of(String aAbsolutePath) {
		return new LocatableString() {
			@Override
			public String asLocatableString() {
				return aAbsolutePath;
			}
		};
	}

	default Locatable getLocatable() { throw new UnintendedUseException("implement or delete");	}

	String asLocatableString();

	default boolean sameString(String aName) {
		return Objects.equals(aName, asLocatableString());
	}
}
