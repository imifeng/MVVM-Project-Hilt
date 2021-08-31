package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.makeShortToast
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.FragmentFirstBinding
import com.android.mvvm.ui.MainActivity
import com.android.mvvm.viewmodel.RepoViewModel

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
        initHeaderView()

        repoViewModel.loadRepoLiveData.observe(viewLifecycleOwner, {
            binding.pbLoading.hide()
            if (it.isNotEmpty()) {
                (activity as? MainActivity)?.gotoSecondFragment()
            }
        })

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
        binding.pbLoading.show()
        repoViewModel.loadRepos(binding.etUsername.text.toString())
    }
}