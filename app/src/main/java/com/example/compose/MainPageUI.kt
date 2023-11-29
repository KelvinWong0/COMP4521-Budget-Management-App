package com.example.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun MainPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(modifier)
        LineSpacer(modifier)
        HomeStats(modifier)
        LineSpacer(modifier)
        LineSpacer(modifier)
        Logs(modifier)
    }
}

@Composable
fun LineSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(20.dp))
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        text = "KYS",
        color = Color.Black,
        textAlign = TextAlign.Start,
        lineHeight = 1.33.em,
        style = TextStyle(
            fontSize = 12.sp),
        modifier = modifier
    )
}


@Composable
fun HomeStats(modifier: Modifier = Modifier) {
    Row (modifier = modifier){
        Column (modifier = modifier.width(100.dp).padding(end = 10.dp).background(
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(R.drawable.icon_money),
                contentDescription = null
            )

            val tvExpenses = remember { mutableStateOf("-42069") }
            Text(text = tvExpenses.value,
//            // Button or other interaction to update the text
//            Button(onClick = { tvExpenses.value = "New Text" }) {
//                Text("Change Text")
//            }
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
            Text(
                text = "Expense",
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
        }
        Column (modifier = modifier.width(100.dp).padding(end = 10.dp).background(
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(R.drawable.icon_balance),
                contentDescription = null
            )
            val tvBalance = remember { mutableStateOf("42069") }
            Text(text = tvBalance.value,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
            Text(
                text = "Balance",
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
        }
        Column (modifier = modifier.width(100.dp).background(
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(R.drawable.icon_income),
                contentDescription = null
            )
            val tvIncome = remember { mutableStateOf("+42069") }
            Text(text = tvIncome.value,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
            Text(
                text = "Income",
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = modifier)
        }
    }
}

@Composable
fun Logs(modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Text(
            text = "Log1",
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = modifier)
        Text(
            text = "Log2",
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = modifier)
        Text(
            text = "Log3",
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = modifier)
    }
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun MainPagePreview() {
    MainPage(Modifier)
}