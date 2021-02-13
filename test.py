# Input :  arr[] = {1, 2, 4, 3, 5, 0, 0, 0};
#                                 slow
#                                       fast
# Output : arr[] = {1, 2, 4, 3, 5, 0, 0};
#
# Input : arr[]  = {1, 2, 0, 0, 0, 3, 6};
# Output : arr[] = {1, 2, 3, 6, 0, 0, 0};

#
# class solution(object):
#     def move0ToEnd(self, arr):
#         # Initailize tow pointers
#         slow = 0
#         fast = 0
#         while fast < len(arr):
#             if arr[fast] != 0:
#                 arr[fast], arr[slow] = arr[slow], arr[fast]
#                 slow += 1
#
#             fast += 1
#
#         return arr

# factorial 3 = 1 * 3
# 3! = 3 * 2 * 1
class solution(object):
    # def __init__(self):
    #     self.ans = 1
    def factorial(self, target):
        # for i in range (1, target + 1):
        #     ans *= i
        #
        return self.helper(target)


    def helper(self, target):
        if target == 0:
            return 1
        return target * helper(target - 1)

