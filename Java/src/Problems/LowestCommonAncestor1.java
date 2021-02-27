/**
126. Lowest Common Ancestor I
Medium
Given two nodes in a binary tree, find their lowest common ancestor.

Assumptions

There is no parent pointer for the nodes in the binary tree

The given two nodes are guaranteed to be in the binary tree

Examples

        5

      /   \

     9     12

   /  \      \

  2    3      14

The lowest common ancestor of 2 and 14 is 5

The lowest common ancestor of 2 and 9 is 9

Answer Author: Tianquan Guo
Date: 2/27/2020
 */

package Problems;

public class LowestCommonAncestor1 {
	public TreeNode lca1 (TreeNode root, TreeNode one, TreeNode two) {
		if(root == null) {
			return root;
		}
		
		if (root == one || root == two) {
			return root;
		}
		
		TreeNode left = lca1(root.left, one, two);
		TreeNode right = lca1(root.right, one, two);
		
		if (left != null && right != null) {
			return root;
		}
		
		if (left != null) {
			return left;
		}
		
		return right;
	}
}
