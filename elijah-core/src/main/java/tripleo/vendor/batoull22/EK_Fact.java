package tripleo.vendor.batoull22;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public record EK_Fact(char ch) {
	//@Override
	//public boolean equals(final Object aO) {
	//	if (this == aO) return true;
	//	if (aO == null || getClass() != aO.getClass()) return false;
	//	final EK_Fact ekFact = (EK_Fact) aO;
	//	return ch == ekFact.ch;
	//}
	//
	//@Override
	//public int hashCode() {
	//	return Objects.hash(ch);
	//}

	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return MessageFormat.format("<FACT {0}>", ch);
	}
}
