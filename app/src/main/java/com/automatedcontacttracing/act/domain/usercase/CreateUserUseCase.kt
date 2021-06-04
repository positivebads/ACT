package com.automatedcontacttracing.act.domain.usercase

import com.automatedcontacttracing.act.base.domain.BaseResponse
import com.automatedcontacttracing.act.base.domain.BaseUseCase
import com.automatedcontacttracing.act.domain.repository.UserRepository
import com.automatedcontacttracing.act.domain.user.param.CreateUserParam
import com.automatedcontacttracing.act.domain.user.response.CreateUserResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<CreateUserParam, CreateUserResponse>() {
    override suspend fun execute(param: CreateUserParam): BaseResponse<CreateUserResponse> {
        return repository.createUser(param)
    }
}