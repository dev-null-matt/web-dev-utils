package com.arrested.research.optimization.suggestion;

import java.util.Set;

public interface SuggestionEngine {

	/**
	 * Returns a Set of Strings consisting of suggestions for improving the performance of the supplied selector.
	 * 
	 * @param selector The selector to get suggestions for
	 * @return A Set of Strings suggesting improvements
	 */
	public Set<String> getSuggestions(String selector);
}
