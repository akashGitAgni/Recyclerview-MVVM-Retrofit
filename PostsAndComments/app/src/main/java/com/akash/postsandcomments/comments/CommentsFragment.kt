package com.akash.postsandcomments.comments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.postsandcomments.R
import com.akash.postsandcomments.utils.MainThreadScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_comments.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CommentsFragment : Fragment() {
    private lateinit var commentsViewModel: CommentsViewModel
    private val uiScope = MainThreadScope()

    private val args: CommentsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(uiScope)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.comments)
        }

        val commentsAdapter = CommentsAdapter(emptyList())

        commentsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = commentsAdapter
        }

        commentsViewModel = getViewModel()
        commentsViewModel.commentsLiveData.observe(this, Observer { commentState ->
            if (commentState == null) {
                return@Observer
            }

            when (commentState) {
                is CommentsState.Loading -> {
                    setUpdateLayoutVisibilty(View.VISIBLE)
                }

                is CommentsState.Error -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    context?.let {
                        val message = commentState.message ?: getString(R.string.error)
                        Snackbar.make(activity!!.rootLayout, message, Snackbar.LENGTH_LONG).show()
                    }
                }

                is CommentsState.PostsLoaded -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    commentsAdapter.updateComments(commentState.comments)
                }
            }
        })

        commentsViewModel.getComments(args.postId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity!!.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
