/*
    Copyright 2014, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.restexpress.plugin.statechange;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The StateContext class maintains a Map of name/value pairs much like the
 * Log4j <em>mapped diagnostic context (MDC)</em>. A <em>StateContext</em>,
 * is an instrument for passing augmentation data from different sources to
 * lower levels in the framework for each message received.
 * <p/>
 * <b><em>The StateContext is managed on a per thread basis</em></b>. A child
 * thread automatically inherits a <em>copy</em> of the state context of its parent.
 * <p/>
 * The class requires JDK 1.6 or above.
 * <p/>
 * The StateContext is cleaned up after each request by removing the values set by that request,
 * since all threads are pooled and re-used. This is accomplished via a call to
 * <code>StateContext.clear()</code> in a finally processor within the StateChangePlugin.
 * 
 * @author toddf
 * @since Feb 17, 2014
 */
public class StateContext
{
	final static StateContext SC = new StateContext();

	private ThreadLocal<Map<String, Object>> tlm;

	private StateContext()
	{
		tlm = new ThreadLocal<Map<String, Object>>();
	}

	/**
	 * Put a context value (the <code>o</code> parameter) as identified with the
	 * <code>key</code> parameter into the current thread's context map.
	 * <p/>
	 * <p>
	 * If the current thread does not have a context map it is created as a side
	 * effect.
	 */
	public static void put(String key, Object o)
	{
		SC._put(key, o);
	}

	/**
	 * Get the context identified by the <code>key</code> parameter.
	 * <p/>
	 * <p>
	 * This method has no side effects.
	 */
	public static Object get(String key)
	{
		return SC._get(key);
	}

	/**
	 * Remove the the context identified by the <code>key</code> parameter.
	 */
	public static void remove(String key)
	{
		SC._remove(key);
	}

	/**
	 * Get the current thread's RequestContext as a Map. This method is intended
	 * to only be used internally.
	 */
	public static Map<String, Object> getContext()
	{
		return SC._getContext();
	}

	@SuppressWarnings("unchecked")
    public static Set<Entry<String, Object>> entrySet()
	{
		Map<String, Object> context = SC._getContext();
		return (context == null ? Collections.EMPTY_SET : context.entrySet());
	}

	public static Iterator<Entry<String, Object>> iterator()
	{
		return entrySet().iterator();
	}

	/**
	 * Remove all values from the thread's RequestContext.
	 */
	public static void clear()
	{
		SC._clear();
	}


	// SECTION: MUTATORS - INTERNAL, PRIVATE

	private void _put(String key, Object o)
	{
		Map<String, Object> m = tlm.get();

		if (m == null)
		{
			m = new Hashtable<String, Object>();
			tlm.set(m);
		}

		m.put(key, o);
	}

	private Object _get(String key)
	{
		Map<String, Object> m = tlm.get();

		return (m == null ? null : m.get(key));
	}

	private void _remove(String key)
	{
		Map<String, Object> m = tlm.get();

		if (m != null)
		{
			m.remove(key);

			if (m.isEmpty()) _clear();
		}
	}

	private Map<String, Object> _getContext()
	{
		return tlm.get();
	}

	private void _clear()
	{
		Map<String, Object> m = tlm.get();

		if (m != null)
		{
			m.clear();
			tlm.remove();
		}
	}
}
