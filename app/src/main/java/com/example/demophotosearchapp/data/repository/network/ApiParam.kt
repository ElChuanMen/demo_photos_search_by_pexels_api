package com.example.demophotosearchapp.data.repository.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ElChuanmen on 5/5/2024.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */

@Parcelize
data class PhotoSearchParams (
    @SerializedName("query") val query: String = "query",
    @SerializedName("per_page") var per_page: Int =20,
    @SerializedName("page") var page: Int = 1,
    @SerializedName("orientation") var orientation: String=""//landscape ,portrait ,square
) : Parcelable

