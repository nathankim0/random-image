package com.jinyeob.data.settings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    // TODO: BASE_URL
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val TIMEOUT_MINUTE: Long = 2

    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_MINUTE, TimeUnit.MINUTES)
        .readTimeout(TIMEOUT_MINUTE, TimeUnit.MINUTES)
        .writeTimeout(TIMEOUT_MINUTE, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        converterFactory: MoshiConverterFactory,
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()
}
