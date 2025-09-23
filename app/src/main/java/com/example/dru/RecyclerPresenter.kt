package com.example.dru

import com.example.dru.api.ApiService
import com.example.dru.models.Offer
import com.example.dru.models.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecyclerPresenter(private val view: RecyclerViewInterface) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dev-api.dru-app.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    fun loadData() {
        val body = mapOf("user_id" to 5353)
        val call = api.getTabSections(body)

        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: RetrofitResponse<Response>) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    val otherSections = resp?.body?.otherSections ?: emptyList()
                    val summerSection = if (otherSections.isNotEmpty()) otherSections[0] else null
                    val sectionName = summerSection?.name ?: "Section"
                    val offers = summerSection?.offers ?: emptyList()

                    view.showSectionTitle(sectionName)
                    view.showOffers(offers)
                } else {
                    view.showError("Failed to load data")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                view.showError("Network error: ${t.message}")
            }
        })
    }

    fun onOfferClick(offer: Offer) {
        view.navigateToMainActivity(offer)
    }
}