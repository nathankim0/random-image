package com.jinyeob.data.settings

import android.content.Context
import androidx.room.Room
import com.jinyeob.nathanks.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            BuildConfig.LIBRARY_PACKAGE_NAME.split(".").last() + ".db"
        ).build()
}
