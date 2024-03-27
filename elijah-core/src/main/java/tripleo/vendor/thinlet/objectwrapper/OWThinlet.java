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

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represent a specialized Thinlet like, that manage the life cycle of <i>Object Wrapper Component</i>.
 * <p>
 * OWThinlet maintain a repository of related (Object wrapper - thinlet component), and define the API
 * required to retrieve {@link OWObject} from a given thinlet component.
 * <p>
 * <b>Implementation Note:</b><br>
 * <ul>
 * <li> With JDK < 1.3, all widget which are definitively removed from the hierachy must have  it's
 * counter part OWObject released by the {@link OWObject#release()} call, to avoid leak memory.
 * <li> For JDK >= 1.3, the repository use a java.util.WeakHashMap, so explicit {@link OWObject#release()}
 * become optional.
 * </ul>
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 * @todo clean API
 * @todo unit test
 * @todo clean comment
 * @todo manage bean
 */
public interface OWThinlet {
	/**
	 * Return a previously registered object wrapper from the given thinlet component, or build a new
	 * wrapper object and register it.
	 */
	public OWObject wrap(Object component);

	/**
	 * register the given OWObject, as managed by the given thinlet
	 */
	public void register(OWObject widget);

	/**
	 * unregister the given OWObject, as not managed by the given thinlet
	 */
	public void unregister(OWObject widget);


	/**
	 * Adds the specified widget to the root desktop
	 *
	 * @param component a widget to be added
	 */
	public void add(OWWidget component);

	/**
	 * Finds the first widget from the specified component by a name
	 *
	 * @param component the widget is searched inside this component
	 * @param name      parameter value identifies the widget
	 * @return the first suitable component, or null
	 */
	public OWObject findObject(OWWidget component, String name);

	/**
	 * Finds the first widget from the root desktop by a specified name value
	 *
	 * @param name parameter value identifies the widget
	 * @return the first suitable component, or null
	 */
	public OWObject findObject(String name);

	/**
	 * Creates a widget (and its subcomponents, and properties)
	 * from the given xml resource
	 *
	 * @param path is relative to your thinlet instance or the classpath
	 *             (if the path starts with an <i>/</i> character), or a full URL
	 * @return the root component of the parsed resource
	 * @throws java.io.IOException
	 */
	public OWWidget parseAsObject(String path) throws IOException;

	/**
	 * Creates a widget from the given xml resource using the
	 * specified event handler
	 *
	 * @param path    is relative to your application package or the classpath, or an URL
	 * @param handler bussiness methods are implemented in this object
	 * @return the parsed components' root
	 * @throws java.io.IOException
	 */
	public OWWidget parseAsObject(String path, Object handler) throws IOException;

	/**
	 * Creates a widget from the given stream
	 *
	 * @param inputstream e.g. <i>new URL("http://myserver/myservlet").openStream()</i>
	 * @return the root component of the parsed stream
	 * @throws java.io.IOException
	 */
	public OWWidget parseAsObject(InputStream inputstream) throws IOException;

	/**
	 * Creates a widget from the given stream and event handler
	 *
	 * @param inputstream read xml from this stream
	 * @param handler     event handlers are implemented in this object
	 * @return the parsed components' root
	 * @throws java.io.IOException
	 */
	public OWWidget parseAsObject(InputStream inputstream, Object handler) throws IOException;


	//---------------------------------------------------------------------------------------------
	// The Thinlet API
	//---------------------------------------------------------------------------------------------

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void defineMethod(Object component, String methodName, Method method);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setString(Object component, String key, String value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public String getString(Object component, String key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setChoice(Object component, String key, String value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public String getChoice(Object component, String key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setBoolean(Object component, String key, boolean value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public boolean getBoolean(Object component, String key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setInteger(Object component, String key, int value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public int getInteger(Object component, String key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setIcon(Object component, String key, Image icon);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public Image getIcon(Object component, String key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setKeystroke(Object component, String key, String value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setFont(Object component, String key, Font font);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setColor(Object component, String key, Color color);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void putProperty(Object component, Object key, Object value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public @Nullable Object getProperty(Object component, Object key);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void setComponent(Object component, String key, Object value);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public @Nullable Object getParent(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void add(Object parent, Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void add(Object parent, Object component, int index);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void remove(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public void removeAll(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public int getSelectedIndex(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public @Nullable Object getSelectedItem(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public Object[] getSelectedItems(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public @Nullable Object getItem(Object component, int index);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public Object[] getItems(Object component);

	/**
	 * Issued from <code>thinlet.Thinlet</code>. Part of the thinlet like interface, required
	 * to have several ObjectWrapperThinlet implementation.
	 */
	public int getCount(Object component);

}
