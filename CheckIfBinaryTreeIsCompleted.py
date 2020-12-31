'''
47. Check If Binary Tree Is Completed (laicode)

Check if a given binary tree is completed. A complete binary tree is one in which every level of the binary tree is completely filled except possibly the last level. Furthermore, all nodes are as far left as possible.

Examples

        5

      /    \

    3        8

  /   \

1      4

is completed.

        5

      /    \

    3        8

  /   \        \

1      4        11

is not completed.

Corner Cases

What if the binary tree is null? Return true in this case.
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
class Solution(object):
    def isCompleted(self, root):
        """
        input: TreeNode root
        return: boolean
        """
        # write your solution here
        if not root:
            return True

        # initialize queue
        que = deque()
        que.append(root)
        # flag is needed to identify first missing left node or right node
        flag = False

        # start BFS
        while len(que) != 0:
            for i in range (len(que)):
                cur = que.popleft()

                # check existence of left or right node also check flag and set flag if any node is missing
                if cur.left and not flag:
                    que.append(cur.left)
                elif cur.left and flag:
                    return False
                elif not cur.left:
                    flag = True

                if cur.right and not flag:
                    que.append(cur.right)
                elif cur.right and flag:
                    return False
                elif not cur.right:
                    flag = True

        return True