package com.jinyeob.doamin.feature.sample.model

data class MyModel(
    val weather: List<WeatherModel>
)

data class WeatherModel(
    val id: Long?,
    val main: String?,
    val description: String?,
    val icon: String?,
)
