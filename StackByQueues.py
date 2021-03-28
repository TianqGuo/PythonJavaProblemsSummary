'''
634. Stack by Queue(s)
Medium
Implement a stack containing integers using queue(s). The stack should provide push(x), pop(), top() and isEmpty() operations.

In java: if the stack is empty, then top() and pop() will return null.

In Python: if the stack is empty, then top() and pop() will return None.

In C++:  if the stack is empty, then top() and pop() will return nullptr.

Author: Tianquan Guo
Date: 3/28/2021
'''

from collections import deque


class Solution:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.que = deque()

    def push(self, x):
        """
        Push element x onto stack.
        """
        self.que.append(x)

    def pop(self):
        """
        Removes the element on top of the stack and returns that element.
        """
        if not self.que:
            return None

        size = len(self.que)

        while size > 1:
            self.que.append(self.que.popleft())
            size -= 1

        ans = self.que.popleft()

        return ans

    def top(self):
        """
        Get the top element.
        """
        if not self.que:
            return None

        ans = self.pop()

        self.que.append(ans)

        return ans

    def isEmpty(self):
        """
        Returns whether the stack is empty.
        """
        return True if len(self.que) == 0 else False


# Test cases
instance = Solution()
print(instance.isEmpty())
print(instance.push(1))
print(instance.push(2))
print(instance.push(3))
print(instance.que)
print(instance.pop())
print(instance.isEmpty())
print(instance.top())
print(instance.pop())
print(instance.pop())
print(instance.pop())
