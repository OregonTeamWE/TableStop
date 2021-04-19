package com.example.tableStop.socialView

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tableStop.R
import com.example.tableStop.viewModel.BlogPost
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class SocialRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<BlogPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_blog_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder -> {
                holder.bind(items[position])
            }
        }
        holder.itemView.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(items[position].url))
            Log.d("Opening intent", items[position].url)
            holder.itemView.context.startActivity(webIntent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<BlogPost>) {
        items = blogList
    }

    class BlogViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val blogImage: ImageView = itemView.blog_image
        private val blogTitle: TextView = itemView.blog_title
        private val blogAuthor: TextView = itemView.blog_author

        fun bind(blogPost: BlogPost) {

            blogTitle.text = blogPost.title
            blogAuthor.text = blogPost.username

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(blogPost.image)
                .into(blogImage)
        }
    }
}
