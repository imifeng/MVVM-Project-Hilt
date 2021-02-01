package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.viewModel
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.ui.test.adapter.ReposAdapter
import com.android.mvvm.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment() {
    override val fragmentProperties = FragmentProperties(resource = R.layout.fragment_second)

    private val repoViewModel: RepoViewModel by viewModel()

    private val reposAdapter by lazy { ReposAdapter() }


    override fun init() {
        super.init()

        rv_data.layoutManager = LinearLayoutManager(requireContext())
        rv_data.adapter = reposAdapter

        initData()

        button_second.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initData() {
        repoViewModel.getRepos().observe(viewLifecycleOwner, Observer {
            if (it != null){
                reposAdapter.setData(it)
            }
        })
    }
}