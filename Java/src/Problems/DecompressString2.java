/**
175. Decompress String II
Hard
Given a string in compressed form, decompress it to the original string. The adjacent repeated characters in the original string are compressed to have the character followed by the number of repeated occurrences.

Assumptions

The string is not null

The characters used in the original string are guaranteed to be ¡®a¡¯ - ¡®z¡¯

There are no adjacent repeated characters with length > 9

Examples

¡°a1c0b2c4¡± ¡ú ¡°abbcccc¡±

Answer Author: Tianquan Guo
Date: 2/15/2020
 */

package Problems;

public class DecompressString2 {
	public String decompress(String input) {
		char[] array = input.toCharArray();
		StringBuilder ans = new StringBuilder();
		int cur = 0;
		
		while (cur < array.length){
			char curChar = array[cur++];
			String curCount = "";
			while (cur < array.length && array[cur] >= '0' && array[cur] <= '9') {
				curCount += array[cur++];
			} 
			
			for (int i = 0; i < Integer.valueOf(curCount); i++) {
				ans.append(curChar);
			}
		}
		
		return ans.toString();
	}
}
