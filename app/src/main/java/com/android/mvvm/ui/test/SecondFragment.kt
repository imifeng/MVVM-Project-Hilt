package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.core.base.*
import com.android.mvvm.core.extension.*
import com.android.mvvm.databinding.FragmentSecondBinding
import com.android.mvvm.ui.test.adapter.ReposPageAdapter
import com.android.mvvm.viewmodel.RepoViewModel
import com.android.mvvm.viewmodel.RepoViewModel.RepoState.RepoDataState
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment]
 */
@AndroidEntryPoint
class SecondFragment : BaseFragment(R.layout.fragment_second) {

    private val binding by viewBinding(FragmentSecondBinding::bind)

    private val repoViewModel: RepoViewModel by viewModels()

    private val reposAdapter by lazy { ReposPageAdapter() }


    override fun init() {
        super.init()
        initHeaderView()

        with(binding.rvData) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reposAdapter
        }

        repoViewModel.dataState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    // 正在加载,可显示加载进度条
                    binding.pbLoading.show()
                }
                is RepoDataState -> {
                    binding.pbLoading.hide()
                    // 加载成功，展示数据
                    val data = state.data
                    if (data.isNotEmpty()) {
                        reposAdapter.setData(data)
                    }
                }
                is ServerErrorState -> {
                    binding.pbLoading.hide()
                    // 加载失败， 提示错误信息
                    state.message?.let {
                        context?.makeShortToast(it)
                    }
                }
                is ErrorState -> {
                    binding.pbLoading.hide()
                    // 加载出错
                }
            }
        }

        initData()
    }

    private fun initHeaderView() {
        with(binding.includeHeader) {
            header.show()
            headerTitle.text = getText(R.string.repos_title)
            headerTitle.show()
            headerAction.setImageResource(R.drawable.ic_clear)
            headerAction.show()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            initData()
        }
    }

    private fun initData() {
        repoViewModel.getRepos()
    }
}