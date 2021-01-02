'''
62. All Subsets I (laicode)

Given a set of characters represented by a String, return a list containing all subsets of the characters.

Assumptions

There are no duplicate characters in the original set.
Examples

Set = "abc", all the subsets are [“”, “a”, “ab”, “abc”, “ac”, “b”, “bc”, “c”]
Set = "", all the subsets are [""]
Set = null, all the subsets are []

Author: Tianquan Guo
Date: 1/2/2021
'''




# Because this is a problem to get subsets, each letter has possiblly to exist or not exist
# So recursion tree for "abc" shoudl be like this:
#               ""
#           "a"          ""
#        "ab"    "a"     "b" ""
#   "abc"  "ab" ...
class Solution(object):
    def subSets(self, set):
        ans = []
        if not set:
            return [[]]
        self.helper(set, ans, 0, "")
        return ans

    def helper(self, string, ans, index, subset):
        # base case, exit condition
        if index >= len(string):
            ans.append(subset)
            return

        # call next recursion for two cases: when current letter exist and current letter doesn't exist
        self.helper(string, ans, index + 1, subset)
        self.helper(string, ans, index + 1, subset + string[index])

# Test cases
print("Start Testing...")

# Test 1
print(Solution().subSets("abc"))
assert Solution().subSets("abc") == ["", "c", "b", "bc", "a", "ac", "ab", "abc"]
print("Test 1 passed")
# Test 2
assert Solution().subSets("a") == ["", "a"]
print("Test 2 passed")
# Test 3
assert Solution().subSets("") == [[]]
print("Test 3 passed")

print("All AllSubsets1 tests passed")
