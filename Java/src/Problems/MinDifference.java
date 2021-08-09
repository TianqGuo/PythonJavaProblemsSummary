/**
263. Two Subsets With Min Difference
Medium
Given a set of n integers, divide the set in two subsets of n/2 sizes each such that the difference of the sum of two subsets is as minimum as possible.

Return the minimum difference(absolute value).

Assumptions:

The given integer array is not null and it has length of >= 2.
Examples:

{1, 3, 2} can be divided into {1, 2} and {3}, the minimum difference is 0

Answer Author: Tianquan Guo
Date: 8/8/2021
 */


package Problems;

import java.util.Arrays;

public class MinDifference {
	public int minDifference(int[] array) {
	    // Write your solution here
	    int[] ans = new int[] {Integer.MAX_VALUE};
	    int totalSum = 0;
	    int leftSum = 0;

	    for (int i = 0; i <array.length; i++) {
	      totalSum += array[i];
	    }

	    for (int i = 0; i < array.length / 2; i++) {
	      leftSum += array[i];
	    }

	    
	    helper(ans, array, leftSum, totalSum, 0);

	    return ans[0];
	  }

	  private void helper(int[] ans, int[] array, int leftSum, int totalSum, int index) {
		System.out.println(Arrays.toString(array) + " " + index + " " + leftSum + " " + totalSum);
	    ans[0] = Math.min(ans[0], Math.abs(leftSum - (totalSum - leftSum)));

	    for (int i = index; i < array.length / 2; i++) {
	      swap(array, index, i + array.length / 2 );
	      helper(ans, array, leftSum -(array[i + array.length / 2 ] - array[index]), totalSum, index + 1);
	      swap(array, index, i + array.length / 2 );
	    }

	    return;
	  }

	  private void swap(int[] array, int i, int j) {
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	  }
}
