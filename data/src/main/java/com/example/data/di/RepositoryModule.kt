package com.example.data.di

import com.example.data.remote.ApiService
import com.example.data.repository.NetworkRepoImpl
import com.example.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideNetworkRepo(apiService: ApiService) : NetworkRepository {
        return NetworkRepoImpl(apiService)
    }
}