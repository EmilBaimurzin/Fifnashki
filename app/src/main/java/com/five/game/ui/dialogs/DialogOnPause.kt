package com.five.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.five.game.R
import com.five.game.core.library.ViewBindingDialog
import com.five.game.databinding.DialogOnPauseBinding
import com.five.game.ui.gameplay.DialogCallbackViewModel

class DialogOnPause: ViewBindingDialog<DialogOnPauseBinding>(DialogOnPauseBinding::inflate) {
    private val viewModel: DialogCallbackViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack()
                viewModel.callback?.invoke()
                true
            } else {
                false
            }
        }
        binding.play.setOnClickListener {
            findNavController().popBackStack()
            viewModel.callback?.invoke()
        }
    }
}