'''
26. Kth Smallest Number In Sorted Matrix (laicode)

Given a matrix of size N x M. For each row the elements are sorted in ascending order, and for each column the elements are also sorted in ascending order. Find the Kth smallest number in it.

Assumptions

the matrix is not null, N > 0 and M > 0
K > 0 and K <= N * M
Examples

{ {1,  3,   5,  7},

  {2,  4,   8,   9},

  {3,  5, 11, 15},

  {6,  8, 13, 18} }

the 5th smallest number is 4
the 8th smallest number is 6

Author: Tianquan Guo
Date: 12/31/2020
'''
from heapq import heappop
from heapq import heappush

class Solution(object):
    def kthSmallest(self, matrix, k):
        """
        input: int[][] matrix, int k
        return: int
        """
        # write your solution here
        if not matrix or not matrix[0]:
            return None

        pq = []
        heappush(pq, cell(0, 0, matrix[0][0]))
        visited = [[False for i in range(len(matrix[0]))] for j in range(len(matrix))]

        #  for each iteration, pop from heap as cur, then check next possible cell is they are visited or out of boundary
        while k > 0:
            cur = heappop(pq)
            if cur.row + 1 < len(matrix) and not visited[cur.row + 1][cur.col]:
                newCell = cell(cur.row + 1, cur.col, matrix[cur.row + 1][cur.col])
                heappush(pq, newCell)
                visited[cur.row + 1][cur.col] = True

            if cur.col + 1 < len(matrix[0]) and not visited[cur.row][cur.col + 1]:
                newCell = cell(cur.row, cur.col + 1, matrix[cur.row][cur.col + 1])
                heappush(pq, newCell)
                visited[cur.row][cur.col + 1] = True

            k -= 1

        return cur.value

# note here cell class is created and override __lt__
class cell(object):
    def __init__ (self, x, y, value):
        self.row = x
        self.col = y
        self.value = value

    # def __repr__(self):
    #     return f'cell value: {self.value}'

    def __lt__(self, other):
        return self.value < other.value


# Test cases
print(Solution().kthSmallest([[1,2,3,4],[11,12,13,14],[15,16,17,18],[19,20,21,22]],6))
print(Solution().kthSmallest([[1, 3, 5, 7], [2, 4, 8, 9], [3, 5, 11, 15], [6, 8, 13, 18]], 6))