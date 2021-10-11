from Domain.HashTable import HashTable


class ST:

    def __init__(self, size) -> None:
        self.__ht = HashTable(size)

    def __str__(self) -> str:
        return str(self.__ht)

    def add(self, key):
        return self.__ht.add(key)

    def contains(self, key):
        return self.__ht.contains(key)

    def remove(self, key):
        self.__ht.remove(key)

    def getPosition(self, key):
        return self.__ht.getPosition(key)