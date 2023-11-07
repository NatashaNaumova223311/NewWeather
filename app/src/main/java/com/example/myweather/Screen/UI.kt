package com.example.myweather.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.ui.theme.BlueWhite
import com.example.myweather.ui.theme.GrayWhite

@Composable
fun MainList(list: List<WeatherModel>, currentday: MutableState<WeatherModel>)
{
    LazyColumn(modifier = Modifier.fillMaxSize())
    {
        itemsIndexed(
            list
        ){
                _, item -> ListItem(item, currentday)
        }
    }
}

@Composable
fun ListItem(item:WeatherModel, currentday: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp).
                clickable {  if (item.Hours.isEmpty()) return@clickable
                    currentday.value = item }
            .background(color = BlueWhite),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().background(GrayWhite),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp)) {
                Text(text = item.Time)
                Text(text = item.Condition, color = Color.White)
            }
            Text(text = item.CurrentTemp.ifEmpty {"${item.MaxTemp} / ${item.MinTemp}" }, color = Color.White, style = TextStyle(fontSize = 25.sp))
            Icon(
                painterResource(id = R.drawable.sunny),
                contentDescription = "img2",
                tint = Color.White
            )

        }
    }
}