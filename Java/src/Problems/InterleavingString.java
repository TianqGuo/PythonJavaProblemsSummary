/**
97. Interleaving String
Medium

2719

135

Add to List

Share
Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.

An interleaving of two strings s and t is a configuration where they are divided into non-empty substrings such that:

s = s1 + s2 + ... + sn
t = t1 + t2 + ... + tm
|n - m| <= 1
The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
Note: a + b is the concatenation of strings a and b.

 

Example 1:


Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Example 2:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
Example 3:

Input: s1 = "", s2 = "", s3 = ""
Output: true
 

Constraints:

0 <= s1.length, s2.length <= 100
0 <= s3.length <= 200
s1, s2, and s3 consist of lowercase English letters.
 

Follow up: Could you solve it using only O(s2.length) additional memory space?

Answer Author: Tianquan Guo
Date: 6/26/2021
 */

package Problems;

public class InterleavingString {
	public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                
                if (i == 0) {
                    dp[i][j] = dp[i][j - 1] && (s2.charAt(j - 1) == s3.charAt(i + j - 1));
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] && (s1.charAt(i - 1) == s3.charAt(i + j - 1));
                } else {
                    dp[i][j] = (dp[i][j - 1] && (s2.charAt(j - 1) == s3.charAt(i + j - 1))) ||
                        (dp[i - 1][j] && (s1.charAt(i - 1) == s3.charAt(i + j - 1)));
                }
                
            }
        }
        
        return dp[s1.length()][s2.length()];
    }
        
        
        
        
        
//         The following method will be ETL
//         if (s1.length() + s2.length() != s3.length()) {
//             return false;
//         }
        
//         return helper(s1, s2, s3, 0, 0, 0);
//     }
    
//     private boolean helper(String s1, String s2, String s3, int s1Index, int s2Index, int s3Index) {
//         if (s1Index == s1.length() && s2Index == s2.length() && s3Index == s3.length()) {
//             return true;
//         }
        
//         if (s1Index < s1.length() && s2Index < s2.length()) {
//             if (s1.charAt(s1Index) == s2.charAt(s2Index) && s1.charAt(s1Index) == s3.charAt(s3Index)) {
//                 return helper(s1, s2, s3, s1Index + 1, s2Index, s3Index + 1) ||
//                     helper(s1, s2, s3, s1Index, s2Index + 1, s3Index + 1);
//             } 
//         }
        
//         if (s1Index < s1.length() && s1.charAt(s1Index) == s3.charAt(s3Index)) {
//             return helper(s1, s2, s3, s1Index + 1, s2Index, s3Index + 1);
//         } 
        
//         if (s2Index < s2.length() && s2.charAt(s2Index) == s3.charAt(s3Index)) {
//             return helper(s1, s2, s3, s1Index, s2Index + 1, s3Index + 1);
//         }
        
//         return false;
//     }
}
