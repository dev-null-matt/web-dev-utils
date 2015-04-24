package com.arrested.research.optimization.suggestion;

import java.util.Set;
import java.util.TreeSet;

class IdBasedOptimizationSuggestionEngine implements SuggestionEngine {
	
	public static final String REWRITE_SELECTOR_WITH_ID_FIRST = "Beginning selectors with an id has the best performance.  Consider re-writing this selector to begin with an id to reduce the size of the DOM to be searched.";
	public static final String SELECTOR_AND_CHILD_TEMPLATE = "Consider re-writing this selector as $('%s').find('%s') to improve selector performance.";
		
	@Override
	public Set<String> getSuggestions(String selector) {
		
		if (SelectorUtils.isIdSelector(selector)) {
			
			return suggestOptimizationsForIdSelector(selector);
			
		} else {
			
			Set<String> suggestions = new TreeSet<>();
			suggestions.add(REWRITE_SELECTOR_WITH_ID_FIRST);
			
			return suggestions;
		}
	}
	
	protected Set<String> suggestOptimizationsForIdSelector(String baseSelector) {
		
		String[] selectors = baseSelector.split(",");
		Set<String> suggestions = new TreeSet<>();
		
		for (String selector : selectors) {
			selector = selector.trim();
			int firstSpace = selector.indexOf(' ');
			
			if (firstSpace > -1) {
				suggestions.add(String.format(SELECTOR_AND_CHILD_TEMPLATE, selector.substring(0, firstSpace), selector.substring(firstSpace+1)));	
			}
		}
		
		return suggestions;
	}
	
	
}
