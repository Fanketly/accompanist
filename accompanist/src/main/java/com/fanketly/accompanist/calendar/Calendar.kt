package com.fanketly.accompanist.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fanketly.accompanist.TAG
import com.fanketly.accompanist.theme.Blue50
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import java.util.*


private val weekList = listOf("一", "二", "三", "四", "五", "六", "日")

/**
 * @param modifier 样式设置
 * @param onDateSelected 点击监听器
 */
@SuppressLint("ModifierParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier
        .height(250.dp),
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val date = Calendar.getInstance()
    val today = "${date.get(Calendar.YEAR)}${(date.get(Calendar.MONTH) + 1)}${
        date.get(
            Calendar.DAY_OF_MONTH
        )
    }"
    Log.i(TAG, "Today:$today ")
    //对页面更改做出反应¶
    //每当所选页面发生更改时，都会更新PagerState.currentPage属性。
    val pagerState = rememberPagerState(date.get(Calendar.MONTH) + 1)
    var year = date.get(Calendar.YEAR)
    var leapYear = isLeapYear(year)
    val selectDate = remember {
        mutableStateOf(today)
    }
    LaunchedEffect(pagerState) {
        // 从读取 currentPage 的快照流中收集
        snapshotFlow { pagerState.currentPage }
            .collect {
                if (it == 0) {
                    year--
                    leapYear = isLeapYear(year)
                    pagerState.scrollToPage(12)
                } else if (it == 13) {
                    year++
                    leapYear = isLeapYear(year)
                    pagerState.scrollToPage(1)
                }
            }
    }
    Column(modifier = modifier) {
        HorizontalPager(
            count = 14, state = pagerState, verticalAlignment = Alignment.Top
        ) { index ->
            var year2 = year
            val month = when (index) {
                0 -> {
                    year2 -= 1
                    12
                }
                13 -> {
                    year2 += 1
                    1
                }
                else -> index
            }
//            Log.i(TAG, "Date: $year2 $month")
            Month(selectDate, month, year2, today, leapYear, onDateSelected)
        }
    }

}


/**
 *@param selectDate 动态更新
 *@param onDateSelected 点击高亮
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Month(
    selectDate: MutableState<String>,
    month: Int,
    year: Int,
    today: String,
    leapYear: Boolean,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Text(
            text = "${year}年${month}月",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Row {
            weekList.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        LazyVerticalGrid(cells = GridCells.Fixed(7)) {
            items(getWeek(year, month)) {
                Text(text = "")
            }
            items(MonthEnum.of(month).length(leapYear)) { index ->
                Day(
                    day = index + 1,
                    month = month,
                    year = year,
                    today = today,
                    selectDate = selectDate,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}

@Composable
fun Day(
    day: Int,
    month: Int,
    year: Int,
    today: String,
    selectDate: MutableState<String>,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) {
    val date = "$year$month$day"
    Text(
        text = day.toString(),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = if (date == selectDate.value) MaterialTheme.colors.onPrimary else if (date == today) MaterialTheme.colors.primary else Color.Black,
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (date == selectDate.value) MaterialTheme.colors.primary else Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                selectDate.value = date
                onDateSelected(year, month, day)
            }
    )
}

/**
 * 判断是否闰年
 */
private fun isLeapYear(year: Int): Boolean =
    ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)

/**
 * 泰勒公式:获取当天星期数,0为星期一
 */
private fun getWeek(year: Int, month: Int): Int {
    var m = month
    var y = year % 100
    if (month == 1) {
        m = 13
        y -= 1
    } else if (month == 2) {
        m = 14
        y -= 1
    }

    return (1 + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7
}

