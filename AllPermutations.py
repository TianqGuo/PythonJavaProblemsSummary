'''
64. All Permutations I (laicode)

Given a string with no duplicate characters, return a list with all permutations of the characters.

Assume that input string is not null.

Examples

Set = “abc”, all permutations are [“abc”, “acb”, “bac”, “bca”, “cab”, “cba”]

Set = "", all permutations are [""]

Author: Tianquan Guo
Date: 1/2/2021
'''

class Solution(object):
    def permutations(self, input):
        """
        input: string input
        return: string[]
        """
        # write your solution here
        # change input to char array
        char_list = list(input)
        ans = []

        self.helper(char_list, ans, 0)

        return ans

    def helper(self, char_list, ans, index):
        if index >= len(char_list):
            cur = "".join(char_list)
            ans.append(cur)
            return

        for i in range (index, len(char_list)):
            char_list[i], char_list[index] = char_list[index], char_list[i]
            self.helper(char_list, ans, index + 1)
            char_list[i], char_list[index] = char_list[index], char_list[i]