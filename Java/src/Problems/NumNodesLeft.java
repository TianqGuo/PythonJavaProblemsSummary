/**
646. Store Number Of Nodes In Left Subtree
Medium
Given a binary tree, count the number of nodes in each node¡¯s left subtree, and store it in the numNodesLeft field.

Examples


                  1(6)

               /          \

           2(3)        3(0)

          /      \

      4(1)     5(0)

    /        \        \

6(0)     7(0)   8(0)

The numNodesLeft is shown in parentheses.

Answer Author: Tianquan Guo
Date: 2/25/2020
 */


package Problems;

public class NumNodesLeft {
	public TreeNodeLeft numNodesLeft(TreeNodeLeft root) {
		helper(root);
		return root;
	}
	
	private int helper (TreeNodeLeft root) {
		if (root == null) {
			return 0;
		}
		
		root.numNodesLeft = helper(root.left);
		
		return  root.numNodesLeft + helper(root.right) + 1;
	}
}
