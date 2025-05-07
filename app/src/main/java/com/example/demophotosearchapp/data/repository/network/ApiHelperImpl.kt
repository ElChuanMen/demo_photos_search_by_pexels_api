package com.example.demophotosearchapp.data.repository.network


import com.example.demophotosearchapp.data.model.Photo
import com.example.demophotosearchapp.data.response.BaseResponsePhotoSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@Singleton
open class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override fun searchPhotos( query:String, page: Int, perPage: Int, orientation : String
    ): Flow<BaseResponsePhotoSearch<MutableList<Photo>>> {
        return flow { emit(apiService.searchPhoto( query,page,perPage,orientation)) }
    }
}