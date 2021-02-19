/**
398. All Anagrams
Medium
Find all occurrence of anagrams of a given string s in a given string l. Return the list of starting indices.

Assumptions

sh is not null or empty.
lo is not null.
Examples

l = "abcbac", s = "ab", return [0, 3] since the substring with length 2 starting from index 0/3 are all anagrams of "ab" ("ab", "ba").

Answer Author: Tianquan Guo
Date: 2/18/2020
 */

package Problems;

import java.util.*;

public class AllAnagrams {
	public List<Integer> allAnagrams(String sh, String lo) {
	//  Step 1 get all combo for lo
	    List<Integer> ans = new ArrayList<Integer>();
	    
	    if (lo.length() == 0) {
	      return ans;
	    }
	    
//	    when s is longer than l, there is no way s can be anagram of l
	    if (sh.length() > lo.length()) {
	      return ans;
	    }

//	    This map records for each of the distinct characters in s,
//	    how many characters are needed, like sh = "abbbcdd" 
//	    map = {'a' : 1, 'b' : 3, 'c' : 1, 'd' : 2}
	    Map<Character, Integer> map = countMap(sh);

//	    Only when match = map.size(), we have an anagram.
	    int match = 0;
	    
//	    We have a sliding window of fixed length sh.length(), so when we moving this window
//	    we only need to record the last char of this windows and remove the previous char 
//	    before the window
	    for (int i = 0; i < lo.length(); i++) {
//	    	add the last char of the window
	      char temp = lo.charAt(i);
	      Integer count = map.get(temp);
	      if ( count != null) {
//	    	  the number of the char in the map should be reduced 1 
//	    	  only when count is 0, we find an additional match
	        map.put(temp, count - 1);
//	        note this count is before count -1
	        if (count == 1) {
	          match++;
	        }
	      }

//	      handle the previous char before the window
	      if (i >= sh.length()) {
	        temp = lo.charAt(i - sh.length());
	        count = map.get(temp);
	        if (count != null) {
//	        	count in the map for the char should be increased 1
//	        	note count below is count before count + 1
	          map.put(temp, count + 1);
	          if (count == 0) {
	            match--;
	          }
	        }
	      }

//	      when the size of the map equal to the match number (all count in the map is 0)
//	      record the start index
	      if (match == map.size()) {
	        ans.add(i - sh.length() + 1);
	      }
	    }
	    return ans;
	}

	  private Map<Character, Integer> countMap(String s) {
	    Map<Character, Integer> map = new HashMap<Character, Integer>();
	    for (char ch : s.toCharArray()) {
	      Integer count = map.get(ch);
	      if (count == null) {
	        map.put(ch, 1);
	      } else {
	        map.put(ch, count + 1);
	      }
	    } 
	    return map;
	  }

}
