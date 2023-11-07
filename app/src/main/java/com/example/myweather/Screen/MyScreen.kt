package com.example.myweather.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.ui.theme.BlueWhite
import com.example.myweather.ui.theme.GrayWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun MyCard(currentday: MutableState<WeatherModel>) {
    Column(
        modifier = Modifier
            .padding(5.dp)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueWhite), shape = RoundedCornerShape(10.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GrayWhite),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = currentday.value.Time,
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    Icon(
                        painterResource(id = R.drawable.sunny),
                        contentDescription = "img2",
                        tint = Color.White
                    )
                }
                Text(text = currentday.value.City, style = TextStyle(fontSize = 24.sp), color = Color.White)
                Text(
                    text = if(currentday.value.CurrentTemp.isNotEmpty())
                        currentday.value.CurrentTemp.toFloat().toInt().toString() + "ºC"
                    else currentday.value.MaxTemp.toFloat().toInt().toString() +
                            "ºC/${currentday.value.MinTemp.toFloat().toInt()}ºC"
                    ,
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )
                Text(text = currentday.value.Condition, style = TextStyle(fontSize = 16.sp), color = Color.White)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(id = R.drawable.search),
                            contentDescription = "img3",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${currentday.value.MaxTemp.toFloat().toInt()}C / ${currentday.value.MinTemp.toFloat().toInt()}C",
                        style = TextStyle(fontSize = 16.sp, color = Color.White)
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(id = R.drawable.baseline_cloud_sync_24),
                            contentDescription = "img4", tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TableLayout(daysList: MutableState<List<WeatherModel>>, currentday: MutableState<WeatherModel>) {
    val tabList = listOf("Hours", "Days")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(5.dp))
    )
    {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState, pos
                    )
                )
            }, backgroundColor = BlueWhite, contentColor = Color.White)
        {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = text) }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) {
            index -> val list = when(index){
                0 -> getWeatherByHours(currentday.value.Hours)
            1 -> daysList.value
            else -> daysList.value
            }
            MainList(list, currentday)

        }
    }
}
private fun getWeatherByHours(hours: String): List<WeatherModel>{
    if (hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for (i in 0 until hoursArray.length()){
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                "",
                item.getString("time"),
                item.getString("temp_c").toFloat().toInt().toString() + "ºC",
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""
            )
        )
    }
    return list
}