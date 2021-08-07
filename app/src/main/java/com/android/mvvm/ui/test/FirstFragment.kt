package com.android.mvvm.ui.test

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.makeShortToast
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.FragmentFirstBinding
import com.android.mvvm.ui.MainActivity
import com.android.mvvm.viewmodel.RepoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(R.layout.fragment_first) {

    private val binding by viewBinding(FragmentFirstBinding::bind)

    /**
     * viewmodel 是在访问时惰性创建的，并且 viewmodel 的创建必须在主线程上完成。 只需尝试在主线程上访问视图模型，然后再在全局范围内访问它就可以解决问题
     */
    private val repoViewModel: RepoViewModel by viewModels()

    override fun init() {
        super.init()
        // 这里需要访问视图模型，使其在主线程上初始化
        repoViewModel
        initHeaderView()
        binding.buttonSign.setOnClickListener {
            if (binding.etUsername.text.isNullOrBlank()) {
                context?.makeShortToast("请输入Git用户名")
                return@setOnClickListener
            }
            signIn()
        }
    }

    private fun initHeaderView() {
        with(binding.includeHeader) {
            header.show()
            headerTitle.text = getText(R.string.sign_title)
            headerTitle.show()
        }
    }

    private fun signIn() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.pbLoading.show()
            val results =
                withContext(Dispatchers.IO) { repoViewModel.loadRepos(binding.etUsername.text.toString()) }
            binding.pbLoading.hide()

            if (!results.isNullOrEmpty()) {
                (activity as? MainActivity)?.gotoSecondFragment()
            }
        }
    }
}