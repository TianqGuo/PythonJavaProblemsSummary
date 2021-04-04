/**
K Closest In Unsorted Array

Find the K closest numbers to a target value in an unsorted integer array A. The returned numbers should be in ascending order.

Assumptions

The given array is not null
Examples

{1, 2, 4, 9, 8, 7}, target = 9, k = 3
return = {9, 8, 7}

Answer Author: Tianquan Guo
Date: 4/4/2021
 */


package Problems;
import java.util.*;

public class KClosestInUnsortedArray {
	public int[] closest(int[] input, int target, int k) {
		if (input == null || input.length == 0) {
			return new int[0];
		}
		
		k = Math.min(k, input.length);
		
		int[] ans = new int[k];
//		Note here PriorityQueue need to be override by a object, because compre function need the value of target
		PriorityQueue<Element> pq = new PriorityQueue<>(new Comparator<Element>() 
		{
			@Override
			public int compare (Element elem1, Element elem2) {
				return Math.abs(elem1.value - elem1.target) < Math.abs(elem2.value - elem2.target) ? -1 : 1;
			}
		});
		
//		offer all values by Element object
		for (int i = 0; i < input.length; i++) {
			pq.offer(new Element(i, input[i], target));
		}
		
//		pop k the elements and put the value of the elements in the ans array
		for (int temp = k; temp > 0; temp--) {
			Element curElem = pq.poll();
			ans[k - temp] = curElem.value;
		}
		
//		System.out.println(Arrays.toString(ans));
		return ans;
	}
	
	private class Element {
		int index;
		int value;
		int target;
		public Element (int index, int value, int target){
			this.index = index;
			this.target = target;
			this.value = value;
		}
	}
}
