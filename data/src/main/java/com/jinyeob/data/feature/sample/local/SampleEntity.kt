package com.jinyeob.data.feature.sample.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample")
internal data class SampleEntity(
    @PrimaryKey val id: Int,
    val name: String = ""
)
