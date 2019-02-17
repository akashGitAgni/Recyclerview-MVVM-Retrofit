package com.akash.postsandcomments.posts

import com.akash.postsandcomments.data.Post

sealed class PostsState {
    object Loading : PostsState()
    data class Error(val message: String?) : PostsState()
    data class PostsLoaded(val posts: List<Post>) : PostsState()
}