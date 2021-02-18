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
//		initialize parameters
		char[] array = input.toCharArray();
		StringBuilder ans = new StringBuilder();
		int cur = 0;
		
//		note use while is better than for for this case
		while (cur < array.length){
//			increase cur and reset curChar and curCount
			char curChar = array[cur++];
			String curCount = "";
//			if current char is numeric continue adding to curCount
			while (cur < array.length && array[cur] >= '0' && array[cur] <= '9') {
				curCount += array[cur++];
			} 
			
//			add all letters to ans
			for (int i = 0; i < Integer.valueOf(curCount); i++) {
				ans.append(curChar);
			}
		}
		
		return ans.toString();
	}
}
