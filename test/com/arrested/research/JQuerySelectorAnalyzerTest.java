package com.arrested.research;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class JQuerySelectorAnalyzerTest {

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
