package com.example.poefinal

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class itemAdapter(private val itemArrayList1: ArrayList<itemDbm>): RecyclerView.Adapter<itemAdapter.categoryHolder>() {
    class categoryHolder(catView: View): RecyclerView.ViewHolder(catView){
        val ItemName :TextView = catView.findViewById(R.id.itemName)
        val ItemImage:ImageView = catView.findViewById(R.id.imageDisplays)
        val ItemPrice: TextView = catView.findViewById(R.id.itemPrice)
        val discription: TextView = catView.findViewById(R.id.decr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view,
        parent, false)
        return categoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemArrayList1.size
    }

    override fun onBindViewHolder(holder: categoryHolder, position: Int) {
        val currentitem = itemArrayList1[position]
        holder.ItemName.setText(currentitem.itemName.toString())
        holder.ItemPrice.setText(currentitem.itemPrice.toString())
        holder.discription.setText(currentitem.itemDisciption.toString())
        val bytes = android.util.Base64.decode(currentitem.image,
            android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.ItemImage.setImageBitmap(bitmap)

    }

}