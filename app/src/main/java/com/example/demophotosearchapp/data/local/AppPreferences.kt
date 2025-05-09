package com.example.demophotosearchapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import timber.log.Timber

/**
 * Created by ElChuanmen on 7/16/2024.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
object AppPreferences {
    const val NAME = "Demo_searches"
    const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    val gson = Gson()

    // list of app specific preferences
    private val NIGHT_MODE_PREF = Pair("NIGHT_MODE_PREF", AppCompatDelegate.MODE_NIGHT_YES)
    private val LOCALE_STRING_PREF = Pair("LOCALE_STRING_PREF", "en")
    private val ACCESS_TOKEN_PREF = Pair("ACCESS_TOKEN_PREF", "")
    private val SEARCH_HISTORY= Pair("SEARCH_HISTORY", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }


    var accessToken: String
        get() = get<String>(ACCESS_TOKEN_PREF.first) ?: ACCESS_TOKEN_PREF.second
        set(value) = save(ACCESS_TOKEN_PREF.first, value)

    fun clearData() {
        val editor = preferences.edit()
        editor?.clear()
        editor?.apply()
    }
    fun saveSearchQueryJson(newQuery: String) {

        val json = preferences.getString("search_history_json", "[]")
        val history = JSONArray(json)
        val list = mutableListOf<String>()

        for (i in 0 until history.length()) {
            list.add(history.getString(i))
        }

        list.remove(newQuery)
        list.add(0, newQuery)
        val trimmed = list.take(5)

        val newJson = JSONArray(trimmed).toString()
        preferences.edit().putString("search_history_json", newJson).apply()
    }

    fun getSearchHistoryJson(): List<String> {

        val json = preferences.getString("search_history_json", "[]")
        val history = JSONArray(json)
        val list = mutableListOf<String>()
        for (i in 0 until history.length()) {
            list.add(history.getString(i))
        }
        return list
    }





    inline fun <reified T> save(key: String, any: T) {
        val editor = preferences.edit()
        when (any) {
            is String -> editor?.putString(key, any)
            is Float -> editor?.putFloat(key, any)
            is Int -> editor?.putInt(key, any)
            is Long -> editor?.putLong(key, any)
            is Boolean -> editor?.putBoolean(key, any)
            else -> editor?.putString(key, gson.toJson(any))
        }
        editor?.apply()
    }

    inline fun <reified T> get(key: String): T? {
        when (T::class) {
            Float::class -> return preferences.getFloat(key, 0f) as T
            Int::class -> return preferences.getInt(key, 0) as T
            Long::class -> return preferences.getLong(key, 0) as T
            String::class -> return preferences.getString(key, "") as T
            Boolean::class -> return preferences.getBoolean(key, false) as T
            else -> {
                val any = preferences.getString(key, "")
                val type = object : TypeToken<T>() {}.type
                if (!any.isNullOrEmpty()) {
                    return gson.fromJson<T>(any, type)
                }
            }
        }
        return null
    }


}