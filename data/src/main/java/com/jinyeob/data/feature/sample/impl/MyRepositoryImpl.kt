package com.jinyeob.data.feature.sample.impl

import android.util.Log
import com.jinyeob.data.feature.sample.remote.MyRemoteDataSource
import com.jinyeob.data.feature.sample.remote.MyResponseDto.Companion.toModel
import com.jinyeob.doamin.feature.sample.model.MyModel
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {

    override suspend fun getMyData(): Result<MyModel> = runCatching {
        myRemoteDataSource.getMyData().toModel()
    }

    override suspend fun getMyDataFlow(): Flow<MyModel> = flow {
        runCatching {
            myRemoteDataSource.getMyData()
        }.onSuccess {
            emit(it.toModel())
        }.onFailure {
            Log.e("MyRepositoryImpl", "getMyDataFlow: ", it)
        }
    }
}
