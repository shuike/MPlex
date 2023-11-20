package com.skit.mplex.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.skit.mplex.R
import com.skit.mplex.base.BaseFragment
import com.skit.mplex.bean.TvShowSeasonChildrenResponse
import com.skit.mplex.databinding.FragmentSeasonListBinding
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.ui.details.adapter.SeasonListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeasonListFragment : BaseFragment<FragmentSeasonListBinding>() {
    companion object {
        private const val TAG = "SeasonListFragment"
        fun newInstance(id: String): SeasonListFragment {
            val args = Bundle()
            args.putString("id", id)
            val fragment = SeasonListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var selectCallback: (String) -> Unit = {}
    private lateinit var seasonListAdapter: SeasonListAdapter
    private val mList = ArrayList<TvShowSeasonChildrenResponse.MediaContainer.Metadata>()
    private val id: String by lazy { arguments?.getString("id") ?: error("error") }
    override fun initView() {
        mBinding.recyclerView.apply {
            adapter = SeasonListAdapter(mList).also {
                it.selectCallback = {
                    selectCallback(it)
                }
                seasonListAdapter = it
            }
        }
    }

    override fun initData() {
        super.initData()
        val plexLocalApi = HttpFactory.localRetrofit.create(PlexLocalApi::class.java)
        lifecycleScope.launch {
            val children = plexLocalApi.getTvShowChildren(id)
            val metadataList = children.mediaContainer.metadata
            if (metadataList.isNotEmpty()) {
                mBinding.tabLayout.apply {
                    metadataList.forEach { metadata ->
                        addTab(newTab().also {
                            it.text = metadata.title
                            it.tag = metadata.ratingKey
                        })
                    }
                    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            tab ?: return
                            val ratingKey = tab.tag as String
                            refreshSeasonChildren(ratingKey)
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }
                    })
                }
                val seasonMetaData = metadataList.first()
                val seasonRatingKey = seasonMetaData.ratingKey
                refreshSeasonChildren(seasonRatingKey)
            }
        }
    }

    private fun refreshSeasonChildren(seasonRatingKey: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            val plexLocalApi = HttpFactory.localRetrofit.create(PlexLocalApi::class.java)
            val seasonChildrenResponse = plexLocalApi.getTvShowSeasonChildren(seasonRatingKey)
            if (mList.isNotEmpty()) {
                seasonListAdapter.notifyItemRangeRemoved(0, mList.size)
                mList.clear()
            }
            mList.addAll(seasonChildrenResponse.mediaContainer.metadata)
            if (mList.isEmpty()) return@launch
            seasonListAdapter.notifyItemRangeInserted(0, mList.size)
            mBinding.recyclerView.post {
                mBinding.recyclerView.layoutManager?.let {
                    val view = it.findViewByPosition(0)
                    view?.let {
                        val cardView = it.findViewById<CardView>(R.id.card_view)
                        cardView?.post {
                            val touch = view.requestFocusFromTouch()
                            Log.d(TAG, "refreshSeasonChildren: ${cardView.focusable}")
                        }
                    }
                }
            }
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSeasonListBinding =
        FragmentSeasonListBinding.inflate(inflater, container, false)
}