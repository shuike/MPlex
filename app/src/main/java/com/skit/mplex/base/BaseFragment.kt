package com.skit.mplex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = getBinding(inflater, container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    protected open fun preInitView(): Boolean = false

    open fun initData() {

    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): T

    abstract fun initView()
}