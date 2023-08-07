package com.five.game.domain.gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.five.game.R
import com.five.game.databinding.ItemGameplayBinding

class GameplayAdapter(private val clickListener: (Int, GameItem) -> Unit): RecyclerView.Adapter<GameplayViewHolder>() {
    var items = mutableListOf<GameItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameplayViewHolder {
        return GameplayViewHolder(ItemGameplayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameplayViewHolder, position: Int) {
        holder.bind(items[position])
        holder.clickListener = clickListener
    }
}

class GameplayViewHolder(private val binding: ItemGameplayBinding): RecyclerView.ViewHolder(binding.root) {
    var clickListener: ((Int, GameItem) -> Unit)? = null
    fun bind(item: GameItem) {
        binding.apply {
            if (item.isEmpty) {
                imgSymbol.setImageDrawable(null)
                root.background = null
                number.isVisible = false
                selection.isVisible = false
            } else {
                val img = when (item.number) {
                    1 -> R.drawable.symbol_01
                    2 -> R.drawable.symbol_02
                    3 -> R.drawable.symbol_03
                    4 -> R.drawable.symbol_04
                    5 -> R.drawable.symbol_05
                    6 -> R.drawable.symbol_06
                    7 -> R.drawable.symbol_07
                    8 -> R.drawable.symbol_08
                    9 -> R.drawable.symbol_09
                    10 -> R.drawable.symbol_10
                    11 -> R.drawable.symbol_11
                    12-> R.drawable.symbol_12
                    13 -> R.drawable.symbol_13
                    14 -> R.drawable.symbol_14
                    else -> R.drawable.symbol_15
                }
                imgSymbol.setImageResource(img)
                root.setBackgroundResource(R.drawable.box)
                number.isVisible = true
                number.text = item.number.toString()
                selection.isVisible = item.isSelected
            }
            binding.root.setOnClickListener {
                clickListener?.invoke(adapterPosition, item)
            }
        }
    }
}