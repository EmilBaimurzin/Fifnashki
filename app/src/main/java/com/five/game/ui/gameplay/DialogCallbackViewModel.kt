package com.five.game.ui.gameplay

import androidx.lifecycle.ViewModel

class DialogCallbackViewModel: ViewModel() {
    var callback: (() -> Unit)? = null
}