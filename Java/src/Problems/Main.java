/**
This main class is created for running manual tests.

Answer Author: Tianquan Guo
Date: 12/31/2020
 */


package Problems;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
////		All_subsets_1
//		All_subsets_1 as1 = new All_subsets_1();
//		System.out.println(as1.subSets("abc"));
		
//		ReorderArray
		ReorderArray ra = new ReorderArray();
		System.out.println(Arrays.toString(ra.reorder(new int[] {1, 2, 3, 4, 5, 6, 7, 8})));
	}
}
