package com.example.poefinal

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poefinal.databinding.ActivityGraphDisplayBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class GraphDisplay : AppCompatActivity() {


    private lateinit var binding: ActivityGraphDisplayBinding
    lateinit var pieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.viewAchi.setOnClickListener {
            startActivity(Intent(this, Achivement::class.java))
        }

        pieChart=findViewById(R.id.pie_chart)


        val list:ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(3f,"Baby Bag"))
        list.add(PieEntry(8f,"Lady Hand Bag"))
        list.add(PieEntry(5f,"Big carrier Bag"))
        list.add(PieEntry(10f,"School Bags"))
        list.add(PieEntry(6f,"Luggage Bag"))

        val pieDataSet= PieDataSet(list,"List")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f

        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "Pie Chart"

        pieChart.centerText="Visual format"

        pieChart.animateY(2000)



    }


}