package com.example.dru

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dru.models.Offer
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val offerJson = intent.getStringExtra("selectedOffer")
        val offer: Offer? = if (offerJson != null) Gson().fromJson(offerJson, Offer::class.java) else null

        if (offer != null) {
            val imageView = findViewById<ImageView>(R.id.imageView2)
            Glide.with(this@MainActivity).load(offer.detailImgUrl).into(imageView)
            findViewById<TextView>(R.id.textView).text = offer.title
            findViewById<TextView>(R.id.textView4).text = offer.description ?: ""
            findViewById<Button>(R.id.button3).text = offer.action?.ctaButton?.text ?: "Get offer"
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}