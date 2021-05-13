/**
205. Implement LRU Cache
Medium
Implement a least recently used cache. 
It should provide set(), get() operations. 
If not exists, return null (Java), false (C++), -1(Python).

Answer Author: Tianquan Guo
Date: 5/12/2021
 */

package Problems;

import java.util.*;

public class LRU<K, V> {
	ListNode<K, V> head;
	ListNode<K, V> tail;
	int size;
	int capacity;
  HashMap<K, ListNode<K, V>> map = new HashMap<>();
  // limit is the max capacity of the cache
  public LRU(int limit) {
    this.size = 0;
    this.capacity = limit;
    this.head = new ListNode<K, V>(null, null);
  }
  
  public void set(K key, V value) {
	  if (!map.containsKey(key)) {
	      ListNode<K, V> cur = new ListNode<K, V>(key, value);
          insertHead(cur);
          
	      if (tail == null) {
	        tail = cur;
	      }
	      	      
	      map.put(key,cur);
	      size++;
	    } else {
            ListNode<K, V> cur = map.get(key);
		    cur.value = value;
	    	if (map.get(key) != head.next) {
                // link cur.next and cur.pre
                removeCur(cur);
                // insert cur between head and headNext               
		        insertHead(cur);
	    	}
	    }
    	//check if size is larger than capacity
//    	System.out.println(size);
        if (size > capacity) {
          map.remove(tail.key);
          tail = tail.pre;
          tail.next = null;
          size--;
        }
        // this.print(head);
  }
  
  public V get(K key) {
	  if (map.containsKey(key)) {
          ListNode<K, V> cur = map.get(key);
	      // insert node between head and head.next
		  if (map.get(key) != head.next) {
			 // link cur.next and cur.pre
            removeCur(cur);
            // insert cur between head and headNext               
	        insertHead(cur);
		  }
		  
          // this.print(head);
	      return map.get(key).value;
    }
    // this.print(head);
    return null;
  }
  
  private void insertHead (ListNode<K, V> cur) {      
      ListNode<K, V> headNext = head.next;

      head.next = cur;
      cur.pre = head;
      cur.next = headNext;
      if (headNext != null) {
          headNext.pre = cur;
	    }
  }
  
  private void removeCur (ListNode<K, V> cur) {
      ListNode<K, V> curNext = cur.next;
      ListNode<K, V> curPre = cur.pre;
      
      if (curNext != null) {
          curNext.pre = curPre;
      } else {
          tail = curPre;
      }
      curPre.next = curNext;
  }

  public class ListNode<K, V> {
    K key;
    V value;
    ListNode<K, V> next;
    ListNode<K, V> pre;
    public ListNode(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }
}
