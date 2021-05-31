/**
118. Array Deduplication IV
Hard
Given an unsorted integer array, remove adjacent duplicate elements repeatedly, from left to right. For each group of elements with the same value do not keep any of them.

Do this in-place, using the left side of the original array. Return the array after deduplication.

Assumptions

The given array is not null
Examples

{1, 2, 3, 3, 3, 2, 2} ¡ú {1, 2, 2, 2} ¡ú {1}, return {1}

Answer Author: Tianquan Guo
Date: 5/31/2021
 */

package Problems;

import java.util.*;

public class ArrayDeduplication4 {
	public int[] dedup(int[] array) {
	    if (array == null || array.length == 0) {
	      return array;
	    }
	    
	    int slow = -1;
	    int fast = 0;

	    // start itertion
	    while (fast < array.length) {
	      // judge if slow == -1 and current slow value is equal to fast
	      if (slow == -1 || array[slow] != array[fast]) {
	        slow++;
	        array[slow] = array[fast];
	        fast++;
	      } else {
	        // find the next fast that value is not equal to slow value
	        while (fast < array.length && array[slow] == array[fast]) {
	          fast++;
	        }
	        // remember slow--
	        slow--;
	      }
	    }
	    
	    return Arrays.copyOf(array, slow + 1);
	}
}
