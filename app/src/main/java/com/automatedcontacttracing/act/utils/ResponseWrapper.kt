package com.automatedcontacttracing.act.utils

sealed class ResponseWrapper {
    object Default : ResponseWrapper()
    object Loading : ResponseWrapper()
    data class Success<out T>(val data: T) : ResponseWrapper()
    data class Failed(val code: String?, val errorMessage: String?) : ResponseWrapper()
}