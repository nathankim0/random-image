package com.jinyeob.data.feature.sample.di

import com.jinyeob.data.feature.sample.impl.MyRepositoryImpl
import com.jinyeob.data.feature.sample.local.MyLocalDataSource
import com.jinyeob.data.feature.sample.remote.MyRemoteDataSource
import com.jinyeob.data.settings.LocalDatabase
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MyModule {

    @Binds
    @Singleton
    fun bindsMyRepository(postRepositoryImpl: MyRepositoryImpl): MyRepository

    companion object {
        @Provides
        @Singleton
        fun providesMyRemoteDataSource(retrofit: Retrofit): MyRemoteDataSource = retrofit.create()

        @Provides
        @Singleton
        fun providesDiscoverLocalDataSource(database: LocalDatabase): MyLocalDataSource =
            database.getMyLocalDataSource()
    }
}
