package com.example.poefinal

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class categoryAdapter(private val cateList: ArrayList<categoryDbm>): RecyclerView.Adapter<categoryAdapter.categoryHolder>() {
    class categoryHolder(catView: View): RecyclerView.ViewHolder(catView){
        val categoryName:TextView = catView.findViewById(R.id.displayCategory)
        val catImage:ImageView = catView.findViewById(R.id.imageDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_display,
        parent, false)
        return categoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    override fun onBindViewHolder(holder: categoryHolder, position: Int) {
        val currentitem = cateList[position]
        holder.categoryName.setText(currentitem.categoryName.toString())
        val bytes = android.util.Base64.decode(currentitem.image,
            android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.catImage.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            onCategoryItemClick(
                holder.itemView.context, currentitem
            )
        }
    }

    private fun onCategoryItemClick(context: Context, currentitem: categoryDbm) {
       val intent = Intent(context, ItemsView::class.java)

        intent.putExtra("name",currentitem.categoryName)
        context.startActivity(intent)
    }
}