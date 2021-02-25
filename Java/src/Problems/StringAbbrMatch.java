/**
292. String Abbreviation Matching
Medium
Word ¡°book¡± can be abbreviated to 4, b3, b2k, etc. Given a string and an abbreviation, return if the string matches the abbreviation.

Assumptions:

The original string only contains alphabetic characters.
Both input and pattern are not null.
Pattern would not contain invalid information like "a0a","0".
Examples:

pattern ¡°s11d¡± matches input ¡°sophisticated¡± since ¡°11¡± matches eleven chars ¡°ophisticate¡±.

Answer Author: Tianquan Guo
Date: 2/24/2020
 */


package Problems;

public class StringAbbrMatch {
	public boolean match(String input, String pattern) {
		return helper(input, pattern, 0, 0);
	}
	
	private boolean helper(String input, String pattern, int inputIndex, int patternIndex) {
//		note this condition should be the first one
		if (inputIndex == input.length() && patternIndex == pattern.length()) {
			return true;
		}
		
//		note if first condition doesn't match, if any index is larger or equal to length
//		it should return false, or there will be out of boundary errors
		if (inputIndex >= input.length() || patternIndex >= pattern.length()) {
			return false;
		}
		
		if (pattern.charAt(patternIndex) < '0' || pattern.charAt(patternIndex) > '9') {
			if (input.charAt(inputIndex) == pattern.charAt(patternIndex)) {
				return helper(input, pattern, inputIndex + 1, patternIndex + 1);
			} else {
				return false;
			}
		} else {
			int count = 0;
			while (patternIndex < pattern.length() && (pattern.charAt(patternIndex) >= '0' && pattern.charAt(patternIndex) <= '9')) {
				count = count * 10 + (pattern.charAt(patternIndex++) - '0');
			}
			return helper(input, pattern, inputIndex + count, patternIndex);
		}
	}
}
