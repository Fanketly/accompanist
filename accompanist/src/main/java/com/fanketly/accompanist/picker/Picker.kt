package com.fanketly.accompanist.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fanketly.accompanist.calendar.Calendar
import java.util.*

@Composable
fun DatePickerDialog(
    dismissOnClickOutside: Boolean = true,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val date = Calendar.getInstance()
    val selDate = remember {
        mutableStateOf(
            Date(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH) + 1,
                date.get(Calendar.DAY_OF_MONTH)
            )
        )
    }
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnClickOutside = dismissOnClickOutside)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "选择日期",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = selDate.value.toString(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))
            }
            Calendar { year, month, dayOfMonth ->
                selDate.value = Date(year, month, dayOfMonth)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .defaultMinSize(minHeight = 62.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
//                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "取消",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val s = selDate.value
                        onDateSelected(s.year, s.month, s.day)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = "确定",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

            }
        }
    }
}

//@Composable
//fun CustomCalendarView(onDateSelected: (String) -> Unit) {
//    AndroidView(
//        modifier = Modifier.wrapContentSize(),
//        factory = { context ->
//            CalendarView(ContextThemeWrapper(context, R.style.Widget_Material_CalendarView))
//        },
//        update = { view ->
//            view.minDate = -2209017943000
//            view.maxDate = 4102416000000
//            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
//                onDateSelected("$year-${month + 1}-$dayOfMonth")
//            }
//        }
//    )
//}
