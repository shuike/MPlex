package com.skit.mplex.ui.home.sestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.R
import com.skit.mplex.bean.LibrarySectionsResponseBean
import com.skit.mplex.databinding.ItemLibraryTypeBinding

class SectionsAdapter(private val list: List<LibrarySectionsResponseBean.MediaContainer.Directory>) :
    RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {
    companion object {
        private val linearDrawableList = arrayOf(
            R.drawable.type_linear_1,
            R.drawable.type_linear_2,
            R.drawable.type_linear_3,
            R.drawable.type_linear_4,
        )
    }

    var focusedCallback: (String) -> Unit = {}

    class ViewHolder(val binding: ItemLibraryTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLibraryTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    private var drawableIndex = -1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val directory = list[position]
        holder.binding.apply {
            tvTitle.text = directory.title
            if (drawableIndex >= linearDrawableList.size - 1) {
                drawableIndex = 0
            } else {
                drawableIndex++
            }
            ivImg.setImageResource(linearDrawableList[drawableIndex])
        }
    }
}