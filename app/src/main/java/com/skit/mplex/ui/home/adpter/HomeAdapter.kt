package com.skit.mplex.ui.home.adpter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.bean.LibrarySectionsResponseBean
import com.skit.mplex.bean.RecentlyAddedResponse
import com.skit.mplex.ui.home.container.BaseTypeContainer
import com.skit.mplex.ui.home.container.HomeMovieTypeView
import com.skit.mplex.ui.home.recentyl.HubRecentlyAddedAdapter
import com.skit.mplex.ui.home.recentyl.RecentlyAddedAdapter
import com.skit.mplex.ui.home.sestions.SectionsAdapter


class HomeAdapter(private val list: List<Bean>) : RecyclerView.Adapter<ViewHolder>() {
    private val TAG = "HomeAdapter"

    data class Bean(
        val type: Type,
        val title: String,
        val data: Data,
    ) {
        data class Data(
            val status: Int,
            val data: Any?,
        )
    }

    var focusedCallback: (String) -> Unit = {}

    inner class NoneViewHolder(context: Context) : ViewHolder(View(context))
    inner class TypeViewHolder(private val container: BaseTypeContainer, type: Type) :
        ViewHolder(container.getView()) {
        fun bind(list: List<Bean>, position: Int) {
            val bean = list[position]
            val data = bean.data.data
            container.tvTypeTitle.text = bean.title
            if (data != null) {
                container.recyclerView.apply {
                    when (bean.type) {
                        Type.MediaType -> {
                            adapter =
                                SectionsAdapter(data as List<LibrarySectionsResponseBean.MediaContainer.Directory>).apply {
                                }
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        }

                        Type.RecentlyAddedTV -> {
                            adapter =
                                HubRecentlyAddedAdapter(data as List<HubRecentlyAddedResponse.MediaContainer.Hub.Metadata>).apply {
                                    this.focusedCallback = this@HomeAdapter.focusedCallback
                                }
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        }

                        Type.RecentlyAddedMovie -> {
                            adapter =
                                HubRecentlyAddedAdapter(data as List<HubRecentlyAddedResponse.MediaContainer.Hub.Metadata>).apply {
                                    this.focusedCallback = this@HomeAdapter.focusedCallback
                                }
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        }

                        Type.RecentlyAdded -> {
                            adapter =
                                RecentlyAddedAdapter(data as List<RecentlyAddedResponse.MediaContainer.Metadata>).apply {
                                    this.focusedCallback = this@HomeAdapter.focusedCallback
                                }
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    enum class Type(val code: Int) {
        NONE(0),
        MediaType(1),
        RecentlyAddedTV(2),
        RecentlyAddedMovie(3),
        RecentlyAdded(4),
        ;

        companion object {
            fun get(code: Int): Type {
                return when (code) {
                    1 -> MediaType
                    2 -> RecentlyAddedTV
                    3 -> RecentlyAddedMovie
                    4 -> RecentlyAdded
                    else -> NONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val type = Type.get(viewType)
        return when (type) {
            Type.NONE -> {
                NoneViewHolder(parent.context)
            }

            Type.MediaType -> {
                TypeViewHolder(HomeMovieTypeView(parent), type)
            }

            Type.RecentlyAddedTV -> {
                TypeViewHolder(BaseTypeContainer(parent), type)
            }

            Type.RecentlyAddedMovie -> {
                TypeViewHolder(BaseTypeContainer(parent), type)
            }

            Type.RecentlyAdded -> {
                TypeViewHolder(BaseTypeContainer(parent), type)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TypeViewHolder) {
            holder.bind(list, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type.code
    }
}