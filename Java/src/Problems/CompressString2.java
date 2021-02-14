/**
611. Compress String II
Hard
Given a string, replace adjacent, repeated characters with the character followed by the number of repeated occurrences.

Assumptions

The string is not null

The characters used in the original string are guaranteed to be ¡®a¡¯ - ¡®z¡¯

Examples

¡°abbcccdeee¡± ¡ú ¡°a1b2c3d1e3¡±

Answer Author: Tianquan Guo
Date: 2/13/2020
 */

package Problems;

public class CompressString2 {
	
	public String compressString2(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		
		String ans = "";
		char[] array = input.toCharArray();
		int start = 0;
		int cur = 0;
		
		while (cur <= array.length - 1) {
			int count = 0;
			start = cur;
			while (cur <= array.length - 1 && array[start] == array[cur]) {
				count += 1;
				cur++;
			}
			ans += Character.toString(array[start]) + count;	
		}
		
		return ans;
	}
}
