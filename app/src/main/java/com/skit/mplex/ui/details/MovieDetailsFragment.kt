package com.skit.mplex.ui.details

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
import com.skit.mplex.databinding.FragmentMovieDetailsBinding
import com.skit.mplex.ktx.loadPlexImg
import com.skit.mplex.ktx.toAlphaColor
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.net.plexUrl
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.ui.play.PlayerActivity
import kotlinx.coroutines.launch

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    companion object {
        fun newInstance(ratingKey: String): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
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
    ): FragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

    override fun initView() {
        mBinding.btPlay.postDelayed({
            mBinding.btPlay.requestFocusFromTouch()
        }, 200)
    }

    override fun initData() {
        super.initData()
        val plexLocalApi = HttpFactory.localRetrofit.create(PlexLocalApi::class.java)
        lifecycleScope.launch {
            val movieMetaData = plexLocalApi.getMovieMetaData(key)
            val metadataList = movieMetaData.mediaContainer.metadata
            if (metadataList.isNotEmpty()) {
                val metadata = metadataList[0]
                mBinding.tvTitle.text = metadata.title
                mBinding.tvSubTitle.text = metadata.originalTitle
                val minute = metadata.duration / (1000 * 60)
                val tags = metadata.genre.map { it.tag }.joinToString(",")
                mBinding.tvTime.text = "${minute}分钟 $tags ${metadata.year}"
                mBinding.tvSummary.text = metadata.summary
                mBinding.ivPoster?.loadPlexImg(metadata.thumb)
                mBinding.rating.rating = metadata.audienceRating.toFloat() / 2
                loadArtImage(metadata.art ?: metadata.thumb)
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
//                loadPlexImgBlur(artPath)
                load(artPath.plexUrl()) {
                    allowHardware(false)
                    listener(
                        onSuccess = { _, result ->
                            Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                                palette?.let { palette ->
                                    val bgMaskColor = resources.getColor(R.color.bg_mask)
                                    val darkVibrantColor = palette.getDarkVibrantColor(bgMaskColor)
                                    val lightVibrantColor =
                                        palette.getLightVibrantColor(Color.WHITE)
                                    val vibrantSwatch = palette.darkVibrantSwatch
                                    val bodyTextColor = vibrantSwatch?.bodyTextColor ?: Color.WHITE
                                    var titleTextColor =
                                        vibrantSwatch?.titleTextColor ?: Color.WHITE
                                    val calculateLuminance =
                                        ColorUtils.calculateLuminance(darkVibrantColor)
                                    val gray =
                                        (Color.red(darkVibrantColor) * 0.299 + Color.green(
                                            darkVibrantColor
                                        ) * 0.587 + Color.blue(
                                            darkVibrantColor
                                        ) * 0.114).toInt()

                                    titleTextColor = titleTextColor.toAlphaColor(0.7f)
//                                    if (gray <= 192) {
//                                        titleTextColor = Color.WHITE
//                                    } else {
//                                        titleTextColor = Color.BLACK
//                                    }
                                    mBinding.tvSummary.setTextColor(titleTextColor)
                                    mBinding.tvTitle.setTextColor(titleTextColor)
                                    mBinding.tvSubTitle.setTextColor(titleTextColor)
                                    mBinding.tvTime.setTextColor(titleTextColor)
                                    mBinding.rating.progressTintList =
                                        ColorStateList.valueOf(lightVibrantColor)
                                    val gradientDrawable = GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        intArrayOf(
                                            Color.TRANSPARENT,
                                            darkVibrantColor
                                        )
                                    )
                                    mBinding.llBottom?.background = gradientDrawable
                                }
                            }
                        }
                    )
                }
                tag = artPath
            }
        }
    }
}