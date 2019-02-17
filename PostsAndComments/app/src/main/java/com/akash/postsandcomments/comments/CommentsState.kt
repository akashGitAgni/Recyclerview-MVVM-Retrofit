package com.akash.postsandcomments.comments

import com.akash.postsandcomments.data.Comment
import com.akash.postsandcomments.data.Post

sealed class CommentsState {
    object Loading : CommentsState()
    data class Error(val message: String?) : CommentsState()
    data class PostsLoaded(val comments: List<Comment>) : CommentsState()
}