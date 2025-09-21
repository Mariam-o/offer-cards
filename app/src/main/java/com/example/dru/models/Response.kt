package com.example.dru.models

import com.google.gson.annotations.SerializedName

data class Response(
    val status: Boolean,
    val code: Int,
    val message: String,
    val body: Body
)

data class Body(
    val advertisements: Advertisements,
    @SerializedName("other_sections")
    val otherSections: List<Section>?
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
    val detailImgUrl: String,
    val action: Action
)

data class Action(
    val type: Int,
    @SerializedName("ctaButton")
    val ctaButton: CtaButton
)

data class CtaButton(
    val text: String?
)

data class Section(
    val id: String,
    val name: String,
    @SerializedName("section_order")
    val sectionOrder: Int,
    val offers: List<Offer>?
)

data class Offer(
    val id: String,
    val title: String,
    @SerializedName("detailImgUrl")
    val detailImgUrl: String,
    @SerializedName("price_before")
    val priceBefore: Int?,
    @SerializedName("price_after")
    val priceAfter: Int?,
    val currency: String?,
    val vendor: String?
)