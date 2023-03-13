package com.jinyeob.data.feature.sample.impl

import com.jinyeob.data.feature.sample.remote.MyRemoteDataSource
import com.jinyeob.data.feature.sample.remote.UnsplashImageElement.Companion.toModel
import com.jinyeob.doamin.feature.sample.model.UnsplashImageModel
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {

    override suspend fun getImages(): Result<List<UnsplashImageModel>> = runCatching {
        myRemoteDataSource.getImages().map { it.toModel() }
    }

    override fun getImagesFlow(): Flow<List<UnsplashImageModel>> = flow {
        runCatching {
            myRemoteDataSource.getImages()
        }.onSuccess { imageElements ->
            emit(imageElements.map { imageElement -> imageElement.toModel() })
        }.onFailure {
            throw it
        }
    }

    override suspend fun getRandomImage(): Result<UnsplashImageModel> = runCatching {
        myRemoteDataSource.getRandomImage().toModel()
    }
}
