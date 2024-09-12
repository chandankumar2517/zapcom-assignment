package com.sample.zap.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sample.zap.R
import com.sample.zap.domain.model.Item

class SplitBannerItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<SplitBannerItemAdapter.HorizontalItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_split_banner, parent, false)
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
        private val imageView: ImageView = itemView.findViewById(R.id.splitImage)
        private val textView: TextView = itemView.findViewById(R.id.splitProductName)

        private val progressBar: ProgressBar = itemView.findViewById(R.id.loading)

        fun bind(item: Item) {
            textView.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.ic_placeholder)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Hide progress bar when image is loaded
                        progressBar.visibility = View.GONE
                        return false // Pass false to allow Glide to handle the image rendering
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Hide progress bar when image load fails
                        progressBar.visibility = View.GONE
                        return false // Pass false to allow Glide to handle the error
                    }


                })
                .into(imageView)

            // Show the ProgressBar before loading the image
            progressBar.visibility = View.VISIBLE
        }
    }
}
