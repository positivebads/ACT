package com.automatedcontacttracing.act.infoconfirmation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import com.automatedcontacttracing.act.databinding.FragmentBasicInfoConfirmationBinding
import com.automatedcontacttracing.act.domain.user.response.CreateUserResponse
import com.automatedcontacttracing.act.utils.ResponseWrapper
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_basic_info_confirmation.*
import kotlinx.coroutines.delay
import java.io.File
import java.net.URI
import javax.inject.Inject

@AndroidEntryPoint
class BasicInfoConfirmationFragment : BaseFragment() {

    private var mImageFirebaseUrl: String? = null

    @Inject
    lateinit var mInfoConfirmationData: InfoConfirmationData
    private val mRadioGroupList = mutableListOf<RadioGroup>()

    private val mViewModel: BasicInfoConfirmationViewModel by viewModels()
    override fun getLayoutResId(): Int = R.layout.fragment_basic_info_confirmation
    override fun shouldShowBackButton(): Boolean = true

    private var mStorageRef: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebaseStorage = FirebaseStorage.getInstance()
        firebaseStorage.maxOperationRetryTimeMillis = 10000
        firebaseStorage.maxUploadRetryTimeMillis = 60000
        mStorageRef = firebaseStorage.reference

        val binding: FragmentBasicInfoConfirmationBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutResId(), container, false
        )
        val view = binding.root
        setData()
        binding.data = mInfoConfirmationData
        return view
    }

    private fun uploadImage() {
        showLoading()
        if (mImageFirebaseUrl == null) {
            val file: Uri = Uri.fromFile(File(mSharedPrefHelper.getImagePath()))
            val imgRef: StorageReference =
                mStorageRef!!.child("images/${getLastSegment(mSharedPrefHelper.getImagePath()!!)}")

            imgRef.putFile(file)
                .addOnSuccessListener { _ -> // Get a URL to the uploaded content
                    imgRef.downloadUrl.addOnSuccessListener {
                        mImageFirebaseUrl = it.toString()
                        createUser(it.toString())
                        hideLoading()
                    }
                }
                .addOnFailureListener {
                    showError(it.message.toString())
                    hideLoading()
                }
        } else {
            createUser(mImageFirebaseUrl!!)
            hideLoading()
        }
    }

    private fun setData() {
        mSharedPrefHelper.let {
            mInfoConfirmationData.lastName = it.getLastName()!!
            mInfoConfirmationData.firstName = it.getFirstName()!!
            mInfoConfirmationData.middleInitial = it.getMiddleName()!!
            mInfoConfirmationData.age = it.getAge()!!
            mInfoConfirmationData.gender = it.getGender()!!
            mInfoConfirmationData.emailAddress = it.getEmail()!!
            mInfoConfirmationData.address = it.getAddress()!!
            mInfoConfirmationData.temperature = it.getTemperature()!!

            mInfoConfirmationData.withFever = it.getWithFever()
            mInfoConfirmationData.withSoreThroat = it.getWithSoreThroat()
            mInfoConfirmationData.withWoresningCough = it.getWithCough()
            mInfoConfirmationData.withDifficultyOfBreathing = it.getWithDifficultyOfBreathing()
            mInfoConfirmationData.withLossOfSmell = it.getWithLossOfSmell()
            mInfoConfirmationData.withRunnyNose = it.getWithRunnyNose()
            mInfoConfirmationData.withHeadache = it.getWithHeadache()
            mInfoConfirmationData.withMusclePain = it.getWithMusclePain()
            mInfoConfirmationData.withDiarrhea = it.getWithDiarrhea()
            mInfoConfirmationData.withCloseContact = it.getWithContact()

            mInfoConfirmationData.idImagePath = getLastSegment(it.getImagePath()!!)

            val mobileNumberSplit = it.getContactNumber()!!.split("|")
            mInfoConfirmationData.mobileNumberPrefix = mobileNumberSplit[0]
            mInfoConfirmationData.mobileNumber = mobileNumberSplit[1]
        }
    }

    private fun collectAllRadioGroups() {
        mRadioGroupList.add(rgFever)
        mRadioGroupList.add(rgCough)
        mRadioGroupList.add(rgSoreThroat)
        mRadioGroupList.add(rgDifficultyOfBreathing)
        mRadioGroupList.add(rgLossOfSmell)
        mRadioGroupList.add(rgRunnynose)
        mRadioGroupList.add(rgHeadache)
        mRadioGroupList.add(rgMusclePain)
        mRadioGroupList.add(rgDiarrhea)
        mRadioGroupList.add(rgWithContact)
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

    private fun hasRadioGroupWithNoSelectedItem(): Boolean {
        var foundNoSelectedItem = false
        mRadioGroupList.forEach {
            if (it.checkedRadioButtonId == -1) {
                foundNoSelectedItem = true
                return@forEach
            }
        }
        return foundNoSelectedItem
    }


    private fun getLastSegment(imagePath: String): String = URI(imagePath).path.split("/").last()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectAllRadioGroups()
        observeViewModel()
        lifecycleScope.launchWhenStarted {
            delay(500)
            spnGender.setSelection(
                resources.getStringArray(R.array.gender).indexOf(mInfoConfirmationData.gender)
            )
        }

        btnNext.setOnClickListener {

            if (!isEmailAddressValid()) {
                showError(getString(R.string.invalid_email))
                return@setOnClickListener
            }

            if (!isMobileNumberValid()) {
                showError(getString(R.string.invalid_mobile_number))
                return@setOnClickListener
            }

            if (hasRadioGroupWithNoSelectedItem() && withNullOrEmptyValues()) {
                showError(getString(R.string.with_empty_field_error))
                return@setOnClickListener
            }
            uploadImage()
        }
    }

    private fun createUser(imageUrl: String) {
        mViewModel.createUser(
            etLastName.text.toString(),
            etFirstName.text.toString(),
            etMiddleInitital.text.toString(),
            etEmailAddress.text.toString(),
            etContactNoPrefix.text.toString() + etContactNo.text.toString(),
            "",
            etAge.text.toString(),
            spnGender.selectedItem.toString(),
            etAddress.text.toString(),
            rbFeverYes.isChecked,
            rbCoughYes.isChecked,
            rbSoreThroatYes.isChecked,
            rbDifficultyOfBreathingYes.isChecked,
            rbLossOfSmellYes.isChecked,
            rbRunnyNoseYes.isChecked,
            rbHeadacheYes.isChecked,
            rbMusclePainYes.isChecked,
            rbDiarrheaYes.isChecked,
            rbWithContactYes.isChecked,
            etTemperature.text.toString(),
            imageUrl
        )
    }

    fun observeViewModel() {
        mViewModel.mCreateUserData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseWrapper.Loading -> {
                    showLoading()
                }
                is ResponseWrapper.Success<*> -> {
                    hideLoading()
                    val action =
                        BasicInfoConfirmationFragmentDirections.actionBasicInfoConfirmationFragmentToSubmissionCompleteFragment2(
                            it.data as CreateUserResponse
                        )
                    findNavController().navigate(action)
                }
                is ResponseWrapper.Failed -> {
                    showError(it.errorMessage!!)
                    hideLoading()
                }
                else -> Unit
            }
        })
    }
}