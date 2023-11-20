package com.skit.mplex.ui.home.newest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.databinding.ItemLibrarySectionNewestBinding
import com.skit.mplex.ktx.loadPlexImg

class NewestAdapter(private val list: List<HubRecentlyAddedResponse.MediaContainer.Hub.Metadata>) :
    RecyclerView.Adapter<NewestAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLibrarySectionNewestBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLibrarySectionNewestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val directory = list[position]
        holder.binding.apply {
            ivImg.loadPlexImg(directory.thumb)
            tvTitle.text = directory.title
        }
    }
}