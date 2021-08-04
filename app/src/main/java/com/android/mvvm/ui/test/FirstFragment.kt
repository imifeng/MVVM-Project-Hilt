package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.ui.MainActivity
import com.android.mvvm.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment() {
    override val fragmentProperties = FragmentProperties(
        resource = R.layout.fragment_first
    )

    /**
     * viewmodel 是在访问时惰性创建的，并且 viewmodel 的创建必须在主线程上完成。 只需尝试在主线程上访问视图模型，然后再在全局范围内访问它就可以解决问题
     */
    private val repoViewModel: RepoViewModel by viewModels()

    override fun init() {
        super.init()
        // 这里需要访问视图模型，使其在主线程上初始化
        repoViewModel

        button_sign.setOnClickListener {
            if (et_username.text.isNullOrBlank()) {
                return@setOnClickListener
            }
            signIn()
        }
    }

    private fun signIn() {
        lifecycleScope.launch(Dispatchers.Main) {
            pb_loading.show()
            val results =
                withContext(Dispatchers.IO) { repoViewModel.loadRepos(et_username.text.toString()) }
            pb_loading.hide()
            if (results != null && results.isNotEmpty()) {
                (activity as? MainActivity)?.gotoSecondFragment()
            }
        }
    }
}