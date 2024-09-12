package com.sample.zap.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.zap.R
import com.sample.zap.domain.model.Item

class HorizontalItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<HorizontalItemAdapter.HorizontalItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return HorizontalItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HorizontalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.productImage)
        private val textView: TextView = itemView.findViewById(R.id.productName)

        fun bind(item: Item) {
            textView.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageView)
        }
    }
}
