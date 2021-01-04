/**
67. Top K Frequent Words (laicode)

Given a composition with different kinds of words, return a list of the top K most frequent words in the composition.

Assumptions

the composition is not null and is not guaranteed to be sorted
K >= 1 and K could be larger than the number of distinct words in the composition, in this case, just return all the distinct words
Return

a list of words ordered from most frequent one to least frequent one (the list could be of size K or smaller than K)
Examples

Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 2 frequent words are [¡°b¡±, ¡°c¡±]
Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 4 frequent words are [¡°b¡±, ¡°c¡±, "a", "d"]
Composition = ["a", "a", "b", "b", "b", "b", "c", "c", "c", "d"], top 5 frequent words are [¡°b¡±, ¡°c¡±, "a", "d"]

Answer Author: Tianquan Guo
Date: 1/3/2020
 */

package Problems;

import java.util.*;

public class TopKFrequentWords {
//	Method1: using max heap
	public String[] topKFrequentMaxHeap(String[] Combo, int k) {
	    // initialize hashmap
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			
	    // traverse each item in Combo, use mapreduce to count each char
			for (int i = 0; i < Combo.length; i++) {
				if (map.containsKey(Combo[i])) {
					map.put(Combo[i], map.get(Combo[i]) + 1);
	      } else {
	        map.put(Combo[i], 1);
	      }
	    }

	    // override pq so it will sort by the item.getValue
	    // note here is using max heap, we can also use min heap
			PriorityQueue<HashMap.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<HashMap.Entry<String, Integer>> ()
	    {
				@Override
	 			public int compare (HashMap.Entry<String, Integer> num1, HashMap.Entry<String, Integer> num2) {
					if (num1.getValue() == num2.getValue()) {
						return 0;
	        }

	        return num1.getValue() < num2.getValue() ? 1 : -1;
	      }
	    } );

	    for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
	      pq.offer(entry);
	    }

	    // initialize an array and put top k or max size items into the array
	    int num = Math.min(k, pq.size());
	    String[] ans = new String[num];
	    
	    for (int i = 0; i < num; i++) {

	      ans[i] = pq.poll().getKey();
	    }

	    return ans;
	}
	
//	Method2: using min heap
	public String[] topKFrequentMinHeap(String[] combo, int k) {
	    // Write your solution here
	    HashMap<String, Integer> counter = new HashMap<String, Integer>();
			
			for (int i = 0; i < combo.length; i++) {
				if (counter.containsKey(combo[i])) {
					counter.put(combo[i], counter.get(combo[i]) + 1);
				} else {
				counter.put(combo[i], 1);
				}
			}
			
			PriorityQueue<HashMap.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<HashMap.Entry<String, Integer>>() 
			{
				@Override
				public int compare(HashMap.Entry<String, Integer> c1, HashMap.Entry<String, Integer> c2) {
					return c1.getValue().compareTo(c2.getValue());
				}
			} );
			
			for (HashMap.Entry<String, Integer> item : counter.entrySet()) {
				if (pq.size() < k) {
					pq.offer(item);
				}
				
				else if (item.getValue() > pq.peek().getValue()) {
					pq.poll();
					pq.offer(item);
				}
			}
			
			String[] ans = new String[pq.size()];
			for (int i = pq.size() - 1; i >= 0; i--) {
				ans[i] = pq.poll().getKey();
			}
					
			return ans;
	}
}
