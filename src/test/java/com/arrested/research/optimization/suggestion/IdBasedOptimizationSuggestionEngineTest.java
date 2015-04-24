package com.arrested.research.optimization.suggestion;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdBasedOptimizationSuggestionEngineTest {

	private IdBasedOptimizationSuggestionEngine engine;
	
	@Before
	public void init() {
		engine = new IdBasedOptimizationSuggestionEngine();
	}
	
	@Test
	public void getSuggestionsTest_noLeadingId() {
		
		Set<String> suggestions = engine.getSuggestions(".test");
		
		Assert.assertEquals(1, suggestions.size());
		Assert.assertTrue(suggestions.contains(IdBasedOptimizationSuggestionEngine.REWRITE_SELECTOR_WITH_ID_FIRST));
	}
	
	@Test
	public void suggestOptimizationsForIdSelectorTest() {
		
		Set<String> suggestions = engine.getSuggestions("#test .child");
		
		Assert.assertEquals(1, suggestions.size());
		
		String suggestion = suggestions.iterator().next();
		
		Assert.assertTrue(suggestion.indexOf("$('#test').find('.child')") > -1);
	}
	
	@Test
	public void suggestOptimizationsForIdSelectorTest_commaSeparated() {
		Assert.assertEquals(0, engine.getSuggestions("#first, #second").size());
	}
	
	@Test
	public void suggestOptimizationsForIdSelectorTest_commaSeparated_shouldSuggest() {
		
		Set<String> suggestions = engine.getSuggestions("#first, #second .child");
		
		Assert.assertEquals(1, suggestions.size());

		String suggestion = suggestions.iterator().next();
		
		Assert.assertTrue(suggestion.indexOf("$('#second').find('.child')") > -1);
	}
	
	@Test
	public void isIdSelectorTest_positive() {
		Assert.assertTrue(engine.isIdSelector("#foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_negative() {
		Assert.assertFalse(engine.isIdSelector("foo-BAR_bat"));
	}
	
	@Test
	public void isIdSelectorTest_idSelectorNotFirst() {
		Assert.assertFalse(engine.isIdSelector(".baz#foo"));
	}
}
