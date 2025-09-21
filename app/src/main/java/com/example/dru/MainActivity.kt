package com.example.dru

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dru.api.ApiService
import com.example.dru.models.Response
import com.example.dru.models.Section
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

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
                    val ads = resp?.body?.advertisements?.advertisements
                    val otherSections = resp?.body?.otherSections ?: emptyList()
                    val sectionIndex = 0
                    val summerSection = if (otherSections.isNotEmpty()) otherSections[sectionIndex] else null
                    val summerOffers = summerSection?.offers
                    if (ads != null && ads.size > 1) {
                        val ad = ads[1] // 2nd
                        val imageView = findViewById<ImageView>(R.id.imageView2)
                        Glide.with(this@MainActivity).load(ad.detailImgUrl).into(imageView)
                        findViewById<TextView>(R.id.textView).text = ad.titleEn
                        findViewById<TextView>(R.id.textView4).text = ad.description
                        findViewById<Button>(R.id.button3).text = ad.action.ctaButton.text ?: "Get offer"
                        findViewById<Button>(R.id.button3).setOnClickListener {
                            val intent = Intent(this@MainActivity, RecyclerActivity::class.java)
                            intent.putExtra("offers", Gson().toJson(summerOffers))
                            intent.putExtra("section", Gson().toJson(summerSection))
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                // Handle error
            }
        })
    }
}