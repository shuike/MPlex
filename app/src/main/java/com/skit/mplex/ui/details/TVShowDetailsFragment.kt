package com.skit.mplex.ui.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.skit.mplex.base.BaseFragment
import com.skit.mplex.bean.TVShowChildMetaDataResponse
import com.skit.mplex.databinding.FragmentTvShowDetailsBinding
import com.skit.mplex.ktx.loadPlexImg
import com.skit.mplex.ktx.loadPlexImgBlur
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.net.plexUrl
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.ui.play.PlayerActivity
import kotlinx.coroutines.launch

class TVShowDetailsFragment : BaseFragment<FragmentTvShowDetailsBinding>() {
    companion object {
        private const val TAG = "TVShowDetailsFragment"
        fun newInstance(ratingKey: String): TVShowDetailsFragment {
            val fragment = TVShowDetailsFragment()
            fragment.arguments = bundleOf(
                "key" to ratingKey
            )
            return fragment
        }
    }

    private val key: String by lazy { arguments?.getString("key") ?: error("error") }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTvShowDetailsBinding =
        FragmentTvShowDetailsBinding.inflate(inflater, container, false)

    override fun initView() {

    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            val beginTransaction = childFragmentManager.beginTransaction()
            beginTransaction.add(
                mBinding.seasonListFragment.id,
                SeasonListFragment.newInstance(key).apply {
                    selectCallback = {
                        Log.d(TAG, "initData: $it")
                        getData(it)
                    }
                }
            ).commit()
        }
    }

    private val map: HashMap<String, TVShowChildMetaDataResponse> = HashMap()
    private val plexLocalApi: PlexLocalApi by lazy { HttpFactory.localRetrofit.create(PlexLocalApi::class.java) }
    private fun getData(key: String) {
        lifecycleScope.launch {
            val movieMetaData = map[key] ?: plexLocalApi.getTvShowChildMetaData(key).also {
                map[key] = it
            }
            val metadataList = movieMetaData.mediaContainer.metadata
            if (metadataList.isNotEmpty()) {
                val metadata = metadataList[0]
                mBinding.tvTitle.text =
                    "第${metadata.parentIndex}季 第${metadata.index}集 ${metadata.title}"
                mBinding.tvSubTitle.text = metadata.originalTitle
                val minute = metadata.duration / (1000 * 60)
                val tags = ""// metadata.genre.map { it.tag }.joinToString(",")
                mBinding.tvTime.text = "${minute}分钟 $tags ${metadata.year}"
                mBinding.tvSummary.text = metadata.summary
                mBinding.ivPoster.loadPlexImg(metadata.thumb)
                mBinding.rating.rating = metadata.audienceRating.toFloat() / 2
                loadArtImage(metadata.art)
                mBinding.btPlay.setOnClickListener {
                    PlayerActivity.launch(
                        requireActivity(),
                        metadata.media[0].part[0].key.plexUrl()
                    )
                }
            }
        }
    }


    private fun loadArtImage(artPath: String) {
        mBinding.ivBg.apply {
            if (tag != artPath) {
                loadPlexImgBlur(artPath)
                tag = artPath
            }
        }
    }
}