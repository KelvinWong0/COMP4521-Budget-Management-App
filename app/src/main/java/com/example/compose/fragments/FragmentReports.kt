package com.example.compose.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import co.yml.charts.ui.piechart.utils.proportion
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.ListAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Random

class FragmentReports : Fragment(R.layout.fragment_report){

    private lateinit var dataViewModel: DataViewModel
    private var adapter = ListAdapter()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = ComposeView(requireContext())
//        view.apply {
//            setContent {
//                Text(text = "Compose created")
//            }
//        }
//        return view
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sbtnExpense = view.findViewById<Button>(R.id.btn_report_cat_1)
        val sbtnIncome = view.findViewById<Button>((R.id.btn_report_cat_2))
        val sbtnBalance = view.findViewById<Button>(R.id.btn_report_cat_3)
        val composeView = view.findViewById<ComposeView>(R.id.composeViewChart)

        var selectedMonth: Int = 0

        // Month Picker
        val sp = view.findViewById<Spinner>(R.id.spMonthSelect)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            view.context,
            R.array.month_code,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
        val calendar = Calendar.getInstance()
        val monthAbbreviation = SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.time)
        val itemCount: Int = adapter.count

        for (i in 0 until itemCount) {
            val item: CharSequence? = adapter.getItem(i)
            if (item != null && item.toString() == monthAbbreviation) {
                sp.setSelection(i)
                selectedMonth = i
                break
            }
        }

        //Log.i("Initial Month", selectedMonth)
        //sp.setSelection()
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position) as String
                selectedMonth = position // Update the selected currency code
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //Default
        composeView.setContent {
            errorShowCharts("Please select the chart to display")
        }
        // Switch between Charts
        //show Expense chart
        sbtnExpense.setOnClickListener {
            val usedColors = mutableSetOf<Color>()
            var recordList: List<Record>
            var pieSlices:  List<PieChartData.Slice> = emptyList()
            var donutChartData: PieChartData

            val now = Calendar.getInstance()
            val calendar = setCalendarMonth(now, selectedMonth)
            //calendar.set(Calendar.MONTH, Calendar.JANUARY)
//            calendar.set(Calendar.HOUR_OF_DAY, 0); // Set hours to 0
//            calendar.set(Calendar.MINUTE, 0); // Set minutes to 0
//            calendar.set(Calendar.SECOND, 0); // Set seconds to 0
//            calendar.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0
//            calendar.set(Calendar.DAY_OF_MONTH,1)
            Log.i("Month", calendar.time.toString())
            val startOfMonth = calendar.time
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = calendar.time

            dataViewModel.readMonthWithRecordsByType(startOfMonth, startOfNextMonth ,false).observe(viewLifecycleOwner, Observer{ records ->
                recordList = records

                pieSlices = recordList.map { Record ->
                    val random = Random()
                    var color = Color(
                        red = random.nextInt(),
                        green = random.nextInt(),
                        blue = random.nextInt()
                    )
                    while (usedColors.contains(color)) {
                        color = Color(
                            red = random.nextInt(),
                            green = random.nextInt(),
                            blue = random.nextInt()
                        ) // Generate a new color if it is already used
                    }
                    usedColors.add(color)
                    PieChartData.Slice(Record.category.name, Record.amount.toFloat(), color)
                }

                donutChartData = PieChartData(
                    slices = pieSlices,
                    plotType = PlotType.Donut
                )

                if(pieSlices.isNotEmpty()){
                    composeView.setContent {
                        DountChart(requireContext(), donutChartData)
                    }
                }else{
                    composeView.setContent {
                        errorShowCharts("Too few records to generate Pie Chart")
                    }
                }

            })

        }
        //show Income chart
        sbtnIncome.setOnClickListener {
            val usedColors = mutableSetOf<Color>()
            var recordList: List<Record>
            var pieSlices:  List<PieChartData.Slice> = emptyList()
            var donutChartData: PieChartData

            val now = Calendar.getInstance()
            val calendar = setCalendarMonth(now, selectedMonth)
//            calendar.set(Calendar.HOUR_OF_DAY, 0); // Set hours to 0
//            calendar.set(Calendar.MINUTE, 0); // Set minutes to 0
//            calendar.set(Calendar.SECOND, 0); // Set seconds to 0
//            calendar.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0
//            calendar.set(Calendar.DAY_OF_MONTH,1)
            //Log.i("Month", calendar.time.toString())
            val startOfMonth = calendar.time
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = calendar.time

            dataViewModel.readMonthWithRecordsByType(startOfMonth, startOfNextMonth ,true).observe(viewLifecycleOwner, Observer{ records ->
                recordList = records
                //Log.i("Bruh", recordList.toString())
                pieSlices = recordList.map { Record ->
                    val random = Random()
                    var color = Color(
                        red = random.nextInt(),
                        green = random.nextInt(),
                        blue = random.nextInt()
                    )
                    while (usedColors.contains(color)) {
                        color = Color(
                            red = random.nextInt(),
                            green = random.nextInt(),
                            blue = random.nextInt()
                        ) // Generate a new color if it is already used
                    }
                    usedColors.add(color)
                    PieChartData.Slice(Record.category.name, Record.amount.toFloat(), color)
                }

                donutChartData = PieChartData(
                    slices = pieSlices,
                    plotType = PlotType.Donut
                )

                if(pieSlices.isNotEmpty()){
                    composeView.setContent {
                        DountChart(requireContext(), donutChartData)
                    }
                }else{
                    composeView.setContent {
                        errorShowCharts("Too few records to generate Pie Chart")
                    }
                }

            })
        }
        //show Balance chart
        sbtnBalance.setOnClickListener {
            composeView.setContent {
                var lineChartData = mutableListOf<Point>()

                val now = Calendar.getInstance()
                val calendar = setCalendarMonth(now, selectedMonth)
//                calendar.set(Calendar.HOUR_OF_DAY, 0); // Set hours to 0
//                calendar.set(Calendar.MINUTE, 0); // Set minutes to 0
//                calendar.set(Calendar.SECOND, 0); // Set seconds to 0
//                calendar.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0
//                calendar.set(Calendar.DAY_OF_MONTH,1)
                val startOfMonth = calendar.time
                calendar.add(Calendar.MONTH, 1)
                val startOfNextMonth = calendar.time

                val balance = 0.0
                dataViewModel.readMonthWithRecords(startOfMonth, startOfNextMonth).observe(viewLifecycleOwner, Observer{records ->

                    val list = mutableListOf<Pair<Int, Float>>()

                    for( record in records){
                        calendar.time = record.date
                        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                        val amount = if (record.category.type) {
                            +record.amount.toFloat()
                        } else {
                            -record.amount.toFloat()
                        }

                        val existingEntry = list.find { it.first == dayOfMonth }
                        if (existingEntry != null) {
                            val updatedAmount = existingEntry.second + amount
                            list[list.indexOf(existingEntry)] = Pair(dayOfMonth, updatedAmount)
                        } else {
                            list.add(Pair(dayOfMonth, amount))
                        }
                    }

                    list.sortBy { it.first }

//                    val lineChartData = list.map { (day, amount) ->
//                        Point(day.toFloat(), amount)
//                    }

//                    lineChartData = list.map { (day, amount) ->
//                        Point(day.toFloat(), amount)
//                    }
                    var cumulativeBalance = balance

                    for (index in 0 until 31) {
                        lineChartData.add(Point(index.toFloat(), 0f))
                    }

//                    for ((day, amount) in list) {
//                        cumulativeBalance += amount
//                        lineChartData.add(Point(day.toFloat(), cumulativeBalance.toFloat()))
//                    }
                    for ((day, amount) in list) {
                        cumulativeBalance += amount
                        // Update the corresponding item in lineChartData with the updated cumulativeBalance
                        lineChartData[day] = Point(day.toFloat(), cumulativeBalance.toFloat())
                    }

                    if(lineChartData.isNotEmpty() && lineChartData.size >=2){
                        Log.i("DLLM", lineChartData.toString())
                        composeView.setContent {
                            Linechart(lineChartData)
                        }
                    }else{
                        composeView.setContent {
                            errorShowCharts("Too few records to generate Line Chart")
                        }
                    }


                })


            }
        }
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
    }

    @Composable
    private fun DountChart(context: Context, donutChartData: PieChartData){

        val scope = rememberCoroutineScope()


        // Sum of all the values
        val sumOfValues = donutChartData.totalLength

        // Calculate each proportion value
        val proportions = donutChartData.slices.proportion(sumOfValues)
        val pieChartConfig =
            PieChartConfig(
                labelVisible = true,
                strokeWidth = 120f,
                labelColor = Color.Black,
                activeSliceAlpha = .9f,
                isEllipsizeEnabled = true,
                labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
                isAnimationEnable = true,
                chartPadding = 25,
                labelFontSize = 42.sp,
            )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData = donutChartData, 3))
            DonutPieChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                donutChartData,
                pieChartConfig
            ) { slice ->
                Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    private fun Linechart(pointsData: List<Point>) {
        val steps = 5
        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .topPadding(105.dp)
            .steps(pointsData.size - 1)
            .labelData { i -> pointsData[i].x.toInt().toString() }
            .labelAndAxisLinePadding(15.dp)
            .build()
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                // Add yMin to get the negative axis values to the scale
                val yMin = pointsData.minOf { it.y }
                val yMax = pointsData.maxOf { it.y }
                val yScale = (yMax - yMin) / steps
                ((i * yScale) + yMin).formatToSinglePrecision()
            }.build()
        val data = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                )
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines()
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            lineChartData = data
        )
    }

    @Composable
    private fun errorShowCharts(msg: String){
        Text(text = msg)
    }

    private fun setCalendarMonth(calendar: Calendar, selectedMonth: Int): Calendar{
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }




}