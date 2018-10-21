package com.keytotech.pagingdemo.presentation.comments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.data.NetworkState
import com.keytotech.pagingdemo.data.models.Comment
import com.keytotech.pagingdemo.presentation.comments.list.CommentsAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_paging.*
import javax.inject.Inject

private const val START = "start"
private const val LIMIT = "limit"

class CommentsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var commentsViewModel: CommentsViewModel

    private var adapter = CommentsAdapter {
        commentsViewModel.retry()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        this.initUI()
        this.bindViewModel()
    }

    private fun initUI() {
        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = adapter
    }

    private fun bindViewModel() {
        this.commentsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CommentsViewModel::class.java)

        this.commentsViewModel.commentsList.observe(this, Observer<PagedList<Comment>> {
            adapter.submitList(it)
        })

        this.commentsViewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
        this.commentsViewModel.fetch()
    }

    companion object {
        fun start(context: Context, start: Int, limit: Int) {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra(START, start)
            intent.putExtra(LIMIT, limit)
            context.startActivity(intent)
        }
    }
}
