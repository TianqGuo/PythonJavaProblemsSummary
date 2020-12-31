/**
26. Kth Smallest Number In Sorted Matrix (laicode)
378. Kth Smallest Element in a Sorted Matrix (leetcode)

Given a matrix of size N x M. For each row the elements are sorted in ascending order, and for each column the elements are also sorted in ascending order. Find the Kth smallest number in it.

Assumptions

the matrix is not null, N > 0 and M > 0
K > 0 and K <= N * M
Examples

{ {1,  3,   5,  7},

  {2,  4,   8,   9},

  {3,  5, 11, 15},

  {6,  8, 13, 18} }

the 5th smallest number is 4
the 8th smallest number is 6

Answer Author: Tianquan Guo
Date: 12/31/2020
 */


package Problems;

import java.util.*;

public class KthSmallestNumberInSortedMatrix {
	public int kthSmallest(int[][] matrix, int k) {
	    // Write your solution here
	    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
	      return -1;
	    }

	    // note we need to write comparator for priority queue
	    PriorityQueue<cell> minpq = new PriorityQueue<cell>(k, new Comparator<cell>() {
	      @Override
	      public int compare(cell c1, cell c2) {
	        if (c1.val == c2.val) {
	          return 0;
	        }

	        return c1.val < c2.val ? -1 : 1;
	      }
	    });


	    
	    // visited is required for BFS checking if the cell is visted
	    boolean visited[][] = new boolean[matrix.length][matrix[0].length];
	    
	    // note here we need to add cell object instead of row and col
	    minpq.add(new cell(0, 0, matrix[0][0]));
	    visited[0][0] = true;

	    for (int i = 0; i < k - 1; i++) {
	      cell cur = minpq.poll();

	      if (cur.row + 1 < matrix.length && visited[cur.row + 1][cur.col] == false) {
	        minpq.add(new cell(cur.row + 1, cur.col, matrix[cur.row + 1][cur.col]));
	        visited[cur.row + 1][cur.col] = true;
	      }

	      if (cur.col + 1 < matrix[0].length && visited[cur.row][cur.col + 1] == false) {
	        minpq.add(new cell(cur.row, cur.col + 1, matrix[cur.row][cur.col + 1]));
	        visited[cur.row][cur.col + 1] = true;
	      }
	    }

	    return minpq.peek().val;
	 
	  }

	  public class cell {
	    int row;
	    int col;
	    int val;
	    cell(int row, int col, int val) {
	      this.row = row;
	      this.col = col;
	      this.val = val;
	    }
	  }

}
