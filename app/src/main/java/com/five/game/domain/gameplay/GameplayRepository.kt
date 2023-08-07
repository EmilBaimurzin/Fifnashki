package com.five.game.domain.gameplay

class GameplayRepository {
    fun generateList(): List<GameItem> {
        val listToReturn = mutableListOf<GameItem>()
        repeat(15) {
            listToReturn.add(GameItem(it + 1))
        }
        listToReturn.shuffle()
        listToReturn.add(GameItem(16, isEmpty = true))
        return listToReturn
    }
}