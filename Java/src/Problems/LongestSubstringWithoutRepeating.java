/**
253. Longest Substring Without Repeating Characters
Medium
Given a string, find the longest substring without any repeating characters and return the length of it. The input string is guaranteed to be not null.

For example, the longest substring without repeating letters for "bcdfbd" is "bcdf", we should return 4 in this case.

Answer Author: Tianquan Guo
Date: 2/17/2020
 */



package Problems;

import java.util.*;

public class LongestSubstringWithoutRepeating {
	public int longest(String input) {
		if ( input == null || input.length() == 0) {
		      return 0;
		    }
			
//		maintain a hashset that doesn't have any duplicated char
		    char[] array = input.toCharArray();
		    int left = 0;
		    int right = 0;
		    HashSet<Character> set = new HashSet<>();
		    int maxNum = 0;
		    
//			Start iteration, if contains, left add 1, and remove the char
//		    if not contains, right add 1, and add the char to set
		    while (right < array.length) {
		      if (set.contains(array[right])) {
		        set.remove(array[left]);
		        left++;        
		      } else {
		        set.add(array[right]);
		        right++;
		      }
		      
//				note need to calculate max number every iteration
		      if ((right - left) > maxNum) {
		        maxNum = right - left;
		      }
		    }

		    return maxNum;
	}
}
