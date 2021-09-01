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
import com.android.mvvm.core.model.DataState
import com.android.mvvm.data.RepoBean
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

        repoViewModel.loadRepoDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    // 正在加载,可显示加载进度条
                    binding.pbLoading.show()
                }
                is DataState.Success<List<RepoBean>> -> {
                    binding.pbLoading.hide()
                    // 加载成功，展示数据
                    val data = dataState.data
                    if (data.isNotEmpty()) {
                        (activity as? MainActivity)?.gotoSecondFragment()
                    }else{
                        context?.makeShortToast("该用户数据为空")
                    }
                }
                is DataState.Failure -> {
                    binding.pbLoading.hide()
                    // 加载失败， 提示错误信息
                    context?.makeShortToast(dataState.message)
                }
                is DataState.Error -> {
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