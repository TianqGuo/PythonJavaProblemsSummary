/**
22. Search In Shifted Sorted Array II
Hard
Given a target integer T and an integer array A, A is sorted in ascending order first, then shifted by an arbitrary number of positions.

For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions). Find the index i such that A[i] == T or return -1 if there is no such index.

Assumptions

There could be duplicate elements in the array.
Return the smallest index if target has multiple occurrence. 
Examples

A = {3, 4, 5, 1, 2}, T = 4, return 1
A = {3, 3, 3, 1, 3}, T = 1, return 3
A = {3, 1, 3, 3, 3}, T = 1, return 1

What if A is null or A is of zero length? We should return -1 in this case.

Answer Author: Tianquan Guo
Date: 4/20/2021
 */


package Problems;

public class ShiftedSortedArray2 {
	public int search(int[] array, int target) {
	    // Write your solution here
	    if (array == null || array.length == 0) {
	      return -1;
	    }

	    // step1: identify the index of the MAX value
	    int shiftIndex = findIndex(array, target);
	    
//	    this condition is used to identify [0,0,0] 1 situation
	    if (shiftIndex == -1) {
            return -1;
        }

//	    this condition is used to identify [0,1,0,0,0] 1 situation
	    if (array[shiftIndex] == target) {
	    	return shiftIndex;
	    }
	    
	    
	    // int left = array[0] <= target ? 0 : shiftIndex + 1;
		  // int right = array[0] <= target ? shiftIndex : array.length - 1;

	    // define left, right, note there are three ways to define, 
	    // 1) when there is not shift
	    // 2) when the target number is in [0, shiftIndex]
	    // 3) when the target number is in [shiftIndex + 1, array.length - 1]
	    int left;
	    int right;

	    if (shiftIndex == array.length - 1) {
	      left = 0; 
	      right = array.length - 1;
	    } else if (array[0] <= target) {
	      left = 0;
	      right = shiftIndex;
	    } else {
	      left = shiftIndex + 1;
	      right = array.length - 1;
	    }
	    
	    // use binary search to find the target value
	    while (left + 1 < right) {
	      int mid = left + (right - left) / 2;
	      if (array[mid] >= target) {
	        right = mid;
	      } else {
	        left = mid;
	      }
	    }

	    if (array[left] == target) {
	      return left;
	    }

	    if (array[right] == target) {
	      return right;
	    }

	    return -1;
	  }

	// identify the index of the MAX value
	  private int findIndex (int[] array, int target) {
	    int left = 0;
	    int right = array.length - 1;
	    int mid = left + (right - left) / 2;

	    // need to add this corner case to identify no shift situation
	    if (array[left] < array[right]) {
	      return array.length - 1;
	    }

	    // note here need to notice the condition, since we are finding largest value,
	    // there are 3 situations in this case
	    while (left + 1 < right) {
	      mid = left + (right - left) / 2;
//	      note here in order to deal with [1,1,1,1,1,1,2,1,1,1,1,1] situation, we need to add isBinarySearchHelpful function
	      while(!isBinarySearchHelpful(array, left, array[mid])) {
		      left++;
//			    this condition is used to identify [0,1,0,0,0] 1 situation
		      if (left < array.length && array[left] == target) {
		    	  return left;
//		  	    this condition is used to identify [0,0,0] 1 situation
		      } else if (left >= array.length) {
	                return -1;
	          }
		  }
	      
	      if (array[mid] > array[mid + 1]) {
	        return mid;
	      } else if (array[mid] < array[left]) {
	        right = mid;
	      } else {
	        left = mid;
	      }
	    }

	    if (array[left] > array[right]) {
	      return left;
	    } 

	    return right;
	  }

	  private boolean isBinarySearchHelpful(int[] array, int left, int midValue) {
	    return array[left] != midValue;
	  }
}


