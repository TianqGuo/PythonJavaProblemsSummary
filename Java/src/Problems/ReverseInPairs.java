/**
35. Reverse Linked List In Pairs
Medium
Reverse pairs of elements in a singly-linked list.

Examples

L = null, after reverse is null
L = 1 -> null, after reverse is 1 -> null
L = 1 -> 2 -> null, after reverse is 2 -> 1 -> null
L = 1 -> 2 -> 3 -> null, after reverse is 2 -> 1 -> 3 -> null
 */

package Problems;

public class ReverseInPairs {
	public ListNode reverseInPairs(ListNode root) {
		if (root == null || root.next == null) {
			return root;
		}
		
		ListNode newHead = reverseInPairs(root.next.next);
		
		ListNode nxt = root.next;
		root.next = newHead;
		nxt.next = root;
		
		return nxt;
	}
	
	public void printNodes(ListNode root) {
		while (root != null) {
			System.out.print(" " + root.value);
			root = root.next;
		}
	}
}
