package com.example.wechat_compose

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.time.Month

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
        Log.i(TAG, "Month: ${Month.values()}")
    }
}