package com.arrested.research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JQuerySelectorAnalyzerTest {

	// Tests for filterSelectors()
	
	@Test
	public void filterSelectorsTest_overThreshold() {
		Assert.assertEquals(1, filterSelectorsTestHelper(3).size());
	}
	
	@Test
	public void filterSelectorsTest_atThreshold() {
		Assert.assertEquals(1, filterSelectorsTestHelper(2).size());
	}
	
	@Test
	public void filterSelectorsTest_underThreshold() {
		Assert.assertEquals(0, filterSelectorsTestHelper(1).size());
	}
	
	private Map<String, List<Integer>> filterSelectorsTestHelper(int numberOfReferences) {
		
		JQuerySelectorAnalyzer analyzer = new JQuerySelectorAnalyzer(2);
		
		Map<String, List<Integer>> rawSelectors = new HashMap<>();
		List<Integer> lineNumbers = new ArrayList<>();
		
		for (int i = 1; i <= numberOfReferences; i++) {
			lineNumbers.add(i);
		}
		
		rawSelectors.put(".test", lineNumbers);
		
		return analyzer.filterSelectors(rawSelectors);
	}
	
	@Test
	public void filterSelectorsTest_manyOnOneLine() {
		JQuerySelectorAnalyzer analyzer = new JQuerySelectorAnalyzer(2);
		
		Map<String, List<Integer>> rawSelectors = new HashMap<>();
		List<Integer> lineNumbers = new ArrayList<>();
		
		lineNumbers.add(1);
		lineNumbers.add(1);
		
		rawSelectors.put(".test", lineNumbers);
		
		Assert.assertEquals(1, analyzer.filterSelectors(rawSelectors).size());
	}
	
	// Tests for getSelectors()
	
	@Test
	public void getSelectorsTest_smokeTest() {
		
		List<String> selectors = JQuerySelectorAnalyzer.getSelectors("        self.refreshContainer = $('#fetchRefreshContent');");
		
		Assert.assertEquals(1, selectors.size());
		Assert.assertEquals("#fetchRefreshContent", selectors.get(0));
	}
	
	@Test
	public void getSelectorsTest_TwoSelectors() {
		
		List<String> selectors = JQuerySelectorAnalyzer.getSelectors("self.refreshContainer = $('#fetchRefreshContent'); foo = $('.somethingElse')");
		
		Assert.assertEquals(2, selectors.size());
		Assert.assertEquals("#fetchRefreshContent", selectors.get(0));
		Assert.assertEquals(".somethingElse", selectors.get(1));
	}
	
	@Test
	public void getSelectorsTest_TwoSelectorsDoubleQuote() {
		
		List<String> selectors = JQuerySelectorAnalyzer.getSelectors("checkMarketPlace = ($(\"#checkMarketPlace\").length > 0) ? $(\"#checkMarketPlace\").val() : '',");
		
		Assert.assertEquals(2, selectors.size());
		Assert.assertEquals("#checkMarketPlace", selectors.get(0));
		Assert.assertEquals("#checkMarketPlace", selectors.get(1));
	}
	
	@Test
	public void getSelectorsTest_MixedQuotes() {
		
		List<String> selectors = JQuerySelectorAnalyzer.getSelectors("self.refreshContainer = $('#fetchRefreshContent'); foo = $(\".somethingElse\")");
		
		Assert.assertEquals(2, selectors.size());
		Assert.assertEquals("#fetchRefreshContent", selectors.get(0));
		Assert.assertEquals(".somethingElse", selectors.get(1));
	}
}
