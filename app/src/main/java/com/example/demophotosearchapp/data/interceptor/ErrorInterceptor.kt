package com.el.mybasekotlin.data.network.interceptor

import android.content.Context
import android.content.Intent
import com.el.mybasekotlin.data.state.ErrorAction
import com.el.mybasekotlin.data.state.ErrorCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException


/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
class ErrorInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        // todo deal with the issues the way you need to
//        Timber.d("ErrorInterceptor response : $response")
        if (response.code == 500) {
            Timber.d("ErrorInterceptor code : 500")
            return response
        }
        if (response.code == 401) { //Start to login activit
            Timber.d("ErrorInterceptor code : 401")

            return response
        }
        response.peekBody(Long.MAX_VALUE).let {
            val jsonResponse = JSONObject(it.string())
            val code = jsonResponse.optString("code", "")
//            Timber.d("ErrorInterceptor jsonResponse : $jsonResponse")
            when (code) {
                ErrorCode.AUTHENTICATION_FAILED.code -> {
                    Timber.d("ErrorInterceptor Need to logout because Token is Expired")
                    val intent = Intent(ErrorAction.ACTION_FORCE_LOGOUT)
//                intent.setPackage("com.el.mybasekotlin")
//                    context.sendBroadcast(intent) // Send broadcast
//
//                    val errorBody = response.peekBody(Long.MAX_VALUE).string()
//                    val exception = IOException("HTTP error ${response.code}: $errorBody")
//
//                    kotlinx.coroutines.runBlocking {
//
//                        ErrorHandler.emitError(exception)
//                    }
                }

            }
        }


        return response
    }
}
object ErrorHandler {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> = _errorFlow

    suspend fun emitError(throwable: Throwable) {
        _errorFlow.emit(throwable)
    }
}