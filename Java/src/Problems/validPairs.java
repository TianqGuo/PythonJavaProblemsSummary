package Problems;

import java.util.*;

//Assumed that an integer n is larger than 1;
//Time Complexity is O(2 ^ n);
//Space Complexity is O(n);
//recursion tree:
//                                             root
//1st char                        "{"              "}"
//2nd char                 "{{"       "{}"         ...
//3rd char              "{{{"  "{{}"    .... 

public class validPairs {
	public void indentation (int n) {
	    // use the difference between two n to get teh indentation length
	    List<String> ans = new ArrayList<>();
		helper(ans, n, n, "");
	    return;
	}
	  
	 private void helper(List<String> ans, int left, int right, String cur) {
	    //if left and right are all 0 means cur should be printed
	    if (left == 0 && right == 0) {
	    	System.out.println(cur);
	    	return;
	    }
	    
	    //if left > right, means it is illegal expression
	    if (left > right) {
	      return;
	    }
	    
	    String newCur = "";
	    boolean flag;
	    
	    //print left which is "if {"
	    //note here if flag is used to identify if it is for right side recursion
	    //newCur is string combination of spaces, if { , and \n
	    if (left > 0) {
	      flag = false;
	      String spaceLeft = space(left, right, flag);
	      newCur = cur + spaceLeft + "if {" + "\n";	      
	      helper(ans, left - 1, right, newCur);
	    }
	    
	    // print right which is "}"
	    //note here if flag is used to identify if it is for left or right
	    //newCur is string combination of spaces, } , and \n
	    if (right > 0) {
	      flag = true;
  	  String spaceRight = space(left, right, flag);
	      newCur = cur + spaceRight + "}" + "\n";	      
	      helper(ans, left, right - 1, newCur);
	    }
	    
	    return;
	  }
	  
	  
	  // print out "if {" or "}" based on the difference of left and right, which is multiple "  "
	  // note when flag is ture(right side recursion), space length need to be reduced "  "
	  private String space(int left, int right, boolean flag) {
	    String temp = "";
	    left = flag == true ? left + 1: left;
	    for (int i = 0; i < right -left; i++) {
	      temp += "  ";
	    }
	    
	    return temp;
	  }

}
