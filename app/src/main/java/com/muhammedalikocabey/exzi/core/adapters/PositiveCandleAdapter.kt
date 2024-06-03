package com.muhammedalikocabey.exzi.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedalikocabey.exzi.core.utils.toFormattedVolumeF
import com.muhammedalikocabey.exzi.core.utils.toOpenCloseCurrencyFormat
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.databinding.ItemPositiveCandleBinding

class PositiveCandleAdapter : ListAdapter<CandleResponseModel.ViewEntity, PositiveCandleAdapter.CandleViewHolder>(
    CandleDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandleViewHolder {
        val binding = ItemPositiveCandleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CandleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CandleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class CandleViewHolder(private val binding: ItemPositiveCandleBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(candle: CandleResponseModel.ViewEntity) {
            binding.candle = candle

            binding.quantityTextView.text = candle.volumeF.toFormattedVolumeF()
            binding.priceTextView.text = candle.high.toOpenCloseCurrencyFormat()

            binding.executePendingBindings()
        }
    }

    class CandleDiffCallback : DiffUtil.ItemCallback<CandleResponseModel.ViewEntity>() {
        override fun areItemsTheSame(
            oldItem: CandleResponseModel.ViewEntity,
            newItem: CandleResponseModel.ViewEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(
            oldItem: CandleResponseModel.ViewEntity,
            newItem: CandleResponseModel.ViewEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
