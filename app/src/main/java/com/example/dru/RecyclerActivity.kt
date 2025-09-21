package com.example.dru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Button
import com.example.dru.models.Offer
import com.example.dru.models.Section
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecyclerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler)

        val offersJson = intent.getStringExtra("offers")
        val offersType = object : TypeToken<List<Offer>>() {}.type
        val offers: List<Offer> = Gson().fromJson(offersJson, offersType) ?: emptyList()

        val sectionJson = intent.getStringExtra("section")
        val section: Section? = if (sectionJson != null) Gson().fromJson(sectionJson, Section::class.java) else null
        val sectionName = section?.name ?: "Section"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdsAdapter(offers)

        findViewById<TextView>(R.id.sectionTitle).text = sectionName

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}