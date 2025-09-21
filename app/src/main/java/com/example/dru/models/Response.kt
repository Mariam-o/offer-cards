package com.example.dru.models

import com.google.gson.annotations.SerializedName

data class Response(
    val status: Boolean,
    val code: Int,
    val message: String,
    val body: Body
)

data class Body(
    val advertisements: Advertisements
)

data class Advertisements(
    val id: String,
    val name: String,
    @SerializedName("section_order")
    val sectionOrder: Int,
    val advertisements: List<Advertisement>
)

data class Advertisement(
    val id: String,
    @SerializedName("title_en")
    val titleEn: String,
    val description: String,
    @SerializedName("bannerImgUrl")
    val bannerImgUrl: String,
    @SerializedName("detailImgUrl")
    val detailImgUrl: String
)