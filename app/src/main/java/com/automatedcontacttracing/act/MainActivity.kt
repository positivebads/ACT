package com.automatedcontacttracing.act

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.automatedcontacttracing.act.base.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mShouldShowBackButton: Boolean = false

    private val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeViewModel() {
        mViewModel.toolbarLiveData.observe(this, Observer {
            mShouldShowBackButton = it.showBackButton
            btnBack.isVisible = it.showBackButton
        })
    }

    override fun onBackPressed() {
        if (mShouldShowBackButton) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}