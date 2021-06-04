package com.automatedcontacttracing.act.domain.user.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HealthInfo(
    @SerializedName("fever")
    val withFever: Int,
    @SerializedName("cough")
    val withCough: Int,
    @SerializedName("sore_throat")
    val withSoreThroat: Int,
    @SerializedName("diff_breathing")
    val withDiffBreathing: Int,
    @SerializedName("loss_taste_smell")
    val withLossOfTasteSmell: Int,
    @SerializedName("runny_nose")
    val withRunnyNose: Int,
    @SerializedName("headache")
    val withHeadache: Int,
    @SerializedName("muscle_pain")
    val withMusclePain: Int,
    @SerializedName("diarrhea")
    val withDiarrhea: Int,
    @SerializedName("close_contact")
    val withCloseContact: Int
) : Serializable