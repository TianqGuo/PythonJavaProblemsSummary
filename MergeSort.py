'''
912. Sort an Array
Given an array of integers nums, sort the array in ascending order.

Example 1:

Input: nums = [5,2,3,1]
Output: [1,2,3,5]
Example 2:

Input: nums = [5,1,1,2,0,0]
Output: [0,0,1,1,2,5]


Constraints:

1 <= nums.length <= 50000
-50000 <= nums[i] <= 50000

Author: Tianquan Guo
Date: 12/27/2020
'''

import copy

# MergeSort method 1
# Time complexity = separate: O(n) + merge: O(nlog(n))
# Space complexity = separate stack depth: O(log(n)) + extra space: O(n)
class Solution(object):
    def mergeSort(self, array = None):
        """
        input: int[] array
        return: int[]
        """
        if not array:
            return array
        # write your solution here
        return self.mergeRecursion(array, 0, len(array) - 1)

    def mergeRecursion(self, array, left, right):
        # print (left, right)
        if left >= right:
            return [array[left]]

        mid = left + int((right - left) / 2)

        leftPart = self.mergeRecursion(array, left, mid)
        rightPart = self.mergeRecursion(array, mid + 1, right)

        return self.merge(leftPart, rightPart)

    def merge(self, leftPart, rightPart):
        leftIndex = 0
        rightIndex = 0
        newArray = []
        while leftIndex < len(leftPart) and rightIndex < len(rightPart):
            if leftPart[leftIndex] > rightPart[rightIndex]:
                newArray.append(rightPart[rightIndex])
                rightIndex += 1
            else:
                newArray.append(leftPart[leftIndex])
                leftIndex += 1

        while leftIndex < len(leftPart):
            newArray.append(leftPart[leftIndex])
            leftIndex += 1

        while rightIndex < len(rightPart):
            newArray.append(rightPart[rightIndex])
            rightIndex += 1

        return newArray

# Test cases
print(Solution().mergeSort([3, 5, 1, 2, 4, 8]))
print(Solution().mergeSort([1, 3, 5, 76, 4, 2, -6, 8, 10]))
print(Solution().mergeSort([10, 9, 4, 7, 3, -1, 3, 5]))
print(Solution().mergeSort([1]))
print(Solution().mergeSort([]))
print(Solution().mergeSort())


# MergeSort method 2
# Time complexity = separate: O(n) + merge: O(nlog(n))
# Space complexity = separate stack depth: O(log(n)) + extra space: O(n)

# MergeSort Better space complexity method
class Solution(object):
    def mergeSort(self, array=None):
        if not array:
            return array

        dummy = copy.deepcopy(array)

        self.helper(array, dummy, 0, len(array) - 1)

        return array

    def helper(self, array, dummy, left, right):
        if left >= right:
            return

        mid = left + int((right - left) / 2)

        self.helper(array, dummy, left, mid)
        self.helper(array, dummy, mid + 1, right)

        self.merge(array, dummy, left, mid, right)

        return

    def merge(self, array, dummy, left, mid, right):
        if left == right:
            return

        for index in range (left, right + 1):
            dummy[index] = array[index]

        i = left
        j = mid + 1
        arrayIndex = left
        while i <= mid and j <= right:
            if dummy[i] <= dummy[j]:
                array[arrayIndex] = dummy[i]
                arrayIndex += 1
                i += 1
            else:
                array[arrayIndex] = dummy[j]
                arrayIndex += 1
                j += 1

        while i <= mid:
            array[arrayIndex] = dummy[i]
            arrayIndex += 1
            i += 1

        return


# Test cases
print(Solution().mergeSort([1, 3, 5, 76, 4, 2, -6, 8, 10]))
print(Solution().mergeSort([10, 9, 4, 7, 3, -1, 3, 5]))
print(Solution().mergeSort([1]))
print(Solution().mergeSort([]))
print(Solution().mergeSort())