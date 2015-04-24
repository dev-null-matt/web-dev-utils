package com.arrested.research.optimization.suggestion;

import java.util.regex.Pattern;

class SelectorUtils {

	private static final Pattern ID_SELECTOR_FIRST = Pattern.compile("^#[A-Za-z][A-Za-z_\\-]*");
	
	public static boolean isIdSelector(String token) {
		return ID_SELECTOR_FIRST.matcher(token).find();
	}
}
