package Problems;

import java.util.*;

public class TopologicalSort {
	public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        ArrayList<DirectedGraphNode> order = new ArrayList<>();
        Map<DirectedGraphNode, Integer> indegree = new HashMap<>();

//      calculte indegree for each node
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                if (indegree.containsKey(neighbor)) {
                    indegree.put(neighbor, indegree.get(neighbor) + 1);
                } else {
                    indegree.put(neighbor, 1);
                }
            }
        }

        // create a queue and search in the graph in order to find the start node
        // start node is not contained in indegree
        Queue<DirectedGraphNode> queue = new ArrayDeque<>();
        for (DirectedGraphNode node : graph) {
            if (!indegree.containsKey(node)) {
                queue.offer(node);
                order.add(node);
            }
        }

        // start BFS, and check each neighbor, if indegree ==0
        // add it to the order and queue
        while (!queue.isEmpty()) {
            DirectedGraphNode node = queue.poll();
            for (DirectedGraphNode neighbor : node.neighbors) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    order.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return order;
    }
}
