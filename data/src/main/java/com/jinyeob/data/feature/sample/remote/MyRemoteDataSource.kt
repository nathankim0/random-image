package com.jinyeob.data.feature.sample.remote

import retrofit2.http.GET

interface MyRemoteDataSource {
    @GET("weather?q=Seoul&appid=2834387742b25d5393a21e88fee8246a")
    suspend fun getMyData(): MyResponseDto
}
