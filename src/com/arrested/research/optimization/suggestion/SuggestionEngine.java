package com.arrested.research.optimization.suggestion;

import java.util.Set;

public interface SuggestionEngine {

	public Set<String> getSuggestions(String selector);
}
