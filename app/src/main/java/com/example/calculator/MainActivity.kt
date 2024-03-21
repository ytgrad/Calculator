package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvOutput : TextView? = null
    private var isLastDigit : Boolean = false
    private var isLastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvOutput = findViewById(R.id.tvOutput)
    }
    fun onDigit(view:View){
        tvOutput?.append((view as Button).text)
        isLastDigit = true
        isLastDot = false
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
    }

    fun onClear(view:View){
        tvOutput?.text = ""
    }

    fun onDot(view:View){
        if(isLastDigit && !isLastDot){
            tvOutput?.append(".")
        }
        isLastDot = true
        isLastDigit = false
    }
}