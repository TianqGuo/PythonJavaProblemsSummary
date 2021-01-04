/**
82. Remove Adjacent Repeated Characters IV (laioffer)

Repeatedly remove all adjacent, repeated characters in a given string from left to right.

No adjacent characters should be identified in the final string.

Examples

"abbbaaccz" ¡ú "aaaccz" ¡ú "ccz" ¡ú "z"
"aabccdc" ¡ú "bccdc" ¡ú "bdc"

Answer Author: Tianquan Guo
Date: 1/3/2020
 */

package Problems;

public class RemoveAdjantRepeatedChar4 {
	public String deDup(String input) {
	    // Write your solution here
	    // identify corner case
	    if (input == null || input.length() <= 1) {
				return input;
			}
			
	    // initialize char array
			char[] char_array = input.toCharArray();
			int end = 0;
			
	    // iterate each char 
			for (int i = 1; i < char_array.length; i++) {
	      // if end is -1 or current char at i and end index char is the same
	      // assign i index char to end index 
				if (end == -1 || char_array[i] != char_array[end]) {
					end++;
					char_array[end] = char_array[i];
	      
	      // if current i index char the same as end index char, end--, and then traverse unless next char is not the same as current 
				} else {
	        end--;
					while (i < char_array.length - 1 && char_array[i] == char_array[i + 1]) {
						i++;
					}
				}
			}
			return new String(char_array, 0, end + 1);
	  }
}
