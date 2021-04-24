/**
215. Kth Largest Element in an Array
Medium
Given an integer array nums and an integer k, return the kth largest element in the array.

Note that it is the kth largest element in the sorted order, not the kth distinct element.

 

Example 1:

Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
Example 2:

Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
Output: 4
 

Constraints:

1 <= k <= nums.length <= 104
-104 <= nums[i] <= 104

Answer Author: Tianquan Guo
Date: 4/24/2021
 */

package Problems;

import java.util.*;

public class KthLargestInArray {
	public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() 
        {
            @Override
            public int compare (Integer n1, Integer n2) {
                if (n1 == n2) {
                    return 0;
                }
                return n1 < n2 ? -1 : 1; 
            }
            
        });
        
//        note this works also:
//        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
        
//         note here we maintain a min heap, so we can pop all the min value out.
        for (int cur : nums) {
            heap.offer(cur);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        
        return heap.poll();
        
    }
}
