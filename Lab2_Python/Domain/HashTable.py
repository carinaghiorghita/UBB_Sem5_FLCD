from collections import deque

class HashTable:

    def __init__(self, size):
        self.__items = [deque() for _ in range(size)]
        self.__size = size

    def hash(self, key):
        sum = 0
        for char in key:
            sum += ord(char) - ord('0')
        return sum % self.__size

    def add(self, key):
        if self.contains(key):
            return self.getPosition(key)
        self.__items[self.hash(key)].append(key)
        return self.getPosition(key)

    def contains(self, key):
        return key in self.__items[self.hash(key)]

    def remove(self, key):
        self.__items[self.hash(key)].remove(key)

    def getPosition(self, key):
        listPosition = self.hash(key)
        listIndex = 0
        for item in self.__items[listPosition]:
            if item != key:
                listIndex += 1
            else:
                break
        return (listPosition, listIndex)


    def __str__(self) -> str:
        result = "Symbol Table\n"
        for i in range(self.__size):
            result = result + str(i) + "->" + str(self.__items[i]) + "\n"
        return result