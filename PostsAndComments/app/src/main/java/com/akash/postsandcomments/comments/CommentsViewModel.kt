package com.akash.postsandcomments.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.postsandcomments.Utils.CouroutineViewModel
import com.akash.postsandcomments.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class CommentsViewModel(private val repository: Repository, uiContext: CoroutineContext = Dispatchers.Main) :
    CouroutineViewModel(uiContext) {

    private val privateState = MutableLiveData<CommentsState>()

    val commentsLiveData: LiveData<CommentsState> = privateState

    fun getComments(id: Int) = launch {
        privateState.value = CommentsState.Loading
        Timber.d("getComments() called....")
        privateState.value = repository.getCommentsForPosts(id)
        Timber.d("repository.getComments() executed....${privateState.value}")
    }

}