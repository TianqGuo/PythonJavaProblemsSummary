/**
122. Spiral Order Traverse II
Medium
Traverse an M * N 2D array in spiral order clock-wise starting from the top left corner. Return the list of traversal sequence.

Assumptions

The 2D array is not null and has size of M * N where M, N >= 0
Examples

{ {1,  2,  3,  4},

  {5,  6,  7,  8},

  {9, 10, 11, 12} }

the traversal sequence is [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]

Answer Author: Tianquan Guo
Date: 4/18/2021
 */

package Problems;

import java.util.*;

public class SpiralOrder {
	public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
//        corner case
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return ans;
        }
        
//      define row and col
        int row = matrix.length;
        int col = matrix[0].length;
        
//      define up and down index
//      define left and right index
        int left = 0;
        int right = col - 1;
        int up = 0;
        int down = row - 1;
        
//      start iteration note that each for is a direction it is traversing
//      note the condition
        while (left < right && up < down) {
            for (int i = left; i < right; i++) {
                ans.add(matrix[up][i]);
            }
            
            for (int i = up; i < down; i++) {
                ans.add(matrix[i][right]);
            }
            
            for (int i = right; i > left; i--) {
                ans.add(matrix[down][i]);
            }
            
            for (int i = down; i > up; i--) {
                ans.add(matrix[i][left]);
            }
            
            left++;
            right--;
            up++;
            down--;
        }
        
//      note here we have 3 situations
//      first, no cell left, we can direct return
//      second, left  == right, means vertical col has nums needed to traverse
//      third, up == down, means horizonal row has nums needed to traverse
        if (left > right || up > down) {
            return ans;
        }
        
        if (left == right) {
            for (int i = up; i <= down; i++) {
                ans.add(matrix[i][left]);
            }
        } else {
            for (int i = left; i <= right; i++) {
                ans.add(matrix[up][i]);
            } 
        }
        
        return ans;
    }
}
