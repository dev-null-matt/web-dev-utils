package com.arrested.research.optimization.suggestion;

import com.arrested.research.optimization.OptimizationType;

public class SuggestionEngineFactory {

	public SuggestionEngine constructSuggestionEngine(OptimizationType type) {
		
		switch (type) {
		case AVOID_UNIVERSAL_SELECTOR:
			return new AvoidUniversalSelectorSuggestionEngine();
		case ID_FIRST:
			return new IdBasedOptimizationSuggestionEngine();
		case INCREASE_SPECIFICITY_LEFT_TO_RIGHT:
			return new IncreaseSpecificityLeftToRightSuggestionEngine();
		case LOW_SELECTOR_DOM_COMPLEXITY:
			return new LowSelectorDomComplexitySuggestionEngine();
		default:
			return null;
		}
	}
}
