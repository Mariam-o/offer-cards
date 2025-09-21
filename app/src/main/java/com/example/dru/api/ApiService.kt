package com.example.dru.api

import com.example.dru.models.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("x-access-key: 6869000b2c3742489c0a9fff1e0ec951")
    @POST("tab-section-orders/get_all_tab_sections")
    fun getTabSections(@Body body: Map<String, Int>): Call<Response>
}