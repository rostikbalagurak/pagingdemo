package com.keytotech.pagingdemo.presentation.comments.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.di.viewModel.Status
import com.keytotech.pagingdemo.domain.entity.Comment

/**
 * CommentsAdapter
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsAdapter : PagedListAdapter<Comment, CommentsViewHolder>(COMMENTS_COMPARATOR) {

    private var networkResource: NetworkResource<*>? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_comment,
            parent,
            false
        )
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int, payloads: MutableList<Any>) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setNetworkState(newNetworkResource: NetworkResource<*>?) {
        val previousState = this.networkResource
        val hadExtraRow = hasExtraRow()
        this.networkResource = newNetworkResource
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkResource) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkResource?.status != Status.SUCCESS

    companion object {

        val COMMENTS_COMPARATOR = object : DiffUtil.ItemCallback<Comment>() {
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.name == newItem.name

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