'''
57. Get Keys In Binary Tree Layer By Layer (laicode)

Get the list of list of keys in a given binary tree layer by layer. Each layer is represented by a list of keys and the keys are traversed from left to right.

Examples

        5

      /    \

    3        8

  /   \        \

 1     4        11

the result is [ [5], [3, 8], [1, 4, 11] ]

Corner Cases

What if the binary tree is null? Return an empty list of list in this case.
How is the binary tree represented?

We use the level order traversal sequence with a special symbol "#" denoting the null node.

For Example:

The sequence [1, 2, 3, #, #, 4] represents the following binary tree:

    1

  /   \

 2     3

      /

    4

Answer Author: Tianquan Guo
Date: 12/30/2020
'''


# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
from collections import deque

# High level idea how to solve this problem:
# create ans list, and start BFS, at the beginning of each BFS level create a list, then after travesal in this level, add this list to ans list
#  note here it is better to use deque compare to list, list.pop(0) is way slower than deque

class Solution(object):
    def layerByLayer(self, root):
        """
        input: TreeNode root
        return: int[][]
        """
        # write your solution here
        ans = []
        if not root:
            return ans

        que = deque()
        que.append(root)

        while que:
            size = len(que)
            curAns = []

            for i in range (size):
                cur = que.popleft()
                curAns.append(cur.val)
                if cur.left:
                    que.append(cur.left)

                if cur.right:
                    que.append(cur.right)

            ans.append(curAns)

        return ans

