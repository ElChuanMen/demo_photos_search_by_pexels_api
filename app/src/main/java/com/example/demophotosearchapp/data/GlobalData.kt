package com.example.demophotosearchapp.data


import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@Singleton
class GlobalData @Inject constructor() {
//    var settingList: MutableList<Setting> = arrayListOf()
//    var user: MutableList<UserResponse> = arrayListOf()
//    fun getInfoUser(): MutableList<UserResponse> = user.ifEmpty { AppPreferences.user ?: arrayListOf(UserResponse()) }
//    fun getSettingsList(): MutableList<Setting> =
//        settingList.ifEmpty { AppPreferences.settingList ?: arrayListOf() }
//
//    fun fetchSettingList(): MutableList<Setting> =
//        settingList.ifEmpty { AppPreferences.settingList ?: arrayListOf() }
}
enum class DownloadStatus {
    IDLE,
    DOWNLOADING,
    SUCCESS,
    ERROR;


}