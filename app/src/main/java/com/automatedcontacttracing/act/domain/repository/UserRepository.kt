package com.automatedcontacttracing.act.domain.repository

import com.automatedcontacttracing.act.base.domain.BaseResponse
import com.automatedcontacttracing.act.domain.user.param.CreateUserParam
import com.automatedcontacttracing.act.domain.user.response.CreateUserResponse

interface UserRepository {
    suspend fun createUser(param: CreateUserParam): BaseResponse<CreateUserResponse>
}