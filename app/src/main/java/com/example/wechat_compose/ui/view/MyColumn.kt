package com.example.wechat_compose.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//@Composable
//fun LazyVerticalGrid(){
//    //要分为几列
//    val nColumns = 4
//    //rows 总共几行
//    val rows = (it.size + nColumns - 1) / nColumns
//    LazyColumn() {
//        items(rows) { rowIndex ->
//            Row {
//                for (columnIndex in 0 until nColumns) {
//                    //itemIndex List数据位置
//                    val itemIndex = rowIndex * 2 + columnIndex
//                    if (itemIndex < it.size) {
//                        Box(
//                            modifier = Modifier.weight(1f, fill = true),
//                            propagateMinConstraints = true
//                        ) {
//                            Tj(data = it[itemIndex])
//                        }
//                    } else {
//                        Spacer(Modifier.weight(1f, fill = true))
//                    }
//                }
//            }
//        }
//    }
//
//}