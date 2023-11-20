package com.skit.mplex.ui.details

import android.content.Context
import android.view.LayoutInflater
import com.skit.mplex.base.BaseActivity
import com.skit.mplex.databinding.ActivityDetailsBinding
import com.skit.mplex.ktx.launch

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {
    companion object {
        private const val UN_KNOWN = -1
        private const val MOVIE = 0
        private const val TV_SHOW = 1

        private const val KEY_ID = "RING_KEY_ID"
        private const val KEY_TYPE = "KEY_TYPE"

        fun launchTVShow(context: Context, movieRingKey: String) {
            context.launch<DetailsActivity> {
                putExtra(KEY_ID, movieRingKey)
                putExtra(KEY_TYPE, TV_SHOW)
            }
        }

        fun launchMovie(context: Context, tvRingKey: String) {
            context.launch<DetailsActivity> {
                putExtra(KEY_ID, tvRingKey)
                putExtra(KEY_TYPE, MOVIE)
            }
        }
    }

    private val ringKey: String by lazy { intent.getStringExtra(KEY_ID) ?: error("没有传递ID") }
    private val type: Int by lazy { intent.getIntExtra(KEY_TYPE, UN_KNOWN) }

    override fun initView() {

    }

    override fun initData() {
        super.initData()
        when (type) {
            TV_SHOW -> {
                val beginTransaction = supportFragmentManager.beginTransaction()
                beginTransaction.add(
                    mBinding.fragmentContainer.id,
                    TVShowDetailsFragment.newInstance(ringKey)
                ).commit()
            }

            MOVIE -> {
                val beginTransaction = supportFragmentManager.beginTransaction()
                beginTransaction.add(
                    mBinding.fragmentContainer.id,
                    MovieDetailsFragment.newInstance(ringKey)
                ).commit()
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater): ActivityDetailsBinding =
        ActivityDetailsBinding.inflate(inflater)
}