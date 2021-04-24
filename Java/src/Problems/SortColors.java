/**
75. Sort Colors
Medium

Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.

 

Example 1:

Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Example 2:

Input: nums = [2,0,1]
Output: [0,1,2]
Example 3:

Input: nums = [0]
Output: [0]
Example 4:

Input: nums = [1]
Output: [1]
 

Constraints:

n == nums.length
1 <= n <= 300
nums[i] is 0, 1, or 2.
 

Follow up:

Could you solve this problem without using the library's sort function?
Could you come up with a one-pass algorithm using only O(1) constant space?

Answer Author: Tianquan Guo
Date: 4/22/2021
 */

package Problems;

public class SortColors {
	public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        
        partition(nums);
        
        return;
    }
    
    private void partition(int[] nums) {
        int redIndex = 0;
        int curIndex = 0;
        int blueIndex = nums.length - 1;        
        
//         note here need to use "<=", or it will not judge all the cases
        while (curIndex <= blueIndex) {
            if (nums[curIndex] == 0) {
                swap(nums, redIndex, curIndex);
                redIndex++;
                curIndex++;
            } else if (nums[curIndex] == 1) {
                curIndex++;
            } else {
                swap(nums, curIndex, blueIndex);
                blueIndex--;
            }
        }            
    }
    
    private void swap (int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}
