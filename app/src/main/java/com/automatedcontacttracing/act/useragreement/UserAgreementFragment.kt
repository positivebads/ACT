package com.automatedcontacttracing.act.useragreement

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_agreement.*


class UserAgreementFragment : BaseFragment() {

    override fun getLayoutResId(): Int = R.layout.fragment_user_agreement
    override fun shouldShowBackButton(): Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewPolicy.movementMethod = LinkMovementMethod.getInstance()

        btnAgree.setOnClickListener {
            val action =
                UserAgreementFragmentDirections.actionUserAgreementFragmentToBasicInfoFragment()
            it.findNavController().navigate(action)
        }
    }
}