package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.extension.viewModelFragment
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.ui.MainActivity
import com.android.mvvm.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment() {
    override val fragmentProperties = FragmentProperties(
        resource = R.layout.fragment_first
    )

    private val repoViewModel: RepoViewModel by viewModelFragment()

    override fun init() {
        super.init()

        button_sign.setOnClickListener {
            if (et_username.text.isNullOrBlank()) {
                return@setOnClickListener
            }
            signIn()
        }
    }

    private fun signIn() {
        lifecycleScope.launch(Dispatchers.Main) {
            pb_loading.show()
            val results = withContext(Dispatchers.IO) {
                repoViewModel.loadRepos(et_username.text.toString())
            }
            pb_loading.hide()
            if (results != null && results.isNotEmpty()) {
                (activity as? MainActivity)?.gotoSecondFragment()
            }
        }
    }
}