/**
178. Reverse Binary Tree Upside Down
Medium
Given a binary tree where all the right nodes are leaf nodes, flip it upside down and turn it into a tree with left leaf nodes as the root.

Examples

        1

      /    \

    2        5

  /   \

3      4

is reversed to

        3

      /    \

    2        4

  /   \

1      5

Answer Author: Tianquan Guo
Date: 2/27/2020
 */

package Problems;

public class ReverseBT {
	public TreeNode reverse(TreeNode root) {
		if (root == null) {
			return root;
		}
		return helper(root);
	}
	
//	note here it is better to use root, instead of root and root.left for signature
	private TreeNode helper(TreeNode root) {
		if (root == null || root.left == null) {
			return root;
		}
		
//		find the new Root
		TreeNode newRoot = helper(root.left);
		
//		reverse root and the root.left which is the most left lower node for newRoot subtree
//		also need to change root.right to root.left.right
		root.left.left = root;
		root.left.right = root.right;
		
//		note need to set root.left and root.right as null
		root.left = null;
		root.right = null;
		
		return newRoot;
	}

}
