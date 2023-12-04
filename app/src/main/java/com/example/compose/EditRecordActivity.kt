package com.example.compose

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yml.charts.common.extensions.isNotNull
import com.example.compose.API.ApiRequest
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.GridAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import javax.net.ssl.HttpsURLConnection

class EditRecordActivity : AppCompatActivity() {

    private lateinit var tvSolution: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvCurCode: TextView
    private lateinit var etRecorName: EditText
    private lateinit var ibInfo: ImageButton

    private lateinit var ibtnSelectDate : ImageButton
    private var shownDateView: Boolean = false
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//    private  var selectedDate: String = dateFormat.format(Date(Instant.now().toEpochMilli()))
    private  var selectedDate: Date = Date(Instant.now().toEpochMilli())

    private lateinit var btnReturn : androidx.appcompat.widget.AppCompatImageButton
    private lateinit var headerLayout : ConstraintLayout

    private var canAddOperation = false
    private var canAddDecimal = true

    private var convertRates: Double = 1.0
    private var selectedCurrencyCode: String? = null

    private lateinit var dataViewModel: DataViewModel
    private lateinit var gridViewAdapter : GridAdapter
    private lateinit var gvCategory: GridView
    private lateinit var switchCategory: androidx.appcompat.widget.SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_record)

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        initializeViews()

        dataViewModel.readCategoryByType(false).observe(this, Observer{categories ->
            gridViewAdapter.setData(categories)
        })
        //switch icons of Expense or Income
        if(switchCategory != null){
            switchCategory.setOnCheckedChangeListener { _, checked ->
                when {
                    checked -> {}
                    else -> {}
                }
                dataViewModel.readCategoryByType(checked).observe(this, Observer{categories ->
                    gridViewAdapter.setData(categories)
                })
            }
        }

        ibInfo = findViewById<ImageButton>(R.id.ibInfo)
        ibInfo.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle("Instruction")
                .setMessage("1.)\tPick a category\n2.)\tEnter the amount, click = to set value\n3.)\tEnter record name(Optional)\n4.)\tClick OK to confirm")
//                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                    // Respond to negative button press
//                }
                .setNeutralButton("Got it!") { dialog, which ->
                    // Respond to positive button press
                }
                .show()
        }
        //icons onClick
        gvCategory.onItemClickListener

        ibtnSelectDate.setOnClickListener{
            // DATE picker
            val datePickerView = findViewById<ComposeView>(R.id.composeViewDatePicker)
            if(!shownDateView){
                datePickerView.setContent {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(Color.LightGray)
                    ) {
                        DatePickerView()
                    }
                }
                shownDateView = true
            }else{
                datePickerView.setContent {}
                //tvResult.text = selectedDate //for debug print only
                shownDateView = false
            }
        }
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
                tvCurCode.text = String.format("%s:",selectedCurrencyCode)
                fetchCurrencyData().start()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


//        headerLayout = findViewById<ConstraintLayout>(R.id.header_edit_record)
//        btnReturn = headerLayout.findViewById<Button>(R.id.backToHome)
        btnReturn.setOnClickListener{
            finish()
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
                }
            }
        }
    }

    private fun initializeViews() {
        tvSolution = findViewById(R.id.tvSolution)
        tvResult = findViewById(R.id.tvResult)
        tvCurCode = findViewById(R.id.tvCurCode)
        ibtnSelectDate = findViewById(R.id.ibtnSelectDate)
        etRecorName = findViewById(R.id.etRecordName)

        headerLayout = findViewById<ConstraintLayout>(R.id.header_edit_record)
        btnReturn = headerLayout.findViewById(R.id.backToHome)
        switchCategory = headerLayout.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        Log.d("KYS", switchCategory.toString())

        gvCategory = findViewById<GridView>(R.id.gvCategory)
        gridViewAdapter = GridAdapter()
        gvCategory.adapter = gridViewAdapter
//
//        dataViewModel.readCategoryByType(false).observe(this, Observer{categories ->
//            gridViewAdapter.setData(categories)
//        })
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

        var num_result: Double = 0.0
        val result = calculateResults()
        if (result!=""){
            num_result = result.toDouble()
        }
//        tvResult.text = String.format("%s to HKD: %.2f", selectedCurrencyCode, result)
        tvResult.text = String.format("HKD: %.2f", num_result)
    }

    fun confrimAddRecord(view: View) {
        val cat = gridViewAdapter.getSelectedCategory().takeIf { it.isNotNull() } ?: null
        // SAMPLE: Record(0,  "GTA6"     , Category(0,"games", R.drawable.ic_cat_entertainment, false)   , "200", Date(30-11-2023))
        if(  cat.isNotNull() ){
            val RecordName =  etRecorName.text.toString().takeIf { it.isNotBlank() } ?: cat?.name
            val record = cat?.let {
                Record(0, RecordName,
                    it,  tvResult.text.toString().substringAfter(": ").trim(), selectedDate)
            }
            if (record != null) {
                dataViewModel.addRecord(record)
            }
        }else{
            Toast.makeText( this, "Please select category", Toast.LENGTH_SHORT).show()
        }

    }

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""
        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""
        val result = addSubtractCalculate(timesDivision) / convertRates //Other currency to HKD
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

        if(passedList.isNotNull()) {
            for (i in passedList.indices) {
                if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                    val operator = passedList[i]
                    val prevDigit = passedList[i - 1] as Float
                    val nextDigit = passedList[i + 1] as Float
                    when (operator) {
                        'X' -> {
                            newList.add(prevDigit * nextDigit)
                            restartIndex = i + 1
                        }

                        "@string/cal_num_divide" -> {
                            newList.add(prevDigit / nextDigit)
                            restartIndex = i + 1
                        }

                        else -> {
                            newList.add(prevDigit)
                            newList.add(operator)
                        }
                    }
                }

                if (i > restartIndex)
                    newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        if(tvSolution.text != ""){
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
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private  fun DatePickerView() {

        val datePickerState = remember {
            DatePickerState(
                yearRange = (2023..2024),
                initialSelectedDateMillis = Instant.now().toEpochMilli(),
                initialDisplayMode = DisplayMode.Input,
                initialDisplayedMonthMillis = null
            )
        }

        DatePicker(
            state = datePickerState,
            dateValidator = { timestamp ->
                // Disable all the days after today
                timestamp <= Instant.now().toEpochMilli()},
            showModeToggle = true, // allow input mode or picker
        )

        //selectedDate = dateFormat.format(Date(datePickerState.selectedDateMillis ?: 0))// gave up database store srting
        selectedDate = Date(datePickerState.selectedDateMillis ?: 0)

    }
}