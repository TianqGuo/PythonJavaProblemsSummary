/**
847. Shortest Path Visiting All Nodes
Hard
84890Add to ListShare
You have an undirected, connected graph of n nodes labeled from 0 to n - 1. You are given an array graph where graph[i] is a list of all the nodes connected with node i by an edge.
Return the length of the shortest path that visits every node. You may start and stop at any node, you may revisit nodes multiple times, and you may reuse edges.
 
Example 1:

Input: graph = [[1,2,3],[0],[0],[0]]
Output: 4
Explanation: One possible path is [1,0,2,0,3]
Example 2:

Input: graph = [[1],[0,2,4],[1,3,4],[2],[1,2]]
Output: 4
Explanation: One possible path is [0,1,4,2,3]
 
Constraints:
	n == graph.length
	1 <= n <= 12
	0 <= graph[i].length < n
	graph[i] does not contain i.
	If graph[a] contains b, then graph[b] contains a.
The input graph is always connected.
Answer Author: Tianquan Guo
Date: 5/16/2021
 */

package Problems;

import java.util.*;

public class ShortestPathVisitingNodes {
	public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // set mask for final result comparison
        int mask = (1<<n) - 1;
        // initialize visited 
        boolean[][] visited = new boolean[n][1<<n];
        Queue<Integer> queue = new LinkedList<>();
        // for every start point assign 1<<i to mark it is visted and assign i<<n to mark current node
        for (int i = 0; i < n; i++) {
            int state = (i<<n) | (1<<i);
            queue.add(state);
            visited[i][1<<i] = true;
        }
        int dep = 0;
        while (queue.size() > 0) {
            int size = queue.size();
            while (size-- > 0) {
                int state = queue.poll();
                // right shift n to get current node 
                int node = state >> n;
                // & with mask to get only current status
                int status = state & mask;
                if (status == mask) return dep;
                for (int next: graph[node]) {
                    // add current int to status
                    int newStatus = (1<<next) | status;
                    // add current node to the state
                    int newState = (next<<n) | newStatus;
                    if (!visited[next][newStatus]) {
                        queue.add(newState);
                        visited[next][newStatus] = true;
                    }
                }
            }
            dep++;
        }
        return -1;
    }
}
