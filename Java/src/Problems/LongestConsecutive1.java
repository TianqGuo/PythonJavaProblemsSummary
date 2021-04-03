/**
103. Longest Consecutive 1s
Easy
Given an array containing only 0s and 1s, find the length of the longest subarray of consecutive 1s.

Assumptions

The given array is not null
Examples

{0, 1, 0, 1, 1, 1, 0}, the longest consecutive 1s is 3.

Answer Author: Tianquan Guo
Date: 4/3/2021
 */


package Problems;

public class LongestConsecutive1 {
	public int longest(int[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		
		int[] dp = new int[array.length + 1];
		dp[0] = 0;
		int ans = 0;
		
		for (int i = 1; i <= array.length; i++) {
			if (array[i - 1] != 1) {
				dp[i] = 0;
				continue;
			}
			
			dp[i] = dp[i - 1] + 1;
			ans = Math.max(ans, dp[i]);
		}
		
		return ans;		
	}
}
