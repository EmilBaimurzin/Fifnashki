package com.five.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.five.game.R
import com.five.game.core.library.ViewBindingDialog
import com.five.game.databinding.DialogWinBinding

class DialogWin: ViewBindingDialog<DialogWinBinding>(DialogWinBinding::inflate) {
    private val args: DialogWinArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack(R.id.fragmentMainMenu, false, false)
                true
            } else {
                false
            }
        }

        val hours = args.time / 3600
        val minutes = (args.time % 3600) / 60
        val seconds = args.time % 60

        binding.timer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        binding.closeButton.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMainMenu, false, false)
        }
        binding.restartButton.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMainMenu, false, false)
            findNavController().navigate(R.id.action_fragmentMainMenu_to_fragmentGameplay)
        }
    }
}