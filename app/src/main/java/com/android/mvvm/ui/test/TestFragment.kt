package com.android.mvvm.ui.test

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment]
 */
@AndroidEntryPoint
class TestFragment : BaseFragment(R.layout.fragment_test) {

    private val binding by viewBinding(FragmentTestBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun init() {
        super.init()
        initHeaderView()
        binding.tv.text = "TestFragment"
    }

    private fun initHeaderView() {
        with(binding.includeHeader) {
            header.show()
            headerTitle.text = getText(R.string.test_title)
            headerTitle.show()
        }
    }

}