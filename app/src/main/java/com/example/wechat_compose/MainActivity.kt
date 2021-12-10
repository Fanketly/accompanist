package com.example.wechat_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wechat_compose.ui.theme.WeChat_ComposeTheme
import com.example.wechat_compose.viewmodel.MainViewModel
import com.fanketly.accompanist.calendar.Calendar
import com.fanketly.accompanist.calendar.Month
import com.fanketly.accompanist.calendar.MonthEnum
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeChat_ComposeTheme {
                val viewModel: MainViewModel = viewModel()

                Calendar(selectDay = viewModel.selectDay, move = {
                    viewModel.selectDay = 1
                }) {
                    viewModel.selectDay = it
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeChat_ComposeTheme {
        var selectDay by remember {
            mutableStateOf(1)
        }
        Month(month = MonthEnum.APRIL, selectDay = selectDay) {
            selectDay = it
        }
    }
}