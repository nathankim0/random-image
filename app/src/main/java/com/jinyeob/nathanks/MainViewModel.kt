package com.jinyeob.nathanks

import androidx.lifecycle.ViewModel
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myRepository: MyRepository
): ViewModel() {
    suspend fun getMyData() = myRepository.getMyData()
    suspend fun getMyDataFlow() = myRepository.getMyDataFlow()
}
