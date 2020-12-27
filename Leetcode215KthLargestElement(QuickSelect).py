class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        if not nums:
            return nums

        if k > len(nums):
            return sys.maxsize

        self.partition(nums, 0, len(nums) - 1, len(nums)- k)

        print(nums)
        return nums[-k]

    def partition(self, array, left, right, target):
        if left >= right:
            return

        pivotIndex = int(left + (right - left) / 2)
        pivot = array[pivotIndex]

        i = left
        j = right

        array[right], array[pivotIndex] = array[pivotIndex], array[right]
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

        array[right], array[i] = array[i], array[right]

        if i > target:
            self.partition(array, left, i - 1, target)

        elif i < target:
            self.partition(array, i + 1, right, target)