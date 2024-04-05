package tripleo.vendor.thinlet;

import org.jetbrains.annotations.Nullable;

/**
 * WORK IN PROGRESS - DON'T USE.
 * Eventually this class will encapsulate most of the widget-related API
 * now implemented in Thinlet.java.
 */
public class Widget {

	private transient Thinlet thinlet;
	private transient Object  widget;

	/**
	 *
	 */
	public Widget(String classname) {
		widget = Thinlet.create(classname);
	}

	/**
	 *
	 */
	public @Nullable String getClassName() {
		return null;
	}

	/**
	 *
	 */
	public void set(String key, String value) {
		thinlet.setString(widget, key, value);
	}

	public boolean requestFocus(Object component) {
		return true;
	}
}
