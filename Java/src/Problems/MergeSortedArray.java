/**
88. Merge Sorted Array
Easy
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

The number of elements initialized in nums1 and nums2 are m and n respectively. You may assume that nums1 has a size equal to m + n such that it has enough space to hold additional elements from nums2.

 

Example 1:

Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Example 2:

Input: nums1 = [1], m = 1, nums2 = [], n = 0
Output: [1]
 

Constraints:

nums1.length == m + n
nums2.length == n
0 <= m, n <= 200
1 <= m + n <= 200
-109 <= nums1[i], nums2[i] <= 109

Answer Author: Tianquan Guo
Date: 4/24/2021
 */

package Problems;

public class MergeSortedArray {
	public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1, index2 = n - 1;
        int index3 = nums1.length - 1;
        
//        note the key to this problem is starting from the end of the array
        while (index1 >=0 && index2 >= 0) {
            if (nums1[index1] > nums2[index2]) {
                nums1[index3--] = nums1[index1--];
            } else {
                nums1[index3--] = nums2[index2--];
            }
        }
        
        while (index1 >=0) {
            nums1[index3--] = nums1[index1--];
        }
        
        while (index2 >=0) {
            nums1[index3--] = nums2[index2--];
        }
    }
}
