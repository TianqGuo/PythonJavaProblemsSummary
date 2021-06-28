/**
410. Split Array Largest Sum
Hard

2829

98

Add to List

Share
Given an array nums which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.

Write an algorithm to minimize the largest sum among these m subarrays.

 

Example 1:

Input: nums = [7,2,5,10,8], m = 2
Output: 18
Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.
Example 2:

Input: nums = [1,2,3,4,5], m = 2
Output: 9
Example 3:

Input: nums = [1,4,4], m = 3
Output: 4
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 106
1 <= m <= min(50, nums.length)

Answer Author: Tianquan Guo
Date: 6/27/2021
 */

package Problems;

public class SplitArrayLargestSum {
	public int splitArray(int[] nums, int m) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        
        for (int num : nums) {
            if (num < min) {
                min = num;
            }
            
            sum += num;
        }
        
        int left = min;
        int right = sum;
        
        // System.out.println(min + " " + sum);
        
        while (left + 1 < right) {
            // System.out.println(left + " " + right);
            int mid = left + (right - left) / 2;
            if (isValid(nums, mid, m)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        
        if (isValid(nums, left, m)) {
            return left;
        } 
        
        if (isValid(nums, right, m)) {
            return right;
        }
        
        return right;
    } 
    
    private boolean isValid(int[] nums, int target, int m) {
        int curIndex = 0;
        int curSum = 0;
        int numOfParts = 0;
        while (curIndex < nums.length) {
            if (nums[curIndex] > target) {
                return false;
            }
            
            if (nums[curIndex] + curSum > target)  {
                curSum = nums[curIndex];
                numOfParts++;                
            } else {
                curSum += nums[curIndex];
            }
            
            curIndex++;
        }
        
        // System.out.println("m " + numOfParts + " " + target);
        
        return numOfParts + 1<= m;
    }
}
