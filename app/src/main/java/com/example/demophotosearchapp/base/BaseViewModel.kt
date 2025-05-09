package com.example.demophotosearchapp.base

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.el.mybasekotlin.data.network.interceptor.NetworkCheckerInterceptor
import com.el.mybasekotlin.data.state.DataState
import com.el.mybasekotlin.data.state.ErrorAction
import com.el.mybasekotlin.data.state.ErrorCode
import com.example.demophotosearchapp.BuildConfig
import com.example.demophotosearchapp.R
import com.example.demophotosearchapp.data.GlobalData
import com.example.demophotosearchapp.data.local.AppPreferences
import com.example.demophotosearchapp.data.repository.local.DatabaseHelper
import com.example.demophotosearchapp.data.repository.network.ApiHelper
import com.example.demophotosearchapp.data.response.BaseDataResponse
import com.example.demophotosearchapp.data.state.SingleLiveEvent
import com.example.demophotosearchapp.utils.extension.getOrBlank
import com.example.demophotosearchapp.utils.extension.mapErrorMessage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject
/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */


abstract class BaseViewModel(
    open val app: Application,
    private val apiHelper: ApiHelper,
) : ViewModel() {
    protected val tag: String = javaClass.simpleName
    var job: Job? = null

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    @Inject
    lateinit var globalData: GlobalData
    protected val sharedPreferences = AppPreferences
    protected var jobCall: Job? = null
    val onError = SingleLiveEvent<Throwable>()
    private val _isLoading = MutableStateFlow(mutableStateOf(false))
    val isLoading = _isLoading.asStateFlow()

    private val _forceLogout = MutableStateFlow<Boolean>(false)
    val forceLogout: StateFlow<Boolean> = _forceLogout.asStateFlow()

    protected fun launchJob(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + createErrorHandler(withOutError = false), start, block)

    /**
     * This launchJob use to call api with response return unstructured
     * Handle exception in @exceptionHandler
     */
    protected fun launchJobCustom(
        exceptionHandler: CoroutineExceptionHandler,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + exceptionHandler, start, block)


    protected fun launchWithoutError(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + createErrorHandler(withOutError = true), start, block)

    protected fun launchLoadingJob(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + createErrorHandler(withOutError = false), start) {
        _isLoading.value = mutableStateOf(true)
        try {
            block()
        } finally {
            _isLoading.value = mutableStateOf(false)
        }
    }

    private fun createErrorHandler(withOutError: Boolean) =
        CoroutineExceptionHandler { _, throwable ->
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace()
            }
            if (throwable !is CancellationException) {
                if (withOutError) {
                    throwable.stackTrace
                } else {
                    onError.postCall(throwable)
                }
            }
        }

    companion object {
        const val SUBSCRIBE_STOP_TIMEOUT = 5000L
    }

    override fun onCleared() {
        super.onCleared()
        jobCall?.cancel()
    }


    /**
     * @param T MutableStateFlow data
     */
    open fun <T> coroutineException(data: MutableStateFlow<DataState<T>>): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->

            if (exception !is CancellationException) {
                when (exception) {
                    is HttpException -> {
                        Timber.e("Handler  coroutineException HttpException")
                        Timber.e("Handler  coroutineException HttpException exception ${exception.message()}")
                        val errorResponse =
                            exception.response()?.errorBody()?.string()?.mapErrorMessage()
                        if (errorResponse != null) {
                            if (errorResponse.errorCode != null) {
                                data.value = DataState.Error(
                                    errorResponse.errorCode.toString(),
                                    errorResponse.message!!,
                                    isException = true
                                )
                            } else if (errorResponse.status != null) {
                                Timber.e("Handler  HttpException $errorResponse?.status ")
                                data.value = DataState.Error(
                                    errorResponse.status.toString(),
                                    errorResponse.message!!,
                                    isException = true
                                )
                            } else data.value = DataState.Error(
                                (-1).toString(),
                                "Lỗi không xác định!",
                                isException = true
                            )
                        } else {
                            data.value = DataState.Error(
                                exception.code().toString(),
                                exception.message(),
                                isException = true
                            )
                            Timber.e("Handler  coroutineException errorResponse null with msg = ${exception.message()} ${exception.code()}")
                        }
                    }

                    else -> {
                        Timber.e("Handler  otherExceptions")
                        otherExceptions(exception, data)
                    }
                }
                data.value = DataState.Empty
            }
        }

    open fun <T> flowCatch(exception: Throwable, data: MutableStateFlow<DataState<T>>, tag:String="") {
        if (exception is HttpException) {
            Timber.e("flowCatch $tag  HttpException ${exception.message}")
            val errorResponse = exception.response()?.errorBody()?.string()
            Timber.e("flowCatch  $tag HttpException errorResponse $errorResponse")
            try {
                if (errorResponse != null) {
                    val errorObject = JSONObject(errorResponse)
                    val errorCode = errorObject.optInt("error_code", -1)
                    val errorMessage = errorObject.optString("message", "Unknown error")
                    data.value =
                        DataState.Error(errorCode.toString(), errorMessage, isException = true)

                    data.value = DataState.Empty
                    return
                }
            } catch (e: Exception) {
                data.value =
                    e.message?.let { DataState.Error(0.toString(), it, isException = true) }!!
                data.value = DataState.Empty
            }
            Timber.e(
                "flowCatch  HttpException2 ${exception.code()} | ${
                    exception.response()?.errorBody()?.string()
                }"
            )
        } else {
            Timber.e("flowCatch  otherExceptions" + exception.message)
            otherExceptions(exception, data)
        }
//        data.value = DataState.Empty
    }


    /**
     * QuangDV edit area here
     */
    open fun <T> otherExceptions(exception: Throwable, data: MutableStateFlow<DataState<T>>) {
        when (exception) {
            is NetworkCheckerInterceptor.NoConnectivityException -> {
                data.value = DataState.Error(
                    ErrorCode.NO_INTERNET.code,
                    message = app.applicationContext.getString(R.string.network_error),
                    isException = false
                )
            }

            else -> {
                Timber.e(
                    "Handler IOException ${exception.message} " + app.applicationContext.getString(
                        R.string.common_error
                    )
                )
                data.value = DataState.Error(
                    400.toString(),
                    message = app.applicationContext.getString(
                        R.string.common_error
                    ),
                    isException = true
                )
            }
        }

        data.value = DataState.Empty
    }


}