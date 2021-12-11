package com.fanketly.accompanist.banner

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_GAME
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.fanketly.accompanist.TAG
import kotlin.math.abs

/***
 * @param mMaxAnular 最大角度
 * @param maxOffset 最大位移
 *@param backImg 图片Id
 */
@Composable
fun Banner3D(
    backImg: Int,
    midImg: Int,
    foreImg: Int,
    mMaxAnular: Int = 30,
    maxOffset: Int = 30
) {
    val imageBack = ImageBitmap.imageResource(id = backImg)
    val imageMid = ImageBitmap.imageResource(id = midImg)
    val imageFore = ImageBitmap.imageResource(id = foreImg)
    var xDistance by remember { mutableStateOf(0f) }
    var yDistance by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    val sensorManager: SensorManager? =
        ContextCompat.getSystemService(context, SensorManager::class.java)
    val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    //时间
    val dT = SENSOR_DELAY_GAME
    var angularX = 0f
    var angularY = 0f
    var angularZ = 0f
    var x: Float
    var y: Float
    var z: Float
    sensorManager?.registerListener(object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            //X轴角速度 event.values?.get(0)!!
            // 将手机在各个轴上的旋转角度相加
            event?.run {
                //角速度乘以时间，就是转过的角度，直接计算旋转的角度值。
                angularX += (values[0] * dT).toLong()
                angularY += (values[1] * dT).toLong()
                angularZ += (values[2] * dT).toLong()
                //设置x轴y轴最大边界值，
                if (angularY > mMaxAnular) {
                    angularY = mMaxAnular.toFloat()
                } else if (angularY < -mMaxAnular) {
                    angularY = -mMaxAnular.toFloat()
                }

                if (angularX > mMaxAnular) {
                    angularX = mMaxAnular.toFloat()
                } else if (angularX < -mMaxAnular) {
                    angularX = -mMaxAnular.toFloat()
                }
                //依据公式:旋转角度/最大角度 = 平移距离/最大平移距离,反推出 平移距离= 旋转角度/最大角度*最大平移距离
                val xRadio: Float = (angularY / mMaxAnular)
                val yRadio: Float = (angularX / mMaxAnular)
                x = abs(values[0])
                y = abs(values[1])
                z = abs(values[2])
//                Log.i(TAG, "onSensorChanged: $x,$y,$z")
                if (x > y + z) {
//                    xDistance = 0f
                    yDistance = yRadio * maxOffset
                } else if (y > x + z) {
                    xDistance = xRadio * maxOffset
//                    yDistance = 0f
                }

            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }, sensor, dT)
    Log.i(TAG, "Banner3D:init ")
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(1.3f)
    ) {
        translate(-xDistance, -yDistance, block = {
            drawImage(imageBack)
        })
        drawImage(imageMid)
        translate(xDistance, yDistance, block = {
            drawImage(imageFore)
        })
//            drawImage(imageFore, dstSize = IntSize(imageFore.width, h))

    }
}

/***
 * @param mMaxAnular 最大角度
 * @param maxOffset 最大位移
 *@param drawFore 自定义图片属性
 */
@Composable
fun Banner3D(
    drawFore: DrawScope.() -> Unit,
    drawMid: DrawScope.() -> Unit,
    drawBack: DrawScope.() -> Unit,
    mMaxAnular: Int = 30,
    maxOffset: Int = 30
) {
    var xDistance by remember { mutableStateOf(0f) }
    var yDistance by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    val sensorManager: SensorManager? =
        ContextCompat.getSystemService(context, SensorManager::class.java)
    val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    //时间
    val dT = SENSOR_DELAY_GAME
    var angularX = 0f
    var angularY = 0f
    var angularZ = 0f
    var x: Float
    var y: Float
    var z: Float
    sensorManager?.registerListener(object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            //X轴角速度 event.values?.get(0)!!
            // 将手机在各个轴上的旋转角度相加
            event?.run {
                //角速度乘以时间，就是转过的角度，直接计算旋转的角度值。
                angularX += (values[0] * dT).toLong()
                angularY += (values[1] * dT).toLong()
                angularZ += (values[2] * dT).toLong()
                //设置x轴y轴最大边界值，
                if (angularY > mMaxAnular) {
                    angularY = mMaxAnular.toFloat()
                } else if (angularY < -mMaxAnular) {
                    angularY = -mMaxAnular.toFloat()
                }

                if (angularX > mMaxAnular) {
                    angularX = mMaxAnular.toFloat()
                } else if (angularX < -mMaxAnular) {
                    angularX = -mMaxAnular.toFloat()
                }
                //依据公式:旋转角度/最大角度 = 平移距离/最大平移距离,反推出 平移距离= 旋转角度/最大角度*最大平移距离
                val xRadio: Float = (angularY / mMaxAnular)
                val yRadio: Float = (angularX / mMaxAnular)
                x = abs(values[0])
                y = abs(values[1])
                z = abs(values[2])
//                Log.i(TAG, "onSensorChanged: $x,$y,$z")
                if (x > y + z) {
//                    xDistance = 0f
                    yDistance = yRadio * maxOffset
                } else if (y > x + z) {
                    xDistance = xRadio * maxOffset
//                    yDistance = 0f
                }

            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }, sensor, dT)
    Log.i(TAG, "Banner3D:init ")
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(1.3f)
    ) {
        val h = size.height.toInt()
        val w = size.width.toInt()
        translate(-xDistance, -yDistance, block = drawBack)
        drawMid
        translate(xDistance, yDistance, block = drawFore)
    }
}
