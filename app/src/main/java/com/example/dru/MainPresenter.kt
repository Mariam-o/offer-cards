package com.example.dru

import com.example.dru.models.Offer
import com.google.gson.Gson

class MainPresenter(private val view: MainViewInterface) {
    fun bindOfferData(intent: android.content.Intent) {
        val offerJson = intent.getStringExtra("selectedOffer")
        val offer: Offer? = if (offerJson != null) Gson().fromJson(offerJson, Offer::class.java) else null

        if (offer != null) {
            view.showOfferData(offer)
        }
    }
}