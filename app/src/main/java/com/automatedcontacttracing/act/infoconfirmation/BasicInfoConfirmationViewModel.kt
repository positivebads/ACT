package com.automatedcontacttracing.act.infoconfirmation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.automatedcontacttracing.act.domain.user.common.HealthInfo
import com.automatedcontacttracing.act.domain.user.common.PersonalInfo
import com.automatedcontacttracing.act.domain.user.param.CreateUserParam
import com.automatedcontacttracing.act.domain.usercase.CreateUserUseCase
import com.automatedcontacttracing.act.utils.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicInfoConfirmationViewModel @Inject constructor(private val mUseCase: CreateUserUseCase) :
    ViewModel() {

    val mCreateUserData = MutableLiveData<ResponseWrapper>()

    fun createUser(
        lastName: String,
        firstName: String,
        middleInitial: String,
        emailAddress: String,
        mobileNumber: String,
        birthday: String,
        age: String,
        gender: String,
        address: String,
        withFever: Boolean,
        withCough: Boolean,
        withSoreThroat: Boolean,
        withDiffBreathing: Boolean,
        withLossOfTasteSmell: Boolean,
        withRunnyNose: Boolean,
        withHeadache: Boolean,
        withMusclePain: Boolean,
        withDiarrhea: Boolean,
        withCloseContact: Boolean,
        temperature: String,
        attachmentLink: String
    ) {

        viewModelScope.launch {
            mCreateUserData.postValue(ResponseWrapper.Loading)
            val response = mUseCase.execute(
                CreateUserParam(
                    PersonalInfo(
                        lastName,
                        firstName,
                        middleInitial,
                        emailAddress,
                        mobileNumber,
                        birthday,
                        age,
                        gender,
                        address
                    ),
                    HealthInfo(
                        getIntValue(withFever),
                        getIntValue(withCough),
                        getIntValue(withSoreThroat),
                        getIntValue(withDiffBreathing),
                        getIntValue(withLossOfTasteSmell),
                        getIntValue(withRunnyNose),
                        getIntValue(withHeadache),
                        getIntValue(withMusclePain),
                        getIntValue(withDiarrhea),
                        getIntValue(withCloseContact)
                    ),
                    temperature,
                    attachmentLink
                )
            )
            if (response.isSuccess()) {
                mCreateUserData.postValue(response.data?.let {
                    ResponseWrapper.Success(
                        it
                    )
                })
            } else {
                mCreateUserData.postValue(
                    ResponseWrapper.Failed(
                        response.code.toString(),
                        response.status
                    )
                )
            }
        }
    }

    private fun getIntValue(value: Boolean): Int = if (value) 1 else 0
}