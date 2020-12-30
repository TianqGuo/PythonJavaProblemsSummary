package Problems;
import java.util.*;

public class MergeKLists {
	public ListNode merge(List<ListNode> listOfLists) {
	    // Write your solution here/.
	    PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
	      @Override
	      public int compare(ListNode e1, ListNode e2) {
	        if (e1.value == e2.value) {
	          return 0;
	        }

	        return e1.value < e2.value ? -1 : 1;
	      }

	    });

	    ListNode dummy = new ListNode(-1);
	    ListNode head = dummy;

	    for (int i = 0; i < listOfLists.size(); i++) {
	      if(listOfLists.get(i) != null) {
	        pq.offer(listOfLists.get(i));
	      }
	    }

	    while (!pq.isEmpty()) {
	    	ListNode cur = pq.poll();
	        dummy.next = cur;

	        if (cur.next != null) {
	          pq.offer(cur.next);
	        }
	        
	        dummy = dummy.next;
	    }

	    return head.next;
	  }

}
