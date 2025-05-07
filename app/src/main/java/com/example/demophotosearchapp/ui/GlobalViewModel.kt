package com.example.demophotosearchapp.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.app.Application
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.demophotosearchapp.base.BaseViewModel
import com.example.demophotosearchapp.data.repository.network.ApiHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@HiltViewModel
class GlobalViewModel @Inject constructor(private val apiHelper: ApiHelper, app: Application) :
    BaseViewModel(app, apiHelper) {

    private val _overlayStateFlow =
        MutableSharedFlow<MutableState<@Composable (BoxScope.() -> Unit)?>>()
    val overlayStateFlow = _overlayStateFlow.asSharedFlow()

    //    val appOverlayContent = mutableStateOf<@Composable (BoxScope.() -> Unit)?>(null)
    fun showOverlayContent(content: @Composable (BoxScope.() -> Unit)?) {
        launchJob {
            _overlayStateFlow.emit(mutableStateOf(content))
        }
    }

    private val _globalBlur = MutableStateFlow(false)
    val globalBlur: StateFlow<Boolean> = _globalBlur

    fun requestGlobalBlur(enable: Boolean) {
        _globalBlur.value = enable
    }

    //    private val _globalBlur = MutableStateFlow(mutableStateOf(false))
//    val globalBlur = _globalBlur.asStateFlow()
//    fun requestGlobalBlur(enable: Boolean) {
//        _globalBlur.value = mutableStateOf(enable)
//    }


//    private val _networkActive = MutableStateFlow(mutableStateOf(false))
//    val networkActive = _networkActive.asStateFlow()




    private val _networkActive = MutableStateFlow(false)
    val networkActive: StateFlow<Boolean> = _networkActive

    fun setNetworkState(enable: Boolean) {
        _networkActive.value = enable
    }

}

val LocalGlobalViewModel = staticCompositionLocalOf<GlobalViewModel> {
    error("No GlobalViewModel provided")
}