package com.jinyeob.data.feature.sample.remote

import com.jinyeob.doamin.feature.sample.model.MyModel
import com.jinyeob.doamin.feature.sample.model.WeatherModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyResponseDto(
    @Json(name="weather")
    val weather: List<WeatherDto?>,

    @Json(name="coord")
    val coord: Coord?,

    @Json(name="base")
    val base: String?,

    @Json(name="main")
    val main: Main?,

    @Json(name="visibility")
    val visibility: Long?,

    @Json(name="wind")
    val wind: Wind?,

    @Json(name="clouds")
    val clouds: Clouds?,

    @Json(name="dt")
    val dt: Long?,

    @Json(name="sys")
    val sys: Sys?,

    @Json(name="timezone")
    val timezone: Long?,

    @Json(name="id")
    val id: Long?,

    @Json(name="name")
    val name: String?,

    @Json(name="cod")
    val cod: Long?
) {
    companion object {
        fun MyResponseDto.toModel() = MyModel(
            weather = weather.map {
                WeatherModel(
                    id = it?.id,
                    main = it?.main,
                    description = it?.description,
                    icon = it?.icon,
                )
            }
        )
    }
}

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @Json(name="id") val id: Long?,
    @Json(name="main") val main: String?,
    @Json(name="description") val description: String?,
    @Json(name="icon") val icon: String?,
)

@JsonClass(generateAdapter = true)
data class Clouds (
    @Json(name = "all")
    val all: Long?
)

@JsonClass(generateAdapter = true)
data class Coord (
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "lat")
    val lat: Double?
)

@JsonClass(generateAdapter = true)
data class Main (
    @Json(name = "temp")
    val temp: Double?,

    @Json(name = "feels_like")
    val feelsLike: Double?,

    @Json(name = "temp_min")
    val tempMin: Double?,

    @Json(name = "temp_max")
    val tempMax: Double?,

    @Json(name = "pressure")
    val pressure: Long?,

    @Json(name = "humidity")
    val humidity: Long?,

    @Json(name = "sea_level")
    val seaLevel: Long?,

    @Json(name = "grnd_level")
    val grndLevel: Long?
)

@JsonClass(generateAdapter = true)
data class Sys (
    @Json(name = "type")
    val type: Long?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "sunrise")
    val sunrise: Long?,
    @Json(name = "sunset")
    val sunset: Long?
)

@JsonClass(generateAdapter = true)
data class Wind (
    @Json(name = "speed")
    val speed: Double?,
    @Json(name = "deg")
    val deg: Long?,
    @Json(name = "gust")
    val gust: Double?
)