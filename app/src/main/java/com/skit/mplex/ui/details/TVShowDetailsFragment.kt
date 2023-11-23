package com.skit.mplex.ui.details

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import coil.load
import com.skit.mplex.R
import com.skit.mplex.base.BaseFragment
import com.skit.mplex.bean.TVShowChildMetaDataResponse
import com.skit.mplex.databinding.FragmentTvShowDetailsBinding
import com.skit.mplex.ktx.loadPlexImg
import com.skit.mplex.ktx.toAlphaColor
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.net.plexUrl
import com.skit.mplex.net.plexUrlAddToken
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.ui.play.PlayerActivity
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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

    private var seasonListFragment: SeasonListFragment? = null

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTvShowDetailsBinding =
        FragmentTvShowDetailsBinding.inflate(inflater, container, false)

    override fun initView() {

    }

    override fun initData() {
        super.initData()
        initListFragment()
    }

    private fun initListFragment() {
        lifecycleScope.launch {
            val beginTransaction = childFragmentManager.beginTransaction()
            beginTransaction.add(
                mBinding.seasonListFragment.id,
                SeasonListFragment.newInstance(key).apply {
                    seasonListFragment = this
                    selectCallback = {
                        Log.d(TAG, "initData: $it")
                        getData(it)
                    }
                    itemClick = {
                        map[it]?.mediaContainer?.metadata?.let { metadata ->
                            if (metadata.isNotEmpty()) {
                                launchPlay(metadata[0])
                            }
                        }
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
                loadArtImage(metadata.art ?: "")
                mBinding.btPlay.setOnClickListener {
                    launchPlay(metadata)
                }
            }
        }
    }

    private fun launchPlay(metadata: TVShowChildMetaDataResponse.MediaContainer.Metadata) {
        PlayerActivity.launch(
            requireActivity(),
            metadata.media[0].part[0].key.plexUrl()
        )
    }


    private fun loadArtImage(artPath: String) {
        mBinding.ivBg.apply {
            if (tag != artPath) {
                load(artPath.plexUrlAddToken()) {
                    allowHardware(false)
                    listener(
                        onSuccess = { _, result ->
                            Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                                palette?.let { palette ->
                                    val detailsTextColor =
                                        resources.getColor(R.color.details_text_color)
                                    val bgMaskColor = resources.getColor(R.color.bg_mask)
                                    val lightVibrantColor = ColorUtils.setAlphaComponent(
                                        palette.getLightVibrantColor(detailsTextColor),
                                        (255 * 0.98).roundToInt()
                                    )
                                    val vibrantSwatch = palette.darkVibrantSwatch
                                    val darkVibrantColor =
                                        palette.getDarkVibrantColor(bgMaskColor)
                                    val alphaComponent =
                                        ColorUtils.setAlphaComponent(darkVibrantColor, 0xE6)

                                    val gradientDrawable = GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        intArrayOf(
                                            Color.TRANSPARENT,
                                            darkVibrantColor
                                        )
                                    )
                                    mBinding.ivBg.foreground =
                                        gradientDrawable // ColorDrawable(alphaComponent)
                                    val originalTitleColor =
                                        (vibrantSwatch?.titleTextColor ?: detailsTextColor)
                                    val textColor = originalTitleColor.toAlphaColor(0.8f)
                                    seasonListFragment?.setTextColor(textColor)

                                    mBinding.tvTitle.setTextColor(textColor)
                                    mBinding.tvSubTitle.setTextColor(textColor)
                                    mBinding.tvTime.setTextColor(textColor)
                                    mBinding.tvSummary.setTextColor(textColor)
                                    mBinding.rating.progressTintList =
                                        ColorStateList.valueOf(lightVibrantColor)
                                }
                            }
                        },
                        onError = { _, _ ->

                        }
                    )
                }
                tag = artPath
            }
        }
    }
}


