package org.vidad.autocomplete;

import java.util.List;

import org.vidad.data.NamedId;

public interface Completer {
	List<NamedId>	autocompEx(String prefix);
	List<String> autocomp(String prefix);
	void update(NamedId newone);
	String name();
}
