'''
25. K Smallest In Unsorted Array (laicode)

Find the K smallest numbers in an unsorted integer array A. The returned numbers should be in ascending order.

Assumptions

A is not null
K is >= 0 and smaller than or equal to size of A
Return

an array with size K containing the K smallest numbers in ascending order
Examples

A = {3, 4, 1, 2, 5}, K = 3, the 3 smallest numbers are {1, 2, 3}

Author: Tianquan Guo
Date: 12/30/2020
'''

import heapq
class Solution(object):
    def kSmallestPQ(self, array, k):
        """
        input: int[] array, int k
        return: int[]
        """
        if not array or k >= len(array):
            return array

        pq = []
        ans = []
        for i in range (len(array)):
            heapq.heappush(pq, array[i])

        for i in range (k):
            ans.append(heapq.heappop(pq))

        return ans




        # # write your solution here
        # if not array or k > len(array):
        #     return array
        #
        # pq = []
        # ans = []
        # for index, cur in enumerate(array):
        #     heapq.heappush(pq, cur)
        #
        # for i in range (k):
        #     ans.append(heapq.heappop(pq))
        #
        # return ans


# Test cases
assert Solution().kSmallestPQ([2, 5, 13, 1, 3, 4, -5, 7], 3) == [-5, 1, 2], "Test1 Failed"
assert Solution().kSmallestPQ([2], 3) == [2], "Test2 Failed"
assert Solution().kSmallestPQ([], 3) == [], "Test3 Failed"
assert Solution().kSmallestPQ(None, 3) == None, "Test4 Failed"
assert Solution().kSmallestPQ([4, 7, -1, -23, -100], 3) == [-100, -23, -1], "Test1 Failed"

print("All tests passed")