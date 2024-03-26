package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var expression: Expression

    private var isLastDot = false
    private var isLastDigit = false
    private var stateError = false
    private var isDotAllowed = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun onDigit(view: View) {

        if(stateError){
            binding.tvInput.text = (view as Button).text
            stateError = false
        }else{
            binding.tvInput.append((view as Button).text)
        }
        isLastDigit = true
        isLastDot = false
        onEqual()
    }

    fun onClearAll(view: View) {
        binding.tvInput.text = ""
        binding.tvOutput.text = ""
        isLastDigit = false
        stateError = false
        isLastDot = false
        isDotAllowed = true
        binding.tvOutput.visibility = View.GONE
    }

    fun onEqualClick(view: View) {
        onEqual()
        if(binding.tvOutput.text.toString().endsWith(".0")){
            binding.tvInput.text = binding.tvOutput.text.toString().dropLast(2)
        }else{
            binding.tvInput.text = binding.tvOutput.text.toString()
        }

    }

    fun onBackspace(view: View) {
        binding.tvInput.text = binding.tvInput.text.toString().dropLast(1)
        try {
            val lastChar = binding.tvInput.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
            if(lastChar.toString() == "."){
                isDotAllowed = true
                isLastDot = false
            }
        }catch (e: Exception){
            binding.tvOutput.text = ""
            binding.tvOutput.visibility = View.GONE
            Log.e("last Char error", e.toString())
        }
    }

    fun onOperator(view: View) {

        if(!stateError && isLastDigit){
            binding.tvInput.append((view as Button).text)
            isDotAllowed = true
            isLastDigit = false
            isLastDot = false
            onEqual()
        }

    }
    private fun onEqual(){
        if(isLastDigit && !stateError){
            val inputString = binding.tvInput.text.toString().replace("x", "*")
            expression = ExpressionBuilder(inputString).build()

            try {
                val result = expression.evaluate()
                binding.tvOutput.visibility = View.VISIBLE
                binding.tvOutput.text = result.toString()
            }catch (ex: ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.tvOutput.text = "error"
                isLastDigit = false
                stateError = true
            }

        }
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        isLastDigit = false
        isLastDot = false
    }

    fun onDot(view: View) {
        if(isDotAllowed && !isLastDot){
            binding.tvInput.append(".")
            isLastDot=true
        }
        isDotAllowed = false
    }
}