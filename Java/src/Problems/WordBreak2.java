/**
140. Word Break II
Hard

Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

 

Example 1:

Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]
Example 2:

Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []
 

Constraints:

1 <= s.length <= 20
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 10
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.

Answer Author: Tianquan Guo
Date: 5/1/2021
 */

package Problems;

import java.util.*;

public class WordBreak2 {
	public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> ans = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        
        for (String word : wordDict) {
            set.add(word);
        }
        
        helper(s, set, ans, cur, 0);
        
        return ans;
    }
    
    private void helper(String s, HashSet<String> set, List<String> ans, List<String> cur, int index) {
        if (index >= s.length()) {
            ans.add(String.join(" ", cur));
            return;
        }
        
        for (int i = index; i < s.length(); i++) {
            String curWord = s.substring(index, i + 1);
            // System.out.println(curWord);
            if (set.contains(curWord)) {
                // System.out.println("CONTAINS:  " +curWord);
                cur.add(curWord);
                helper(s, set, ans, cur, i + 1);
                cur.remove(cur.size() - 1);
            }            
        }
        
        return;   
    }
}
