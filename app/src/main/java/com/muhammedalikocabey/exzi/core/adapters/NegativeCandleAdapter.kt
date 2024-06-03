package com.muhammedalikocabey.exzi.core.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedalikocabey.exzi.core.utils.toFormattedVolumeF
import com.muhammedalikocabey.exzi.core.utils.toOpenCloseCurrencyFormat
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.databinding.ItemNegativeCandleBinding

class NegativeCandleAdapter : ListAdapter<CandleResponseModel.ViewEntity, NegativeCandleAdapter.CandleViewHolder>(
    CandleDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandleViewHolder {
        val binding = ItemNegativeCandleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CandleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CandleViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int = currentList.size

    inner class CandleViewHolder(private val binding: ItemNegativeCandleBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(candle: CandleResponseModel.ViewEntity, position: Int) {
            binding.candle = candle

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(0x0E111B.toInt(), 0x26F65454.toInt())
            )
            gradientDrawable.cornerRadius = 0f

            binding.quantityTextView.text = candle.volumeF.toFormattedVolumeF()
            binding.priceTextView.text = candle.low.toOpenCloseCurrencyFormat()

            val quantityFraction = calculateInverseScaledValue(position + 1, itemCount)
            val layoutParams =
                binding.quantityTextView.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.matchConstraintPercentWidth = quantityFraction.toFloat() / 100
            binding.quantityTextView.layoutParams = layoutParams

            binding.quantityTextView.background = gradientDrawable

            binding.executePendingBindings()
        }
    }

    class CandleDiffCallback : DiffUtil.ItemCallback<CandleResponseModel.ViewEntity>() {
        override fun areItemsTheSame(
            oldItem: CandleResponseModel.ViewEntity,
            newItem: CandleResponseModel.ViewEntity
        ): Boolean {
            return oldItem.time == newItem.time && oldItem.volume == newItem.volume
        }

        override fun areContentsTheSame(
            oldItem: CandleResponseModel.ViewEntity,
            newItem: CandleResponseModel.ViewEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun calculateInverseScaledValue(currentStep: Int, totalSteps: Int): Double {
        val minValue = 0
        val maxValue = 100

        return maxValue * (totalSteps - currentStep).toDouble() / (totalSteps - 1)
    }
}
