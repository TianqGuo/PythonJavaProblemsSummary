/**
121. Spiral Order Traverse I
Medium
Traverse an N * N 2D array in spiral order clock-wise starting from the top left corner. Return the list of traversal sequence.

Assumptions

The 2D array is not null and has size of N * N where N >= 0
Examples

{ {1,  2,  3},

  {4,  5,  6},

  {7,  8,  9} }

the traversal sequence is [1, 2, 3, 6, 9, 8, 7, 4, 5]


Answer Author: Tianquan Guo
Date: 2/21/2020
 */

package Problems;

import java.util.*;

public class SpiralOrderTraversal {
	public List<Integer> spiral(int[][] matrix) {
		List<Integer> ans = new ArrayList<>();
		int length = matrix.length;
		int offset = 0;
		
		helper(matrix, offset, length, ans);
		
		return ans;		
	}
	
	private void helper(int[][] matrix, int offset, int length, List<Integer> ans) {
		if (length == 0) {
			return;
		}
		
		if (length == 1) {
			ans.add(matrix[offset][offset]);
			return;
		}
		
		for (int i = offset; i < offset + length - 1; i++) {
			ans.add(matrix[offset][i]);
		}
		
		for (int j = offset; j < offset + length - 1; j++) {
			ans.add(matrix[j][offset + length - 1]);
		}
		
		for (int i = offset + length - 1; i > offset; i--) {
			ans.add(matrix[offset + length - 1][i]);
		}
		
		for (int j = offset + length - 1; j > offset; j--) {
			ans.add(matrix[j][offset]);
		}
		
		helper(matrix, offset + 1, length - 2, ans);
		
		return;
	}
}
