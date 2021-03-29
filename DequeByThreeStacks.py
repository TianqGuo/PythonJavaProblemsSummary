'''
645. Deque By Three Stacks
Medium
Java: Implement a deque by using three stacks. The queue should provide size(), isEmpty(), offerFirst(), offerLast(), pollFirst(), pollLast(), peekFirst() and peekLast() operations. When the queue is empty, pollFirst(), pollLast(), peekFirst() and peek() should return null.


Python: Implement a deque by using three stacks. The queue should provide size(), isEmpty(), offerFirst(), offerLast(), pollFirst(), pollLast(), peekFirst() and peekLast() operations. When the queue is empty, pollFirst(), pollLast(), peekFirst() and peek() should return None


C++: Implement a deque by using three stacks. The queue should provide size(), isEmpty(), push_front(), push_back(), pop_front(), pop_back(), front() and back() operations. When the queue is empty, front() and back() should return -1.



Assumptions



The elements in the queue are all Integers.

size() should return the number of elements buffered in the queue.

isEmpty() should return true if there is no element buffered in the queue, false otherwise.

The amortized time complexity of all operations should be O(1).

Author: Tianquan Guo
Date: 3/28/2021
'''

from collections import deque
class Solution:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.stack1 = deque()
        self.stack2 = deque()
        self.stack3 = deque()

    def isEmpty(self):
        """
        Return true if the deque is empty otherwise return false
        """

        if len(self.stack1) + len(self.stack2)  == 0:
            return True

        return False

    def size(self):
        """
        return Size of deque
        """

        return len(self.stack1) + len(self.stack2)

    def offerFirst(self, x):
        """
        Offer x to the first position of the deque
        """

        self.stack2.append(x)

    def offerLast(self, x):
        """
        Offer x to the last position of the deque
        """
        self.stack1.append(x)

    def peekFirst(self):
        """
        Peek the first element of the deque.
        Return None if the deque is empty.
        """
        self.balance(self.stack1, self.stack2)
        return None if not self.stack2 else self.stack2[len(self.stack2) - 1]

    def peekLast(self):
        """
        Peek the last element of the deque.
        Return None if the deque is empty.
        """
        self.balance(self.stack2, self.stack1)
        return None if not self.stack1 else self.stack1[len(self.stack1) - 1]

    def pollFirst(self):
        """
        Poll the first element of the deque.
        Return None if the deque is empty.
        """
        self.balance(self.stack1, self.stack2)
        return None if not self.stack2 else self.stack2.pop()

    def pollLast(self):
        """
        Poll the last element of the deque.
        Return None if the deque is empty.
        """
        self.balance(self.stack2, self.stack1)
        return None if not self.stack1 else self.stack1.pop()

    def balance(self, one, two):
        if (len(two) > 0):
            return

        for i in range (int(len(one) / 2)):
            self.stack3.append(one.pop())

        while len(one) != 0:
            two.append(one.pop())

        while len(self.stack3) !=0:
            one.append(self.stack3.pop())