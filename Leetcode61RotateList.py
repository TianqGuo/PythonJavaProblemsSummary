'''
61. Rotate List
Given the head of a linked list, rotate the list to the right by k places.




Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9

Author: Tianquan Guo
Date: 12/29/2020
'''

import ListNode

# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def rotateRight(self, head: ListNode, k: int) -> ListNode:
        if not head:
            return head

        # traverse list to get length and tail
        dummy1 = head
        list_length = 0
        while dummy1:
            list_length += 1
            if not dummy1.next:
                tail = dummy1
            dummy1 = dummy1.next

        # calculate shift number needed
        shift_num = list_length - k % list_length
        temp = head

        # if no shift needed, return head
        if shift_num == list_length:
            return head

        # get the newHead node and break link for the shift
        for _ in range (shift_num - 1):
            temp = temp.next

        new_head = temp.next
        temp.next = None
        tail.next = head

        return new_head