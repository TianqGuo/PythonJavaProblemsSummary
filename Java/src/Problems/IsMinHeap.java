/**
391. Determine If Array Is Min Heap
Easy
Determine if the given integer array is min heap.

Answer Author: Tianquan Guo
Date: 4/19/2021
 */


package Problems;

public class IsMinHeap {
	public boolean isMinHeap(int[] array) {
	    // Write your solution here		
	    if (array == null) {
				return false;			
			}
			
			return helper(array, 0, Integer.MIN_VALUE);		
		}
		
//		Note the conditions that cause helper return true or false
		private boolean helper(int[] input, int index, int minValue) {
			if (index >= input.length) {
				return true;
			}
			
			if (input[index] < minValue) {
				return false;
			}
			
			return helper(input, index * 2 + 1, input[index]) && helper(input, index * 2 + 2, input[index]);
		}
}
