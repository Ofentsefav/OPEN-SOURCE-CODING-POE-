package com.example.poefinal

import android.widget.EditText

data class categoryDbm(val categoryName: String? = "",
                       val image: String? = "",
                       val goal:String? ="")
data class itemDbm(
    val itemName: String? = "",
    val itemDisciption: String? = "",
    val itemPrice: String? = "",
    val categoryName: String? = "",
    val image: String?="")
