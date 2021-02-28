/**
89. Array Hopper II
Medium
Given an array A of non-negative integers, you are initially positioned at index 0 of the array. A[i] means the maximum jump distance from index i (you can only jump towards the end of the array). Determine the minimum number of jumps you need to reach the end of array. If you can not reach the end of the array, return -1.

Assumptions

The given array is not null and has length of at least 1.
Examples

{3, 3, 1, 0, 4}, the minimum jumps needed is 2 (jump to index 1 then to the end of array)

{2, 1, 1, 0, 2}, you are not able to reach the end of array, return -1 in this case.

Answer Author: Tianquan Guo
Date: 2/28/2020
 */

package Problems;

public class ArrayHopper2 {
	public int minJump (int[] array) {
//		if (array.length == 1) {
//			return 0;
//		}
		
//		note here dp can be array.length, because the value of last element in the array doesn't matter
		int[] dp = new int[array.length];
		dp[array.length - 1] = 0;
		
//		from right to left, note the conditions to judge whethter dp[i] should be updated
		for (int i = array.length - 2; i >= 0; i--) {
			dp[i] = -1;
			for (int j = i + 1; j <= array.length - 1; j++) {
				if (j - i <= array[i] && dp[j] != -1) {
					dp[i] = dp[i] != -1 ? Math.min(dp[j] + 1, dp[i]) : dp[j] + 1;
				}
			}
		}
		return dp[0];
	}
}


