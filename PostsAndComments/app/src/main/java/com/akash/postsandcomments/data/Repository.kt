package com.akash.postsandcomments.data

import com.akash.postsandcomments.comments.CommentsState
import com.akash.postsandcomments.posts.PostsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryImpl(
    private val postApi: Api,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default
) : Repository {

    //Switched context to backgroundthread
    override suspend fun getPostsState(): PostsState = withContext(backgroundDispatcher) {
        val response = postApi.getPostsResponse()

        return@withContext if (response.isSuccessful) {
            response.body()?.let {
                PostsState.PostsLoaded(it)
            } ?: PostsState.Error("Empty Body")
        } else {
            Timber.d(response.message())
            PostsState.Error(response.message())
        }
    }

    override suspend fun getCommentsForPosts(postId: Int): CommentsState = withContext(backgroundDispatcher) {
        val response = postApi.getComments(postId)

        return@withContext if (response.isSuccessful) {
            response.body()?.let {
                CommentsState.PostsLoaded(it)
            } ?: CommentsState.Error("Empty Body")
        } else {
            Timber.d(response.message())
            CommentsState.Error(response.message())
        }
    }

}

interface Repository {
    suspend fun getPostsState(): PostsState
    suspend fun getCommentsForPosts(postId: Int): CommentsState
}