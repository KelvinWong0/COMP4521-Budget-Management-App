package com.example.compose.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
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

class FragmentReports : Fragment(R.layout.fragment_report){

    private lateinit var dataViewModel: DataViewModel

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
        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            //Text(text = "Compose created")
            TestDountChart(requireContext())
        }
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
    }
}

@Composable
private fun TestDountChart(context: Context){

    val scope = rememberCoroutineScope()
    //val data = DataUtils.getDonutChartData()
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("CAT 1", 15f, Color(0xFF5F0A87)),
            PieChartData.Slice("CAT 2", 30f, Color(0xFF20BF55)),
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