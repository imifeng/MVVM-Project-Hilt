package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.*
import com.android.mvvm.core.model.DataState
import com.android.mvvm.data.RepoBean
import com.android.mvvm.databinding.FragmentSecondBinding
import com.android.mvvm.ui.test.adapter.ReposPageAdapter
import com.android.mvvm.viewmodel.RepoViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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

        repoViewModel.getReposDataState.observe(viewLifecycleOwner, { dataState ->
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
                        reposAdapter.setData(data)
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

    private fun initData() {
        repoViewModel.getRepos()
    }
}