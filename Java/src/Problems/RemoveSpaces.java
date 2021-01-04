/**
281. Remove Spaces (laicode)

Given a string, remove all leading/trailing/duplicated empty spaces.

Assumptions:

The given string is not null.
Examples:

¡°  a¡± --> ¡°a¡±
¡°   I     love MTV ¡± --> ¡°I love MTV¡±

Answer Author: Tianquan Guo
Date: 1/3/2020
 */

package Problems;

public class RemoveSpaces {
	public String removeSpaces(String input) {
	    // Write your solution here
	    String ans = new String();
	    boolean flag = false;
	    // start iteration, need to identify when first char is " " and when current char is " " and flag is ture
	    for (int i = 0; i < input.length(); i++) {
	      if (!(input.charAt(i) == ' ')) {
	        ans += input.charAt(i);
	        flag = true;
	      } else if ((input.charAt(i) == ' ')  && flag == true) {
	        ans += input.charAt(i);
	        flag = false;
	      } 
	    }

	    // And delete the " " at the end of the string
	    int len = ans.length() - 1;
	    while (len > 0 && ans.charAt(len) == ' ') {
	      len -= 1;
	    }
	    
	    // reconstruct string
	    String result = new String();
	    for (int i = 0; i <= len; i++) {
	      result += ans.charAt(i);
	    }

	    return result;
	}
}
