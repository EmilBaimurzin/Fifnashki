package com.five.game.ui.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.game.domain.gameplay.GameItem
import com.five.game.domain.gameplay.GameplayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameplayViewModel: ViewModel() {
    private val repository = GameplayRepository()
    private var gameScope = CoroutineScope(Dispatchers.Default)
    private val _list =  MutableLiveData(repository.generateList())
    val list: LiveData<List<GameItem>> = _list

    private val _timer = MutableLiveData(0)
    val timer: LiveData<Int> = _timer

    var gameState = true
    var pauseState = false
    var winCallback: (() -> Unit)? = null

    fun startTimer() {
        gameScope = CoroutineScope(Dispatchers.Default)
        gameScope.launch {
            while (true) {
                delay(1000)
                _timer.postValue(_timer.value!! + 1)
            }
        }
    }

    fun stopTimer() {
        gameScope.cancel()
    }

    fun clickItem(item: GameItem, position: Int) {
        if (!item.isEmpty) {
            if (_list.value!!.find { it.isSelected } != null) {
                val newList = _list.value!!
                newList.map { it.isSelected = false }
                _list.postValue(newList)
            } else {
                val newList = _list.value!!.toMutableList()
                newList[position].isSelected = true
                _list.postValue(newList)
            }
        } else {
            if (_list.value!!.find { it.isSelected } != null) {
                val newList = _list.value!!.toMutableList()
                val selectedItem = newList.find { it.isSelected }!!
                val selectedItemIndex = newList.indexOf(selectedItem)
                newList[selectedItemIndex] = GameItem(16, isEmpty = true)
                newList[position] = selectedItem
                newList.map { it.isSelected = false }
                _list.postValue(newList)
            }
        }
        checkWin()
    }

    private fun checkWin() {
        viewModelScope.launch {
            delay(20)
            val currentList = _list.value!!.toMutableList()
            val newList = mutableListOf<Int>()
            currentList.forEach {
                newList.add(it.number)
            }
            if (newList.isIncremental()) {
                winCallback?.invoke()
            }
        }
    }

    private fun List<Int>.isIncremental(): Boolean {
        for (i in 1 until size) {
            if (this[i] != this[i - 1] + 1) {
                return false
            }
        }
        return true
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}