package com.skit.mplex.ui.home.container

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.R

open class BaseTypeContainer(
    parent: ViewGroup,
) {
    private val view: View
    val tvTypeTitle: TextView
    val recyclerView: RecyclerView

    @LayoutRes
    open val layoutRes = R.layout.item_home_library_list

    init {
        view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        tvTypeTitle = view.findViewById(R.id.tv_type_title)
        recyclerView = view.findViewById(R.id.recycler_view)
    }


    open fun getView(): View = view
}