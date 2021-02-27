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
		
////		ReorderArray
//		ReorderArray ra = new ReorderArray();
//		System.out.println(Arrays.toString(ra.reorder(new int[] {1, 2, 3, 4, 5, 6, 7, 8})));
		
////		CompressString2
//		CompressString2 cs2 = new CompressString2();
//		System.out.println(cs2.compressString2("abbcccdeee"));
		
////		MinCombineSands
//		MinCombineSands mcs = new MinCombineSands();
//		System.out.println(mcs.minCombine(new int[] {13, 7, 8, 10}));
		
////		DecompressString2
//		DecompressString2 ds2 = new DecompressString2();
//		System.out.println(ds2.decompress("a1d14c5"));
//		System.out.println(ds2.decompress("a1d0c5"));
		
////		LongestSubstringWithoutRepeating
//		LongestSubstringWithoutRepeating lswr = new LongestSubstringWithoutRepeating();
//		System.out.println(lswr.longest("abcdabcdeabc"));
		
		
////		AllAnagrams
//		AllAnagrams aa = new AllAnagrams();
//		System.out.println(aa.allAnagrams("ab", "aabbabaccabcba"));
		
////		LongestSubArrayOnly1s
//		LongestSubArrayOnly1s lsao = new LongestSubArrayOnly1s();
//		System.out.println(lsao.longestConsecutiveOnes(new int[] {1,1,0,0,1,1,1,0,0,0}, 2));
		
////		SpiralOrderTraversal
//		SpiralOrderTraversal sot = new SpiralOrderTraversal();
//		System.out.println(sot.spiral(new int[][] {{-85,56,37,48},
//													{-25,-78,-29,62},
//													{18,-60,-74,-84},
//													{90,44,5,1}}));
		
////		NQueens
//		NQueens nqs = new NQueens();
//		System.out.println(nqs.nQueens(5));	
		
////		ReverseInPairs
//		ReverseInPairs rp = new ReverseInPairs();
//		ListNode root = new ListNode(1);
//		root.next = new ListNode(2);
//		root.next.next = new ListNode(3);
//		
//		rp.printNodes(rp.reverseInPairs(root));
		
////		StringAbbrMatch
//		StringAbbrMatch sam = new StringAbbrMatch();
//		System.out.println(sam.match("laioffercom", "6fer3"));
//		System.out.println(sam.match("laioffercom", "6er3"));
		
////		LowestCommonAncestor1
//		LowestCommonAncestor1 lca1 = new LowestCommonAncestor1();
//		TreeNode root = new TreeNode(0);
//		root.left = new TreeNode(1);
//		root.right = new TreeNode(2);
//		TreeNode two = root.right;
//		root.left.left = new TreeNode(3);
//		TreeNode one = root.left.left;
//		System.out.println(lca1.lca1(root, one, two).key);
		
//		ReverseBT
		ReverseBT rbt = new ReverseBT();
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.printTreeNode(root);
		System.out.print("\n");
		root.printTreeNode(rbt.reverse(root));
		
		
		
	}
}
