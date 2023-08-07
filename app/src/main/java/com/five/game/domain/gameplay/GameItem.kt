package com.five.game.domain.gameplay

data class GameItem(
    val number: Int,
    var isSelected: Boolean = false,
    val isEmpty: Boolean = false
)