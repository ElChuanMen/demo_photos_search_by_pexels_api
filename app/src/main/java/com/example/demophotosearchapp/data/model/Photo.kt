package com.example.demophotosearchapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Photo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("photographer")
    val photographer: String,
    @SerializedName("photographer_url")
    val photographer_url: String,
    @SerializedName("photographer_id")
    val photographer_id: String,
    @SerializedName("avg_color")
    val avg_color: String,
    @SerializedName("src")
    val src: Src,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("alt")
    val alt: String
) : Parcelable

@Serializable
@Parcelize
data class Src(
    @SerializedName("original")
    val original: String,
    @SerializedName("large2x")
    val large2x: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("portrait")
    val portrait: String,
    @SerializedName("landscape")
    val landscape: String,
    @SerializedName("tiny")
    val tiny: String
) : Parcelable
