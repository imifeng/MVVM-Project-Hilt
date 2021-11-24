package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.base.ErrorState
import com.android.mvvm.core.base.FailureState
import com.android.mvvm.core.base.LoadingState
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.makeShortToast
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.FragmentFirstBinding
import com.android.mvvm.ui.MainActivity
import com.android.mvvm.viewmodel.RepoViewModel
import com.android.mvvm.viewmodel.RepoViewModel.RepoState.CheckSuccess

/**
 * A simple [Fragment]
 */
class FirstFragment : BaseFragment(R.layout.fragment_first) {

    private val binding by viewBinding(FragmentFirstBinding::bind)

    /**
     * viewmodel 是在访问时惰性创建的，并且 viewmodel 的创建必须在主线程上完成。
     * 只需尝试在主线程上访问视图模型，然后再在全局范围内访问它就可以解决问题
     */
    private val repoViewModel: RepoViewModel by viewModels()

    override fun init() {
        super.init()
        initHeaderView()

        repoViewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoadingState -> {
                    // 正在加载,可显示加载进度条
                    binding.pbLoading.show()
                }
                is CheckSuccess -> {
                    binding.pbLoading.hide()
                    // 加载成功，展示数据
                    if (state.checkLogin) {
                        (activity as? MainActivity)?.gotoSecondFragment()
                    }
                }
                is FailureState -> {
                    binding.pbLoading.hide()
                    // 加载失败， 提示错误信息
                    context?.makeShortToast(state.message)
                }
                is ErrorState -> {
                    binding.pbLoading.hide()
                    // 加载出错

                }
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
        repoViewModel.loadRepos(binding.etUsername.text.toString())
    }
}