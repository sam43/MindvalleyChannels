package com.sam43.mindvalleychannels.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sam43.mindvalleychannels.R

@SuppressLint("NotifyDataSetChanged")
class ChildAdapter : RecyclerView.Adapter<ChildAdapter.VH>() {

    private var items = listOf<String>()

    fun setItems(list: List<String>) {
        this.items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_child_data,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.tvInfo)

        init {
            view.setOnClickListener {
                it.isSelected = !it.isSelected
            }
        }

        fun bind(item: String) {
            textView.text = item
        }

    }
}
