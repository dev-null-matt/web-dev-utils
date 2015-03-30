package com.arrested.research.optimization;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.arrested.research.optimization.suggestion.SuggestionEngine;
import com.arrested.research.optimization.suggestion.SuggestionEngineFactory;

public class SelectorOptimizationEngine {

	Map<OptimizationType, SuggestionEngine> suggestionEngines;
	
	public SelectorOptimizationEngine() {
		
		SuggestionEngineFactory factory = new SuggestionEngineFactory();
		suggestionEngines = new HashMap<>();
		
		for (OptimizationType type : OptimizationType.values()) {
			suggestionEngines.put(type, factory.constructSuggestionEngine(type));
		}
	}
	
	/**
	 * Gets a set of suggestions for improving the performance of a jquery selector.
	 * 
	 * @param selector the selector to get suggestions for
	 * @return a Set<String> consisting of suggestions to improve the performance of the selector.
	 */
	public Set<String> getSuggestions(String selector) {

		TreeSet<String> suggestions = new TreeSet<>();

		suggestions.addAll(suggestIdBasedOptimization(selector));
		suggestions.addAll(suggestIncreasingSpecificity(selector));
		suggestions.addAll(suggestLowerComplexity(selector));
		suggestions.addAll(suggestAvoidingUniversalSelector(selector));

		return suggestions;
	}

	// Documentation below this line is from jquery.org

	/**
	 * ID-Based Selectors
	 * <p>
	 * 
	 * Beginning your selector with an ID is always best.
	 * <p>
	 * 
	 * Fast: <code>$( "#container div.robotarm" );</code>
	 * <p>
	 * 
	 * Super-fast: <code>$( "#container" ).find( "div.robotarm" );</code>
	 * <p>
	 * 
	 * The <code>.find()</code> approach is faster because the first selection
	 * is handled without going through the Sizzle selector engine – ID-only
	 * selections are handled using document.getElementById(), which is
	 * extremely fast because it is native to the browser.
	 */
	private Set<String> suggestIdBasedOptimization(String selector) {
		
		OptimizationType type = OptimizationType.ID_FIRST;
		
		if (suggestionEngines.containsKey(type)) {
			return suggestionEngines.get(type).getSuggestions(selector);
		} else {
			return new TreeSet<>();	
		}
	}

	/**
	 * Specificity
	 * <p>
	 * 
	 * Be specific on the right-hand side of your selector, and less specific on
	 * the left.
	 * <p>
	 * 
	 * Unoptimized: <code>$( "div.data .gonzalez" );</code>
	 * <p>
	 * 
	 * Optimized: <code>$( ".data td.gonzalez" ); </code>
	 * <p>
	 * 
	 * Use <code>tag.class</code> if possible on your right-most selector, and
	 * just tag or just .class on the left.
	 */
	private Set<String> suggestIncreasingSpecificity(String selector) {

		OptimizationType type = OptimizationType.INCREASE_SPECIFICITY_LEFT_TO_RIGHT;
		
		if (suggestionEngines.containsKey(type)) {
			return suggestionEngines.get(type).getSuggestions(selector);
		} else {
			return new TreeSet<>();	
		}
	}

	/**
	 * Avoid Excessive Specificity
	 * <p>
	 * 
	 * <code>$( ".data table.attendees td.gonzalez" );</code>
	 * <p>
	 * 
	 * Better: Drop the middle if possible.
	 * <code>$( ".data td.gonzalez" );</code>
	 * <p>
	 * 
	 * A "flatter" DOM also helps improve selector performance, as the selector
	 * engine has fewer layers to traverse when looking for an element.
	 */
	private Set<String> suggestLowerComplexity(String selector) {
		
		OptimizationType type = OptimizationType.LOW_SELECTOR_DOM_COMPLEXITY;
		
		if (suggestionEngines.containsKey(type)) {
			return suggestionEngines.get(type).getSuggestions(selector);
		} else {
			return new TreeSet<>();	
		}
	}

	/**
	 * Avoid the Universal Selector
	 * <p>
	 * 
	 * Selections that specify or imply that a match could be found anywhere can
	 * be very slow.
	 * <p>
	 * 
	 * <code>
	 * $( ".buttons > *" ); // Extremely expensive.<br>
	 * $( ".buttons" ).children(); // Much better.
	 * <p>
	 * $( ".category :radio" ); // Implied universal selection.<br>
	 * $( ".category *:radio" ); // Same thing, explicit now.<br>
	 * $( ".category input:radio" ); // Much better.
	 * </code>
	 */
	private Set<String> suggestAvoidingUniversalSelector(String selector) {
		
		OptimizationType type = OptimizationType.AVOID_UNIVERSAL_SELECTOR;
		
		if (suggestionEngines.containsKey(type)) {
			return suggestionEngines.get(type).getSuggestions(selector);
		} else {
			return new TreeSet<>();	
		}
	}
}
