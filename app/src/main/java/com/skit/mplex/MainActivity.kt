package com.skit.mplex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.skit.mplex.base.BaseActivity
import com.skit.mplex.broadcast.ClockBroadcast
import com.skit.mplex.databinding.ActivityMainBinding
import com.skit.mplex.ktx.loadPlexImgBlur
import com.skit.mplex.repository.PlexRepository
import com.skit.mplex.repository.PlexRepositoryImpl
import com.skit.mplex.storage.SharedStorage
import com.skit.mplex.ui.account.LoginActivity
import com.skit.mplex.ui.home.adpter.HomeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun launch(loginActivity: AppCompatActivity) {
            loginActivity.startActivity(Intent(loginActivity, MainActivity::class.java))
        }

        private const val TAG = "MainActivity"
    }

    private val mTimeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private lateinit var mHomeAdapter: HomeAdapter

    private val mList = ArrayList<HomeAdapter.Bean>()

    private val mPlexRepository: PlexRepository = PlexRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun preInitView(): Boolean {
        if (SharedStorage.host.isEmpty() && SharedStorage.token.isEmpty()) {
            launchToLogin()
            return true
        }
        return super.preInitView()
    }

    override fun initView() {
        lifecycle.addObserver(ClockBroadcast {
            updateTime(it)
        })
        mBinding.recyclerView.apply {
            isNestedScrollingEnabled = true
            adapter = HomeAdapter(mList).also {
                mHomeAdapter = it
                it.focusedCallback = { artPath ->
                    loadArtImage(artPath)
                }
            }
        }
        mBinding.btRefresh.setOnClickListener {
            refresh()
        }
        lifecycleScope.launch {
            getData()
        }
    }

    override fun initData() {
        super.initData()
        updateTime(System.currentTimeMillis())
    }

    override fun getBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    private fun updateTime(it: Long) {
        mBinding.tvClock.text = mTimeFormat.format(it)
    }

    private fun loadArtImage(artPath: String) {
        mBinding.ivBg.apply {
            if (tag != artPath) {
                loadPlexImgBlur(artPath)
                tag = artPath
            }
        }
    }

    private fun refresh() {
        mHomeAdapter.notifyItemRangeRemoved(0, mHomeAdapter.itemCount)
        mList.clear()
        lifecycleScope.launch {
            getData()
        }
    }

    private suspend fun getData() {
        withContext(Dispatchers.Main) {
            mBinding.loadingProgressBar.show()
            mBinding.llErrorContainer.isVisible = false
        }
        withContext(Dispatchers.IO) {
            try {
                val body = mPlexRepository.getRecentlyAdded()
                withContext(Dispatchers.Main) {
                    val mediaContainer = body.mediaContainer
                    val directories =
                        mediaContainer.metadata.sortedByDescending { it.addedAt }
                            .distinctBy { if (it.type == "season") it.parentRatingKey else it.ratingKey }
                    mList.add(
                        HomeAdapter.Bean(
                            HomeAdapter.Type.RecentlyAdded,
                            "最近添加",
                            HomeAdapter.Bean.Data(0, directories)
                        )
                    )
                    mHomeAdapter.notifyItemInserted(mList.size)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                val libraryBean = mPlexRepository.getLibraryBean()
                val mediaContainer = libraryBean.mediaContainer
                val directories = mediaContainer.directory

                runOnUiThread {
                    mBinding.loadingProgressBar.hide()
//                    mBinding.swipeRefreshLayout.isEnabled = true
//                    mBinding.swipeRefreshLayout.isRefreshing = false
                    mList.add(
                        HomeAdapter.Bean(
                            HomeAdapter.Type.MediaType,
                            "影片类型",
                            HomeAdapter.Bean.Data(0, directories)
                        )
                    )
                    mHomeAdapter.notifyItemInserted(mList.size)
                }

                val ids = directories.joinToString(",") { it.key }
                Log.d(TAG, "onCreate: ids:${ids}")
                val recentlyAdded = mPlexRepository.getRecentlyAdded(ids)
                recentlyAdded.let { addedResponse ->
                    addedResponse.mediaContainer.hub.forEach { hub ->
                        val type = when (hub.type) {
                            "show",
                            "mixed",
                            -> {
                                HomeAdapter.Type.RecentlyAddedTV
                            }

                            "movie" -> {
                                HomeAdapter.Type.RecentlyAddedMovie
                            }

                            else -> HomeAdapter.Type.NONE
                        }

                        if (type != HomeAdapter.Type.NONE) {
                            runOnUiThread {
                                val map = hub.metadata
                                mList.add(
                                    HomeAdapter.Bean(
                                        type,
                                        hub.title,
                                        HomeAdapter.Bean.Data(0, map)
                                    )
                                )
                                mHomeAdapter.notifyItemInserted(mList.size)
                            }
                        }
                    }
                }
                Log.d(TAG, "onCreate: $directories")
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    mBinding.loadingProgressBar.hide()
                    mBinding.llErrorContainer.isVisible = true
                    mBinding.nestedSctollView.isVisible = false
                    mBinding.tvError.text = e.message
                    mBinding.retry.setOnClickListener {
                        lifecycleScope.launch {
                            getData()
                        }
                    }
                    mBinding.relog.setOnClickListener {
                        SharedStorage.host = ""
                        SharedStorage.token = ""
                        launchToLogin()
                    }
                }
            }
        }
    }


    private fun launchToLogin() {
        LoginActivity.launch(this)
        finish()
    }

    private fun ProgressBar.show() {
        visibility = View.VISIBLE
    }

    private fun ProgressBar.hide() {
        visibility = View.INVISIBLE
    }
}