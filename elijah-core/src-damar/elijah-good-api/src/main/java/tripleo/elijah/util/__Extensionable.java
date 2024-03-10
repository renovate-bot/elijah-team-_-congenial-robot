package tripleo.elijah.util;

import java.util.HashMap;
import java.util.Map;

public abstract class __Extensionable implements _Extensionable {
	private final Map<Object, Object> exts = new HashMap<>();

	@Override
	public Object getExt(Class<?> aClass) {
		if (exts.containsKey(aClass)) {
			return exts.get(aClass);
		}
		return null;
	}

	@Override
	public void putExt(Class<?> aClass, Object o) {
		exts.put(aClass, o);
	}
}
