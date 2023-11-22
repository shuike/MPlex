package com.skit.mplex.ui.home.recentyl

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.databinding.ItemLibrarySectionNewestBinding
import com.skit.mplex.ktx.loadPlexImg
import com.skit.mplex.net.HttpConstant
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.ui.details.DetailsActivity
import com.skit.mplex.ui.play.PlayerActivity

class HubRecentlyAddedAdapter(private val list: List<HubRecentlyAddedResponse.MediaContainer.Hub.Metadata>) :
    RecyclerView.Adapter<HubRecentlyAddedAdapter.ViewHolder>() {
    var focusedCallback: (String) -> Unit = {}

    companion object {
        private const val TAG = "RecentlyAddedAdapter"
    }

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
            cardView.setOnClickListener {
                when (directory.type) {
                    "season" -> {
                        DetailsActivity.launchTVShow(it.context, directory.parentRatingKey)
                    }

                    "episode" -> {
                        DetailsActivity.launchTVShow(it.context, directory.grandparentRatingKey)
                    }

                    "show" -> {
                        DetailsActivity.launchTVShow(it.context, directory.ratingKey)
                    }

                    "movie" -> {
                        DetailsActivity.launchMovie(it.context, directory.ratingKey)
                    }

                    else -> {
                    }
                }

//                DetailsActivity.launchMovie(it.context)
//                val media = directory.media
//                if (!media.isNullOrEmpty()) {
//                    PlayerActivity.launch(
//                        it.context,
//                        HttpFactory.HOST + media[0].part[0].key + HttpConstant.URL_END_PLEX_TOKEN
//                    )
//                    Log.d(TAG, "onBindViewHolder: ${Gson().toJson(media)}")
//                }
            }
            var thumb_url = directory.thumb
            when (directory.type) {
                "season" -> { // 季-可能是电视剧的某一季
                    tvTitle.text = directory.parentTitle
                    tvSubTitle.text = "季 ${directory.index}"
                    thumb_url = directory.parentThumb
                }

                "episode" -> { // 剧集-可能是电视剧的某一集
                    tvTitle.text = directory.grandparentTitle
                    tvSubTitle.text = "第${directory.parentIndex}季·第${directory.index}集"
                    thumb_url = directory.grandparentThumb
                }

                "show" -> { // 电视剧
                    tvTitle.text = directory.title
                    tvSubTitle.text = "共 ${directory.childCount} 季"
                    thumb_url = directory.thumb
                }

                "movie" -> { // 电影
                    tvTitle.text = directory.title
                    tvSubTitle.text = directory.year.toString()
                    thumb_url = directory.thumb
                }
            }
            ivImg.loadPlexImg(thumb_url)
            val art = directory.art ?: ""
            cardView.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    focusedCallback(art.ifEmpty { thumb_url })
                }
            }
        }
    }
}