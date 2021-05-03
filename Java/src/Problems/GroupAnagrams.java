/**
49. Group Anagrams
Medium

Given an array of strings strs, group the anagrams together. You can return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 

Example 1:

Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
Example 2:

Input: strs = [""]
Output: [[""]]
Example 3:

Input: strs = ["a"]
Output: [["a"]]
 

Constraints:

1 <= strs.length <= 104
0 <= strs[i].length <= 100
strs[i] consists of lower-case English letters.

Answer Author: Tianquan Guo
Date: 5/2/2021
 */


package Problems;

import java.util.*;

public class GroupAnagrams {
	public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) {
            return new ArrayList<>();
        }
        HashMap<String, List<String>> anagrams = new HashMap<>();
        
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            if (anagrams.containsKey(key)) {
                anagrams.get(key).add(str);
            } else {
                anagrams.put(key, new ArrayList<>());
                anagrams.get(key).add(str);
            }
        }
        
        List<List<String>> ans = new ArrayList<>();
        for (HashMap.Entry<String, List<String>> cur : anagrams.entrySet()) {
            ans.add(cur.getValue());
        }
        
        return ans;
    }
}
