package com.arrested.research.optimization.suggestion;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class IncreaseSpecificityLeftToRightSuggestionEngine implements SuggestionEngine {
	
	static final String REWRITE_SUBSELECTOR_LESS_SPECIFIC = "Consider rewriting %s to include only a class or tag.";
	static final String REWRITE_SUBSELECTOR_MORE_SPECIFIC = "Consider rewriting %s to include both a class and tag.";
	
	@Override
	public Set<String> getSuggestions(String selector) {
		
		List<List<String>> tokens = SelectorUtils.tokenizeSelector(selector);
		Set<String> suggestions = new TreeSet<>();
		
		for (List<String> selectorTokens : tokens) {
			
			if (selectorTokens.size() <= 1) {
				continue;
			}
			
			String lastToken = "";
			boolean isComplex = false;
			
			for (String token : selectorTokens) {
				
				if (isComplexSelector(token)) {
					isComplex = true; 
				} else if (isComplex) {
					suggestions.add(String.format(REWRITE_SUBSELECTOR_LESS_SPECIFIC, lastToken));
				}
				
				lastToken = token;
			}
			
			if (!isComplexSelector(lastToken)) {
				suggestions.add(String.format(REWRITE_SUBSELECTOR_MORE_SPECIFIC, lastToken));
			}
		}
		
		return suggestions;
	}
	
	public boolean isComplexSelector(String selector) {
		return SelectorUtils.isTagSelector(selector) && (selector.indexOf(".") > -1 || selector.indexOf("#") > -1);
	}
}
