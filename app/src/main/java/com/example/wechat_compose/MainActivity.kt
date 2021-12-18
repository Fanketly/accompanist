package com.example.wechat_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wechat_compose.ui.theme.WeChat_ComposeTheme
import com.example.wechat_compose.viewmodel.MainViewModel
import com.fanketly.accompanist.calendar.Calendar
import com.fanketly.accompanist.picker.DatePickerDialog


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeChat_ComposeTheme {
////                Banner3D(R.drawable.background, R.drawable.mid, R.drawable.fore,false)
                val openDialog = remember { mutableStateOf(true) }
                if (openDialog.value)
                    DatePickerDialog(false, onDateSelected = { year, month, dayOfMonth ->
                        Toast.makeText(this, "$year-$month-$dayOfMonth", Toast.LENGTH_SHORT).show()
                    }, onDismissRequest = {
                        openDialog.value = false
                    })
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeChat_ComposeTheme {

    }
}