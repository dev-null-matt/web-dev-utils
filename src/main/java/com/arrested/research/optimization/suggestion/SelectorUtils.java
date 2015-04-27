package com.arrested.research.optimization.suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

class SelectorUtils {

	private static final Pattern ID_SELECTOR_FIRST = Pattern.compile("^#[A-Za-z][A-Za-z_\\-]*");
	private static final Pattern TAG_SELECTOR_FIRST = Pattern.compile("^[A-Za-z][A-Za-z_\\-]*");
	
	/**
	 * Determines if the specified selector is anchored on the left by an id.
	 */
	public static boolean isIdSelector(String token) {
		return ID_SELECTOR_FIRST.matcher(token).find();
	}
	
	/**
	 * Determines if the specified selector is anchored on the left by a tag.
	 */
	public static boolean isTagSelector(String token) {
		return TAG_SELECTOR_FIRST.matcher(token).find();
	}
	
	/**
	 * Tokenizes a multiple selector.  
	 * <p>
	 * If the selector is not a multiple selector (consisting of only one, possibly complex, selector) the outer list will contain one element, consisting of the tokens in that selector.
	 * <p>
	 * If the selector is a multiple selector (consisting of a comma separated list of selectors) then the outer list will consist of lists of the tokens from each selector.
	 */
	public static List<List<String>> tokenizeSelector(String multipleSelector) {
		
		List<List<String>> tokens = new ArrayList<List<String>>();
		
		for (String selector : multipleSelector.split(",")) {
			
			selector = selector.trim();
			
			List<String> selectorTokens = new ArrayList<String>();
			selectorTokens.addAll(tokenizeSingleSelector(selector));
			
			tokens.add(selectorTokens);
		}
		
		return tokens;
	}
	
	/**
	 * Tokenizes a simple selector (a selector with only one sub-selector, or containing no commas).
	 */
	public static List<String> tokenizeSingleSelector(String singleSelector) {

		if (StringUtils.contains(singleSelector, ",")) {
			throw new IllegalArgumentException("tokenizeSingleSelector() cannot tokenize multiple selectors.  Use tokenizeSelector() instead.  Selector = \"" + singleSelector + "\"");
		}
		
		List<String> tokens = new ArrayList<String>();
		
		for (String token : singleSelector.split(" ")) {
			if (StringUtils.isNotEmpty(token)) {
				tokens.add(token.trim());
			}
		}
		
		return tokens;
	}
}
