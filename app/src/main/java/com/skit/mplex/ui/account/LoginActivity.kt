package com.skit.mplex.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.skit.mplex.MainActivity
import com.skit.mplex.bean.ResourceResponse
import com.skit.mplex.databinding.ActivtyLoginBinding
import com.skit.mplex.databinding.ItemConnectionListBinding
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.server.PlexApi
import com.skit.mplex.storage.SharedStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivtyLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivtyLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.llLoadingContainer.isVisible = false
        mBinding.btLogin.setOnClickListener {
            val account = mBinding.etAccount.text.toString()
            val password = mBinding.etPassword.text.toString()
            val create = HttpFactory.remoteRetrofit.create(PlexApi::class.java)
            lifecycleScope.launch {
                val login = create.login(account, password)
                Log.d(TAG, "onCreate: ${login.authToken}")
                SharedStorage.token = login.authToken
                val resources = create.resources()
                mBinding.scrollView.isVisible = true
                mBinding.scrollView.alpha = 0f
                mBinding.scrollView.animate().alpha(1f).start()
                mBinding.llInputContainer.isVisible = false
                mBinding.llConnectionContainer.apply {
                    resources.forEach { item ->
                        val binding =
                            ItemConnectionListBinding.inflate(
                                LayoutInflater.from(this@LoginActivity),
                                mBinding.llConnectionContainer,
                                false
                            )
                        binding.text.apply {
                            text = item.name
                        }
                        binding.root.setOnClickListener {
                            click(item)
                        }
                        addView(binding.root)
                    }
                }
//                HttpFactory.host = resources.
            }
        }
    }

    private fun click(item: ResourceResponse.ResourceResponseItem) {
        val connections = item.connections
        mBinding.llLoadingContainer.isVisible = true
        lifecycleScope.launch {
            var selectConnection: ResourceResponse.ResourceResponseItem.Connection? = null
            withContext(Dispatchers.IO) {
                connections.forEachIndexed { index, connection ->
                    withContext(Dispatchers.Main) {
                        mBinding.tvLoadingText.text =
                            "正在查找合适的路线 ${index + 1}/${connections.size}"
                    }
                    val reachable = InetAddress.getByName(connection.address).isReachable(2000)
                    if (reachable) {
                        selectConnection = connection
                        return@withContext
                    }
                }
            }
            if (selectConnection != null) {
                SharedStorage.host = selectConnection!!.uri
                MainActivity.launch(this@LoginActivity)
                finish()
                overridePendingTransition(0, 0)
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
        fun launch(activity: MainActivity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}