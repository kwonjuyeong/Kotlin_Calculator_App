package com.example.kotlincalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var Input: TextView ?= null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    /*원래 정석 코드 1
    private  var btnOne : Button ?= null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Input = findViewById(R.id.Input)

        /*원래 정석 코드 2 = 이렇게 모든 버튼에 onClickListener를 달아줘야함.
        btnOne = findViewById(R.id.btnOne)
        btnOne?.setOnClickListener{
            Input?.append("1")
        }*/

    }

    //onDigit을 통해 간단하게 만듬.
    fun onDigit(view: View){
        Input?.append((view as Button).text)
        lastNumeric = true
        lastDot = false

    }

    fun onClear(view:View){
        Input?.text = ""
    }

    fun onDemicalPoint(view:View){
        if(lastNumeric && !lastDot){
            Input?.append(".")
            //Flag
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        Input?.text?.let {

            if (lastNumeric && !isOperatorAdded(it.toString())){
                Input?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }

        }
    }
    fun onEqual(view: View) {
        if (lastNumeric) {
            var toValue = Input?.text.toString()
            var prefix = ""
            try{
                if(toValue.startsWith("-")){
                    prefix = "-"
                    toValue = toValue.substring(1)
                }
                if(toValue.contains("-")){
                    val splitValue = toValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result =  one.toDouble() - two.toDouble()
                    Input?.text = removeZeroAfterDot(result.toString())
                }

                else if(toValue.contains("+")){
                    val splitValue = toValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result =  one.toDouble() + two.toDouble()
                    Input?.text = removeZeroAfterDot(result.toString())
                }

                else if(toValue.contains("/")){
                    val splitValue = toValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result =  one.toDouble() / two.toDouble()
                    Input?.text = removeZeroAfterDot(result.toString())
                }

                else if(toValue.contains("x")){
                    val splitValue = toValue.split("x")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result =  one.toDouble() * two.toDouble()
                    Input?.text = removeZeroAfterDot(result.toString())
                }


            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result:String) :String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    private fun isOperatorAdded(value : String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    ||value.contains("x")
                    ||value.contains("+")
                    ||value.contains("-")

        }

    }
}