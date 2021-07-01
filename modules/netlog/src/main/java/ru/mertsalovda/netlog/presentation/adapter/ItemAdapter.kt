package ru.mertsalovda.netlog.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mertsalovda.netlog.NetLogItem
import ru.mertsalovda.netlog.databinding.FragmentNetlogDialogListDialogItemBinding


class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = mutableListOf<NetLogItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FragmentNetlogDialogListDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: MutableList<NetLogItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val binding: FragmentNetlogDialogListDialogItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetLogItem) {
            binding.url.text = item.request.url.toString()
        }
    }
}