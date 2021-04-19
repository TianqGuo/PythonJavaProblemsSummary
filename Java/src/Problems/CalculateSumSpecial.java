/**
calculte the total number by adding and minus number for each digit in a int

For example:

[1, 2, 3, 4, 5] ==> 1 - 2 + 3 - 4 + 5 ==>3 

Answer Author: Tianquan Guo
Date: 4/19/2021
 */

package Problems;

public class CalculateSumSpecial {
	public int calculteSumSpecial (int num) {
		if (num < 0 ) {
			return 0;
		}
		
		char[] array = String.valueOf(num).toCharArray();
//		System.out.println(array);
		int ans = 0;
		boolean plus = true;
		for (char cur : array) {
			if (plus) {
				ans += cur - '0';
			} else {
				ans -= cur - '0';
			}
			plus = !plus;
		}
		
		return ans;
	}
}
