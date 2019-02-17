package com.akash.postsandcomments.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.postsandcomments.R
import com.akash.postsandcomments.data.Post
import kotlinx.android.synthetic.main.adapter_posts.view.*

class PostAdapter(private var listOfPosts: List<Post>, private val postClickListener: OnPostClickListener) :
    RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfPosts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = listOfPosts[position]
        holder.bindView(post)
        holder.itemView.setOnClickListener {
            postClickListener.postClicked(post)
        }
    }

    fun updatePosts(posts: List<Post>) {
        listOfPosts = posts
        notifyDataSetChanged()
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(post: Post) {
        itemView.apply {
            postTitle.text = post.title
            postBody.text = post.body
        }
    }
}

interface OnPostClickListener {
    fun postClicked(post: Post)
}
