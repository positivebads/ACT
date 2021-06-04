package com.automatedcontacttracing.act.base.domain

abstract class BaseUseCase<in P, out R> {

    abstract suspend fun execute(param: P): BaseResponse<R>
}