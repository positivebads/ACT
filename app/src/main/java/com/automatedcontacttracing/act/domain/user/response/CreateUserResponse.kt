package com.automatedcontacttracing.act.domain.user.response

import com.automatedcontacttracing.act.domain.user.common.HealthInfo
import com.automatedcontacttracing.act.domain.user.common.PersonalInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreateUserResponse(
    @SerializedName("personal_information")
    val personalInfo: PersonalInfo,
    @SerializedName("health_information")
    val healthInfo: HealthInfo,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("attachment")
    val attachmentLink: String,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("_id")
    val id: String
) : Serializable