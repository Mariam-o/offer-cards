package com.example.dru

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.dru.api.ApiService
import com.example.dru.models.Response
import com.example.dru.models.Offer
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecyclerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-api.dru-app.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
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

                    findViewById<TextView>(R.id.sectionTitle).text = sectionName

                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@RecyclerActivity)
                    recyclerView.adapter = AdsAdapter(offers).apply {
                        setOnItemClickListener { offer ->
                            val intent = Intent(this@RecyclerActivity, MainActivity::class.java)
                            intent.putExtra("selectedOffer", Gson().toJson(offer))
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                findViewById<TextView>(R.id.sectionTitle).text = "Error Loading Section"
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this@RecyclerActivity)
                recyclerView.adapter = AdsAdapter(emptyList())
            }
        })
    }
}