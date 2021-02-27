package Problems;

public class TreeNode {
	public int key;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int key) {
      this.key = key;
    }
    
    public void printTreeNode(TreeNode root) {
//    	inorder
    	if (root == null) {
    		return;
    	}
    	
    	printTreeNode(root.left);
    	System.out.print(root.key);
    	printTreeNode(root.right);
    	
    	return;
    }
}
