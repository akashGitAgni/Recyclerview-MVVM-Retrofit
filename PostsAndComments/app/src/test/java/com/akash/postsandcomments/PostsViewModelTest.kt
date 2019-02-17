package com.akash.postsandcomments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.akash.postsandcomments.posts.PostsState
import com.akash.postsandcomments.posts.PostsViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.test.KoinTest
import timber.log.Timber

class PostsViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var mockObserver: Observer<PostsState>

    private lateinit var postsViewModel: PostsViewModel

    @Before
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)
        startKoin(listOf(dataSourceModule, viewmodelModule))
        postsViewModel = get()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun testgetPostsData() =
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