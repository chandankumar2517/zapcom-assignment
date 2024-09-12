package com.sample.zap.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sample.zap.R
import com.sample.zap.domain.model.Item
import com.sample.zap.domain.model.ProductListEntity

class SectionAdapter(private val sections: List<ProductListEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val BANNER = 0
        const val HORIZONTAL_SCROLL = 1
        const val SPLIT_BANNER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (sections[position].sectionType) {
            "banner" -> BANNER
            "horizontalFreeScroll" -> HORIZONTAL_SCROLL
            "splitBanner" -> SPLIT_BANNER
            else -> throw IllegalArgumentException("Invalid section type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BANNER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
                BannerViewHolder(view)
            }
            HORIZONTAL_SCROLL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_scroll, parent, false)
                HorizontalScrollViewHolder(view)
            }
            SPLIT_BANNER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_split_banner, parent, false)
                SplitBannerViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = sections[position]
        when (holder) {
            is BannerViewHolder -> {
                val item = section.items.firstOrNull()
                if (item != null) {
                    holder.bind(item)
                }
            }
            is HorizontalScrollViewHolder -> {
                holder.bind(section.items)
            }
            is SplitBannerViewHolder -> {
                holder.bind(section.items)
            }
        }
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    // ViewHolder for Banner
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.bannerImage)
        private val bannerName: TextView = itemView.findViewById(R.id.bannerName)

        fun bind(item: Item) {
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageView)

            bannerName.text = item.title
        }
    }

    // ViewHolder for Horizontal Free Scroll
    class HorizontalScrollViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.horizontalRecyclerView)

        fun bind(items: List<Item>) {
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = HorizontalItemAdapter(items)
        }
    }

    // ViewHolder for Split Banner
    class SplitBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView1: ImageView = itemView.findViewById(R.id.splitImage1)
        private val imageView2: ImageView = itemView.findViewById(R.id.splitImage2)

        fun bind(items: List<Item>) {
            Glide.with(itemView.context).load(items[0].image).into(imageView1)
            Glide.with(itemView.context).load(items[1].image).into(imageView2)
        }
    }
}
