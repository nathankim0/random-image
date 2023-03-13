package com.jinyeob.data.settings

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jinyeob.data.feature.sample.local.MyLocalDataSource
import com.jinyeob.data.feature.sample.local.SampleEntity

@Database(
    version = 1,
    entities = [
        SampleEntity::class
    ],
    exportSchema = false
)

internal abstract class LocalDatabase : RoomDatabase() {
    abstract fun getMyLocalDataSource(): MyLocalDataSource
}
