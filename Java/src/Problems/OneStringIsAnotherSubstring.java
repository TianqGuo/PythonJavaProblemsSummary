/**
85. Determine If One String Is Another's Substring (laioffer)

Determine if a small string is a substring of another large string.

Return the index of the first occurrence of the small string in the large string.

Return -1 if the small string is not a substring of the large string.

Assumptions

Both large and small are not null
If small is empty string, return 0
Examples

¡°ab¡± is a substring of ¡°bcabc¡±, return 2
¡°bcd¡± is not a substring of ¡°bcabc¡±, return -1
"" is substring of "abc", return 0

Answer Author: Tianquan Guo
Date: 1/3/2020
 */

package Problems;

public class OneStringIsAnotherSubstring {
	public int strstr(String large, String small) {
	    // Write your solution here
	    if (small == "") {
				return 0;
			}
	    
	    char[] array_large = large.toCharArray();
			char[] array_small = small.toCharArray();
			
	    // check if each char in small are the same as the ones in large
	    // traverse start point in large
			for (int i = 0; i <= array_large.length - array_small.length; i++) {
				if (array_large[i] != array_small[0]) {
					continue;
				}
				
	      // traverse small and check whether each char is the same
				int temp  = i;
				for (int j = 0; j < array_small.length; j++) {
					if (array_large[temp] == array_small[j]) {
						temp++;
					} else {
						break;
					}
				}
				
				if (temp == i + array_small.length) {
					return i;
				}
			}
			return -1;
	  }
}
