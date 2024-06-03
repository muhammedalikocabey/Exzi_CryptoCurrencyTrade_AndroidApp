package com.muhammedalikocabey.exzi.core.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedalikocabey.exzi.data.model.OrderBookEntry
import com.muhammedalikocabey.exzi.databinding.ItemNegativeOrderBinding

class NegativeOrderAdapter : ListAdapter<OrderBookEntry.ViewEntity, NegativeOrderAdapter.OrderViewHolder>(
    OrderDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemNegativeOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int = currentList.size

    inner class OrderViewHolder(private val binding: ItemNegativeOrderBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(order: OrderBookEntry.ViewEntity, position: Int) {
            binding.order = order

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(0x0E111B.toInt(), 0x26F65454.toInt())
            )
            gradientDrawable.cornerRadius = 0f

            binding.quantityTextView.text = order.volumeF
            binding.priceTextView.text = order.rateF

            val quantityFraction = calculateInverseScaledValue(position + 1, itemCount)
            val layoutParams =
                binding.quantityTextView.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.matchConstraintPercentWidth = quantityFraction.toFloat() / 100
            binding.quantityTextView.layoutParams = layoutParams

            binding.quantityTextView.background = gradientDrawable

            binding.executePendingBindings()
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<OrderBookEntry.ViewEntity>() {
        override fun areItemsTheSame(
            oldItem: OrderBookEntry.ViewEntity,
            newItem: OrderBookEntry.ViewEntity
        ): Boolean {
            return oldItem.rate == newItem.rate && oldItem.volume == newItem.volume
        }

        override fun areContentsTheSame(
            oldItem: OrderBookEntry.ViewEntity,
            newItem: OrderBookEntry.ViewEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun calculateInverseScaledValue(currentStep: Int, totalSteps: Int): Double {
        val minValue = 0
        val maxValue = 100

        return maxValue - (currentStep - 1) * (maxValue - minValue).toDouble() / (totalSteps - 1)
    }
}
