package com.skit.mplex.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.bean.TvShowSeasonChildrenResponse
import com.skit.mplex.databinding.ItemSeasonListBinding
import com.skit.mplex.ktx.loadPlexImg

class SeasonListAdapter(val list: List<TvShowSeasonChildrenResponse.MediaContainer.Metadata>) :
    RecyclerView.Adapter<SeasonListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemSeasonListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSeasonListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var selectCallback: (String) -> Unit = {}

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val response = list[position]
        val mediaContainer = response
        holder.binding.apply {
            ivImg.loadPlexImg(mediaContainer.thumb)
            tvTitle.text = "${mediaContainer.index}.${mediaContainer.title}"
            cardView.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    selectCallback(response.ratingKey)
                }
            }
            cardView.setOnClickListener {
                if (!it.isFocused) {
                    it.requestFocusFromTouch()
                }
            }
        }
    }
}