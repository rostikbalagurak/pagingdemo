package com.keytotech.pagingdemo.presentation.comments.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.keytotech.pagingdemo.R
import com.keytotech.pagingdemo.di.viewModel.NetworkState
import com.keytotech.pagingdemo.domain.entity.CommentEntity

/**
 * CommentsAdapter
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<CommentEntity, CommentsViewHolder>(COMMENTS_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_comment,
            parent,
            false
        )
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CommentsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        if (payloads.isNotEmpty()) {
//            getItem(position)?.let {
//                holder.bind(it)
//            }
//        } else {
//            //TODO
//        }
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


//    override fun getItemCount(): Int {
//        return super.getItemCount() + if (hasExtraRow()) 1 else 0
//    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    companion object {

        val COMMENTS_COMPARATOR = object : DiffUtil.ItemCallback<CommentEntity>() {
            override fun areContentsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean =
                oldItem.name == newItem.name

            override fun getChangePayload(oldItem: CommentEntity, newItem: CommentEntity): Any? {
                return null
            }
        }
    }
}

class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvBody = itemView.findViewById<TextView>(R.id.tvBody)

    fun bind(comment: CommentEntity) {
        tvEmail.text = comment.email
        tvTitle.text = comment.name
        tvBody.text = comment.body
    }
}