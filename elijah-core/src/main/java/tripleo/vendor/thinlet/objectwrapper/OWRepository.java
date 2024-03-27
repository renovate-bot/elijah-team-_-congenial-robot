/* objectWrapperThinlet - http://perso.club-internet.fr/sjobic/thinlet/
 * Copyright (C) 2004 Norbert Barbosa (norbert.barbosa@laposte.net)
 *
 * An extension of the Thinlet GUI toolkit (http://www.thinlet.com)
 * that add wrapper object for thinlet component.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tripleo.vendor.thinlet.objectwrapper;

import org.jetbrains.annotations.NotNull;
import tripleo.vendor.thinlet.ThinletConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

/**
 * OWRepository is a repository implementation of (object wrapper - thinlet component), which simply acts like
 * a Map.
 * <p>
 * <b>Implementation Note:</b><br>
 * <ul>
 * <li> With JDK < 1.3, all widget which are definitively removed from the hierachy must have  it's
 * conter part OWObject released by the {@link OWObject#release()} call, to avoid leak memory.
 * <li> For JDK >= 1.3, the repository use a java.util.WeakHashMap, so explicit {@link OWObject#release()}
 * is optional.
 * </ul>
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public final class OWRepository {

	protected static Object[] @NotNull []     fcomponents = {
			{ThinletConstants.BUTTON, Button.class},
			{ThinletConstants.CELL, Cell.class},
			{ThinletConstants.CHECKBOX, CheckBox.class},
			{ThinletConstants.CHECKBOXMENUITEM, CheckBoxMenuItem.class},
			{ThinletConstants.CHOICE, Choice.class},
			{ThinletConstants.COLUMN, Column.class},
			{ThinletConstants.COMBOBOX, ComboBox.class},
			{ThinletConstants.DESKTOP, Desktop.class},
			{ThinletConstants.DIALOG, Dialog.class},
			{ThinletConstants.HEADER, Header.class},
			{ThinletConstants.ITEM, Item.class},
			{ThinletConstants.LABEL, Label.class},
			{ThinletConstants.LIST, List.class},
			{ThinletConstants.MENU, Menu.class},
			{ThinletConstants.MENUBAR, MenuBar.class},
			{ThinletConstants.MENUITEM, MenuItem.class},
			{ThinletConstants.NODE, Node.class},
			{ThinletConstants.PANEL, Panel.class},
			{ThinletConstants.PASSWORDFIELD, PasswordField.class},
			{ThinletConstants.POPUPMENU, PopupMenu.class},
			{ThinletConstants.PROGRESSBAR, ProgressBar.class},
			{ThinletConstants.ROW, Row.class},
			{ThinletConstants.SEPARATOR, Separator.class},
			{ThinletConstants.SLIDER, Slider.class},
			{ThinletConstants.SPINBOX, SpinBox.class},
			{ThinletConstants.SPLITPANE, SplitPane.class},
			{ThinletConstants.TAB, Tab.class},
			{ThinletConstants.TABBEDPANE, TabbedPane.class},
			{ThinletConstants.TABLE, Table.class},
			{ThinletConstants.TEXTAREA, TextArea.class},
			{ThinletConstants.TEXTFIELD, TextField.class},
			{ThinletConstants.TOGGLEBUTTON, ToggleButton.class},
			{ThinletConstants.TREE, Tree.class},
	};
	protected        Object                   fobjectWrappers;
	protected        java.lang.reflect.Method fobjectWrappersGet;
	protected        java.lang.reflect.Method fobjectWrappersPut;
	protected        java.lang.reflect.Method fobjectWrappersRemove;


	/**
	 * build a new OWThinlet, for managing Objecr Wrapper thinlet widget
	 */
	public OWRepository() {
		super();
		try {
			Class clazz = Class.forName("java.util.WeakHashMap");
			fobjectWrappers       = clazz.newInstance();
			fobjectWrappersGet    = clazz.getMethod("get", Object.class);
			fobjectWrappersPut    = clazz.getMethod("put", Object.class, Object.class);
			fobjectWrappersRemove = clazz.getMethod("remove", Object.class);
		} catch (Exception e) {
			try {
				fobjectWrappers = new Hashtable<>();
				fobjectWrappersGet    = fobjectWrappers.getClass().getMethod("get", Object.class);
				fobjectWrappersPut    = fobjectWrappers.getClass().getMethod("put", Object.class, Object.class);
				fobjectWrappersRemove = fobjectWrappers.getClass().getMethod("remove", Object.class);
			} catch (Exception e2) {
				throw new IllegalStateException("Cannot build repository");
			}
		}
	}

	/**
	 * unregister the given OWObject, as not managed by the given thinlet
	 */
	public void remove(@NotNull OWObject widget) {
		try {
			fobjectWrappersRemove.invoke(fobjectWrappers, widget.unwrap());
		} catch (Exception e) {
			throw new IllegalStateException("Cannot remove component on the repository");
		}
	}

	/**
	 * return a previously registered object wrapper from the given thinlet component, or build a new
	 * wrapper object, ans register it.
	 */
	public @NotNull OWObject wrap(OWThinlet thinlet, Object component, String componentClassname) throws NoSuchMethodException,
																										 IllegalAccessException, InvocationTargetException, InstantiationException {
		OWObject ret = get(component);
		if (ret == null) {
			Class       clazz = OWRepository.findWrapperClassForComponent(componentClassname);
			Constructor c     = clazz.getConstructor(OWThinlet.class, Object.class);
			ret = (OWObject) c.newInstance(new Object[]{thinlet, component});
			add(ret);
		}
		return ret;
	}

	/**
	 * register the given OWObject, as managed by the given thinlet
	 */
	public OWObject get(Object component) {
		try {
			return (OWObject) fobjectWrappersGet.invoke(fobjectWrappers, new Object[]{component});
		} catch (Exception e) {
			throw new IllegalStateException("Cannot get component on the repository");
		}
	}

	/**
	 * helper method to find the wrapper object class corresponding to the given thinlet component
	 */
	protected static Class findWrapperClassForComponent(String componentName) {
		for (int i = 0; i < fcomponents.length; i++)
			if (fcomponents[i][0].equals(componentName))
				return (Class) fcomponents[i][1];
		throw new IllegalArgumentException(componentName + " unknow");
	}

	/**
	 * register the given OWObject, as managed by the given thinlet
	 */
	public void add(@NotNull OWObject widget) {
		try {
			fobjectWrappersPut.invoke(fobjectWrappers, widget.unwrap(), widget);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot add component on the repository");
		}
	}


}
