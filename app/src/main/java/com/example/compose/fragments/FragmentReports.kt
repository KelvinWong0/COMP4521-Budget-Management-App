package com.example.compose.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import co.yml.charts.ui.piechart.utils.proportion
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.ListAdapter
import java.time.Instant
import java.util.Calendar
import java.util.Date
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
        //Default show Expense chart
        composeView.setContent {
            val usedColors = mutableSetOf<Color>()
            var recordList: List<Record>
            var pieSlices:  List<PieChartData.Slice> = emptyList()
            var donutChartData: PieChartData
            var launchChart: Boolean = false

            dataViewModel.readAllExpense.observe(viewLifecycleOwner, Observer{records ->
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
                    Log.i("Fecthed Record", Record.category.name)
                    Log.i("RNG Color", color.toString())
                    PieChartData.Slice(Record.category.name, Record.amount.toFloat(), color)
                }

                donutChartData = PieChartData(
                    slices = pieSlices,
                    plotType = PlotType.Donut
                )

                if(pieSlices.isNotEmpty()){
                    composeView.setContent {
                        ExpenseDountChart(requireContext(), donutChartData)
                    }
                }

            })
        }
        // Switch between Charts
        //show Expense chart
        sbtnExpense.setOnClickListener {
            val usedColors = mutableSetOf<Color>()
            var recordList: List<Record>
            var pieSlices:  List<PieChartData.Slice> = emptyList()
            var donutChartData: PieChartData
            var launchChart: Boolean = false

            dataViewModel.readAllExpense.observe(viewLifecycleOwner, Observer{records ->
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
                    Log.i("Fecthed Record", Record.category.name)
                    Log.i("RNG Color", color.toString())
                    PieChartData.Slice(Record.category.name, Record.amount.toFloat(), color)
                }

                donutChartData = PieChartData(
                    slices = pieSlices,
                    plotType = PlotType.Donut
                )

                if(pieSlices.isNotEmpty()){
                    composeView.setContent {
                        ExpenseDountChart(requireContext(), donutChartData)
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
            val refDate = Date(Instant.now().toEpochMilli())
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0); // Set hours to 0
            calendar.set(Calendar.MINUTE, 0); // Set minutes to 0
            calendar.set(Calendar.SECOND, 0); // Set seconds to 0
            calendar.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0
            calendar.set(Calendar.DAY_OF_MONTH,1)
            Log.i("Month", calendar.time.toString())
            val startOfMonth = calendar.time
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = calendar.time

            dataViewModel.readDatesWithRecordsByType(startOfMonth, startOfNextMonth ,true).observe(viewLifecycleOwner, Observer{ records ->
                recordList = records
                Log.i("Bruh", recordList.toString())
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
                    Log.i("Fecthed Record", Record.category.name)
                    Log.i("RNG Color", color.toString())
                    PieChartData.Slice(Record.category.name, Record.amount.toFloat(), color)
                }

                donutChartData = PieChartData(
                    slices = pieSlices,
                    plotType = PlotType.Donut
                )

                if(pieSlices.isNotEmpty()){
                    composeView.setContent {
                        ExpenseDountChart(requireContext(), donutChartData)
                    }
                }

            })
        }
        //show Balance chart
        sbtnBalance.setOnClickListener {
            composeView.setContent {
                IncomeDountChart(requireContext())
            }
        }
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
    }

    @Composable
    private fun ExpenseDountChart(context: Context, donutChartData: PieChartData){

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
    private fun IncomeDountChart(context: Context){

        val scope = rememberCoroutineScope()
        //val data = DataUtils.getDonutChartData()
        val donutChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice("CAT 1", 30f, Color(0xFF5F0A87)),
                PieChartData.Slice("CAT 2", 20f, Color(0xFF20BF55)),
                PieChartData.Slice("CAT 3", 40f,  Color(0xFFEC9F05)),
                PieChartData.Slice("CAT 4", 10f, Color(0xFFF53844))
            ),
            plotType = PlotType.Donut
        )
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
}