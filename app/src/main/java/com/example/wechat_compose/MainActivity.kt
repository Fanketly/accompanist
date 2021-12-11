package com.example.wechat_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wechat_compose.ui.theme.WeChat_ComposeTheme
import com.example.wechat_compose.viewmodel.MainViewModel
import com.fanketly.accompanist.banner.Banner3D
import com.fanketly.accompanist.calendar.Calendar


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
        Banner3D(R.drawable.background, R.drawable.mid, R.drawable.fore)
//        Banner3D(drawBack = {}, drawMid = {
//            drawImage()
//        }, drawFore = {})
    }
}