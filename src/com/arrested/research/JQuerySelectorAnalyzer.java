package com.arrested.research;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arrested.research.optimization.SelectorOptimizationEngine;

/**
 * Searches through files to find duplicated jquery selectors.
 * 
 * @author Matt Rick
 */
public class JQuerySelectorAnalyzer {
	
	private static final Pattern SELECTOR_REGEX_SINGLE_QUOTE = Pattern.compile("\\$\\('([^']*)'\\)");
	private static final Pattern SELECTOR_REGEX_DOUBLE_QUOTE = Pattern.compile("\\$\\(\"([^\"]*)\"\\)");
	
	private static final String SUGGESTION_LINE_PREFIX = ">>>> ";
	
	private static final int DEFAULT_REFERENCE_THRESHOLD = 0;
	
	private SelectorOptimizationEngine optimizationEngine = new SelectorOptimizationEngine();
	
	private int referenceThreshold;
	
	/**
	 * Constructs a JQuerySelectorAnalyzer with the default reference threshold.
	 * The analyzer only reports on selectors that appear more times than the
	 * threshold.
	 */
	public JQuerySelectorAnalyzer() {
		referenceThreshold = DEFAULT_REFERENCE_THRESHOLD;
	}
	
	/**
	 * Constructs a JQuerySelectorAnalyzer with the specified reference threshold.
	 * The analyzer only reports on selectors that appear more times than the
	 * threshold.
	 * 
	 * @param referenceThreshold the reference threshold for the new analyzer
	 */
	public JQuerySelectorAnalyzer(int referenceThreshold) {
		this.referenceThreshold = referenceThreshold;
	}
	
	public static void main(String[] args) {
		new JQuerySelectorAnalyzer().parseCommandLineArguments(args);
	}
	
	protected final void parseCommandLineArguments(final String[] args) {

		for (int i = 0; i < args.length; i++) {
			
			String argument = args[i];
			
			if ("-t".equalsIgnoreCase(argument) || "--threshold".equalsIgnoreCase(argument)) {
				
				if ( i + 1 < args.length) {
					referenceThreshold = Integer.parseInt(args[++i]);
				}
				
			} else {
				
				File file = new File(argument);
				
				System.out.println("****************************************");
				System.out.println(file.getName());
				System.out.println("****************************************");
				
				Map<String, List<Integer>> fileSelectors = filterSelectors(getSelectorsInFile(file));
				TreeSet<String> selectors = new TreeSet<>(fileSelectors.keySet());
				
				for (String selector : selectors) {
					
					System.out.println(selector + " " + fileSelectors.get(selector));
					
					for (String suggestion : optimizationEngine.getSuggestions(selector)) {
						System.out.println(SUGGESTION_LINE_PREFIX + suggestion);
					}
					
					System.out.println();
				}
			}
		}
	}
	
	protected final Map<String, List<Integer>> filterSelectors(Map<String, List<Integer>> rawSelectorMap) {
		
		Map<String, List<Integer>> filteredSelectors = new HashMap<>();
		
		for (String selector : rawSelectorMap.keySet()) {
			
			List<Integer> references = rawSelectorMap.get(selector);
			Collections.sort(references);
			
			if (references.size() >= referenceThreshold) {
				filteredSelectors.put(selector, references);
			}
		}
		
		return filteredSelectors;
	}
 	
	/**
	 * Returns a Map consisting of the jquery selectors found mapped to the
	 * lines of the file they were found on.
	 * 
	 * @param file the File to search for jquery selectors
	 * @return a Map of the jquery selectors found to the lines they were found
	 *         on
	 */
	public final static Map<String, List<Integer>> getSelectorsInFile(final File file) {
		
		BufferedReader reader = null;
		
		try {
		
			return parseFile(new BufferedReader(new FileReader(file)));
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;

		} finally {
			
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected final static Map<String, List<Integer>> parseFile(BufferedReader reader) throws IOException {
		
		Map<String, List<Integer>> fileSelectors = new HashMap<>();
		
		for (int lineNumber = 1; reader.ready(); lineNumber++) {
			
			List<String> lineSelectors = getSelectors(reader.readLine());
			
			for (String selector : lineSelectors) {
				
				if (!fileSelectors.containsKey(selector)) {
					fileSelectors.put(selector, new ArrayList<Integer>());
				}
				
				fileSelectors.get(selector).add(lineNumber);
			}
		}
		
		return fileSelectors;
	}
	
	/**
	 * Returns a List of Strings consisting of the jquery selectors found in the
	 * string specified as the method argument.
	 * 
	 * @param line the String to search for jquery selectors
	 * @return a List of the jquery selectors found
	 */
	public final static List<String> getSelectors(final String line) {
		
		List<String> selectors = new ArrayList<>();
		
		Matcher singleQuotes = SELECTOR_REGEX_SINGLE_QUOTE.matcher(line);
		Matcher doubleQuotes = SELECTOR_REGEX_DOUBLE_QUOTE.matcher(line);
		
		while (singleQuotes.find()) {
			selectors.add(singleQuotes.group(1));
		}
		
		while (doubleQuotes.find()) {
			selectors.add(doubleQuotes.group(1));
		}
		
		return selectors;
	}
}
