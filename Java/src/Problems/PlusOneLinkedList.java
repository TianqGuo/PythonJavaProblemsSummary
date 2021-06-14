/**
369. Plus One Linked List
Medium

Given a non-negative integer represented as a linked list of digits, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list.

 

Example 1:

Input: head = [1,2,3]
Output: [1,2,4]
Example 2:

Input: head = [0]
Output: [1]
 

Constraints:

The number of nodes in the linked list is in the range [1, 100].
0 <= Node.val <= 9
The number represented by the linked list does not contain leading zeros except for the zero itself. 

Answer Author: Tianquan Guo
Date: 6/13/2021
 */

package Problems;

public class PlusOneLinkedList {
	public ListNode plusOne(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }
        
        int carray = helper(head);
        
        if (carray == 1) {
            ListNode newHead = new ListNode(1);
            newHead.next = head;
            return newHead;
        }
        
        return head;
    }
    
    private int helper(ListNode head) {
        if (head == null) {
            return 1;
        }
        
        int carry = helper(head.next);
        int nextCarry = 0;
        
        if (carry == 1) {
            head.val++;
            if (head.val == 10) {
                head.val = 0;
                nextCarry = 1;
            }
        }
        
        return nextCarry;
    }
    
    public class ListNode {
    	 int val;
    	 ListNode next;
    	 ListNode() {}
    	 ListNode(int val) { this.val = val; }
    	 ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
