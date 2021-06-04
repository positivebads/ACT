package com.automatedcontacttracing.act.infoconfirmation

import androidx.databinding.BaseObservable
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class InfoConfirmationData @Inject constructor() : BaseObservable() {

    lateinit var lastName: String
    lateinit var firstName: String
    lateinit var middleInitial: String
    lateinit var age: String
    lateinit var gender: String
    lateinit var emailAddress: String
    lateinit var contactNumber: String
    lateinit var address: String
    lateinit var temperature: String

    lateinit var idImagePath: String

    lateinit var mobileNumberPrefix: String
    lateinit var mobileNumber: String

    var withFever: Boolean = false
    var withWoresningCough: Boolean = false
    var withSoreThroat: Boolean = false
    var withDifficultyOfBreathing: Boolean = false
    var withLossOfSmell: Boolean = false
    var withRunnyNose: Boolean = false
    var withHeadache: Boolean = false
    var withMusclePain: Boolean = false
    var withDiarrhea: Boolean = false
    var withCloseContact: Boolean = false
    var affirmative: Boolean = false
}