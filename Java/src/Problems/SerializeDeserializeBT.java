package Problems;

import java.util.*;

public class SerializeDeserializeBT {
	public String serialize(TreeNode root) {
        StringBuilder ans = new StringBuilder();
        if (root == null) {
            return ans.toString();
        }
        
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        ans.append(root.val);
        
        while (!que.isEmpty()) {
            TreeNode cur = que.poll();
            if (cur.left != null) {
                que.offer(cur.left);
                ans.append(" " + cur.left.val);
            } else {
                ans.append(" " + "n");
            }
            
            if (cur.right != null) {
                que.offer(cur.right);
                ans.append(" " + cur.right.val);
            } else {
                ans.append(" " + "n");
            }
        }
        
        int lastDigit = ans.length() - 1;
        while (ans.charAt(lastDigit) > '9' || ans.charAt(lastDigit) < '0') {
            lastDigit--;
        }
        
        // System.out.println(ans.substring(0, lastDigit + 1));
        // System.out.println(ans);
        return ans.substring(0, lastDigit + 1);
    }
	
	// Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length() == 0) {
            return null;
        }
        
        String[] dataArray = data.split(" ");
        
        Queue<TreeNode> que = new LinkedList<>();
        int curIndex = 0;
        TreeNode ans = new TreeNode(Integer.valueOf(dataArray[curIndex]));
        que.offer(ans);
        
        
        while (!que.isEmpty()) {
            TreeNode cur = que.poll();
            curIndex++;
            // System.out.println(cur.val);
            
            if (curIndex >= dataArray.length) {
                return ans;
            }
            
            // System.out.println(dataArray[curIndex]);
            // System.out.println(Arrays.asList(dataArray));
            
            if (!dataArray[curIndex].equals("n")) {      
                cur.left = new TreeNode(Integer.valueOf(dataArray[curIndex]));
                que.offer(cur.left);
            }
            
            curIndex++;
            
            if (curIndex >= dataArray.length) {
                return ans;
            }
            
            if (!dataArray[curIndex].equals("n")) {                
                cur.right = new TreeNode(Integer.valueOf(dataArray[curIndex]));
                que.offer(cur.right);
            }        
            
        }
        
        return ans;
    }
}
