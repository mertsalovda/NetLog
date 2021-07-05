package ru.mertsalovda.netlog.presentation.ui.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mertsalovda.netlog.interceptor.NetLogItem
import ru.mertsalovda.netlog.databinding.FragmentNetlogDialogListDialogItemBinding
import ru.mertsalovda.netlog.utils.format
import java.util.*
import kotlin.coroutines.CoroutineContext


class ItemAdapter(private val onItemClick: ((NetLogItem) -> Unit)? = null) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = mutableListOf<NetLogItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FragmentNetlogDialogListDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items[position]?.let { holder.bind(it, onItemClick) }
    }

    override fun getItemCount(): Int = items.size

    fun setData(scope: LifecycleCoroutineScope, data: MutableList<NetLogItem>) {
        scope.launch(Dispatchers.Main) {
            notifyChanges(this.coroutineContext, data)
        }
    }

    private suspend fun notifyChanges(coroutineContext: CoroutineContext, data: MutableList<NetLogItem>) {
        withContext(coroutineContext) {
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
    }

    class ItemViewHolder(
        private val binding: FragmentNetlogDialogListDialogItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NetLogItem, onItemClick: ((NetLogItem) -> Unit)?) {

            binding.container.setOnClickListener {
                onItemClick?.invoke(item)
            }

            binding.url.text = item.request.url().toString()
            binding.method.text = item.request.method()
            binding.type.text = item.response.headers()["content-Type"]
            setCodeColor(item.response.code())
            setTimestamp(item.response.receivedResponseAtMillis())
            setDelayBetweenRequestAndResponse(item)
        }

        private fun setDelayBetweenRequestAndResponse(item: NetLogItem) {
            val sendTime = item.response.sentRequestAtMillis()
            val receivedTime = item.response.receivedResponseAtMillis()
            val differTime = (receivedTime - sendTime).toFloat() / 1000f
            binding.delay.text = String.format("%.2f", differTime)

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