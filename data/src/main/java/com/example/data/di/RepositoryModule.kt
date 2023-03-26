package com.example.data.di

import com.example.data.local.FavoriteDao
import com.example.data.remote.ApiService
import com.example.data.repository.LocalRepoImpl
import com.example.data.repository.NetworkRepoImpl
import com.example.domain.repository.LocalRepository
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

    @Provides
    fun provideLocalRepo(favoriteDao: FavoriteDao) : LocalRepository {
        return LocalRepoImpl(favoriteDao)
    }
}