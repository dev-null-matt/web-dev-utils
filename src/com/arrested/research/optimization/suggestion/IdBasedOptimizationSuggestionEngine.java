package com.arrested.research.optimization.suggestion;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

class IdBasedOptimizationSuggestionEngine implements SuggestionEngine {
	
	public static final String REWRITE_SELECTOR_WITH_ID_FIRST = "Beginning selectors with an id has the best performance.  Consider re-writing this selector to begin with an id to reduce the size of the DOM to be searched.";
	
	private static final Pattern ID_SELECTOR_FIRST = Pattern.compile("^#[A-Za-z][A-Za-z_\\-]*");
	
	@Override
	public Set<String> getSuggestions(String selector) {
		
		if (isIdSelector(selector)) {
			
			return suggestOptimizationsForIdSelector(selector);
			
		} else {
			
			Set<String> suggestions = new TreeSet<>();
			suggestions.add(REWRITE_SELECTOR_WITH_ID_FIRST);
			
			return suggestions;
		}
	}
	
	protected Set<String> suggestOptimizationsForIdSelector(String selector) {
		
		return new TreeSet<>();
	}
	
	protected boolean isIdSelector(String token) {
		return ID_SELECTOR_FIRST.matcher(token).find();
	}
}
