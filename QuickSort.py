'''
10. Quick Sort
Medium
Given an array of integers, sort the elements in the array in ascending order. The quick sort algorithm should be used to solve this problem.

Examples

{1} is sorted to {1}
{1, 2, 3} is sorted to {1, 2, 3}
{3, 2, 1} is sorted to {1, 2, 3}
{4, 2, -3, 6, 1} is sorted to {-3, 1, 2, 4, 6}
Corner Cases

What if the given array is null? In this case, we do not need to do anything.
What if the given array is of length zero? In this case, we do not need to do anything.

Author: Tianquan Guo
Date: 3/30/2021
'''

class Solution(object):
    def quickSort(self, array=None):
        """
        input: int[] array
        return: int[]
        """
        # write your solution here
        if not array:
            return array

        self.partition(array, 0, len(array) - 1)

        return array

    def partition(self, array, left, right):
        if left >= right:
            return

        i = left
        j = right
        pivotIndex = int (left + (right - left)/2)
        pivot = array[pivotIndex]
        array[pivotIndex], array[right] = array[right], array[pivotIndex]
        j -= 1

        while i <= j:
            if array[i] < pivot:
                i += 1
            elif array[j] > pivot:
                j -= 1
            else:
                array[i], array[j] = array[j], array[i]
                i += 1
                j -= 1

        array[i], array[right] = array[right], array[i]

        self.partition(array, left, i - 1)
        self.partition(array, i + 1, right)

        return


assert Solution().quickSort([1, 3, 5, 76, 4, 2, -6, 8, 10]) == [-6, 1, 2, 3, 4, 5, 8, 10, 76], "Test1 Failed"
assert Solution().quickSort([10, 9, 4, 7, 3, -1, 3, 5]) == [-1, 3, 3, 4, 5, 7, 9, 10], "Test2 Failed"
assert Solution().quickSort([1]) == [1], "Test3 Failed"
assert Solution().quickSort([]) == [], "Test4 Failed"
assert Solution().quickSort() is None, "Test5 Failed"
# print(Solution().quickSort([1, 3, 5, 76, 4, 2, -6, 8, 10]))
# print(Solution().quickSort([10, 9, 4, 7, 3, -1, 3, 5]))
# print(Solution().quickSort([1]))
# print(Solution().quickSort([]))
# print(Solution().quickSort())