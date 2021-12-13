package com.fanketly.accompanist.banner

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_GAME
import android.util.Log
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.fanketly.accompanist.TAG
import kotlin.math.abs
import kotlin.math.roundToInt

/***
 * @param mMaxAngle 最大角度
 * @param maxOffset 最大位移
 * @param backImg  图片Id
 * @param midImg  图片Id
 * @param foreImg  图片Id
 * @param isHeightProportion true为以高的比例缩放，false为以宽的比例缩放
 */
@Composable
fun Banner3D(
    @DrawableRes backImg: Int,
    @DrawableRes midImg: Int,
    @DrawableRes foreImg: Int,
    isHeightProportion: Boolean = true,
    mMaxAngle: Int = 30,
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
    var angleX = 0f
    var angleY = 0f
//    var angularZ = 0f
    var x: Float
    var y: Float
    var z: Float
    sensorManager?.registerListener(object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            //X轴角速度 event.values?.get(0)!!
            // 将手机在各个轴上的旋转角度相加
            event?.run {
                //角速度乘以时间，就是转过的角度，直接计算旋转的角度值。
                angleX += (values[0] * dT).toLong()
                angleY += (values[1] * dT).toLong()
//                angularZ += (values[2] * dT).toLong()
                //设置x轴y轴最大边界值，
                if (angleY > mMaxAngle) {
                    angleY = mMaxAngle.toFloat()
                } else if (angleY < -mMaxAngle) {
                    angleY = -mMaxAngle.toFloat()
                }

                if (angleX > mMaxAngle) {
                    angleX = mMaxAngle.toFloat()
                } else if (angleX < -mMaxAngle) {
                    angleX = -mMaxAngle.toFloat()
                }
                //依据公式:旋转角度/最大角度 = 平移距离/最大平移距离,反推出 平移距离= 旋转角度/最大角度*最大平移距离
                val xRadio: Float = (angleY / mMaxAngle)
                val yRadio: Float = (angleX / mMaxAngle)
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
    var isInit = false
    val midHeight = imageMid.height
    val midWidth = imageMid.width
    val foreHeight = imageFore.height
    val foreWidth = imageFore.width
    var w = 0
    var h = 0
    var midHeightProportion = 0
    var midWidthProportion = 0
    var foreHeightProportion = 0
    var foreWidthProportion = 0
    Log.i(TAG, "Banner3D:init ")
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(1.3f)
    ) {
        if (!isInit) {
            isInit = true
            h = size.height.toInt()
            w = size.width.toInt()
            if (isHeightProportion) {
                val d1 = midHeight.toDouble() / h
                midHeightProportion = midHeight / d1.roundToInt()
                midWidthProportion = midWidth / d1.roundToInt()
                val d = foreHeight.toDouble() / h
                foreHeightProportion = foreHeight / d.roundToInt()
                foreWidthProportion = foreWidth / d.roundToInt()
            } else {
                val d1 = midWidth.toDouble() / w
                midHeightProportion = midHeight / d1.roundToInt()
                midWidthProportion = midWidth / d1.roundToInt()
                val d = foreWidth.toDouble() / w
                foreHeightProportion = foreHeight / d.roundToInt()
                foreWidthProportion = foreWidth / d.roundToInt()
            }
        }
        Log.i(TAG, "Canvas:h:$h,w:$w ")
        Log.i(TAG, "Mid:h:$midHeight,w:$midWidth h:$midHeightProportion,w:$midWidthProportion")
        Log.i(TAG, "Fore:h:$foreHeight,w:$foreWidth h:$foreHeightProportion,w:$foreWidthProportion")
        translate(-xDistance, -yDistance, block = {
            drawImage(imageBack, dstSize = IntSize(w, h))
        })
        drawImage(
            imageMid,
            dstSize = IntSize(midWidthProportion, midHeightProportion)
        )
        translate(xDistance, yDistance, block = {
            drawImage(
                imageFore,
                dstSize = IntSize(foreWidthProportion, foreHeightProportion),
            )
        })
    }
}

/***
 * @param mMaxAngle 最大角度
 * @param maxOffset 最大位移
 * @param drawFore 自定义图片属性
 * @param drawMid 自定义图片属性
 * @param drawBack 自定义图片属性
 *   h = size.height.toInt()
w = size.width.toInt()可以获取Canvas宽高
 * val imageBack = ImageBitmap.imageResource(id = backImg)
 * 如：drawImage(imageBack)
 */
@Composable
fun Banner3D(
    drawFore: DrawScope.() -> Unit,
    drawMid: DrawScope.() -> Unit,
    drawBack: DrawScope.() -> Unit,
    mMaxAngle: Int = 30,
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
//    var angularZ = 0f
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
//                angularZ += (values[2] * dT).toLong()
                //设置x轴y轴最大边界值，
                if (angularY > mMaxAngle) {
                    angularY = mMaxAngle.toFloat()
                } else if (angularY < -mMaxAngle) {
                    angularY = -mMaxAngle.toFloat()
                }

                if (angularX > mMaxAngle) {
                    angularX = mMaxAngle.toFloat()
                } else if (angularX < -mMaxAngle) {
                    angularX = -mMaxAngle.toFloat()
                }
                //依据公式:旋转角度/最大角度 = 平移距离/最大平移距离,反推出 平移距离= 旋转角度/最大角度*最大平移距离
                x = abs(values[0])
                y = abs(values[1])
                z = abs(values[2])
                if (x > y + z) {
                    yDistance = (angularY / mMaxAngle) * maxOffset
                } else if (y > x + z) {
                    xDistance = (angularX / mMaxAngle) * maxOffset
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
        translate(-xDistance, -yDistance, block = drawBack)
        drawMid()
        translate(xDistance, yDistance, block = drawFore)
    }
}
