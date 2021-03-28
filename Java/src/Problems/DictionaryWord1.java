/**
99. Dictionary Word I
Medium
Given a word and a dictionary, determine if it can be composed by concatenating words from the given dictionary.

Assumptions

The given word is not null and is not empty
The given dictionary is not null and is not empty and all the words in the dictionary are not null or empty
Examples

Dictionary: {¡°bob¡±, ¡°cat¡±, ¡°rob¡±}

Word: ¡°robob¡± return false

Word: ¡°robcatbob¡± return true since it can be composed by "rob", "cat", "bob"
 */

package Problems;

import java.util.*;

public class DictionaryWord1 {
	public boolean DictionaryWord (String input, String[] dict) {
		if (dict == null || dict.length == 0) {
			return true;
		}
		
		HashSet<String> set = new HashSet<>();
		for (String cur : dict) {
			set.add(cur);
		}
		
//		note here we need length() + 1 dp array, because the first dp[0] is true
		boolean[] dp = new boolean[input.length() + 1];
		dp[0] = true;
		
		for (int i = 1; i < dp.length; i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && set.contains(input.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		
		return dp[dp.length - 1];
	}
}
