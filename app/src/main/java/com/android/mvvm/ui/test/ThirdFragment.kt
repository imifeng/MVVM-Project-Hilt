package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.adaptStatusBarHeight
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.FragmentTestBinding
import com.android.mvvm.databinding.FragmentThirdBinding
import com.android.mvvm.ui.test.adapter.ThirdListAdapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    private val binding by viewBinding(FragmentThirdBinding::bind)

    private val thirdListAdapter by lazy {
        ThirdListAdapter()
    }

    override fun init() {
        super.init()
        initHeaderView()

        with(binding.rvData) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = thirdListAdapter
        }

        loadData()
    }

    private fun initHeaderView() {
        with(binding.includeHeader) {
            header.show()
            headerTitle.text = getText(R.string.list_title)
            headerTitle.show()
        }
    }

    private fun loadData() {
        val list = mutableListOf<String>()
        list.add("测试数据 一行")
        list.add("测试数据 二行")
        list.add("测试数据 三行")
        list.add("测试数据 四行")
        list.add("测试数据 五行")
        list.add("测试数据 六行")
        list.add("测试数据 一一行")
        list.add("测试数据 一二行")
        list.add("测试数据 一三行")
        list.add("测试数据 一四行")
        list.add("测试数据 一五行")
        list.add("测试数据 一六行")

        thirdListAdapter.datas = list
    }

}