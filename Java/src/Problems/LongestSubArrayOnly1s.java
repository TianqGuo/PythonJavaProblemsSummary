/**
625. Longest subarray contains only 1s
Medium
Given an array of integers that contains only 0s and 1s and a positive integer k, you can flip at most k 0s to 1s, return the longest subarray that contains only integer 1 after flipping.

Assumptions:

1. Length of given array is between [1, 20000].

2. The given array only contains 1s and 0s.

3. 0 <= k <= length of given array.

Example 1:

Input: array = [1,1,0,0,1,1,1,0,0,0], k = 2

Output: 7

Explanation: flip 0s at index 2 and 3, then the array becomes [1,1,1,1,1,1,1,0,0,0], so that the length of longest subarray that contains only integer 1 is 7.

Example 2:

Input: array = [1,1,0,0,1,1,1,0,0,0], k = 0

Output: 3

Explanation: k is 0 so you can not flip any 0 to 1, then the length of longest subarray that contains only integer 1 is 3.


Answer Author: Tianquan Guo
Date: 2/20/2020
 */

package Problems;

public class LongestSubArrayOnly1s {
	public int longestConsecutiveOnes(int[] nums, int k) {
		int slow = 0;
		int fast = 0;
		int numk = 0;
		int ans = 0;
		
//		note there are three situation, when nums[fast] == 1, when nums[fast] == 0 but numk < k or numk >=k
//		ans should only update when numk <= k
//		when numk >= k, slow should ++ and if the value is 0, we should reduce numk by 1.
		while (fast < nums.length) {
			if (nums[fast] == 1) {
				ans = Math.max(ans, fast - slow + 1);
				fast++;
			} else {
				if (numk < k) {
					ans = Math.max(ans, fast - slow + 1);
					numk++;
					fast++;
				} else if (nums[slow++] == 0){
					numk--;
				}
			}
		}
		
		return ans;
	}
}
