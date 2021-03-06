package ru.mertsalovda.netlog.presentation.ui.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.mertsalovda.netlog.model.NetLogItem
import ru.mertsalovda.netlog.databinding.ItemNetlogBinding
import ru.mertsalovda.netlog.utils.format
import java.util.*


class ItemAdapter(private val onItemClick: ((NetLogItem) -> Unit)? = null) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = mutableListOf<NetLogItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemNetlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: MutableList<NetLogItem>) {
        val diffUtilCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] === data[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == data[newItemPosition]
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this@ItemAdapter)
    }

    class ItemViewHolder(
        private val binding: ItemNetlogBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NetLogItem, onItemClick: ((NetLogItem) -> Unit)?) {

            binding.container.setOnClickListener {
                onItemClick?.invoke(item)
            }

            binding.url.text = item.request?.url()?.toString()
            binding.method.text = item.request?.method()
            binding.type.text = item.response?.headers()?.get("content-Type")
            item.response?.let { setCodeColor(it.code()) }
            item.response?.let { setTimestamp(it.receivedResponseAtMillis()) }
            setDelayBetweenRequestAndResponse(item)
        }

        private fun setDelayBetweenRequestAndResponse(item: NetLogItem) {
            val sendTime = item.response?.sentRequestAtMillis() ?: 0
            val receivedTime = item.response?.receivedResponseAtMillis() ?: 0
            val differTime = (receivedTime - sendTime).toFloat() / 1000f
            binding.delay.text = String.format("%.2f", differTime) + " s"
        }

        private fun setTimestamp(millis: Long) {
            binding.timestamp.text = Date(millis).format("HH:mm")
        }

        private fun setCodeColor(code: Int) {
            val color =  if (code == 200) {
                ContextCompat.getColor(binding.root.context.applicationContext, android.R.color.holo_green_light)
            } else {
                ContextCompat.getColor(binding.root.context.applicationContext, android.R.color.holo_red_light)
            }
            binding.codeColor.setBackgroundColor(color)
        }
    }
}