/**
 Given an array that is initially stored in one stack, sort it with one additional stacks (total 2 stacks).

After sorting the original stack should contain the sorted integers and from top to bottom the integers are sorted in ascending order.

Assumptions:

The given stack is not null.
There can be duplicated numbers in the give stack.
Requirements:

No additional memory (constant).

Author: Tianquan Guo
Date: 12/29/2020

**/



//Time complexity = O(n ^ 2)
//Space complexity = O(1)

package Test;
import java.util.*;

public class sort_with_2_stacks {
	public void sort(LinkedList<Integer> s1) {
		LinkedList<Integer> s2 = new LinkedList<Integer>();
	    // Write your solution here.
	    // corner case
	    if (s1.size() == 0 && s1.size() == 1) {
	      return;
	    }
	    
	    // for every iteration, pop s1 to s2 then mark min_value and count_min, then pop all values >= min_value from s2
	    // then offer all values only > min_value to s1
	    while (!s1.isEmpty()) {
	      int min_value = Integer.MAX_VALUE;
	      int count_min = 0;
	      // pop s1 to s2 then mark min_value and count_min
	      while (!s1.isEmpty()) {
	        int cur = s1.pollFirst();
	        if (cur < min_value) {
	          min_value = cur;
	          count_min = 1;
	        } else if (cur == min_value) {
	          count_min ++;
	        }
	
	        s2.offerFirst(cur);
	      }
	      
	      // pop all values >= min_value from s2
	      // then offer all values only > min_value to s1
	      while (!s2.isEmpty() && s2.peekFirst() >= min_value) {
	        int temp = s2.pollFirst();
	        if (temp != min_value) {
	          s1.offerFirst(temp);
	        }
	      }
	
	      // offer current min_value count_min times to s2
	      while (count_min != 0) {
	        s2.offerFirst(min_value);
	        count_min--;
	      }
	    }
	
	    
	    while (!s2.isEmpty()) {
	      s1.offerFirst(s2.pollFirst());
	    }
	}
}
