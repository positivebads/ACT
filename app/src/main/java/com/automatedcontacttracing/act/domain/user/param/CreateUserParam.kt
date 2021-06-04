package com.automatedcontacttracing.act.domain.user.param

import com.automatedcontacttracing.act.domain.user.common.HealthInfo
import com.automatedcontacttracing.act.domain.user.common.PersonalInfo
import com.google.gson.annotations.SerializedName

data class CreateUserParam(
    @SerializedName("personal_information")
    val personalInfo: PersonalInfo,
    @SerializedName("health_information")
    val healthInfo: HealthInfo,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("attachment")
    val attachmentLink: String
)