package com.skit.mplex.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preInitView()) {
            return
        }
        WindowCompat.getInsetsController(window, window.decorView).also {
            it.hide(WindowInsetsCompat.Type.systemBars())
        }
        mBinding = getBinding(LayoutInflater.from(this))
        setContentView(mBinding.root)
        initView()
        initData()
    }

    protected open fun preInitView(): Boolean = false

    open fun initData() {

    }

    abstract fun initView()

    abstract fun getBinding(inflater: LayoutInflater): T


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode in arrayOf(KeyEvent.KEYCODE_ESCAPE, KeyEvent.KEYCODE_BACK)) {
            return if (onBackButtonClick()) {
                true
            } else {
                finish()
                true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun onBackButtonClick(): Boolean = false

}