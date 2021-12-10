package com.fanketly.accompanist.calendar

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fanketly.accompanist.theme.Blue200
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect

const val TAG = "CalendarTAG"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Calendar(selectDay: Int, move: (Int) -> Unit, dayClick: (Int) -> Unit) {
    //对页面更改做出反应¶
    //每当所选页面发生更改时，都会更新PagerState.currentPage属性。
    val pagerState = rememberPagerState()
    LaunchedEffect(pagerState) {
        // 从读取 currentPage 的快照流中收集
        snapshotFlow { pagerState.currentPage }
            .collect {
                move(it)
            }
    }
    HorizontalPager(
        count = 12, state = pagerState, modifier = Modifier
            .background(Blue200)
//            .paint(
//                painter = painterResource(
//                    id = R.drawable.background
//                ), contentScale = ContentScale.Crop
//            )
            .height(300.dp), verticalAlignment = Alignment.Top
    ) { index ->
        Month(
            month = MonthEnum.of(index + 1),
            selectDay = selectDay,
            dayClick = dayClick
        )
    }
}

/**
 *@param selectDay 动态更新
 *@param dayClick 点击高亮
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Month(
    month: MonthEnum,
    selectDay: Int,
    dayClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 60.dp)
    ) {
        Text(
            text = month.name,
            modifier = Modifier
//                .background(Color.Red)
                .fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Log.i(TAG, "Month: ${month.name} ")
        LazyVerticalGrid(cells = GridCells.Fixed(6)) {
            items(month.length(true)) { index ->
                Day(day = index + 1, selectDay, dayClick)
            }
        }
    }
}

@Composable
fun Day(day: Int, selectDay: Int, dayClick: (Int) -> Unit) {
    Text(text = day.toString(),
        textAlign = TextAlign.Center,
        color = if (day == selectDay) Color.Red else Color.White,
        modifier = Modifier
            .clickable {
                dayClick(day)
            })
}