package Problems;

public class ArrayHopper {
	public boolean canJump(int[] array) {
	    // Write your solution here
	    if (array.length == 1) {
	      return true;
	    }

	    boolean[] dp = new boolean[array.length];
	    // base case
	    dp[array.length - 1] = true;

	    for (int i = array.length - 2; i >= 0; i--) {
	      if (i + array[i] >= array.length - 1) {
	        dp[i] = true;
	      } else {
	        for (int j = array[i]; j >= 1; j--) {
	          if (dp[j + i] == true) {
	            dp[i] = dp[j + i];
	            break;
	          }
	        }
	      }
	    }

	    return dp[0];
	  }
}
