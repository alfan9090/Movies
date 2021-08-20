package com.ahmadalfan.movieapp.ui.tv

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmadalfan.movieapp.R
import com.ahmadalfan.movieapp.data.model.TvResult
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.databinding.NetworkStateItemBinding
import com.ahmadalfan.movieapp.databinding.UpcomingItemBinding
import com.ahmadalfan.movieapp.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class AdapterTv(
    var context: Context,

    ) : PagedListAdapter<TvResult, RecyclerView.ViewHolder>(TvDiffCallback()) {
    private val resViewType = 1
    private val networkViewType = 2
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == resViewType) {
            val binding =
                UpcomingItemBinding.inflate(LayoutInflater.from(context), parent, false)
            TvViewHolder(binding)
        } else {
            val binding =
                NetworkStateItemBinding.inflate(LayoutInflater.from(context), parent, false)
            NetworkStateItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == resViewType) {
            (holder as TvViewHolder).bind(getItem(position), context)

        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    class TvDiffCallback : DiffUtil.ItemCallback<TvResult>() {
        override fun areItemsTheSame(oldItem: TvResult, newItem: TvResult): Boolean {
            return oldItem.id == newItem.id

        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: TvResult, newItem: TvResult): Boolean {
            return oldItem == newItem
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    class TvViewHolder(val binding: UpcomingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(tvResult: TvResult?, context: Context) {
            binding.tvTitle.text = tvResult?.original_name
            binding.tvDesc.text = tvResult?.overview
            binding.tvVote.text = tvResult?.vote_average.toString()

            Glide.with(context)
                .load(tvResult!!.poster_path)
                .placeholder(R.drawable.ic_poster)
                .into(binding.ivPosterImage)

            binding.listItem.setOnClickListener {
                itemView
                println("oke")
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", tvResult.id)
                intent.putExtra("title", tvResult.original_name)
                intent.putExtra("overview", tvResult.overview)
                intent.putExtra("original_language", tvResult.original_language)
                 context.startActivity(intent)
            }

        }
    }

    class NetworkStateItemViewHolder(val binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                binding.progressBarItem.visibility = View.VISIBLE
            } else {
                binding.progressBarItem.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                binding.errorMsgItem.visibility = View.VISIBLE
                binding.errorMsgItem.text = networkState.message
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                binding.errorMsgItem.visibility = View.VISIBLE
                binding.errorMsgItem.text = networkState.message
            } else {
                binding.errorMsgItem.visibility = View.GONE
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
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

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            networkViewType
        } else {
            resViewType
        }
    }
}