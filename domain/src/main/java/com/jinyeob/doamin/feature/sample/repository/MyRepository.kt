package com.jinyeob.doamin.feature.sample.repository

import com.jinyeob.doamin.feature.sample.model.MyModel
import kotlinx.coroutines.flow.Flow

interface MyRepository {
    suspend fun getMyData(): Result<MyModel>
    suspend fun getMyDataFlow(): Flow<MyModel>
}
