'''
66. All Valid Permutations Of Parentheses I (laicode)

Given N pairs of parentheses “()”, return a list with all the valid permutations.

Assumptions

N > 0
Examples

N = 1, all valid permutations are ["()"]
N = 3, all valid permutations are ["((()))", "(()())", "(())()", "()(())", "()()()"]

Author: Tianquan Guo
Date: 1/2/2021
'''

# count remaining left and right numbers and if left larger than right means it is illegal
# append subset to ans when both are 0

class Solution(object):
    def validParentheses(self, n):
        """
        input: int n
        return: string[]
        """
        # write your solution here
        ans = []
        self.helper(ans, n, n, "")

        return ans

    def helper(self, ans, left, right, subset):
        # base case/exit condition
        if left == 0 and right == 0:
            ans.append(subset)
            return ans

        # when left larger than right, it means the subset is illegal
        if right < left:
            return

        # when left is larger than 0, append "(" and left--
        if left > 0:
            self.helper(ans, left - 1, right, subset + "(")

        # when right is larger than 0, append ")" and right--
        if right > 0:
            self.helper(ans, left, right - 1, subset + ")")

        return