package com.automatedcontacttracing.act.base.data

import com.automatedcontacttracing.act.base.domain.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

open class BaseRepository {

    suspend inline fun <reified T : BaseResponse<*>> safeApiCall(
        crossinline call: suspend () -> Response<T>,
        defaultErrorMessage: String? = "Something went wrong!"
    ): T {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()
                val resultSuccess = response.body() ?: T::class.java.newInstance()

                if (response.isSuccessful) {
                    return@withContext resultSuccess
                } else {
                    val type = object : TypeToken<T>() {}.type
                    val resultError: T = Gson().fromJson(response.errorBody()?.string(), type)
                    with(resultError.status) {
                        return@with if (this!!.isBlank() && !defaultErrorMessage.isNullOrBlank()) {
                            defaultErrorMessage
                        } else {
                            this
                        }
                    }
                    return@withContext resultError
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                val response = T::class.java.newInstance()
                response.code = -1

                if (ex is ConnectException || ex is UnknownHostException) {
                    response.status = "Connection timeout"
                } else {
                    response.status =
                        defaultErrorMessage ?: "Have something error. Please try again later!"
                }
                return@withContext response
            }
        }
    }
}