/**
197. ReOrder Array
Hard
Given an array of elements, reorder it as follow:

{ N1, N2, N3, ¡­, N2k } ¡ú { N1, Nk+1, N2, Nk+2, N3, Nk+3, ¡­ , Nk, N2k }

{ N1, N2, N3, ¡­, N2k+1 } ¡ú { N1, Nk+1, N2, Nk+2, N3, Nk+3, ¡­ , Nk, N2k, N2k+1 }

Try to do it in place.

Assumptions

The given array is not null
Examples

{ 1, 2, 3, 4, 5, 6} ¡ú { 1, 4, 2, 5, 3, 6 }

{ 1, 2, 3, 4, 5, 6, 7, 8 } ¡ú { 1, 5, 2, 6, 3, 7, 4, 8 }

{ 1, 2, 3, 4, 5, 6, 7 } ¡ú { 1, 4, 2, 5, 3, 6, 7 }

Answer Author: Tianquan Guo
Date: 2/12/2020
 */

package Problems;

import java.util.Arrays;

public class ReorderArray {
	public int[] reorder(int[] array) {
		if (array == null) {
			return array;
		}
		
		if (array.length % 2 == 0) {
			helper(array, 0, array.length - 1);
		} else {
			helper(array, 0, array.length - 2);
		}
		
//		System.out.println(Arrays.toString(array));
		
		return array;
	}
	
	private void helper(int[] array, int left, int right) {
		if (left >= right - 1) {
			return;
		}
		
//		define length
		int len = right - left + 1;
		
		int mid = len / 2 + left;
		int start = len / 4 + left;
		int end = len / 4 + mid;
		
		
		reverse(array, start, mid - 1);
		reverse(array, mid, end - 1);
		reverse(array, start, end - 1);
		
		helper(array, left, left + (start - left) * 2 - 1);
		helper(array, left + (start - left) * 2, right);
		
		return;
	}
	
	private void reverse(int[] array, int start, int end) {
//		System.out.println(start + " " + end);
		for (int i = start, j = end; i < j; i++, j--) {
			int temp = array[i];
			array[i]  = array[j];
			array[j] = temp;
		}
	}
}
