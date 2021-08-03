package Problems;

public class RotateMatrix {
	public void rotate(int[][] matrix) {
	    // Write your solution here
	    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
	      return;
	    }

	    helper(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1);

	    return;
	  }

	  private void helper(int[][] matrix, int left, int right, int top, int down) {
	    if (left >= right) {
	      return;
	    }

	    int[] temp = new int[right - left];

	    for (int i = down; i > top; i--) {
	      temp[down - i] = matrix[i][left];
//	      System.out.println(temp[down - i] + " " + left + " " + down);
	    }

	    for (int i = left; i < right; i++) {
	      int cur = temp[i - left];
	      temp[i - left] = matrix[top][i];
	      matrix[top][i] = cur;
//	      System.out.println(cur + " " + temp[i - left]);
	    }

	    for (int i = top; i < down; i++) {
	      int cur = temp[i - top];
	      temp[i - top] = matrix[i][right];
	      matrix[i][right] = cur;
	    }

	    for (int i = right; i > left; i--) {
	      int cur = temp[right - i];
	      temp[right - i] = matrix[down][i];
	      matrix[down][i] = cur;
	    }

	    for (int i = down; i > top; i--) {
	      int cur = temp[down - i];
	      temp[down - i] = matrix[i][left];
	      matrix[i][left] = cur;
	    }

	    helper(matrix, left + 1, right - 1, top + 1, down - 1);

	    return;
	  }

}
