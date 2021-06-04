package com.automatedcontacttracing.act.basicinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import com.automatedcontacttracing.act.basicinfo.mapper.BasicInfoMapper
import kotlinx.android.synthetic.main.fragment_basic_info.*

class BasicInfoFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_basic_info
    override fun shouldShowBackButton(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener {
            if (withNullOrEmptyValues()) {
                showError(getString(R.string.with_empty_input))
                return@setOnClickListener
            }

            if (!isEmailAddressValid()) {
                showError(getString(R.string.invalid_email))
                return@setOnClickListener
            }

            if (!isMobileNumberValid()) {
                showError(getString(R.string.invalid_mobile_number))
                return@setOnClickListener
            }

            saveBasicInfo()
            val action =
                BasicInfoFragmentDirections.actionBasicInfoFragmentToHealthDeclarationFragment()
            it.findNavController().navigate(action)
        }
    }


    private fun withNullOrEmptyValues(): Boolean =
        etLastName.text.isNullOrEmpty()
                || etLastName.text.isNullOrEmpty()
                || etFirstName.text.isNullOrEmpty()
                || etMiddleInitital.text.isNullOrEmpty()
                || etContactNoPrefix.text.isNullOrEmpty()
                || etContactNo.text.isNullOrEmpty()
                || etAddress.text.isNullOrEmpty()
                || etTemperature.text.isNullOrEmpty()

    private fun isEmailAddressValid() =
        etEmailAddress.text.isValidEmail()

    private fun isMobileNumberValid() =
        (etContactNoPrefix.text.toString() + etContactNo.text.toString()).isValidMobileNumber()


    private fun saveBasicInfo() {
        mSharedPrefHelper.saveBasicInfo(
            BasicInfoMapper.map(
                etLastName.text.toString(),
                etFirstName.text.toString(),
                etMiddleInitital.text.toString(),
                etAge.text.toString(),
                spnGender.selectedItem.toString(),
                etEmailAddress.text.toString(),
                "${etContactNoPrefix.text}|${etContactNo.text}",
                etAddress.text.toString(),
                etTemperature.text.toString()
            )
        )
    }
}