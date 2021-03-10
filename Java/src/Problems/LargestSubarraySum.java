/**
97. Largest SubArray Sum
Medium
Given an unsorted integer array, find the subarray that has the greatest sum. Return the sum.

Assumptions

The given array is not null and has length of at least 1.
Examples

{2, -1, 4, -2, 1}, the largest subarray sum is 2 + (-1) + 4 = 5

{-2, -1, -3}, the largest subarray sum is -1

Answer Author: Tianquan Guo
Date: 3/29/2021
 */

package Problems;

public class LargestSubarraySum {
	public int largestSum(int[] array) {
		int[] dp = new int[array.length];
		dp[0] = array[0];
		int maxValue = array[0];
		for (int i = 1; i < array.length; i++) {
			dp[i] = Math.max(array[i], dp[i - 1] + array[i]);
			maxValue = Math.max(maxValue, dp[i]);
		}
		
		return maxValue;
	}

}
