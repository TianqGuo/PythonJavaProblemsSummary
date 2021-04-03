/**
101. Largest Square Of 1s
Hard
Determine the largest square of 1s in a binary matrix (a binary matrix only contains 0 and 1), return the length of the largest square.

Assumptions

The given matrix is not null and guaranteed to be of size N * N, N >= 0
Examples

{ {0, 0, 0, 0},

  {1, 1, 1, 1},

  {0, 1, 1, 1},

  {1, 0, 1, 1}}

the largest square of 1s has length of 2

Answer Author: Tianquan Guo
Date: 3/31/2021
 */

package Problems;

import java.util.Arrays;

public class LargestSquareOf1s {
	public int largest(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		
		int[][] dp = new int[matrix.length][matrix[0].length];
		int ans = 0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if ((i == 0 || j == 0) && matrix[i][j] == 1) {
					dp[i][j] = 1;
				} else if (matrix[i][j] == 1) {
//					note here we need to compared the left one and up one
//					and check how many previous matrix elements are 1 or not
//					[[1, 1, 1, 1], 
//					[1, 2, 2, 2], 
//					[0, 1, 2, 3], 
//					[1, 1, 2, 3]]
//					
					dp[i][j] = Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1);
//					note here we have to add the following line to solve this situation:
//					[0, 1]
//					[1, 1]
					dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, dp[i][j]);
				}
				
				ans = Math.max(ans, dp[i][j]);
			}
		}
		
		System.out.println(Arrays.deepToString(dp));
		return ans;
	}
}
