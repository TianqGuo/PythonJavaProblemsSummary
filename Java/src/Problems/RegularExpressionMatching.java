/**
10. Regular Expression Matching
Hard
Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where: 

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).
Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input: s = "aab", p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
Example 5:

Input: s = "mississippi", p = "mis*is*p*."
Output: false
Constraints:

0 <= s.length <= 20
0 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.

Answer Author: Tianquan Guo
Date: 5/16/2021
 */

package Problems;

public class RegularExpressionMatching {
	public boolean isMatch(String s, String p) {
        int lenS = s.length();
        int lenP = p.length();
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        // for (int i = 1; i <= lenP; i++) {
        //     dp[0][i] = i;
        // }
        
        for (int i = 1; i < lenP; i++) {
            // note here is to solve this situation:
            // ab, a*ab
            // p.charAt(1) = '*', because dp[0][0] is true, so dp[0][2] = true
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }
        
        // note here there are 3 situations in each iteration
        for (int i = 0; i < lenS; i++) {
            for (int j = 0; j < lenP; j++) {
                // when two chars are the same, check previous dp records
                if (s.charAt(i) == p.charAt(j)){
                    dp[i + 1][j + 1] = dp[i][j];                     
                } 
                
                // when current p char is '.', means p.charAt(i) is the same as s.charAt(j)
                if (p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                
                //when current p chr is '*', need to check several situation
                if (p.charAt(j) == '*') {
                    // when p.charAt is not the same as s and not '.', need to check dp[i + 1][j - 1]
                    // consider it x* is ''
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else {
                        // here there are 3 situation, 
                        // 1) when .* is one char
                        // 2) when .* is multiple chars
                        // 3) when .* is ''
                        dp[i + 1][j + 1] = dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j -1];
                    }
                }
            }
        }
        
        // System.out.println(Arrays.deepToString(dp));
        return dp[lenS][lenP];
    }
}
