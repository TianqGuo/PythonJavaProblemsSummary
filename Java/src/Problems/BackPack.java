/**
92 Backpack
Algorithms
Medium
Description
Given n items with size A[i]an integer m denotes the size of a backpack. 
How full you can fill this backpack?

You can not divide any item into small pieces.

Example
Example 1:

Input:

array = [3,4,8,5]
backpack size = 10
Output:

9
Explanation:

Load 4 and 5.

Example 2:

Input:

array = [2,3,5,7]
backpack size = 12
Output:

12
Explanation:

Load 5 and 7.

Challenge
O(n x m) time and O(m) memory.

O(n x m) memory is also acceptable if you do not know how to optimize memory.

Answer Author: Tianquan Guo
Date: 5/13/2021
 */

package Problems;

public class BackPack {
	public int backPack(int m, int[] A) {
        // write your code here
        int[][] dp = new int[A.length + 1][m + 1];
        dp[0][0] = 0;

        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                    continue;
                }

                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + A[i - 1]);
                    // System.out.println(i + " " + j + " " + A[i - 1] + " " + dp[i][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // System.out.println(Arrays.deepToString(dp));

        return dp[A.length][m];
    }
}
