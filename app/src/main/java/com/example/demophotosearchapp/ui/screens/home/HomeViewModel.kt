package com.example.demophotosearchapp.ui.screens.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.toLowerCase
import com.el.mybasekotlin.data.state.DataState
import com.example.demophotosearchapp.BuildConfig
import com.example.demophotosearchapp.base.BaseViewModel
import com.example.demophotosearchapp.data.model.Photo
import com.example.demophotosearchapp.data.repository.network.ApiHelper
import com.example.demophotosearchapp.data.repository.network.PhotoSearchParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val apiHelper: ApiHelper,
) : BaseViewModel(application, apiHelper) {

    private val _searchPhotoState =
        MutableStateFlow<DataState<MutableList<Photo>>>(DataState.Empty)// listener state
    val searchPhotoState: StateFlow<DataState<MutableList<Photo>>> = _searchPhotoState.asStateFlow()
    private var _dataPhotoSearch = mutableStateListOf<Photo>()
    val dataPhotoSearch: SnapshotStateList<Photo> get() = _dataPhotoSearch
    private var page: Int = 1
    private var perPage: Int = 20
    private var totalResult: Int = 0
    fun reloadSearch(isLoadMore: Boolean, keySearch: String, orientation: String){
        page=1
        _dataPhotoSearch.clear()
        searchPhoto(isLoadMore,keySearch,orientation)
    }
    fun loadMore(isLoadMore: Boolean, keySearch: String, orientation: String){
        page++
        searchPhoto(isLoadMore,keySearch,orientation)
    }

    fun searchPhoto(isLoadMore: Boolean, keySearch: String, orientation: String) {
        launchJobCustom(coroutineException(_searchPhotoState)) {
            _searchPhotoState.value = DataState.Loading
            Timber.d("getListNotice: running!")
            apiHelper.searchPhotos(keySearch,page,perPage,
                orientation.lowercase(Locale.getDefault())
            ).flowOn(Dispatchers.IO).catch { e ->
                flowCatch(e, _searchPhotoState)
            }.collect {
                it.let {
                    _searchPhotoState.value = DataState.Success(it.photos)

                    val photos = it.photos
                    if (!photos.isNullOrEmpty()) {
                        if (isLoadMore) {
                            _dataPhotoSearch.addAll(photos)
                        } else {
                            _dataPhotoSearch.clear()
                            _dataPhotoSearch.addAll(photos)
                        }
                        totalResult = it.totalResults
                    } else {
                        totalResult = 0
                        if (isLoadMore) {
                            page--
                        } else {
                            _dataPhotoSearch.clear()
                        }
                    }
                }
                delay(100)
                _searchPhotoState.value = DataState.Empty

            }
        }
    }

}