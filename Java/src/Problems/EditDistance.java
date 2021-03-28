/**
100. Edit Distance
Medium
Given two strings of alphanumeric characters, determine the minimum number of Replace, Delete, and Insert operations needed to transform one string into the other.

Assumptions

Both strings are not null
Examples

string one: ¡°sigh¡±, string two : ¡°asith¡±

the edit distance between one and two is 2 (one insert ¡°a¡± at front then replace ¡°g¡± with ¡°t¡±).
 */

package Problems;

public class EditDistance {
	public int editDistance(String one, String two) {
//		two dimensional dp:
//		 	1 	2 	3 	4 	5 
//		1  	
//		2
//		3
//		4
//		dp[i][j] means the min operations to change first i char of one to first j char of two
		int[][] dp = new int[one.length() + 1][two.length() + 1];
		dp[0][0] = 0;
		
		for (int i = 1; i <= one.length();i++) {
			dp[i][0] = i;
		}
		
		for (int j = 1; j <= two.length();j++) {
			dp[0][j] = j;
		}
		
		for (int i = 1; i <= one.length(); i++) {
			for (int j = 1; j <= two.length();j++) {
//				note here dp[i-1][j-1] is used to describe char equal situation
//				dp[i - 1][j] is used to describe delete one char in one situation
//				dp[i][j - 1] is used to describe insert one char in one situation
				dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + (one.charAt(i - 1) == two.charAt(j - 1) ? 0 : 1),
									dp[i - 1][j] + 1), 
									dp[i][j - 1] + 1);	
				System.out.println("" + dp[i - 1][j - 1] + " " + dp[i - 1][j] + " " + dp[i][j - 1]);
			}
		}
		
		return dp[one.length()][two.length()];		
	}
}
