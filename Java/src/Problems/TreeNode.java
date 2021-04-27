package Problems;

//public class TreeNode {
//	public int key;
//    public TreeNode left;
//    public TreeNode right;
//    public TreeNode(int key) {
//      this.key = key;
//    }
//    
//    public void printTreeNode(TreeNode root) {
////    	inorder
//    	if (root == null) {
//    		return;
//    	}
//    	
//    	printTreeNode(root.left);
//    	System.out.print(root.key);
//    	printTreeNode(root.right);
//    	
//    	return;
//    }
//}


public class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int x) { val = x; }
	
	public void printTreeNode(TreeNode root) {
//    	inorder
    	if (root == null) {
    		return;
    	}
    	
    	printTreeNode(root.left);
    	System.out.print(root.val);
    	printTreeNode(root.right);
    	
    	return;
    }
}


