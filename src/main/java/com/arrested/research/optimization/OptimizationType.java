package com.arrested.research.optimization;

/**
 * Each enumerated value corresponds to a suggestion for improving the
 * performance of jquery selectors from jquery.com.
 * 
 * @author Matt Rick
 */
public enum OptimizationType {

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
	ID_FIRST,
	
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
	INCREASE_SPECIFICITY_LEFT_TO_RIGHT,
	
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
	LOW_SELECTOR_DOM_COMPLEXITY,
	
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
	AVOID_UNIVERSAL_SELECTOR
}
