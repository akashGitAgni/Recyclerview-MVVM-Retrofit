package com.akash.postsandcomments.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("posts")
    suspend fun getPostsResponse(): Response<List<Post>>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<Comment>>
}

