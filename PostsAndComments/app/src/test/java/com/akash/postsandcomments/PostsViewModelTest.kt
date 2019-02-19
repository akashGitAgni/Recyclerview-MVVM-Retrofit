package com.akash.postsandcomments

import androidx.lifecycle.Observer
import com.akash.postsandcomments.posts.PostsState
import com.akash.postsandcomments.posts.PostsViewModel
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
import timber.log.Timber

class PostsViewModelTest : BaseTestClass() {

    private lateinit var postsViewModel: PostsViewModel
    @RelaxedMockK
    lateinit var mockObserver: Observer<PostsState>


    @Before
    override fun before() {
        super.before()
        postsViewModel = get()
    }

    @Test
    fun testGetPostsDataIsSuccessful() =
        runBlocking<Unit> {
            postsViewModel.postLiveData.observeForever(mockObserver)
            assert(postsViewModel.postLiveData.value == null)
            postsViewModel.refreshPosts().join()
            val value = postsViewModel.postLiveData.value
            println("called..... suspend function")

            assert(value != null)
            when (value) {
                is PostsState.PostsLoaded -> {
                    assert(value.posts.size >= 0)
                }
                is PostsState.Error -> {
                    Timber.e(value.message)
                    assert(false)
                }
            }

            assert((value as PostsState.PostsLoaded).posts.size >= 0)
        }


}