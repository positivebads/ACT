package com.automatedcontacttracing.act.base.domain

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

open class BaseResponse<out T> {
    @Nullable
    @SerializedName("status")
    var status: String? = null

    @SerializedName("code")
    var code: Int = 0

    @Nullable
    @SerializedName("data")
    val data: T? = null

    fun isSuccess(): Boolean = code == 200

}