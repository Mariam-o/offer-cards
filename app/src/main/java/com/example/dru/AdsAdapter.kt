package com.example.dru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Paint
import com.bumptech.glide.Glide
import com.example.dru.models.Offer

class AdsAdapter(private val offers: List<Offer>) : RecyclerView.Adapter<AdsAdapter.ViewHolder>() {

    private var onItemClickListener: ((Offer) -> Unit)? = null

    fun setOnItemClickListener(listener: (Offer) -> Unit) {
        onItemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cardImage)
        val textView: TextView = itemView.findViewById(R.id.cardText)
        val priceButton: TextView = itemView.findViewById(R.id.priceButton)
        val oldPriceText: TextView = itemView.findViewById(R.id.oldPriceText)
        val vendorButton: TextView = itemView.findViewById(R.id.vendorButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = offers[position]
        Glide.with(holder.itemView.context).load(offer.detailImgUrl).into(holder.imageView)
        holder.textView.text = offer.title
        holder.priceButton.text = if (offer.priceAfter != null && offer.currency != null) "${offer.priceAfter} ${offer.currency}" else "N/A"
        holder.oldPriceText.text = if (offer.priceBefore != null && offer.currency != null) "${offer.priceBefore} ${offer.currency}" else ""
        holder.oldPriceText.paintFlags = holder.oldPriceText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.vendorButton.text = offer.vendor ?: "N/A"

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(offer)
        }
    }

    override fun getItemCount(): Int = offers.size
}