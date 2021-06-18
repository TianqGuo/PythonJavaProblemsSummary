/**
863. All Nodes Distance K in Binary Tree
Medium

We are given a binary tree (with root node root), a target node, and an integer value k.

Return a list of the values of all nodes that have a distance k from the target node.  The answer can be returned in any order.

 

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2

Output: [7,4,1]

Explanation: 
The nodes that are a distance 2 from the target node (with value 5)
have values 7, 4, and 1.



Note that the inputs "root" and "target" are actually TreeNodes.
The descriptions of the inputs above are just serializations of these objects.
 

Note:

The given tree is non-empty.
Each node in the tree has unique values 0 <= node.val <= 500.
The target node is a node in the tree.
0 <= k <= 1000.

Answer Author: Tianquan Guo
Date: 6/17/2021
 */

package Problems;

import java.util.*;

public class NodesDistanceKInBT {
	public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // Graph graph = new Graph(-1);
        HashMap<TreeNode, List<TreeNode> >graph = new HashMap<>();
        HashSet<TreeNode> set = new HashSet<>();
        List<Integer> ans = new ArrayList<>();
        
        helper(root, null, graph);
        
        int level = 0;
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(target);
        
        while (!que.isEmpty()) {
            int size = que.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = que.poll();
                set.add(cur);
                if (level == k) {
                    ans.add(cur.val);
                } else {
                    List<TreeNode> neighbors = graph.get(cur);
                    for (TreeNode neighbor : neighbors) {
                        if (set.contains(neighbor)) {
                            continue;
                        }
                        que.add(neighbor);
                    }
                }
            }
            
            if (level == k) {
                return ans;
            } 
            
            level++;
        }
        
        return ans;
    }
    
    private void helper(TreeNode root, TreeNode parent, HashMap<TreeNode, List<TreeNode>> graph) {
        if (root == null) {
            return;
        }
        
        if (!graph.containsKey(root)) {
            graph.put(root, new ArrayList<TreeNode>());
        }
        
        if (parent != null) {
            graph.get(root).add(parent);
        }
        
        if (root.left != null) {
            graph.get(root).add(root.left);
        }
        
        if (root.right != null) {
            graph.get(root).add(root.right);
        }
        
        helper(root.left, root, graph);
        
        helper(root.right, root, graph);
        
        return;
    }
}
