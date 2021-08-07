package Problems;

import java.util.*;

public class DeepCopyGraph {
	public List<GraphNode> copy(List<GraphNode> graph) {
		HashMap<GraphNode, GraphNode> map = new HashMap<>();
	    // GraphNode newHead = new GraphNode(-1);
	    Queue<GraphNode> que = new LinkedList<>();

	    for (GraphNode cur : graph) {
	      if (!map.containsKey(cur)) {
	        map.put(cur, new GraphNode(cur.key));
	      }
	      que.offer(cur);
	    }

	    while (!que.isEmpty()) {
	      GraphNode cur = que.poll();

	      for (GraphNode child : cur.neighbors) {
	        if (!map.containsKey(child)) {
	          map.put(child, new GraphNode(child.key));
	          que.offer(child);
	        }

	        map.get(cur).neighbors.add(map.get(child));  

	      }
	    }

	    return new ArrayList<GraphNode>(map.values());
	    


















	  //   solution2, DFS
	  //   if (graph == null) {
	  //     return null;
	  //   }

	  //   // List<GraphNode> ans = new ArrayList<GraphNode>();
	  //   HashMap<GraphNode, GraphNode> map = new HashMap<>();

	  //   for (GraphNode neighbor : graph) {
	  //     if (!map.containsKey(neighbor)) {
	  //       map.put(neighbor, new GraphNode(neighbor.key));
	  //       DFS(neighbor, map);
	  //     }
	  //   }
	  //   return new ArrayList<GraphNode>(map.values());
	  // }

	  // private void DFS(GraphNode node, HashMap<GraphNode, GraphNode> map) {
	  //   GraphNode cur = map.get(node);
	  //   for (GraphNode neighbor : node.neighbors) {
	  //     if (!map.containsKey(neighbor)) {
	  //       map.put(neighbor, new GraphNode(neighbor.key));
	  //       DFS(neighbor, map);
	  //     }
	  //     cur.neighbors.add(map.get(neighbor));
	  //   }
	  
	    
	    
	  }
	
}
