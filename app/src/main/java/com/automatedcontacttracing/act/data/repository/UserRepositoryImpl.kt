package com.automatedcontacttracing.act.data.repository

import com.automatedcontacttracing.act.base.data.BaseRepository
import com.automatedcontacttracing.act.base.domain.BaseResponse
import com.automatedcontacttracing.act.data.service.UserService
import com.automatedcontacttracing.act.domain.repository.UserRepository
import com.automatedcontacttracing.act.domain.user.param.CreateUserParam
import com.automatedcontacttracing.act.domain.user.response.CreateUserResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service : UserService) : UserRepository, BaseRepository() {


    override suspend fun createUser(param: CreateUserParam): BaseResponse<CreateUserResponse> {
        return safeApiCall(
            call = {service.createUser(param)},
            defaultErrorMessage = "Something went wrong!"
        )
    }
}