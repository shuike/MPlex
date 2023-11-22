package com.skit.mplex.ui.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.bean.TvShowSeasonChildrenResponse
import com.skit.mplex.databinding.ItemSeasonListBinding
import com.skit.mplex.ktx.loadPlexImg

class SeasonListAdapter(val list: List<TvShowSeasonChildrenResponse.MediaContainer.Metadata>) :
    RecyclerView.Adapter<SeasonListAdapter.ViewHolder>() {

    private var color: Int = Color.WHITE

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

    var selectCallback: (TvShowSeasonChildrenResponse.MediaContainer.Metadata) -> Unit = {}
    var itemClick: (TvShowSeasonChildrenResponse.MediaContainer.Metadata) -> Unit = {}

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val metadata = list[position]
        holder.binding.apply {
            ivImg.loadPlexImg(metadata.thumb)
            tvTitle.text = "${metadata.index}.${metadata.title}"
            tvSummary.text = metadata.summary
            tvSummary.setTextColor(color)
            tvTitle.setTextColor(color)
            cardView.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    selectCallback(metadata)
                }
            }
            cardView.setOnClickListener {
                if (!(it.parent as View).isFocused) {
                    it.requestFocusFromTouch()
                }
                itemClick(metadata)
            }
        }
    }

    fun setTextColor(color: Int) {
        this.color = color
        notifyDataSetChanged()
    }
}