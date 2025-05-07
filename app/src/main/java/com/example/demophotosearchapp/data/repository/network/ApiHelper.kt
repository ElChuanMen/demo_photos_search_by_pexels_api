package com.example.demophotosearchapp.data.repository.network

import com.example.demophotosearchapp.data.model.Photo
import com.example.demophotosearchapp.data.response.BaseDataResponse
import com.example.demophotosearchapp.data.response.BaseResponsePhotoSearch
import kotlinx.coroutines.flow.Flow
import retrofit2.http.HeaderMap
import retrofit2.http.Query


/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
interface ApiHelper {
    fun searchPhotos( query:String,page: Int, perPage: Int, orientation : String): Flow<BaseResponsePhotoSearch<MutableList<Photo>>>

}