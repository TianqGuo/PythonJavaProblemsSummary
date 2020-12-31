'''
785. Is Graph Bipartite? (leetcode)

Given an undirected graph, return true if and only if it is bipartite.

Recall that a graph is bipartite if we can split its set of nodes into two independent subsets A and B, such that every edge in the graph has one node in A and another node in B.

The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.

Example 1:


Input: graph = [[1,3],[0,2],[1,3],[0,2]]
Output: true
Explanation: We can divide the vertices into two groups: {0, 2} and {1, 3}.



Example 2:


Input: graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
Output: false
Explanation: We cannot find a way to divide the set of nodes into two independent subsets.

Constraints:

1 <= graph.length <= 100
0 <= graph[i].length < 100
0 <= graph[i][j] <= graph.length - 1
graph[i][j] != i
All the values of graph[i] are unique.
The graph is guaranteed to be undirected.



56. Bipartite (laicode)

Determine if an undirected graph is bipartite. A bipartite graph is one in which the nodes can be divided into two groups such that no nodes have direct edges to other nodes in the same group.

Examples

1  --   2

    /

3  --   4

is bipartite (1, 3 in group 1 and 2, 4 in group 2).

1  --   2

    /   |

3  --   4

is not bipartite.

Assumptions

The graph is represented by a list of nodes, and the list of nodes is not null.

Answer Author: Tianquan Guo
Date: 12/30/2020
'''



# Definition for a undirected graph node
# class GraphNode:
#     def __init__(self, x):
#         self.key = x
#         self.neighbors = []
from collections import deque
from typing import List


class Solution(object):
    #### Solution for laicode ####
    def bipartite(self, graph):
        """
        input : List<GraphNode> graph
        return : boolean
        """
        # write your solution here
        # created a node to group dictionary
        node_to_group = {}

        # check each node in graph
        for node in graph:
            if not self.node_is_in_same_group(node, node_to_group):
                return False

        return True

    def node_is_in_same_group(self, node, node_to_group):
        # skipped if it is already visited
        if node in node_to_group:
            return True

        que = deque()
        que.append(node)

        # assume first node is group 1
        node_to_group[node] = 1

        # start BFS
        while len(que) != 0:
            for i in range (len(que)):

                # assign value to cur_node and cur_group
                cur_node = que.popleft()
                cur_group = node_to_group[cur_node]

                # check neighbor in the neighbors list
                for neighbor in cur_node.neighbors:

                    # if it is in the dictionary which means vistied and its group is the same as current group,
                    # then it is against Bipartite semantic meaning
                    if neighbor in node_to_group:
                        if node_to_group[neighbor] == cur_group:
                            return False

                    # note don't forgot to assign group number
                    else:
                        node_to_group[neighbor] = 1 if cur_group == 2 else 2
                        que.append(neighbor)

        return True



    #### Solution for leetcode ####

    def isBipartite(self, graph: List[List[int]]) -> bool:
        if not graph:
            return True

        # created a node to group dictionary
        node_to_group = {}

        # go thourgh list
        for index in range(len(graph)):
            # check if the index in the dictionary
            if index in node_to_group:
                continue

            # reset node_to_group, que
            node_to_group[index] = 1
            que = deque()
            que.append(index)

            # start BFS
            while que:
                cur_index = que.popleft()
                cur_group = node_to_group[cur_index]

                # check each neighbor and if its group is against to the Bipartite sematic
                for neighbor in graph[cur_index]:
                    if neighbor in node_to_group:
                        if node_to_group[neighbor] == cur_group:
                            return False

                    # if neighbor not in the group, append it.
                    else:
                        node_to_group[neighbor] = 2 if node_to_group[cur_index] == 1 else 1
                        que.append(neighbor)

        return True