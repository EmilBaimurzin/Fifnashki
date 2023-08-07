package com.five.game.ui.main

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.five.game.R
import com.five.game.databinding.FragmentMainMenuBinding
import com.five.game.ui.other.ViewBindingFragment

class FragmentMainMenu : ViewBindingFragment<FragmentMainMenuBinding>(FragmentMainMenuBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPlay.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMainMenu_to_fragmentGameplay)
        }

        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}