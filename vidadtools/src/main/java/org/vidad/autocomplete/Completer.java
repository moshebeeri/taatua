package org.vidad.autocomplete;

import java.util.List;

import org.vidad.data.NamedId;

public interface Completer {
	List<NamedId>	autocompEx(String prefix);
	List<String> 	autocomp(String prefix);
	String 			name();
	void 			update(NamedId newone);
}
