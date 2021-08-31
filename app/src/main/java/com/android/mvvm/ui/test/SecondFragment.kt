package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.*
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
        repoViewModel.getRepos().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                reposAdapter.setData(it)
            }
        })
    }
}