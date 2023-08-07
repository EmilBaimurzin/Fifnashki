package com.five.game.ui.gameplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.five.game.R
import com.five.game.databinding.FragmentGameplayBinding
import com.five.game.domain.gameplay.GameplayAdapter
import com.five.game.ui.other.ViewBindingFragment

class FragmentGameplay :
    ViewBindingFragment<FragmentGameplayBinding>(FragmentGameplayBinding::inflate) {
    private val viewModel: GameplayViewModel by viewModels()
    private val callbackViewModel: DialogCallbackViewModel by activityViewModels()
    private lateinit var gameplayAdapter: GameplayAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.winCallback = {
            end()
        }

        callbackViewModel.callback = {
            viewModel.pauseState = false
            viewModel.startTimer()
        }

        binding.buttonPause.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentGameplay_to_dialogOnPause)
            viewModel.pauseState = true
            viewModel.stopTimer()
        }

        binding.buttonMenu.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMainMenu, false, false)
        }

        binding.buttonRestart.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMainMenu, false, false)
            findNavController().navigate(R.id.action_fragmentMainMenu_to_fragmentGameplay)
        }

        viewModel.timer.observe(viewLifecycleOwner) { totalSecs ->
            val hours = totalSecs / 3600;
            val minutes = (totalSecs % 3600) / 60;
            val seconds = totalSecs % 60;

            binding.timer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            gameplayAdapter.items = it.toMutableList()
            gameplayAdapter.notifyDataSetChanged()
        }

        if (viewModel.gameState && !viewModel.pauseState) {
            viewModel.startTimer()
        }
    }

    private fun end() {
        viewModel.stopTimer()
        viewModel.gameState = false
        findNavController().navigate(FragmentGameplayDirections.actionFragmentGameplayToDialogWin(viewModel.timer.value!!))
    }

    private fun initAdapter() {
        gameplayAdapter = GameplayAdapter { position, item ->
            viewModel.clickItem(item, position)
        }
        with(binding.gameRV) {
            adapter = gameplayAdapter
            layoutManager = GridLayoutManager(requireContext(), 4).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(false)
            itemAnimator = null
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }
}