package com.arrested.research.optimization.suggestion;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IncreaseSpecificityLeftToRightSuggestionEngineTest {

	private SuggestionEngine engine;
	
	@Before
	public void init() {
		engine = new IncreaseSpecificityLeftToRightSuggestionEngine();
	}
	
	@Test
	public void getSuggestionsTest() {
		
		Set<String> suggestions = engine.getSuggestions("div.data .gonzalez");
		
		String expectedLessSpecificMessage = String.format(IncreaseSpecificityLeftToRightSuggestionEngine.REWRITE_SUBSELECTOR_LESS_SPECIFIC, "div.data");
		String expectedMoreSpecificMessage = String.format(IncreaseSpecificityLeftToRightSuggestionEngine.REWRITE_SUBSELECTOR_MORE_SPECIFIC, ".gonzalez");
		
		Assert.assertEquals("Size", 2, suggestions.size());
		Assert.assertTrue(suggestions.contains(expectedLessSpecificMessage));
		Assert.assertTrue(suggestions.contains(expectedMoreSpecificMessage));
	}
}
