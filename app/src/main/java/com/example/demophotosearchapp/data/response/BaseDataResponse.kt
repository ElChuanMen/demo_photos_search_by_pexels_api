package com.example.demophotosearchapp.data.response

import com.el.mybasekotlin.data.state.ResponseCode
import com.google.gson.annotations.SerializedName

class BaseDataResponse<T>(
    @SerializedName("message") var message: String = "",
    @SerializedName(value = "error_code", alternate = ["err_code"]) var errorCode: String? = "",
    @SerializedName("status") val status: Int,
    @SerializedName("code") val code: String?="",
    @SerializedName("data") val data: T? = null
) {
    val isSuccess: Boolean
        get() = status == ResponseCode.SERVER_SUCCESS.code && errorCode == "0"

    val isFailed: Boolean
        get() = errorCode != ResponseCode.SERVER_SUCCESS.code.toString()

    val isDataNotNull: Boolean
        get() = data != null
}
class BaseResponsePhotoSearch<T>(
    @SerializedName("page") var page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 20,
    @SerializedName("total_results") val totalResults: Int = 20,
    @SerializedName("next_page") val nextPage: String = "",
    @SerializedName("photos") val photos: T? = null
)
