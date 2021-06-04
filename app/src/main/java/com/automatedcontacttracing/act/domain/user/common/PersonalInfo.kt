package com.automatedcontacttracing.act.domain.user.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonalInfo(
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("middle_initial")
    val middleInitial: String,
    @SerializedName("email_address")
    val emailAddress: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("address")
    val address: String
) : Serializable