package com.jinyeob.randomimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinyeob.doamin.feature.sample.model.UnsplashImageModel
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _imagesState = MutableStateFlow<List<UnsplashImageModel>>(emptyList())
    val imagesState: StateFlow<List<UnsplashImageModel>> = _imagesState

    private val _randomImageState = MutableStateFlow(UnsplashImageModel())
    val randomImageState: StateFlow<UnsplashImageModel> = _randomImageState

    init {
        refreshRandomImage()
    }

    fun refreshImages() {
        viewModelScope.launch {
            myRepository.getImages().onSuccess {
                _imagesState.value = it
            }
        }
    }

    fun refreshRandomImage() {
        viewModelScope.launch {
            myRepository.getRandomImage().onSuccess {
                _randomImageState.value = it
            }
        }
    }

    /**
     * stateIn
     */
//        myRepository.getMyDataFlow().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = emptyList()
//        )
}
