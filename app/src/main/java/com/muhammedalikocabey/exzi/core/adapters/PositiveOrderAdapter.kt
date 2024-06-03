package com.muhammedalikocabey.exzi.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedalikocabey.exzi.data.model.OrderBookEntry
import com.muhammedalikocabey.exzi.databinding.ItemPositiveOrderBinding

class PositiveOrderAdapter : ListAdapter<OrderBookEntry.ViewEntity, PositiveOrderAdapter.OrderViewHolder>(
    OrderDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemPositiveOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class OrderViewHolder(private val binding: ItemPositiveOrderBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(order: OrderBookEntry.ViewEntity) {
            binding.order = order

            binding.quantityTextView.text = order.volumeF
            binding.priceTextView.text = order.rateF

            binding.executePendingBindings()
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<OrderBookEntry.ViewEntity>() {
        override fun areItemsTheSame(
            oldItem: OrderBookEntry.ViewEntity,
            newItem: OrderBookEntry.ViewEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(
            oldItem: OrderBookEntry.ViewEntity,
            newItem: OrderBookEntry.ViewEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
