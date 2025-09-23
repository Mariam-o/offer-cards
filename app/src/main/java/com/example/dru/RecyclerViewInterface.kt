package com.example.dru

import com.example.dru.models.Offer
import com.example.dru.models.Section

interface RecyclerViewInterface {
    fun showSectionTitle(name: String)
    fun showOffers(offers: List<Offer>)
    fun showError(message: String)
    fun navigateToMainActivity(offer: Offer)
}