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

//Definition of ListNode

/**
 * class ListNode {
 *   public int value;
 *   public ListNode next;
 *   public ListNode(int value) {
 *     this.value = value;
 *     next = null;
 *   }
 * }
 */


package Test;

public class reverse_linkedlist {
	// Method 1 Recursion
	public ListNode reverse_recursion(ListNode head) {
	    // Write your solution here
	    // base case
	    if (head == null || head.next == null) {
	      return head;
	    }

	    // get newhead from next recusion step
	    ListNode newhead = reverse_recursion(head.next);
	    
	    // reverse link of two nodes
	    head.next.next = head;
	    head.next = null;

	    return newhead;
	}
	
	// Method 2 Iteration
	 public ListNode reverse_iteration(ListNode head) {
		    // Write your solution here
		    if (head == null) {
		      return null;
		    }

		    ListNode pre = null;
		    ListNode cur = head;
		    ListNode nxt = head;

		    while (cur != null) {
		      nxt = nxt.next;
		      cur.next = pre;
		      pre = cur;
		      cur = nxt;
		      
		    }

		    return pre;
	 }
}

