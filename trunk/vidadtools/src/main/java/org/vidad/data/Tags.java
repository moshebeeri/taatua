/**
 * 
 */
package org.vidad.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.vidad.autocomplete.Completer;

/**
 * @author moshe
 *
 */
public class Tags implements Completer {
	transient Logger log = Logger.getLogger(Tags.class);

	/**
	 * 
	 */
	public Tags() {
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.vidad.autocomplete.Completer#autocompEx(java.lang.String)
	 */
	@Override
	public List<NamedId> autocompEx(String prefix) {
		return null;
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.vidad.autocomplete.Completer#autocomp(java.lang.String)
	 */
	@Override
	public List<String> autocomp(String prefix) {
		return null;
	}

	/**
	 * @param newone
	 * @see org.vidad.autocomplete.Completer#update(org.vidad.data.NamedId)
	 */
	@Override
	public void update(NamedId newone) {
	}

	/**
	 * @return
	 * @see org.vidad.autocomplete.Completer#name()
	 */
	@Override
	public String name() {
		return null;
	}
}
