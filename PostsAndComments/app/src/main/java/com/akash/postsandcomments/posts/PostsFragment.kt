package com.akash.postsandcomments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.postsandcomments.R
import com.akash.postsandcomments.utils.MainThreadScope
import com.akash.postsandcomments.data.Post
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class PostsFragment : Fragment(),OnPostClickListener {


    private val uiScope = MainThreadScope()
    private var postsViewModel: PostsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(uiScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val postAdapter = PostAdapter(emptyList(),this)
        postsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = postAdapter
        }

        postsViewModel = getViewModel<PostsViewModel>()
        postsViewModel?.postLiveData?.observe(this, Observer { postState ->
            if (postState == null) {
                return@Observer
            }

            when (postState) {
                is PostsState.Loading -> {
                    setUpdateLayoutVisibilty(View.VISIBLE)
                }

                is PostsState.Error -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    context?.let {
                        val message = postState.message ?: getString(R.string.error)
                        Snackbar.make(activity!!.rootLayout, message, Snackbar.LENGTH_LONG).show()
                    }
                }

                is PostsState.PostsLoaded -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    postAdapter.updatePosts(postState.posts)
                }
            }
        })

        postsViewModel?.refreshPosts()
    }

    override fun postClicked(post: Post) {
        findNavController().navigate(PostsFragmentDirections.actionPostsFragmentToCommentsFragment(post.id))
    }

    fun setUpdateLayoutVisibilty(value: Int) {
        activity!!.updateLayout.apply {
            visibility = value
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
