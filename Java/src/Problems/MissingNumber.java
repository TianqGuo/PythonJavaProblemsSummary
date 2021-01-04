/**
68. Missing Number I (laicode)

Given an integer array of size N - 1, containing all the numbers from 1 to N except one, find the missing number.

Assumptions

The given array is not null, and N >= 1
Examples

A = {2, 1, 4}, the missing number is 3
A = {1, 2, 3}, the missing number is 4
A = {}, the missing number is 1

Answer Author: Tianquan Guo
Date: 1/3/2020
 */

package Problems;
import java.util.*;

public class MissingNumber {
	public class Solution {
		  public int missing(int[] array) {
		    // Write your solution here
		    // Use Hashset to record and then go through again to check
		    HashSet<Integer> set = new HashSet<>();
						
				for (int num : array) {
					set.add(num);
				}
				

				for (int i = 1; i <= array.length + 1; i++) {
					if (!set.contains(i)) {
						return i;
					}
				}
				
				return -1;
		  }
	}
}
