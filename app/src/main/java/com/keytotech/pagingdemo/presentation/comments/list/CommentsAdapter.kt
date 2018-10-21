package com.keytotech.pagingdemo.presentation.comments.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.di.viewModel.Status
import com.keytotech.pagingdemo.domain.entity.Comment

/**
 * CommentsAdapter
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsAdapter : PagedListAdapter<Comment, RecyclerView.ViewHolder>(COMMENTS_COMPARATOR) {

    private var loadingState: Status? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_comment -> CommentsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_comment,
                    parent,
                    false
                )
            )
            R.layout.item_loading -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_comment -> {
                getItem(position)?.let {
                    (holder as CommentsViewHolder).bind(it)
                }
            }
            R.layout.item_loading -> {
                (holder as LoadingViewHolder).bind(loadingState)
            }
        }
    }

    fun setLoadingState(loadingResource: Status?) {
        val previousState = this.loadingState
        val hadExtraRow = hasExtraRow()
        this.loadingState = loadingResource
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != loadingResource) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = loadingState != Status.SUCCESS

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_loading
        } else {
            R.layout.item_comment
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    companion object {

        val COMMENTS_COMPARATOR = object : DiffUtil.ItemCallback<Comment>() {
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: Comment, newItem: Comment): Any? {
                return null
            }
        }
    }
}

class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvId = itemView.findViewById<TextView>(R.id.tvId)
    private val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvBody = itemView.findViewById<TextView>(R.id.tvBody)

    fun bind(comment: Comment) {
        tvId.text = "${comment.id}"
        tvEmail.text = comment.email
        tvTitle.text = comment.name
        tvBody.text = comment.body
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)

    fun bind(loadingState: Status? = null) {
        val visibility: Int
        if (loadingState == Status.RUNNING) {
            visibility = View.VISIBLE
        } else {
            visibility = View.INVISIBLE
        }
        progressBar.visibility = visibility
    }
}