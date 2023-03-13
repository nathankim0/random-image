package com.jinyeob.data.feature.sample.remote

import retrofit2.http.GET

interface MyRemoteDataSource {
    @GET("https://api.unsplash.com/photos/?client_id=JanbICZpVwgzFXeEgveBItyBXdfUnCifQ4ClaCByTyM")
    suspend fun getImages(): List<UnsplashImageElement>

    @GET("https://api.unsplash.com/photos/random/?client_id=JanbICZpVwgzFXeEgveBItyBXdfUnCifQ4ClaCByTyM")
    suspend fun getRandomImage(): UnsplashImageElement
}
