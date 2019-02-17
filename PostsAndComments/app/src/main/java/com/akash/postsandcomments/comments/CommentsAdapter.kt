package com.akash.postsandcomments.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.postsandcomments.R
import com.akash.postsandcomments.data.Comment
import kotlinx.android.synthetic.main.adapter_comments.view.*

class CommentsAdapter(private var listOfComments: List<Comment>) : RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_comments, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfComments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindView(listOfComments[position])
    }

    fun updateComments(comments: List<Comment>) {
        listOfComments = comments
        notifyDataSetChanged()
    }
}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(comment: Comment) {
        itemView.apply {
            name.text = comment.name
            commentBody.text = comment.body
            email.text = comment.email
        }
    }
}
