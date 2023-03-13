package com.jinyeob.randomimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinyeob.doamin.feature.sample.model.UnsplashImageModel
import com.jinyeob.doamin.feature.sample.repository.MyRepository
import com.jinyeob.randomimage.common.LoadingStatus
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

    private val _getRandomImageLoadingStatus = MutableStateFlow(LoadingStatus.DEFAULT)
    val getRandomImageLoadingStatus: StateFlow<LoadingStatus> = _getRandomImageLoadingStatus

    private val _getImagesLoadingStatus = MutableStateFlow(LoadingStatus.DEFAULT)
    val getImagesLoadingStatus: StateFlow<LoadingStatus> = _getImagesLoadingStatus

    init {
        refreshRandomImage()
    }

    fun setRandomLoadingStatus(loadingStatus: LoadingStatus) {
        _getRandomImageLoadingStatus.value = loadingStatus
    }

    fun refreshImages() {
        viewModelScope.launch {
            _getImagesLoadingStatus.value = LoadingStatus.LOADING
            myRepository.getImages().onSuccess {
                _getImagesLoadingStatus.value = LoadingStatus.SUCCESS
                _imagesState.value = it
                _randomImageState.value = it.random()
            }.onFailure {
                _getImagesLoadingStatus.value = LoadingStatus.FAILURE
            }
        }
    }

    fun refreshRandomImage() {
        viewModelScope.launch {
            _getRandomImageLoadingStatus.value = LoadingStatus.LOADING
            myRepository.getRandomImage().onSuccess {
                _getRandomImageLoadingStatus.value = LoadingStatus.SUCCESS
                _randomImageState.value = it
            }.onFailure {
                _getRandomImageLoadingStatus.value = LoadingStatus.FAILURE
            }
        }
    }

    fun getFreeRandomImage() = myRepository.getFreeRandomImage()

    /**
     * stateIn
     */
//        myRepository.getMyDataFlow().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = emptyList()
//        )
}
