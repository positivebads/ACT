package com.automatedcontacttracing.act.di

import com.automatedcontacttracing.act.data.repository.UserRepositoryImpl
import com.automatedcontacttracing.act.data.service.UserService
import com.automatedcontacttracing.act.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideUserRepository(service: UserService): UserRepository = UserRepositoryImpl(service)
}