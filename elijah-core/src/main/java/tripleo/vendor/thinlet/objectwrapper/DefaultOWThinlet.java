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
import tripleo.vendor.thinlet.Thinlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * The default {@link OWThinlet} implementation, directly based on <code>thinlet.Thinlet</code>,
 * that manage the life cycle of <i>Object Wrapper Component</i>.
 * <p>
 * DefaultOWThinlet maintain a repository of related (Object wrapper - thinlet compoennt),
 * and define the API required to retrieve {@link OWObject} from a given thinlet component.
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
public class DefaultOWThinlet extends Thinlet implements OWThinlet {
	static final long serialVersionUID = -8439471783949917743L;

	protected transient final OWRepository frepository = new OWRepository();

	/**
	 * build a new OWThinlet, for managing Objecr Wrapper thinlet widget
	 */
	public DefaultOWThinlet() {
		super();
	}

	/**
	 * overload to call the registered objectwrapper method
	 */
	@Override
	protected boolean invoke(Object component, Object part, String event) {
		String key    = ":owMethod_" + event;
		Method method = (Method) get(component, key.intern());
		if (method != null) {
			method.run(this, wrap(component));
			return true;
		} else
			return super.invoke(component, part, event);
	}

	/**
	 * return a previously registered object wrapper from the given thinlet component, or build a new
	 * wrapper object, ans register it.
	 */
	@Override
	public @NotNull OWObject wrap(Object component) {
		try {
			return frepository.wrap(this, component, getClass(component));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * register the given OWObject, as managed by the given thinlet
	 */
	@Override
	public void register(@NotNull OWObject widget) {
		frepository.add(widget);
	}

	/**
	 * unregister the given OWObject, as not managed by the given thinlet
	 */
	@Override
	public void unregister(@NotNull OWObject widget) {
		frepository.remove(widget);
	}


	/**
	 * Adds the specified component to the root desktop
	 *
	 * @param component a widget to be added
	 */
	@Override
	public void add(@NotNull OWWidget component) {
		add(component.unwrap());
	}

	/**
	 * Finds the first component from the specified component by a name
	 *
	 * @param component the widget is searched inside this component
	 * @param name      parameter value identifies the widget
	 * @return the first suitable component, or null
	 */
	@Override
	public OWObject findObject(@NotNull OWWidget component, @NotNull String name) {
		return wrap(find(component.unwrap(), name));
	}

	/**
	 * Finds the first component from the root desktop by a specified name value
	 *
	 * @param name parameter value identifies the widget
	 * @return the first suitable component, or null
	 */
	@Override
	public OWObject findObject(@NotNull String name) {
		return wrap(find(name));
	}

	/**
	 * Creates a component (and its subcomponents, and properties)
	 * from the given xml resource
	 *
	 * @param path is relative to your thinlet instance or the classpath
	 *             (if the path starts with an <i>/</i> character), or a full URL
	 * @return the root component of the parsed resource
	 * @throws java.io.IOException
	 */
	@Override
	public OWWidget parseAsObject(@NotNull String path) throws IOException {
		return (OWWidget) wrap(parse(path));
	}

	/**
	 * Creates a component from the given xml resource using the
	 * specified event handler
	 *
	 * @param path    is relative to your application package or the classpath, or an URL
	 * @param handler bussiness methods are implemented in this object
	 * @return the parsed components' root
	 * @throws java.io.IOException
	 */
	@Override
	public OWWidget parseAsObject(@NotNull String path, @NotNull Object handler) throws IOException {
		return (OWWidget) wrap(parse(path, handler));
	}

	/**
	 * Creates a component from the given stream
	 *
	 * @param inputstream e.g. <i>new URL("http://myserver/myservlet").openStream()</i>
	 * @return the root component of the parsed stream
	 * @throws java.io.IOException
	 */
	@Override
	public OWWidget parseAsObject(@NotNull InputStream inputstream) throws IOException {
		return (OWWidget) wrap(parse(inputstream));
	}

	/**
	 * Creates a component from the given stream and event handler
	 *
	 * @param inputstream read xml from this stream
	 * @param handler     event handlers are implemented in this object
	 * @return the parsed components' root
	 * @throws java.io.IOException
	 */
	@Override
	public OWWidget parseAsObject(@NotNull InputStream inputstream, @NotNull Object handler) throws IOException {
		return (OWWidget) wrap(parse(inputstream, handler));
	}


	/**
	 * register the given method as named method on the given component
	 */
	@Override
	public void defineMethod(Object component, String methodName, Method method) {
		set(component, (":owMethod_" + methodName).intern(), method);
	}

	@Override
	public void setComponent(Object component, String key, Object widget) {
		set(component, key, widget);
	}


}
