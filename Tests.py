import sys


# quick select

class solution(object):
    def quickSelect(self, array, target):
        if not array:
            return array

        if target >= len(array):
            return sys.minint

        self.partition(array, 0, len(array) - 1, target)

        return array[target]

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
            elif array[j] >= pivot:
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


# print(solution().quickSelect([1, 6, 2, 4, 3, 3, 3, 8, 7], 4))


class Solution(object):
    def dedup(self, array):
        """
    input: int[] array
    return: int[]
    """
        # write your solution here
        slow = 0
        for fast in range(1, len(array)):
            if array[fast] != array[slow]:
                slow += 1
                array[slow], array[fast] = array[fast], array[slow]
        print(slow)
        return array[:slow + 1]


# print(Solution().dedup([1, 1, 2, 2, 2]))

class Solution(object):
    def mergeSort(self, array):
        """
        input: int[] array
        return: int[]
        """
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


# print(Solution().mergeSort([3, 5, 1, 2, 4, 8]))

from enum import IntEnum
from enum import Enum


class Face(IntEnum):
    ONE = 1
    TWO = 2
    THREE = 3
    FOUR = 4
    FIVE = 5
    SIX = 6
    SEVEN = 7
    EIGHT = 8
    NINE = 9
    TEN = 10
    JACK = 11
    QUEEN = 12
    KING = 13

    def __init__(self, value):
        self.face_val = value


class Suit(Enum):
    HEARTS = "Hearts"
    CLUBS = "Clubs"
    DIAMONDS = "Diamonds"
    SPADES = "Spades"

    def __init__(self, suit_text):
        self.suit_text = suit_text


class Card(object):
    def __init__(self, suit_text, value):
        # Suit.__init__(suit_text)
        # Face.__init__(value)
        self.face = value
        self.suit = suit_text


class Hand(object):
    def __init__(self, name = "PlayerDefault1"):
        self.cards = []
        self.palyer_name = name

    def add_cards(self, card):
        self.cards.append(card)

    def size(self):
        return len(self.cards)

    def score(self):
        score = 0
        for card in self.cards:
            score += card.value
        return score


cur_hand = Hand()
for face in Face:
    for suit in Suit:
        # print(face.value, " of ", suit.value)
        cur_hand.add_cards(Card(suit.value, face.value))

# print(cur_hand.size())




