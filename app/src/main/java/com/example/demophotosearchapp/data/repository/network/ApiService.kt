package com.example.demophotosearchapp.data.repository.network

import com.example.demophotosearchapp.data.model.Photo
import com.example.demophotosearchapp.data.response.BaseDataResponse
import com.example.demophotosearchapp.data.response.BaseResponsePhotoSearch
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query


/**
 * Created by ElChuanmen on 1/16/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
interface ApiService {
//    @GET("v1/search")
//    suspend fun searchPhoto(@HeaderMap map: Map<String,String>, @Body params: PhotoSearchParams): BaseResponsePhotoSearch<MutableList<Photo>>
    @GET("v1/search")
    suspend fun searchPhoto(
                            @Query("query") query:String,
                            @Query("page") page: Int,
                            @Query("per_page") perPage: Int,
                            @Query("orientation") orientation : String): BaseResponsePhotoSearch<MutableList<Photo>>
}