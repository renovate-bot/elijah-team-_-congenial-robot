package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FormalArgList {
	List<FormalArgListItem> falis();

	@NotNull List<FormalArgListItem> items();

	FormalArgListItem next();

	@Override
	String toString();

	void serializeTo(SmallWriter sw);
}
