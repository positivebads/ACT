package com.automatedcontacttracing.act.data.service

import com.automatedcontacttracing.act.base.domain.BaseResponse
import com.automatedcontacttracing.act.domain.user.param.CreateUserParam
import com.automatedcontacttracing.act.domain.user.response.CreateUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("create-user")
    suspend fun createUser(@Body param: CreateUserParam): Response<BaseResponse<CreateUserResponse>>
}