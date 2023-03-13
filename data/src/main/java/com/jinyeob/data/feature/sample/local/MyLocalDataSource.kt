package com.jinyeob.data.feature.sample.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface MyLocalDataSource {
    @Query("SELECT * FROM sample")
    fun selectAll(): List<SampleEntity>

    @Query("SELECT * FROM sample WHERE id = :id")
    fun selectById(id: Int): SampleEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(sample: SampleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAll(vararg samples: SampleEntity)

    @Delete
    suspend fun delete(sample: SampleEntity)

    @Delete
    suspend fun deleteAll(vararg samples: SampleEntity)

    @Query("DELETE FROM sample WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM sample WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)
}
