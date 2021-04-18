/**
489. Quiz: Largest SubArray Sum
Medium
Given an unsorted integer array, find the subarray that has the greatest sum. Return the sum and the indices of the left and right boundaries of the subarray. If there are multiple solutions, return the leftmost subarray.

Assumptions

The given array is not null and has length of at least 1.
Examples

{2, -1, 4, -2, 1}, the largest subarray sum is 2 + (-1) + 4 = 5. The indices of the left and right boundaries are 0 and 2, respectively.

{-2, -1, -3}, the largest subarray sum is -1. The indices of the left and right boundaries are both 1


Return the result in a array as [sum, left, right]

Answer Author: Tianquan Guo
Date: 4/17/2021
 */

package Problems;

public class largestSubArraySum2 {
	public int[] largestSum(int[] array) {
		if (array == null || array.length == 0) {
			return new int[0];
		}
		
		int curLeft = 0;
		int ansLeft = 0;
		int ansRight = 0;
		int[] dp = new int[array.length];
		dp[0] = array[0];
		int ansValue = array[0];
		
		for (int i = 1; i < array.length; i++) {
//			note here condition is very crutial, only when dp[i - 1] < 0, we will like to reassign i
			if (dp[i - 1] < 0) {
				curLeft = i;
				dp[i] = array[i];
			} else {
				dp[i] = dp[i - 1] + array[i];
			}
			
			if (dp[i] > ansValue) {
				ansLeft = curLeft;
				ansRight = i;
				ansValue = dp[i];
			}
		}
		
		return new int[] {ansValue, ansLeft, ansRight};
	}
}
