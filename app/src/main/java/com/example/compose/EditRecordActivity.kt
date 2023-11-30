package com.example.compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.compose.API.ApiRequest
import com.example.compose.databinding.EditRecordBinding
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class EditRecordActivity : AppCompatActivity() {

    private lateinit var tvSolution: TextView
    private lateinit var tvResult: TextView

    private var canAddOperation = false
    private var canAddDecimal = true

    private var convertRates: Double = 1.0
    private var selectedCurrencyCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_record)

        initializeViews()
        val sp = findViewById<Spinner>(R.id.spCurrency)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position) as String
                selectedCurrencyCode = selectedItem // Update the selected currency code
                fetchCurrencyData().start()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val url = URL("https://open.er-api.com/v6/latest/hkd")
            val connection  = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, ApiRequest::class.java)
                updateRate(request)
                inputStreamReader.close()
                inputSystem.close()
            }
        }
    }
    private fun updateRate(request: ApiRequest)
    {
        runOnUiThread {
            kotlin.run {
                if (selectedCurrencyCode != null) {
                    convertRates = when (selectedCurrencyCode) {
                        "JPY" -> request.rates.JPY
                        "USD" -> request.rates.USD
                        "EUR" -> request.rates.EUR
                        "TWD" -> request.rates.TWD
                        "GBP" -> request.rates.GBP
                        "CNY" -> request.rates.CNY
                        else -> 1.0 // Handle the case when the selected currency code is not found
                    }
                    Log.i("ConvertRates", "Value: $convertRates")
                    tvResult.setText(selectedCurrencyCode)
                }
            }
        }
    }
    private fun newInput(tv: TextView,num: String) {
        tv.append(num)
    }
    private fun initializeViews() {
        tvSolution = findViewById(R.id.tvSolution)
        tvResult = findViewById(R.id.tvResult)
    }

    fun numberAction(view: View)
    {
        if(view is MaterialButton)
        {
            if(view.text.toString() == ".")
            {
                if(canAddDecimal)
                    tvSolution.append(view.text.toString())

                canAddDecimal = false
            }
            else
                tvSolution.append(view.text.toString())

            canAddOperation = true
        }
    }

    fun operationAction(view: View)
    {
        if(view is Button && canAddOperation)
        {
            tvSolution.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View)
    {
        tvSolution.text = ""
        tvResult.text = ""
    }

    fun backSpaceAction(view: View)
    {
        val length = tvSolution.length()
        if(length > 0)
            tvSolution.text = tvSolution.text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View)
    {
        val result = calculateResults().toDouble()
        tvResult.text = String.format("%s: %.2f", selectedCurrencyCode, result)
    }

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision) * convertRates
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('X') || list.contains("@string/cal_num_divide") )
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'X' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    "@string/cal_num_divide"->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in tvSolution.text)
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }
}