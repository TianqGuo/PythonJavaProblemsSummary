import copy
from collections import deque

class Solution(object):
    DIR = [(-1, 0), (1, 0), (0, 1), (0, -1), (-1, 1), (1, 1), (1, -1), (-1, -1)]
    def shortestPathBinaryMatrix(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        if not grid or not grid[0] or grid[0][0] == 1:
            return -1

        visited = set()

        ans = self.helper(grid, visited)

        return ans

    def helper(self, grid, visited):
        queue = deque()
        queue.append((0, 0))
        visited.add((0, 0))
        step = 1

        while queue:
            cur_level_count = len(queue)
            for _ in range (cur_level_count):
                cur_x, cur_y = queue.popleft()
                if cur_x == len(grid) - 1 and cur_y == len(grid[0]) - 1:
                    return step

                for diff_x, diff_y in self.DIR:
                    new_x, new_y = cur_x + diff_x, cur_y + diff_y

                    if new_x < 0 or new_y < 0 or new_x >= len(grid) or new_y >= len(grid[0]):
                        continue

                    if (new_x, new_y) in visited:
                        continue

                    if grid[new_x][new_y] == 1:
                        continue

                    queue.append((new_x, new_y))
                    visited.add((new_x, new_y))

            step += 1

        return -1

if __name__ == "__main__":
    test_cases = [
        ([[0]], 1),
        ([[1]], -1),
        ([[0, 1], [1, 0]], 2),
        ([[0, 1], [1, 1]], -1),
        ([[0, 0, 0], [1, 1, 0], [1, 1, 0]], 4),
        ([[1, 0, 0], [0, 0, 0], [0, 0, 0]], -1),
        ([[0, 0, 0], [0, 0, 0], [0, 0, 1]], -1),
        ([[0, 0, 0], [0, 0, 0], [0, 0, 0]], 3),
        ([[0, 1, 0], [0, 1, 0], [0, 0, 0]], 4),
        ([[0, 1, 1, 1],
          [0, 0, 1, 1],
          [1, 0, 0, 1],
          [1, 1, 0, 0]], 4),
        ([[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]], 4),
        ([[0, 1, 1], [1, 1, 1], [1, 1, 0]], -1),
    ]

    sol = Solution()

    for i, (grid, expected) in enumerate(test_cases, 1):
        result = sol.shortestPathBinaryMatrix(copy.deepcopy(grid))
        print("Test {}: result = {}, expected = {}, pass = {}".format(
            i, result, expected, result == expected
        ))