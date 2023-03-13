package com.jinyeob.doamin.feature.sample.repository

import com.jinyeob.doamin.feature.sample.model.UnsplashImageModel
import kotlinx.coroutines.flow.Flow

interface MyRepository {
    suspend fun getImages(): Result<List<UnsplashImageModel>>
    fun getImagesFlow(): Flow<List<UnsplashImageModel>>

    suspend fun getRandomImage(): Result<UnsplashImageModel>

    fun getFreeRandomImage(): String
}
