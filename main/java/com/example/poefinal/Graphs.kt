package com.example.poefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poefinal.databinding.ActivityGraphsBinding

class Graphs : AppCompatActivity() {
    private  lateinit var binding: ActivityGraphsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPie.setOnClickListener {
            startActivity(Intent(this, GraphDisplay::class.java))
        }
        binding.viewradar.setOnClickListener {
            startActivity(Intent(this, RadarGraph::class.java))
        }
        binding.viewAch.setOnClickListener {
            startActivity(Intent(this, Achivement::class.java))
        }
    }
}