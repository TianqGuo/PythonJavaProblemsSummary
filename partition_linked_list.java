package Test;

public class partition_linked_list {
	public ListNode partition(ListNode head, int target) {
	    // Write your solution here
	    ListNode dummy_fast = new ListNode(0);
	    ListNode dummy_slow = dummy_fast;
	    ListNode ans = dummy_slow;
	    dummy_fast.next = head;
	    dummy_slow.next = head;
	    
	    while (dummy_fast != null && dummy_fast.next != null) {
	      if (dummy_fast.next.value < target) {
	        if (dummy_fast == dummy_slow) {
	          dummy_slow = dummy_slow.next;
	          dummy_fast = dummy_fast.next;
	          continue;
	        }

	        // remove
	        ListNode current_node = dummy_fast.next;
	        dummy_fast.next = dummy_fast.next.next;

	        // relocate
	        current_node.next = dummy_slow.next;
	        dummy_slow.next = current_node;

	        dummy_slow = dummy_slow.next;
	      }
	      else {
	      dummy_fast = dummy_fast.next;
	      }
	    }
	    return ans.next;
	  }
}

// better soultion:
//	public ListNode partition(ListNode head, int target) {
//	    // Write your solution here
//	    if (head == null || head.next == null) {
//	      return head;
//	    }
//	
//	    ListNode L1 = new ListNode(-1);
//	    ListNode L2 = new ListNode(-1);
//	    ListNode dummyL1 = L1;
//	    ListNode dummyL2 = L2;
//	
//	    while (head != null) {
//	      if (head.value < target) {
//	        dummyL1.next = head;
//	        dummyL1 = dummyL1.next;
//	        head = head.next;
//	      } else {
//	        dummyL2.next = head;
//	        dummyL2 = dummyL2.next;
//	        head = head.next;
//	      }
//	    }
//	
//	    dummyL1.next = L2.next;
//	    dummyL2.next = null;
//	
//	    return L1.next;
//	  }
